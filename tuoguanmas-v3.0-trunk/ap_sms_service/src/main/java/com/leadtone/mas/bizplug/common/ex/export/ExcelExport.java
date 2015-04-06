package com.leadtone.mas.bizplug.common.ex.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 输出内容至Excel表格，生成的表格是03格式的表格
 * 
 * @author licb-leadtone
 * 
 *         Jan 17, 2011 10:28:22 AM
 */
public class ExcelExport implements FileExport {
	private Logger logger = Logger.getLogger(this.getClass());
	private String defaultExportDirectory = "F:" + File.separator
			+ "exportdata" + File.separator + "excel" + File.separator;
	private String fileName;
	private Workbook wb;
	private Sheet sheet;
	private OutputStream os;
	
	private  String strFileType = "";

	public ExcelExport() {
	}
	public ExcelExport(String strFileType) {
		this.strFileType = strFileType ;
	}

	@Override
	public String exportToFile(String directory, String fileName,
			String[][] content, String[] cols) {
		this.setDirectory(directory);
		this.setFileName(fileName);

		/**
		 * 判断文件名称是不是重复，如果用户传入的文件名和以前的文件重复，那么重新生成一个文件
		 */
		new File(this.defaultExportDirectory).mkdirs();
		String filePath = this.defaultExportDirectory + this.fileName;
		while (new File(filePath).exists()) {
			this.setFileName(null);
			filePath = this.defaultExportDirectory + this.fileName;
		}

		try {
			this.os = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if(strFileType.equals(DataExport.EXCEL_XLS))
			this.wb = new HSSFWorkbook();
		else if(strFileType.equals(DataExport.EXCEL_XLSX))
			this.wb = new XSSFWorkbook();
		this.sheet = wb.createSheet("1");
		

		Row row = null;
		Cell cell = null;
		int rowNum = 0;

		/**
		 * 首先写入文件的第一列是文件的标题行
		 */
		if (cols != null && cols.length > 0) {
			row = this.sheet.createRow(rowNum++);
			for (int i = 0; i < cols.length; i++) {
				cell = row.createCell(i, Cell.CELL_TYPE_STRING);
				cell.setCellValue(cols[i]);
			}
		}

		/**
		 * 然后再写入数据
		 */
		/*
		 * PAN-Z-G
		 * 解决大数据量时无法导出BUG
		 */
		 int bookNum=1;
		if (content != null && content.length > 0) {
			for (int i = 0; i < content.length; i++) {// 遍历写入每一行数据
				if( i>60000*bookNum){
					this.sheet = wb.createSheet(String.valueOf(++bookNum)); 
					rowNum=0;
				}
				row = this.sheet.createRow(rowNum++);
				if (content[i] == null)
					continue;

				for (int j = 0; j < content[i].length; j++) {// 遍历写入每一列数据
					if (content[i][j] == null)
						continue;

					cell = row.createCell(j, Cell.CELL_TYPE_STRING);
					cell.setCellValue(content[i][j]);
				}
			}
		}

		try {
			wb.write(os);
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.warn("生成新文件异常,message= " + e.getMessage());
		}

		return filePath;
	}

	@Override
	public void setDirectory(String directory) {
		if (directory == null) {
			logger.error("导出文件目录为空，文件将导出至默认目录下！");
			return;
		}

		this.defaultExportDirectory = directory;
	}

	@Override
	public void setFileName(String fileName) {
		if (fileName == null) {
			logger.error("导出文件名称为空，文件名称将随机生成！");
			this.fileName = Calendar.getInstance().getTime().getTime() + ".xls";
			return;
		}

		this.fileName = fileName;
	}
	public static void main(String[] args){
		String[][] content = new String[6][4];
		content[0][0] = "00000";
		content[1][0] = "10000";
		content[2][0] = "20000";
		content[3][0] = "30000";
		content[4][0] = "40000";
		String[] cols = new String[5];
		cols[0] = "cols0";
		cols[1] = "cols1";
		cols[2] = "cols2";
		cols[3] = "cols3";
		cols[4] = "cols4";
		
		new ExcelExport().exportToFile("", "test", content, cols);
	}
}
