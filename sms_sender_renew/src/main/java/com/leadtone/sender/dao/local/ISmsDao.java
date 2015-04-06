package com.leadtone.sender.dao.local;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leadtone.sender.bean.GatewaySmsBean;
import com.leadtone.sender.bean.MbnSmsInbox;
import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.bean.SmsLimitBean;
import com.leadtone.sender.bean.UTcomGatewaySmsBean;
import com.leadtone.sender.bean.User;
import com.leadtone.sender.bean.ZxtReceiveBean;

/**
 * @author limh 
 */

public interface ISmsDao {
	public List getProvinceByTunnelType(int tunnelType,int sendResult, String readSendTime);
    
    public List getMerchantPinList(int tunnelType,int sendResult,String province, String readSendTime);
    
    public SmsLimitBean getLimitByMerchantPin(Long merchantPin);
    
    public List<Map<String, Object>> saveGatewaySms(List<GatewaySmsBean> list);
    public List<Map<String, Object>> saveUTcomGatewaySms(final List<UTcomGatewaySmsBean> list);
    
    public List<SmsBean> getReadySendSms(Long merchantPin, int tunnelType,int sendResult,  String readSendTime,int limit, String orderFlag);
    
    public void updateSmsSendRestlt(List<Map<String, Object>> list);
    
    public void updateMerchantGatewayLimit(Long merchantPin, int currentDay, int currentMonth);

    public void updateMerchantModemLimit(Long merchantPin, int currentDay, int currentMonth);
    
	public List getGatewaySmsResult(int limit);
	public List getEmppSmsResult(int limit);
	public List getLtdxGatewaySmsResult(int limit);
	
	public List getHttpSmsResult(int limit);
	
	public void updateGatewaySms(List<Map<String, Object>> list);
	public void updateEmppSms(List<Map<String, Object>> list);
	public void updateLtdxGatewaySms(List<Map<String, Object>> list);
	
    public void butchInsertGatewaySms(String values);
    
	public void saveHttpSmsRsp(String taskId,Long smsId, Long userId,String status);
	
	public void savePukerSmsRsp(String taskId, Long smsId);
	public List getsmsHttpRsp(Long taskId);
	
    public void updateSmsHttp(Long taskId, String final_status);

    public User getUserByZXTId(String zxtUserId);
    
    public  List<User> getUsers();
    
    public Integer addUpSms(Long id,String sender,String receiver,String content,Timestamp createTime);
    public void getBackLimit(String userId);
    
    public List getMerchantPriorityList(Long merchantPin, int tunnelType,int sendResult,String province, String readSendTime);
    public List<SmsBean> getReadySendSmsByPriority(Long merchantPin,int tunnelType, int sendResult, String readSendTime, int limit, int priority);
    public List getMerchantPriorityBatchList(Long merchantPin,int tunnelType, int sendResult, String readSendTime, int priority);
    public List<SmsBean> getReadySendSmsByPriorityBatch(Long merchantPin,int tunnelType, int sendResult, String readSendTime, int limit, int priority, Long batchId);

    //sunyadong add
	List getMerchantPinList(int sendResult, String readSendTime);
	List<SmsBean> getReadySendSms(Long merchantPin, int sendResult,  String readSendTime,int limit);
	List<Map<String, Object>> saveZxtDriverSms(List<SmsBean> list);
	List<SmsBean> getZxtSmsResult(int limit);
	void updateZxtSmsSendRestlt(List<SmsBean> list);
	void updateZxtMoRestlt(List<SmsBean> list);
	void updateSmsSendCancel(List<SmsBean> list);
	List<ZxtReceiveBean> getZxtMoBean(int limit);
	void updateZxtMoBean(List<ZxtReceiveBean> list);
	void saveZxtMoToInbox(List<MbnSmsInbox> list);
	void updateQxtSmsSendRestlt(List<SmsBean> list);
	void updateQxtNewSmsSendRestlt(List<SmsBean> list);
	List<Map<String, Object>> saveQxtDriverSms(List<SmsBean> list);
	List<SmsBean> getQxtSmsResult(int limit);
    void updateQxtMoRestlt(List<SmsBean> list);
    void updateQxtNewProcRestlt(List<SmsBean> list);
    
    List<Map<String, Object>> saveQxtNewDriverSms(List<SmsBean> list);
	List<SmsBean> getQxtNewSmsResult(int limit);
	
	public List<Map<String, Object>> saveEmppSms(List<GatewaySmsBean> list);
    
}
