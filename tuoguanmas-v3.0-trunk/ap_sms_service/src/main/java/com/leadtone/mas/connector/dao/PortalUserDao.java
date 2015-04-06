package com.leadtone.mas.connector.dao;

import java.util.List;

import com.leadtone.mas.connector.domain.PortalUser;


/**
 * 
 * @author hejiyong
 * date:2013-1-22
 * 
 */
public interface PortalUserDao {
	PortalUser loadbyUserNameAndPwd(String name,String pwd,String merchant_pin);
	PortalUser loadbyUid(String Uid);
	List<PortalUser> loadAllUser();
	
	
	/**
	 * 查询用户信息
	 */
	PortalUser loadbyUserName(String accountId, Long merchant_pin);
	/**
	 * 获取所有商户PIN码(根据portal_user)
	 * @return
	 */
	List<Long> loadAllMerchantPin();
	List<PortalUser> loadAllMerchantPinDBUser(Long merchantPin);
}
