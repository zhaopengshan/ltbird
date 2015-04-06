package com.leadtone.mas.bizplug.common.im;

/**
 * 导出文件内容接口
 * 
 * @author licb-leadtone
 * 
 * Jan 12, 2011 3:09:02 PM
 */
public interface FileImport {

	/**
	 * 以二维数组的形式返回文件的内容，默认认为该文件的第一行是标题行，即第一行不会作为文件的内容被读取
	 * 
	 * 
	 * @param colsIndexes
	 *            要读取的文件的列
	 * @return
	 */
	String[][] getFileContent(Integer[] colsIndexes);

	/**
	 * 以二维数组的形式返回文件的内容
	 * 
	 * 
	 * @param colsIndexes
	 *            要读取的文件的列
	 * @param isHaveCaption
	 *            是否包含标题行
	 * @return
	 */
	String[][] getFileContent(Integer[] colsIndexes, boolean isHaveCaption);

	/**
	 * 以二维数组的形式返回文件的内容
	 * 
	 * 当读取CSV文件的时候sheetNum是没有作用的
	 * 
	 * @param colsIndexes
	 *            要读取的文件的列
	 * @param sheetNum
	 *            要读取的文件sheet页码
	 * @param isHaveCaption
	 *            是否包含标题行
	 * @return
	 */
	String[][] getFileContent(Integer[] colsIndexes, int sheetNum,
			boolean isHaveCaption);

	/**
	 * 获取文件的列数。例如，某个文件有三列，那么返回数组{"A","B","C"}
	 * 
	 * 不指定Sheet的时候，默认读取第一个Sheet
	 * 
	 * @return 以数组的形式返回文件的列数
	 */
	String[] getColumnsInArray();

	/**
	 * 获取文件的列数。例如，某个文件有三列，那么返回数组{"A","B","C"}
	 * 
	 * @param i
	 *            要读取的sheet的页码，从0开始
	 * 
	 * @return 以数组的形式返回文件的列数
	 */
	String[] getColumnsInArray(int sheetIndex);
}
