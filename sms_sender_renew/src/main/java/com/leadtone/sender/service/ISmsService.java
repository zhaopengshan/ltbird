package com.leadtone.sender.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leadtone.sender.bean.GatewaySmsBean;
import com.leadtone.sender.bean.MbnSmsInbox;
import com.leadtone.sender.bean.ModemSmsBean;
import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.bean.UTcomGatewaySmsBean;
import com.leadtone.sender.bean.User;
import com.leadtone.sender.bean.ZxtReceiveBean;

public interface ISmsService {
	
    /**
     * 获取省份列表
     * @param tunnelType 渠道
     * @param sendResult 发送结果（0未发送，1发送中，2发送成功，3发送失败）
     * @param readSendTime 当前时间，格式yyyy-MM-dd HH:mm:ss
     * @return 省份列表
     */
	public List getProvinceByTunnelType(int tunnelType, int sendResult, String readSendTime );
	
    /**
     * 获取商户pin码列表
     * @param tunnelType 渠道
     * @param sendResult 发送结果（0未发送，1发送中，2发送成功，3发送失败）
     * @param province 省份
     * @param readSendTime 当前时间，格式yyyy-MM-dd HH:mm:ss
     * @return 商户pin码列表
     */
	public List<Long> getMerchantPinList(int tunnelType,int sendResult,String province, String readSendTime);
	
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

    /**
     * 网关代发短信发送
     * @param list 待发送短信列表
     * @return 待发送短信id（mbn_sms_ready_send表中id）
     */
	public List<Map<String, Object>> saveGatewaySms(List<GatewaySmsBean> list);
	
	public List<Map<String, Object>> saveUTcomGatewaySms(List<UTcomGatewaySmsBean> list);

    /**
     * 更新发送结果
     * @param list 待发送短信id（mbn_sms_ready_send表中id）
     * @param sendResult 发送结果（0未发送，1发送中，2发送成功，3发送失败）
     * @return
     */
	public void updateSmsSendRestlt(List<Map<String, Object>> list);

    /**
     * 服务端猫短信发送
     * @Time 2009-10-19 下午02:52:04 create
     * @param yyyymmString
     * @return
     * @author maoliang
     */
	public List<Map<String, Object>> saveModemSms(List<ModemSmsBean> list);
	
    /**
     * 从smw_cmpp_submit_result获取已接受到回执的短信Id
     * @Time 2009-10-19 下午02:52:04 create
     * @param yyyymmString
     * @return
     * @author maoliang
     */
	public List getGatewaySmsResult();
	public List getEmppSmsResult();
	public List getLtdxGatewaySmsResult();
	
    /**
     * 更新发送结果
     * @param list 待发送短信id（mbn_sms_ready_send表中id）
     * @param sendResult 发送结果（0未发送，1发送中，2发送成功，3发送失败）
     * @return
     */
	public void updateGatewaySms(List<Map<String, Object>> list);
	public void updateEmppSms(List<Map<String, Object>> list);
	public void updateLtdxGatewaySms(List<Map<String, Object>> list);
	
    /**
     * 从smw_cmpp_submit_result获取已接受到回执的短信Id
     * @Time 2009-10-19 下午02:52:04 create
     * @param yyyymmString
     * @return
     * @author maoliang
     */
	public List getModemSmsResult();
	
    /**
     * 从smw_cmpp_submit_result获取已接受到回执的短信Id
     * @Time 2009-10-19 下午02:52:04 create
     * @param yyyymmString
     * @return
     * @author maoliang
     */
	public void deleteSendedSms(List<Map<String, Object>> list);
	
	public void saveHttpSmsRsp(String taskId,Long smsId, Long userId,String status);
	
	public void savePukerSmsRsp(String taskId,Long smsId);
	
	public List getHttpSmsResult();
	
	public List<Long> getsmsHttpRsp(Long taskId);
	
	public void updateSmsHttp(Long taskId,String status);

	public User getUserByZXTId(String zxtUserId);
	
	public List<User> getUsers();
	public Integer addUpSms(Long id,String sender,String receiver,String content,Timestamp createTime);
	
	public void getBackLimit(String userId);
	/**
	 * 查询企业ready_send表内待发短信 priority，
	 * @param tunnelType
	 * @param sendResult
	 * @param province
	 * @param readSendTime
	 * @return
	 */
	public List<Integer> getMerchantPriorityList(Long merchantPin, int tunnelType,int sendResult,String province, String readSendTime);
	/**
	 * get ready_send sms by priority 
	 * @param merchantPin
	 * @param tunnelType
	 * @param sendResult
	 * @param readSendTime
	 * @param limit
	 * @param orderFlag
	 * @return
	 */
	public List<SmsBean> getReadySendSmsByPriority(Long merchantPin,int tunnelType, int sendResult, String readSendTime, int limit, int priority);
	/**
	 * 同优先级 不同批次统计
	 * @param merchantPin
	 * @param tunnelType
	 * @param sendResult
	 * @param readSendTime
	 * @param limit
	 * @param priority
	 * @return
	 */
	public List getMerchantPriorityBatchList(Long merchantPin,int tunnelType, int sendResult, String readSendTime, int priority);
	/**
	 * 同优先级，指定批次查询
	 * @param merchantPin
	 * @param tunnelType
	 * @param sendResult
	 * @param readSendTime
	 * @param limit
	 * @param priority
	 * @param batchId
	 * @return
	 */
	public List<SmsBean> getReadySendSmsByPriorityBatch(Long merchantPin,int tunnelType, int sendResult, String readSendTime, int limit, int priority, Long batchId);

	/**
	 * 当前可发短信所属企业及省份
	 * @param sendResult
	 * @param readSendTime
	 * @return
	 */
	List getMerchantPinList(int sendResult, String readSendTime);
	/**
	 * 资信通 保存到驱动表
	 * @param list
	 * @return
	 */
	List<Map<String, Object>> saveZxtDriverSms(List<SmsBean> list);
	List<SmsBean> getZxtSmsResult();
	void updateZxtSmsSendRestlt(List<SmsBean> list);
	void updateZxtMoRestlt(List<SmsBean> list);
	void updateSmsSendCancel(List<SmsBean> list);
	List<ZxtReceiveBean> getZxtMoBean();
	void updateZxtMoBean(List<ZxtReceiveBean> list);
	void saveZxtMoToInbox(List<MbnSmsInbox> list);
	
	List<Map<String, Object>> saveQxtDriverSms(List<SmsBean> list);
	List<SmsBean> getQxtSmsResult();
	void updateQxtMoRestlt(List<SmsBean> list);
	void updateQxtSmsSendRestlt(List<SmsBean> list);
	void updateQxtNewSmsSendRestlt(List<SmsBean> list);
	void updateQxtNewProcRestlt(List<SmsBean> list);
	
	List<Map<String, Object>> saveQxtNewDriverSms(List<SmsBean> list);
	List<SmsBean> getQxtNewSmsResult();
	
	public List<Map<String, Object>> saveEmppSms(List<GatewaySmsBean> list);
}
