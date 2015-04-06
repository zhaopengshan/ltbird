package com.leadtone.mas.util.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;

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

			String osname=System.getProperty( "os.name").toLowerCase();

		    if(osname.indexOf("window")==0){
		    	System.out.println("current os name is windows");
		    	beanFactory = new FileSystemXmlApplicationContext(config).getBeanFactory();
		    }else{
		    	System.out.println("current os name is linux ");
		    	beanFactory = new FileSystemXmlApplicationContext("file:"+config).getBeanFactory();
		    }

		}
		if (beanFactory == null) {
			throw new RuntimeException("MasBeanFactory is null");
		}
	}

	public static void init(Resource resource) {
		if (beanFactory == null) {
			//XmlBeanFactory factory = new XmlBeanFactory(res);
			beanFactory = new XmlBeanFactory(resource);


		}
	}

	public static String getConfig() {
		return config;
	}

	public static void setConfig(String config) {
		MasBeanFactory.config = config;
	}
}