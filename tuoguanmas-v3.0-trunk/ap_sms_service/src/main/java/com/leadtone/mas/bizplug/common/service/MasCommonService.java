package com.leadtone.mas.bizplug.common.service;


import com.leadtone.mas.bizplug.common.bean.MasCommonBean;

public interface MasCommonService {
	/**
	 * 短信对外发送公共接口
	 * @param colsList
	 * @param userSet
	 * @param loginUser
	 * @throws Exception
	 */
	public void insertMbnSmsSendTask(MasCommonBean masCommonBean) throws Exception;

}
