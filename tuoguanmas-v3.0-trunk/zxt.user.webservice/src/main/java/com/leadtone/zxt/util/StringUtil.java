package com.leadtone.zxt.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

	private static final String DEFAULT_SPLIT = ",";
	private static final String EMPTY_STRING = "";
	private static final String SINGLE_QUOTATION_MARKS = "'";

	private static final String[] EMPTY_STRING_ARRAY = new String[] {};

	public static byte[] getBytes(String str, String encoding) {
		try {
			return str.getBytes(encoding);
		} catch (UnsupportedEncodingException ex) {
			logger.error("", ex);
		}
		return null;
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
	
	public static String getLongPrefix(String mobile){
		if( mobile == null || "".equals(mobile.trim())) {
			return "";
		}
		if( mobile.length() <= 7){
			return mobile;
		}
		return mobile.substring(0, 7);
	}

	public static List<String> str2List(String str) {
		return str2List(str, DEFAULT_SPLIT);
	}

	
	private static List<String> str2List(String str, String split) {
		if (StringUtils.isEmpty(str)) {
			return Collections.emptyList();
		}
		String[] strArray = str.split(split);
		return Arrays.asList(strArray);
	}

	
	public static String list2str(List<String> strList) {
		StringBuilder resultBuffer = new StringBuilder();
		if (strList == null || strList.size() == 0) {
			return EMPTY_STRING;
		}
		for (Iterator<String> iterator = strList.iterator(); iterator.hasNext();) {
			resultBuffer.append(",").append(iterator.next());
		}
		return resultBuffer.toString().substring(1);
	}

	
	public static String list2str(List<String> strList, String split) {
		StringBuilder resultBuffer = new StringBuilder();
		if (strList == null || strList.size() == 0) {
			return EMPTY_STRING;
		}
		for (Iterator<String> iterator = strList.iterator(); iterator.hasNext();) {
			resultBuffer.append(split).append(iterator.next());
		}
		return resultBuffer.toString().substring(1);
	}

	
	public static String arr2str(String[] array) {
		if (array == null || array.length == 0) {
			return EMPTY_STRING;
		}
		return list2str(Arrays.asList(array));
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static String[] list2Array(List<String> list) {
		if (list == null || list.size() == 0) {
			return EMPTY_STRING_ARRAY;
		}
		return list.toArray(new String[] {});
	}

	
	public static String iso2gbk(String isostr) {
		return convStrEncode(isostr, "ISO-8859-1", "GBK");
	}

	
	public static String convGBK2UTF8(String gbkstr) {
		return convStrEncode(gbkstr, "GBK", "UTF-8");
	}

	public static String bytes2str(byte[] bytes, String charset) {
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException ex) {
			logger.error("", ex);
		}
		return EMPTY_STRING;
	}


	public static String convStrEncode(String str, String srcCharset, String destCharset) {
		try {
			String result = new String(str.getBytes(srcCharset), destCharset);
			return result;
		} catch (UnsupportedEncodingException ex) {
			logger.error("error:", ex);
		}
		return EMPTY_STRING;
	}

	
	public static String firstLetter2LowerCase(String s) {
		if (StringUtils.isBlank(s))
			return EMPTY_STRING;

		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	public static Long str2LongWrapper(String str, Long defaultValue) {
		Long _long = defaultValue;
		try {
			_long = Long.valueOf(str);
		} catch (NumberFormatException ex) {
			// noop
		}
		return _long;
	}

	public static Long str2LongWrapper(String str) {
		return str2LongWrapper(str, null);
	}

	public static long str2Long(String str) {
		return str2Long(str, -1);
	}

	public static Integer str2Integer(String str, Integer defaultValue) {
		Integer _integer = defaultValue;
		try {
			_integer = Integer.valueOf(str);
		} catch (NumberFormatException ex) {
			// noop
		}
		return _integer;
	}

	public static Integer str2Integer(String str) {
		return str2Integer(str, null);
	}

	
	public static int str2Int(String str) {
		return str2Int(str, -1);
	}

	
	public static int str2Int(String str, int defaultValue) {
		if (StringUtils.isBlank(str))
			return defaultValue;
		int _int = defaultValue;
		try {
			_int = Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			// noop
		}
		return _int;
	}

	
	public static long str2Long(String str, long defaultValue) {
		if (StringUtils.isBlank(str))
			return defaultValue;
		long _long = defaultValue;
		try {
			_long = Long.parseLong(str);
		} catch (NumberFormatException ex) {
			// noop
		}
		return _long;
	}


	
	public static String concat(Object... args) {
		if (args == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (Object arg : args) {
			sb.append(arg);
		}
		return sb.toString();
	}

	
	public static String sprintf(String format, Object... args) {
		StringWriter writer = new StringWriter();
		PrintWriter out = new PrintWriter(writer);
		out.printf(format, args);
		out.close();
		return writer.toString();
	}

	public static String toSqlInString(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}
		String[] strArray = str.split(DEFAULT_SPLIT);
		StringBuilder stringBuilder = new StringBuilder();
		for (String _string : strArray) {
			if (_string.startsWith(SINGLE_QUOTATION_MARKS) && _string.endsWith(SINGLE_QUOTATION_MARKS)) {
				stringBuilder.append(DEFAULT_SPLIT).append(_string);
			} else {
				stringBuilder.append(DEFAULT_SPLIT);
				stringBuilder.append(SINGLE_QUOTATION_MARKS).append(_string).append(SINGLE_QUOTATION_MARKS);
			}
		}
		return stringBuilder.substring(1);
	}

	public static boolean isSecondLevelDomain(String arg0) {
		if (StringUtils.isEmpty(arg0))
			return false;
		return match(arg0, "[a-zA-Z0-9_][a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+){2,}");
	}

	public static boolean match(String arg, String pattern) {
		Pattern pat = Pattern.compile(pattern);
		Matcher matcher = pat.matcher(arg);
		return matcher.matches();
	}

	public static Integer[] stringArray2IntegerArray(String[] stringArray) {
		return stringArray2IntegerArray(stringArray, -1);
	}

	public static Integer[] stringArray2IntegerArray(String[] stringArray, Integer defaultInteger) {
		if (stringArray == null || stringArray.length == 0) {
			return null;
		}
		Integer[] integerArray = new Integer[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			integerArray[i] = str2Integer(stringArray[i].trim(), defaultInteger);
		}
		return integerArray;
	}
}
