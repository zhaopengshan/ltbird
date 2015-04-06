package com.leadtone.mas.util.spring;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * 应用程序配置读取工具
 * 
 * @author admin
 * 
 */
public class AppConfig {

	private static final Properties props = new Properties();

	public static void readConfig(String CONFIG_FILE_PATH) {
		FileInputStream in = null;
		try {
			// 得到当前类的类加载器,以流的方式读取配置文件
			in = new FileInputStream(new File(CONFIG_FILE_PATH));
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 根据一个参数，取得其值。
	public static String getValueAsString(String key) {
		String value = (String) props.get(key);
		return value == null ? "" : value.trim();
	}

	// 根据一个参数，取得其值。
	public static int getValueAsInt(String key) {
		String value = (String) props.get(key);
		return Integer.parseInt(value);
	}

	// 根据一个参数，取得其值。
	public static boolean getValueAsBoolean(String key) {
		String value = (String) props.get(key);
		return Boolean.parseBoolean(value);
	}

}
