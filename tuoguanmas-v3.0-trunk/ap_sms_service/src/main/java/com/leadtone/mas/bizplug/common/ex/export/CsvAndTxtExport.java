package com.leadtone.mas.bizplug.common.ex.export;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import org.apache.log4j.Logger;

public class CsvAndTxtExport implements FileExport {
	private String defaultExportDirectory = "F:" + File.separator
			+ "exportdata" + File.separator + "csv" + File.separator;
	private String fileName;
	private Logger logger = Logger.getLogger(this.getClass());
	private PrintWriter ps;

	@Override
	public String exportToFile(String directory, String fileName,
			String[][] content, String[] cols) {
		this.setDirectory(directory);
		this.setFileName(fileName);

		/**
		 * 判断文件名称是不是重复，如果用户传入的文件名和以前的文件重复，那么重新生成一个文件
		 */
		String filePath = this.defaultExportDirectory + this.fileName;
		new File(this.defaultExportDirectory).mkdirs();
		while (new File(filePath).exists()) {
			this.setFileName(null);
			filePath = this.defaultExportDirectory + this.fileName;
		}

		try {
			this.ps = new PrintWriter(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		int rowNum = 0;
		StringBuffer rowContent = new StringBuffer();
		/**
		 * 首先写入标题行，如果标题是复杂类型，那么要用“”包含
		 */
		if (cols != null && cols.length > 0) {
			for (int i = 0; i < cols.length - 1; i++) {
				if (cols[i] != null && cols[i].indexOf(",") != -1) {
					rowContent.append("\"").append(cols[i]).append("\"")
							.append(",");
				} else {
					rowContent.append(cols[i]).append(",");
				}
			}

			rowContent.append(cols[cols.length - 1]);
			rowNum++;

			this.ps.println(rowContent.toString());
		}

		/**
		 * 写入文件正文，如果内容字段是复杂类型，那么要用“”包含
		 */
		if (content != null && content.length > 0) {
			for (int i = 0; i < content.length; i++) {// 遍历写入每一行数据
				rowContent = new StringBuffer();
				for (int j = 0; j < content[i].length; j++) {// 遍历写入每一列数据
					if (content[i][j] != null
							&& content[i][j].indexOf(",") != -1) {
						rowContent.append("\"").append(content[i][j]).append(
								"\"").append(",");
					} else {
						rowContent.append(content[i][j] == null ? ""
								: content[i][j]);
						rowContent.append(",");
					}
				}

				this.ps.println(rowContent
						.substring(0, rowContent.length() - 1));// 依次写入每行数据
			}
		}

		this.ps.close();

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
			this.fileName = Calendar.getInstance().getTime().getTime() + ".csv";
			return;
		}

		this.fileName = fileName;
	}

}
