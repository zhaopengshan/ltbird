package com.leadtone.mas.bizplug.common.ex.export;

/**
 * 导出内容到新文件接口
 * 
 * 可以将要导出的内容导出到一个新的excel或者csv文件
 * 
 * @author licb-leadtone
 * 
 * Jan 17, 2011 10:21:11 AM
 */
public interface FileExport {
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
	 * @return 生成的新文件的完整路径
	 */
	String exportToFile(String directory, String fileName, String[][] content,
			String[] cols);

	void setDirectory(String directory);

	void setFileName(String fileName);
}
