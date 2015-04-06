package com.leadtone.mas.bizplug.openaccount.service;

import java.util.Map;

import com.leadtone.mas.bizplug.security.bean.Users;

public interface OpenAccountIService {
	/**
	 * 开销户通用方法 	1开户2变更3暂停4销户
	 * @param num
	 * @param parasMap
	 * @param entityMap
	 */
	public void openAccount(int num,Map parasMap,Map entityMap);
	/**
	 * 实现开户
	 * @param parasMap
	 * @param entityMap
	 */
	public void createMerchant(Map parasMap,Map entityMap);//1
	/**
	 * 变更用户
	 * @param parasMap
	 * @param entityMap
	 */
	public void updateMerchant(Map parasMap,Map entityMap);//2
	/**
	 * 暂停用户
	 */
	public void delayMerchant();//3
	/**
	 * 销户
	 */
	public void logoutMerchant();//4
	/**
	 * 预处理
	 */
	public void before();
	/**
	 * 后期处理
	 */
	public void after();
	/**
	 * 检查管理员对应的企业是否存在
	 */
	public  boolean checkMerchantExist(Users users);
}
