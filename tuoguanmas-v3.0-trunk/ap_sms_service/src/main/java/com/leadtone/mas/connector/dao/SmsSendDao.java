package com.leadtone.mas.connector.dao;

import com.leadtone.mas.connector.domain.SmsSend;

/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */
public interface SmsSendDao {
	void save(SmsSend msg); 
	String loadAccessNumByMobile(String merchant_pin,String mobileType);
	String loadMobileTypeByH3(String mobile_prefix);
}
