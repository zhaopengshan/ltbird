package com.leadtone.driver.service;

import java.util.List;
import java.util.Map;

import com.leadtone.driver.bean.ReceiveResult;
import com.leadtone.driver.bean.ReportResult;
import com.leadtone.driver.bean.SmsBean;
public interface ISmsService {
	/**
     * 获取待发短信列表
     * @param merchantPin 商户pin码
     * @param tunnelType 渠道
     * @param sendResult 发送结果（0未发送，1发送中，2发送成功，3发送失败）
     * @param readSendTime 当前时间，格式yyyy-MM-dd HH:mm:ss
     * @param limit 发送限制
     * @return 待发短信列表
     */
	public List<SmsBean> getReadySendSms(Long merchantPin,int tunnelType, int sendResult, String readSendTime, int limit,String orderFlag);
	public List<SmsBean> getReadySendSms(Long merchantPin, int sendResult, String readSendTime, int limit);

	List<Map<String, Object>> saveZxtDriverSms(List<SmsBean> list);
	void saveHttpSmsRsp(String taskId,Long smsId, Long userId,String status);
	/**
     * 更新发送结果
     * @param list 待发送短信id（mbn_sms_ready_send表中id）
     * @param sendResult 发送结果（0未发送，1发送中，2发送成功，3发送失败）
     * @return
     */
	void updateSmsSendRestlt(List<Map<String, Object>> list)throws Exception;
	void updateSmsSendCpoid(final List<Map<String, Object>> list)throws Exception;
	void updateSmsSendReceive(List<ReceiveResult> list)throws Exception;
	
	List<SmsBean> getReportSms(Long merchantPin, List<ReportResult> resultMap);
}
