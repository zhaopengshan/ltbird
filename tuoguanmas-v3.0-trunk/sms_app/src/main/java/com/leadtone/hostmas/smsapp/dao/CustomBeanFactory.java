package com.leadtone.hostmas.smsapp.dao;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class CustomBeanFactory {
	static BeanFactory factory = null;
	

	public CustomBeanFactory() {
	}
	public static Object getBean(String beanName) {

		if (factory == null)
			factory = new ClassPathXmlApplicationContext(
					"applicationContext.xml");
		return factory.getBean(beanName);

	}

	
}
