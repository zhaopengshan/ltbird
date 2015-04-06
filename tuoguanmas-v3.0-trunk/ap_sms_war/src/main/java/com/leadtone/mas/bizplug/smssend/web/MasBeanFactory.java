package com.leadtone.mas.bizplug.smssend.web;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MasBeanFactory {

	protected static BeanFactory beanFactory;

	static String config;

	public static Object getBean(String beanName) {
		if (beanFactory == null) {
			init(config);
		}
		return beanFactory.getBean(beanName);
	}

	public BeanFactory getBeanFactory() {
		if (beanFactory == null) {
			init(config);
		}
		return beanFactory;
	}

	void setBeanFactory(BeanFactory context) {
		beanFactory = context;
	}

	/**
	 * 
	 */
	public static void init(String filePath) {
		if (beanFactory == null) {
			config = filePath;

			String osname = System.getProperty("os.name").toLowerCase();

			if (osname.indexOf("window") == 0) {
				beanFactory = new FileSystemXmlApplicationContext(config)
						.getBeanFactory();
			} else {
				beanFactory = new FileSystemXmlApplicationContext("file:"
						+ config).getBeanFactory();
			}
		}
		if (beanFactory == null) {
			throw new RuntimeException("MasBeanFactory is null");
		}
	}

	public static String getConfig() {
		return config;
	}

	public static void setConfig(String config) {
		MasBeanFactory.config = config;
	}
}