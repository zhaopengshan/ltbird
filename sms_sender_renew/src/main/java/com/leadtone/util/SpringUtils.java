/*  
 *  This is a part of the ADC Platform.
 *  Copyright (C) 2004-2009 leadtone.com Corporation
 *  All rights reserved.
 *
 *  Licensed under the leadtone.com private License.
 *  
 *
 */
package com.leadtone.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.leadtone.sender.service.ISmsService;

public final class SpringUtils {

	private static final String[] DEFAULT_CONFIG_FILES = { "applicationContext.xml" };

	private static BeanFactory factory;

	public static synchronized Object getBean(String beanName) {
		if (beanName != null && !("".equals(beanName.trim()))) {
			try {
				/*
				 * 判断Spring是否已经初始化
				 */
				if (factory == null) {
					/*
					 * 尚未初始化，需要执行初始化操作
					 */
					factory = new ClassPathXmlApplicationContext(
							DEFAULT_CONFIG_FILES);
				}

				if (factory != null) {
					return factory.getBean(beanName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		ISmsService smsService = (ISmsService) SpringUtils
				.getBean("smsService");

	}

}
