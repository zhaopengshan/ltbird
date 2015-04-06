package com.leadtone.mas.connector.utils;

import org.apache.commons.lang.StringUtils;

import cn.j2eebestpractice.ssiframework.util.SpringUtil;

/**
 * 
 * @author hejiyong
 * date:2013-1-23
 * 
 */
public abstract class SpringUtils {

	public static Object getBean(String name) {
		try {
			if (StringUtils.isNotBlank(name)) {
				return SpringUtil.getApplicationContext().getBean(name);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
