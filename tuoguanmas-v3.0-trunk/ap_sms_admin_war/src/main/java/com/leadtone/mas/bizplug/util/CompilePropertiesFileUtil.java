/**
 * 
 */
package com.leadtone.mas.bizplug.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * @author PAN-Z-G  ReadPropertiesUtil
 */
public class CompilePropertiesFileUtil {
	public static List<String> ReadPropertys(String filePath)
			throws IOException {
		// 创建Properties对象
		Properties pro = new Properties(); 
		// 如果文件不存在，创建一个新的
		File file = new File(filePath);

		if (!file.exists())
			file.createNewFile();
		InputStream in = new FileInputStream(file);
		pro.load(in);
		Enumeration en = pro.propertyNames();// 枚举
		List<String> result = new ArrayList<String>();
		while (en.hasMoreElements()) {
			// 以key读取 ==值
			result.add(pro.getProperty((String) en.nextElement()));
		}
		in.close();
		pro.clear();
		return result;

	}
	
	public static  String ReadProperty(String filePath)
			throws IOException {
		// 创建Properties对象
		Properties pro = new Properties(); 
		// 如果文件不存在，创建一个新的
		File file = new File(filePath);

		if (!file.exists())
			file.createNewFile();
		InputStream in = new FileInputStream(file);
		pro.load(in);
		Enumeration en = pro.propertyNames();// 枚举
		 StringBuffer  result=null ;
		 if(result==null)
			 result=new StringBuffer();
		 
		while (en.hasMoreElements()) {
			// 以key读取 ==值
			result.append(pro.getProperty((String) en.nextElement()));
		} 
		in.close();
		pro.clear();
		return result.toString();

	}
}