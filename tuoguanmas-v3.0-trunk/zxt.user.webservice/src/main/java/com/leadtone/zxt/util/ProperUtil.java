package com.leadtone.zxt.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ProperUtil {

	static Logger logger = Logger.getLogger(ProperUtil.class);
	static Properties props;
	
	public static void setLocalPath(String localPath){
		props= new Properties();
		try {
			InputStream inputStream = ProperUtil.class.getClassLoader()
					.getResourceAsStream(localPath);
			//InputStream in = new BufferedInputStream (new FileInputStream(localPath));
	         props.load(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public static String readValue(String key) {
		try {
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
}
