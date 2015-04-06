package com.leadtone.mas.bizplug.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 读取CSV文件和TXT文件
 * 
 * 注意：文件格式为 1.每行为一条记录 2.每个字段之间采用半角逗号作为分隔符 3.如果有复杂类型，如内容中包含有逗号的类型，复杂类型的值采用半角逗号包含
 * 
 * 本类中sheetNum没有实际的作用。txt文件和csv文件本来就是一个工作区间的
 * 
 * @author admin
 * 
 */
public class TxtAndCsvImport implements FileImport {
	private InputStream is;
	private BufferedReader reader;
	private File file;
	/**
	 * 暂时不用Set返回文件的内容
	 */
	private String[][] csvFileContent;
	private static String[] letters = new String[] { "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };

	public TxtAndCsvImport(File file) {
		this.file = file;
		try {
			is = new FileInputStream(this.file);
			reader = new BufferedReader(new InputStreamReader(is));

			/**
			 * 首先读取出文件的内容，后面在处理其格式等问题
			 */
			this.readFileFirst();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 首先读取出csv文件的内容
	 * 
	 * 采用commons-csv解析csv文件
	 * 
	 * CSVStrategy的三个参数依次是 分隔符、复杂类型包含符号、注释标识
	 */
	public void readFileFirst() {
		try {
			this.csvFileContent = getFileData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[][] getFileData() throws IOException{
		List<String[]> list = new ArrayList<String[]>();
		BufferedReader reader = new BufferedReader(new FileReader(this.file));
		String tmpString = null;
		while((tmpString = reader.readLine())!=null){
			if( StringUtils.isBlank(tmpString)){
				continue;
			}
			String[] t = new String[2];
			if( tmpString.contains(",")){
				t = tmpString.split(",", 2);
			}
			else{
				t[0] = tmpString;
				t[1] = "";
			}
			list.add(t);
		}
		String[][] result = new String[list.size()][2];
		for( int i=0;i<list.size();i++){
			result[i] = list.get(i);
		}
		return result;
	}
	
	/**
	 * 返回类型采用二维数组，接收的参数也采用数组，从而放弃了集合的使用
	 * 
	 * 如果用户没有指定要获取的列，那么返回所有的内容
	 * 
	 * 2011.3.24日修改：System.arraycopy方法，使用错误，该方法实现数组复制的同时不会减少数组的长度！
	 * 
	 * @param colsIndexes
	 * @return
	 */
	@Override
	public String[][] getFileContent(Integer[] colIndexes, boolean isHaveCaption) {
		String[][] result = null;
		String[][] arr = null;

		/**
		 * 取出用户指定的列的内容，放入新的二维数组中并且返回
		 */
		if (colIndexes != null && colIndexes.length > 0) {
			result = new String[this.csvFileContent.length][colIndexes.length];

			int col = 0;
			for (Integer colIndex : colIndexes) {
				int row = 0;
				if (isHaveCaption) {
					row = 1;
				}
				for (; row < this.csvFileContent.length; row++) {
					result[row][col] = this.csvFileContent[row][colIndex];
				}
				++col;
			}

			if (isHaveCaption) {
				arr = new String[result.length][];
				System.arraycopy(result, 1, arr, 0, result.length - 1);
			}
			else{
				arr = new String[result.length][];
				System.arraycopy(result, 0, arr, 0, result.length);
			}
		} else {
			if (!isHaveCaption)
				arr = this.csvFileContent;
			else {
				arr = new String[this.csvFileContent.length][];
				System.arraycopy(this.csvFileContent, 1, arr, 0,
						this.csvFileContent.length - 1);
			}
		}

		return arr;
	}

	@Override
	public String[][] getFileContent(Integer[] colsIndexes) {
		return this.getFileContent(colsIndexes, true);
	}

	@Override
	public String[][] getFileContent(Integer[] colsIndexes, int sheetNum,
			boolean isHaveCaption) {
		return this.getFileContent(colsIndexes, true);
	}

	@Override
	public String[] getColumnsInArray() {
		int maxColIndex = 0;

		for (String[] row : this.csvFileContent) {
			maxColIndex = maxColIndex > row.length ? maxColIndex : row.length;
		}

		/**
		 * 生成CSV文件列表数组，内容如["A","B","C"]
		 */
		String[] cols = null;
		if (maxColIndex > 0) {
			cols = new String[maxColIndex];
			for (int i = 0; i < maxColIndex; i++) {
				cols[i] = letters[i];
			}
		}

		return cols;
	}

	@Override
	public String[] getColumnsInArray(int sheetIndex) {
		return this.getColumnsInArray();
	}

	public static void main(String[] args) {
		TxtAndCsvImport txtImport = new TxtAndCsvImport(new File("D:/x.txt"));

		String[][] xx = txtImport.getFileContent(new Integer[] { 0 }, false);
		for (int i = 0; i < xx.length; i++) {
			System.out.println(xx[i][0]);
		}

	}
}
