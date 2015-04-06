package com.leadtone.mas.util.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean容器管理类
 *
 *
 */
public class BeanContainer {

	private static ApplicationContext act = null;

	private static BeanContainer beanContainer;

	private BeanContainer() {
		JarTool tools = new JarTool(BeanContainer.class);
//		System.out.println(tools.getJarDir());
		if (act == null)
			act = new ClassPathXmlApplicationContext("./conf/spring-ap-sms-mo.xml");
//			act = new ClassPathXmlApplicationContext("spring-ap-sms-mo.xml");//debug
	}

	/**
	 * 初始化Spring上下文
	 */
	public static void initBeans() {
		if (beanContainer == null) {
			beanContainer = new BeanContainer();
		}
	}

	/**
	 * 指定类型获取实体
	 *
	 * @param beanName
	 * @param classType
	 * @return
	 */
	public static Object getBean(String beanName, Class<?> classType) {
		initBeans();
		return act.getBean(beanName, classType);
	}

	/**
	 * 根据实体名获取
	 *
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		initBeans();
		return act.getBean(beanName);
	}
}
