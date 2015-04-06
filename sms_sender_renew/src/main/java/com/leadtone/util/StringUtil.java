package com.leadtone.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
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
	 * 连接数组元素成字符串
	 * 
	 * @param array
	 *            字符串数组
	 * @param separate
	 *            分隔符
	 * @return 由分隔符将数组边接的字符串
	 */
	public static String joinStrArray(Serializable[] array, String separate) {
		String result = "[";
		StringBuffer sb = new StringBuffer(result);
		for (Serializable string : array) {
			sb.append(string).append(separate);
		}
		if (sb.lastIndexOf(separate) == (sb.length() - 1)) {
			result = sb.substring(0, sb.length() - 1);
		}
		return result + "]";
	}

	/**
	 * 判断字符串是否以数字开头
	 * 
	 * @param str
	 *            需要判断的字符串
	 * @return 返回值
	 */
	public static boolean isStartWithNum(String str) {
		String reg = "^[0-9]\\w*$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

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
        
        public static byte[] getBytes(String str, String encoding) {
		try {
			return str.getBytes(encoding);
		} catch (UnsupportedEncodingException ex) {
			log.error("", ex);
		}
		return null;
	}
	public static void main(String[] args) {
		// String[] array = new String[] { "aaa" };
		// String result = joinStrArray(array, ",");
		// System.out.println(result);
		String t = "1kjdf";
		String s = "sdfs";
		String m = "1";
		String a = "a";
		System.out.println(isStartWithNum(t));
		System.out.println(isStartWithNum(s));
		System.out.println(isStartWithNum(m));
		System.out.println(isStartWithNum(a));
	}
}
