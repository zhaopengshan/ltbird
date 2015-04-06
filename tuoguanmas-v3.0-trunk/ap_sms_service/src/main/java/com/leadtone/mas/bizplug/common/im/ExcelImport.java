package com.leadtone.mas.bizplug.common.im;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.opensymphony.xwork2.util.logging.Logger;


/**
 * 读取Excel文件,支持.xls和.xlsx格式
 * 
 * 返回文件内容的集合使用Set，是为了达到去重的目的
 * 
 * @author licb-leadtone
 * 
 *         Jan 12, 2011 2:42:03 PM
 */
public class ExcelImport implements FileImport {
	private InputStream is;
	private Workbook w;
	private Sheet sheet;
	private File file;
	private String[][] fileContent;
	/**
	 * 该sheet的记录条数
	 */
	private int phyRowNum = 0;
	/**
	 * 该记录的记录最大列数
	 */
	private int phyColNum = 0;

	private static String[] letters = new String[] { "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };

	public ExcelImport(File file) {
		this.file = file;
		try {
			this.is = new FileInputStream(file);
			this.w = WorkbookFactory.create(this.is);
			/**
			 * 默认在实例化的时候读取第一张sheet，如果用户需要读取其他的sheet，再掉用该方法即可。
			 */
			this.readFileFirst(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				this.is = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void readFileFirst(int sheetNum) {
		this.sheet = this.w.getSheetAt(sheetNum);
		Iterator<Row> rowNumIt = sheet.rowIterator();
		List<Row> rowList = new ArrayList<Row>();
		while(rowNumIt.hasNext()){
			Row row = (Row) rowNumIt.next();
			if(row!=null){
			    rowList.add(row);
			}
		}
		this.phyRowNum = rowList.size();
		this.phyColNum = this.getMaxCol(this.sheet);

		Cell cell = null;
		Iterator<Row> rowIt = rowList.iterator();
		this.fileContent = new String[this.phyRowNum][this.phyColNum];
		for(int i = 0;rowIt.hasNext();i++){
			Row row = (Row) rowIt.next();
			if(row!=null){
//				Iterator<Cell> cellIt = row.cellIterator();
//				for(int j=0;cellIt.hasNext();j++){
//					cell = (Cell) cellIt.next();
//					if(cell!=null){
//						this.fileContent[i][j] = this.getCellValue(cell);
//					}else{
//						this.fileContent[i][j] = "无";
//					}
//				}
				for (int j = 0; j < this.phyColNum; j++) {
					cell = row.getCell(j);
					this.fileContent[i][j] = this.getCellValue(cell);
				}
			}else{
				i--;
			}
		}
	}

	@Override
	public String[][] getFileContent(Integer[] colIndexes) {

		return this.getFileContent(colIndexes, true);
	}

	@Override
	public String[][] getFileContent(Integer[] colIndexes, boolean isHaveCaption) {

		return this.getFileContent(colIndexes, 0, isHaveCaption);
	}

	@Override
	public String[][] getFileContent(Integer[] colIndexes, int sheetNum,
			boolean isHaveCaption) {
		String[][] result = null;
		String[][] arr = null;

		/**
		 * 首先判断用户选择的Sheet存在不存在，不存在的话直接返回NULL
		 */
		if (sheetNum > this.getSheetNum() - 1
				|| (this.w.getSheetAt(sheetNum).getPhysicalNumberOfRows() <= 0)) {
			return result;
		} else if (sheetNum != 0) {
			this.readFileFirst(sheetNum);
		}

		// 如果含有标题行，那么从第二行开始读取文件内容。否则从第一行开始读取
		if (colIndexes != null) {
			result = new String[this.phyRowNum][colIndexes.length];

			int i = 0;
			if (isHaveCaption) {
				i = 1;
			}
			for (; i < this.phyRowNum; i++) {
				result[i] = new String[7];
				for (int j = 0; j < colIndexes.length; j++) {
					result[i][j] = this.fileContent[i][colIndexes[j]];
				}
			}

			/**
			 * 这里利用的数组的自动复制功能，但是自动复制不会减少数组的长度
			 */
			if (isHaveCaption) {
				arr = new String[result.length][];
				System.arraycopy(result, 1, arr, 0, result.length - 1);
			}
		} else {
			if (!isHaveCaption)
				arr = this.fileContent;
			else {
				try {
					arr = new String[this.fileContent.length-1][];
					System.arraycopy(this.fileContent, 1, arr, 0,
							this.fileContent.length - 1);
				} catch (Exception e) {
					System.out.println("文件复制异常！ " + e.getMessage());
				}
			}
		}
		return arr;
	}

	/**
	 * 根据单元格的类型，返回其内容。数值型和布尔型的值转化成String类型的值返回；空值和错误类型的数值直接返回NULL
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellValue(Cell cell) {
		String result = null;

		if (cell == null) {
			return result;
		}

		/**
		 * 根据Cell的类型返回Cell的值，Blank和Error类型的值直接返回NULL
		 */
		switch (cell.getCellType()) {
		case (Cell.CELL_TYPE_NUMERIC):// 数值型--转化成字符串类型
                    //added by guo 处理日期格式单元格
                        if(HSSFDateUtil.isCellDateFormatted(cell)) {
                            double d=cell.getNumericCellValue();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date date = HSSFDateUtil.getJavaDate(d);
                            result = sdf.format(date);
                            break;
                        }
			result = new DataFormatter().formatCellValue(cell);
			break;
		case (Cell.CELL_TYPE_STRING):// 字符串
			result = cell.getStringCellValue();
			break;
		case (Cell.CELL_TYPE_FORMULA):// 表达式
			result = cell.getCellFormula();
			break;
		case (Cell.CELL_TYPE_BOOLEAN):// 布尔型--转化成字符串型
			result = new DataFormatter().formatCellValue(cell);
			break;
		case (Cell.CELL_TYPE_BLANK):// 空值
			break;
		default:// CELL_TYPE_ERROR(byte)，错误类型和其他类型
			break;
		}

		return result;
	}

	@Override
	public String[] getColumnsInArray() {
		return this.getColumnsInArray(0);
	}

	@Override
	public String[] getColumnsInArray(int sheetIndex) {
		String[] cols = null;

		// 首先判断用户选择的Sheet存在不存在，不存在的话直接返回NULL
		if (sheetIndex > this.getSheetNum() - 1) {
			return cols;
		}
		if (sheetIndex != 0) {
			this.readFileFirst(sheetIndex);
		}

		/**
		 * 生成excel文件列数组，内容如["A","B","C"]
		 */
		if (this.phyColNum > 0) {
			cols = new String[this.phyColNum];
			for (int i = 0; i < this.phyColNum; i++) {
				cols[i] = letters[i];
			}
		}
		return cols;
	}

	/**
	 * 获取某一个sheet的最大列下标
	 * 
	 * poi不提供直接的方法，需要遍历每个sheet中的每行，将每行的最大列下标值比较，获取该sheet的最大列下标值。
	 * 
	 * @param sheet
	 * @return
	 */
	public int getMaxCol(Sheet sheet) {
		int maxColumnNo = 0;
		Row row = null;

		if (sheet.getPhysicalNumberOfRows() <= 0) {
			return -1;
		}

		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			if(row!=null){
			maxColumnNo = maxColumnNo > row.getLastCellNum() ? maxColumnNo
					: row.getLastCellNum();
			}
		}

		return maxColumnNo;
	}

	/**
	 * 获取表格的Sheet的数量
	 * 
	 * @return
	 */
	public int getSheetNum() {
		return this.w.getNumberOfSheets();
	}
	
	public static void testExcel()
	{
		try {
			InputStream inp;
			//inp = new FileInputStream("d:\\customer.xls");
			//inp = new FileInputStream("d:\\customer.xls");
			String strFile ="D:\\software\\eclipsembn3\\eclipse\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\mbs_okolehao\\uploads\\1322797071432.xlsx";
			File ss = new File(strFile);
			inp = new FileInputStream(ss);
			Workbook wb = WorkbookFactory.create(inp);
			Sheet sheet = wb.getSheetAt(0);
	
			for (Iterator rit = sheet.rowIterator(); rit.hasNext();) {
				// 迭代行
				Row row = (Row) rit.next();
				// 迭代单元格
				for (Iterator cit = row.cellIterator(); cit.hasNext();) {
					Cell cell = (Cell) cit.next();
					System.out.print(cell.getRowIndex() + ":"
							+ cell.getColumnIndex() + " ");
					// 打印单元格内的数据
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING:
						System.out.println(cell.getRichStringCellValue()
								.getString());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell)) {
							System.out.println(cell.getDateCellValue());
						} else {
							System.out.println(cell.getNumericCellValue());
						}
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.println(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_FORMULA:
						System.out.println(cell.getCellFormula());
						break;
					default:
						System.out.println();
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		testExcel();
		System.exit(0);
	}


}
