package com.leadtone.mas.bizplug.common.im;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;



/**
 * 文件导入的入口
 * 
 * 本类对文件导入的具体实现进行了隐藏，用户只需要输入文件路径就可以获取文件内容，而不必关心具体的实现
 * 
 * @author licb-leadtone
 * 
 *         Jan 12, 2011 2:47:27 PM
 */
public class DataImport {
	/**
	 * 日志
	 */
	private Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 文件导出的超类
	 */
	private FileImport fi = null;
	private File file;

	public DataImport() {

	}

	public DataImport(File file) {
		this.setFile(file);
	}

	public DataImport(String fileName) {
		File file = new File(fileName);
		this.setFile(file);
	}

	public String[][] getFileContent(boolean isHaveCaption) {
		return this.getFileContent(null,isHaveCaption);
	}
	
	public String[][] getFileContent(Integer[] colsIndexes,boolean isHaveCaption) {
		String[][] content = null;

		content = fi.getFileContent(colsIndexes, isHaveCaption);
		return content;
	}

	/**
	 * 根据用户传入的文件和内容所在的列获取文件的内容列表并返回
	 * 
	 * @param file
	 *            要导入的文件
	 * @param columns
	 *            要读取的文件的列。形式如{"A","C"}，对应文件中的第一列和第三列
	 * @return 文件的内容列表，是一个二维数组，第一位代表行，第二维代表列
	 */
	public String[][] getFileContent(Integer[] colsIndexes) {
		String[][] content = null;

		content = fi.getFileContent(colsIndexes);
		return content;
	}

	/**
	 * 返回文件中的记录去重后的结果，目前还不能按照用户输入的重复字段进行去重，只能将所有字段相同的重复字段去除
	 * 
	 * @param colIndexes
	 *            要读取的文件中的列
	 * @param duplicateColIndexes
	 *            唯一确定一条记录的列的下标集合。如果两条记录中的这几个字段相同，那么这两条记录就是重复记录(这个字段暂时没用)
	 * @return
	 */
	public Set<Map<Integer, String>> getFileContentWithoutDuplicate(
			Integer[] colIndexes, Integer[] duplicateColIndexes) {
		String[][] result = this.getFileContent(colIndexes);
		if (result == null) {
			logger.warn("文件内容为空！");
			return null;
		}

		/**
		 * 采用set存储文件记录，达到去重的目的
		 * 
		 * 将String[][]中的所有内容放入Set中，去除所有字段都相同的重复记录
		 */
		Set<Map<Integer, String>> content = new HashSet<Map<Integer, String>>();
		Map<Integer, String> rowContent = null;
		for (int row = 0; row < result.length; row++) {// 遍历每行
			rowContent = new HashMap<Integer, String>();
			Integer col = 0;
			for (String temp : result[row]) {// 遍历取出行中的每列的数据
				rowContent.put(col++, temp);
			}

			content.add(rowContent);
		}

		return content;
	}

	/**
	 * 获取文件类型
	 * 
	 * @param fileName
	 *            文件全路径
	 * @return 文件类型
	 */
	private String getFileType(String fileName) {
		if (null == fileName || "".equals(fileName)
				|| fileName.indexOf(".") == -1) {
			logger.error("文件名有误，不能获得文件类型, fileName=" + fileName);
			return null;
		}

		return fileName.substring(fileName.lastIndexOf(".") + 1,
				fileName.length());
	}

	/**
	 * 根据传入的文件获取文件类型
	 * 
	 * @param file
	 *            要读取的文件
	 * @return
	 */
	private String getFileType(File file) {
		String fileName = file.getName();
		return this.getFileType(fileName);
	}

	/**
	 * 进行初始化工作
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
		if (this.file == null || !this.file.isFile()) {
			logger.error("文件为NULL或传入的文件不是普通文件！");
		}

		String fileType = this.getFileType(this.file);
		if ("xls".equals(fileType) || "xlsx".equals(fileType)) {
			this.fi = new ExcelImport(this.file);
		} else if ("txt".equals(fileType) || "csv".equals(fileType)) {
			this.fi = new TxtAndCsvImport(this.file);
		} else {
			logger.error("不支持的文件类型, fileName=" + this.file.getName()
					+ ", 文件内容返回NULL！");
		}
	}

	/**
	 * 获得文件的列的数组
	 * 
	 * @return
	 */
	public String[] getFileCols() {
		String[] result = null;
		if (this.file == null) {
			logger.error("文件不存在! this.file=" + this.file);
		} else if (this.fi == null) {
			logger.error("不能正确的解析该文件！ this.file=" + this.file);
		}

		result = this.fi.getColumnsInArray();
		return result;
	}
}
