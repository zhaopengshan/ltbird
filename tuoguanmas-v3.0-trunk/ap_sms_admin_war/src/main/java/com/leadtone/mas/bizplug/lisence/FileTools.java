package com.leadtone.mas.bizplug.lisence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类
 * 
 * @author ChangJun
 * 
 */
public class FileTools {

	public static List<String> read(File file) {
		List<String> list = new ArrayList<String>();

		FileReader fr = null;
		BufferedReader reader = null;
		try {
			fr = new FileReader(file);
			reader = new BufferedReader(fr);
			String imei = null;
			while ((imei = reader.readLine()) != null) {
				if (imei != null) {
					imei = imei.trim();
				}
				list.add(imei);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fr != null) {
					fr.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return list;
	}

	public static List<String> calculateSerial(List<String> imeiList,
			List<String> serialList) {
		List<String> result = new ArrayList<String>();

		if (serialList != null && serialList.size() != 0) {
			result.addAll(serialList);
		}

		for (int i = 0; i < imeiList.size(); i++) {
			String imei = imeiList.get(i);
			result.add(imei + " " + Encrypt.calculateSerial(imei));
		}

		return result;
	}

	public static void save(File serialFile, List<String> batchSerialList) {
		FileWriter fileWriter = null;
		try {
			if (serialFile.exists()) {
				serialFile.delete();
				serialFile.createNewFile();
			}
			fileWriter = new FileWriter(serialFile);
			int count = 0;
			for (int i = 0; i < batchSerialList.size(); i++) {
				fileWriter.write(batchSerialList.get(i) + "\n");
				if(count > 100) {
					fileWriter.flush();
					count = 0;
				}
				count++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileWriter != null) {
					fileWriter.flush();
					fileWriter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
