package com.leadtone.mas.bizplug.common.ex.export;

import org.apache.log4j.Logger;




/**
 * 将传入的内容导出到一个新的Excel文件中
 * 
 * 传入内容集合、文件名、路径等信息
 * 
 * 输出生成的新表格地址
 * 
 * @author licb-leadtone
 * 
 *         Jan 12, 2011 2:42:25 PM
 */
public class DataExport {
	private FileExport fe;
	public static String EXCEL_XLS = ".xls";
	public static String EXCEL_XLSX = ".xlsx";
	public static String CSV = "csv";
	private Logger logger = Logger.getLogger(this.getClass());

	public DataExport() {

	}

	/**
	 * 输出文件内容至Excel表格或者CSV文件
	 * 
	 * @param directory
	 *            新文件的目录。可以为空，生成的新文件会保存在默认路径下
	 * @param fileName
	 *            新文件的文件名，包含扩展名。可以为空，生成的新文件会使用随机生成的文件名
	 * @param content
	 *            要输出的文件内容。可以为空，程序将生成一个空文件
	 * @param cols
	 *            文件标题数组。可以为空，那么生成的文件将没有标题行
	 * @param fileType
	 *            要导出的文件类型。不可以为空，默认是DataExport.EXCEL类型
	 * @return 生成的新文件的完整路径
	 */
	public String exportToFile(String directory, String fileName,
			String[][] content, String[] cols, String fileType) {
		String filePath = null;
		if (DataExport.EXCEL_XLS.equals(fileType) ||DataExport.EXCEL_XLSX.equals(fileType)) {
			fe = new ExcelExport(fileType);
		} else if (DataExport.CSV.equals(fileType)) {
			fe = new CsvAndTxtExport();
		} else {
			logger.error("不支持的写入文件类型，数据将默认被导入到Excel文件中！ fileType=" + fileType);
			fe = new ExcelExport();
		}

		filePath = fe.exportToFile(directory, fileName, content, cols);
		return filePath;
	}

	/**
	 * 默认将内容导出到一个excel文件中
	 * 
	 * 文件将保存在系统默认的路径下
	 * 
	 * @param content
	 *            文件内容，一个二维数组
	 * @param cols
	 *            导出文件的标题行
	 * @param fileType
	 *            导出文件类型，Excel类型或者CSV类型
	 * @return 返回生成的文件的完整路径
	 */
	public String exportToFile(String[][] content, String[] cols,
			String fileType) {
		return this.exportToFile(null, null, content, cols, fileType);
	}

	/**
	 * 默认将内容导出到一个excel文件中
	 * 
	 * 文件将保存在系统默认的路径下。默认导出的文件类型是Excel文件
	 * 
	 * @param content
	 *            文件内容，一个二维数组
	 * @param cols
	 *            导出文件的标题行
	 * @return 返回生成的文件的完整路径
	 */
//	public String exportToFile(String[][] content, String[] cols) {
//		return this.exportToFile(null, null, content, cols, DataExport.);
//	}

	/**
	 * 默认将内容导出到一个excel文件中
	 * 
	 * 文件将保存在系统默认的路径下。而且没有标题行，默认导出的文件类型是Excel文件
	 * 
	 * @param content
	 *            文件内容，一个二维数组
	 * @return 返回生成的文件的完整路径
	 */
//	public String exportToFile(String[][] content) {
//		return this.exportToFile(null, null, content, null, DataExport.EXCEL);
//	}
}
