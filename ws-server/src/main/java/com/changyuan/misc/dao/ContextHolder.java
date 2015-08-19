package com.changyuan.misc.dao;
/** 
 *@author: sunyadong@rd.tuan800.com
 *@date: 2015年5月6日 下午4:01:35
 *@version: 1.0
 */
public class ContextHolder {
	public static final String DATA_SOURCE = "dataSource";
	public static final String DATA_SOURCE_READ = "dataSourceRead";
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	public static void setCustomerType(String customerType){
		contextHolder.set(customerType);
	}
	
	public static String getCustomerType(){
		return contextHolder.get();
	}
	
	public static void clearCustomerType(){
		contextHolder.remove();
	}
}
