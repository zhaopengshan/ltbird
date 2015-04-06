package com.leadtone.driver.dao.local;

import java.util.List;
import java.util.Map;

import com.leadtone.driver.bean.ReceiveResult;
import com.leadtone.driver.bean.ReportResult;
import com.leadtone.driver.bean.SmsBean;
/**
 * @author limh 
 */

public interface ISmsDao {

	List<SmsBean> getReadySendSms(Long merchantPin, int tunnelType,int sendResult,  String readSendTime,int limit, String orderFlag, String uid, boolean perAccount);
	List<Map<String, Object>> saveZxtDriverSms(List<SmsBean> list);
	void saveHttpSmsRsp(String taskId,Long smsId, Long userId,String status);
	void updateSmsSendCpoid(List<Map<String, Object>> list)throws Exception;
	void updateSmsSendReceive(List<ReceiveResult> list)throws Exception;
	List<SmsBean> getReportSms(Long merchantPin, List<ReportResult> resultMap, String uid, boolean perAccount);
}
