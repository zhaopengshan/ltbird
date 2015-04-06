package com.leadtone.zxt.util;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class SpringUtils {

	private static final String[] DEFAULT_CONFIG_FILES = { "applicationContext.xml" };
	private static BeanFactory factory;

	public static synchronized Object getBean(String beanName) {
		if (beanName != null && !("".equals(beanName.trim()))) {
			try {
				if (factory == null) {
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

}
