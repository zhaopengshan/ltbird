package com.leadtone.driver.service.impl;

import java.util.List;
import java.util.Map;

import com.leadtone.driver.bean.ReceiveResult;
import com.leadtone.driver.bean.ReportResult;
import com.leadtone.driver.bean.SmsBean;
import com.leadtone.driver.dao.local.ISmsDao;
import com.leadtone.driver.service.ISmsService;

public class SmsServiceImpl implements ISmsService {
    private ISmsDao smsDao;

	@Override
	public List<Map<String, Object>> saveZxtDriverSms(List<SmsBean> list) {
		return smsDao.saveZxtDriverSms(list);
	}

	@Override
	public List<SmsBean> getReadySendSms(Long merchantPin, int tunnelType, int sendResult, String readSendTime, int limit, String orderFlag) {
		return smsDao.getReadySendSms(merchantPin, tunnelType, sendResult,readSendTime,limit,orderFlag);
	}
	
	@Override
	public List<SmsBean> getReadySendSms(Long merchantPin, int sendResult,
			String readSendTime, int limit) {
		return smsDao.getReadySendSms(merchantPin, sendResult, readSendTime, limit);
	}
	
	@Override
	public void saveHttpSmsRsp(String taskId, Long smsId, Long userId, String status) {
		smsDao.saveHttpSmsRsp(taskId,smsId,userId,status);		
	}
	
	@Override
	public void updateSmsSendRestlt(List<Map<String, Object>> list) throws Exception{
		 smsDao.updateSmsSendRestlt(list);		
	}

	@Override
	public void updateSmsSendCpoid(List<Map<String, Object>> list)throws Exception {
		smsDao.updateSmsSendCpoid(list);
	}
	@Override
	public void updateSmsSendReceive(List<ReceiveResult> list) throws Exception {
		smsDao.updateSmsSendReceive(list);
	}
	
	@Override
	public List<SmsBean> getReportSms(Long merchantPin, List<ReportResult> resultMap) {
		return smsDao.getReportSms(merchantPin, resultMap);
	}
	public ISmsDao getSmsDao() {
		return smsDao;
	}

	public void setSmsDao(ISmsDao smsDao) {
		this.smsDao = smsDao;
	}

}
