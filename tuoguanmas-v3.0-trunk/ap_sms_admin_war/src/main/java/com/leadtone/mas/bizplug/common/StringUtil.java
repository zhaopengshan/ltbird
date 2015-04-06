package com.leadtone.mas.bizplug.common;

import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 字符串操作类
 * 
 * @author lidongdong
 * 
 */
public class StringUtil {
    private static final Log log = LogFactory.getLog(StringUtil.class);
	/**
	 * 判断字符串是否为空
	 * 
	 * @param tar
	 * @return
	 */
	public static boolean isEmpty(String tar) {
		if (null == tar || "".equals(tar.trim())) {
			return true;
		}
		return false;
	}
	
	public static boolean isMobile(String mobile){
		if (null == mobile || "".equals(mobile.trim())) {
			return false;
		}
		return Pattern.matches("^1\\d{10}$", mobile);
	}
	
	public static String getShortPrefix(String mobile){
		if( mobile == null || "".equals(mobile.trim())) {
			return "";
		}
		if( mobile.length() <= 3){
			return mobile;
		}
		return mobile.substring(0, 3);
	}
	
	public static String getShortPrefixFourth(String mobile){
		if( mobile == null || "".equals(mobile.trim())) {
			return "";
		}
		if( mobile.length() <= 4){
			return mobile;
		}
		return mobile.substring(3, 4);
	}
	
	public static String getLongPrefix(String mobile){
		if( mobile == null || "".equals(mobile.trim())) {
			return "";
		}
		if( mobile.length() <= 7){
			return mobile;
		}
		return mobile.substring(0, 7);
	}
}
