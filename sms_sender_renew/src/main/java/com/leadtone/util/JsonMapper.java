package com.leadtone.util;

/*  
 *  This is a part of the MOP Platform.
 *  Copyright (C) 2004-2011 leadtone.com Corporation
 *  All rights reserved.
 *
 *  Licensed under the leadtone.com private License.
 *  
 *
 */


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * Json映射工具类，对Json处理框架Jackson进行进一步包装，完成从任意的JavaBean到json</br>
 * 以及从json到java对象(包括集合类)之间的转换
 * 
 * @author wangweiping
 * 
 */
public abstract class JsonMapper {
	static Logger logger = Logger.getLogger(JsonMapper.class);

	/**
	 * 从任意java对象转换到json字符串
	 * 
	 * @param object
	 *            待转换的java对象
	 * @return json字符串
	 */
	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(object);
		} catch (Exception ex) {
			logger.error("from object to JSON error", ex);
		}
		return jsonString;
	}

	/**
	 * 将json字符串转换为指定的java对象，不包括泛型的集合类
	 * 
	 * @param json
	 *            json字符串
	 * @param clazz
	 *            转换java目标类
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = mapper.readValue(json, clazz);
		} catch (Exception ex) {
			logger.error("from json to Object error", ex);
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	public static <T> T toObject(String json, TypeReference<T> typeReference) {
		ObjectMapper mapper = new ObjectMapper();
		T object = null;
		try {
			object = (T) mapper.readValue(json, typeReference);
		} catch (Exception ex) {
			logger.error("from json to Object error", ex);
		}
		return (T) object;
	}

	/**
	 * 将json字符串转换为java.util.Map,键值类型都为java.lang.String
	 * 
	 * @param json
	 * @return
	 */
	public static Map<String, String> toMap(String json) {
		return toObject(json, new TypeReference<Map<String, String>>() {
		});
	}

	/**
	 * 将json字符串转换为java.util.Map实例，键为java.lang.String,值为java.lang.Object
	 * 
	 * @param json
	 *            json字符串
	 * @return
	 */
	public static Map<String, Object> toObjectMap(String json) {
		return toObject(json, new TypeReference<Map<String, Object>>() {
		});
	}

	/**
	 * 转换为指定键值类型的泛型java.util.Map实例
	 * 
	 * @param json
	 *            json字符串
	 * @param k
	 *            键目标类
	 * @param v
	 *            值目标类
	 * @return
	 */
	public static <K, V> Map<K, V> toGenericMap(String json, Class<K> k, Class<V> v) {
		return toObject(json, new TypeReference<Map<K, V>>() {
		});
	}

	public static <T> List<T> toList(String json, Class<T> t) {
		return toObject(json, new TypeReference<List<T>>() {
		});
	}
}

