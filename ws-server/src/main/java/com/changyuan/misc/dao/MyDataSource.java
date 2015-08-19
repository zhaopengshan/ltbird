package com.changyuan.misc.dao;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/** 
 *@author: sunyadong@rd.tuan800.com
 *@date: 2015年5月6日 下午4:00:57
 *@version: 1.0
 */
public class MyDataSource extends AbstractRoutingDataSource {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		return ContextHolder.getCustomerType();
	}

}
