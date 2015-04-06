package com.litong.utils;

import java.util.Properties;

public class PropertiesUtil {

	private static Properties properties;
	
	public static String getProperty(String key){
		if(properties==null){
			try{
				properties = new Properties();
				properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("init.properties"));
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
		}
		return properties.getProperty(key);
	}
}
