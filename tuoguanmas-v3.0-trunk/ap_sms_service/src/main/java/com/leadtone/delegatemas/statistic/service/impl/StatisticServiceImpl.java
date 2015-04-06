/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.statistic.service.impl;

import com.leadtone.delegatemas.statistic.bean.SmQueryResult;
import com.leadtone.delegatemas.statistic.bean.SmSummary;
import com.leadtone.delegatemas.statistic.service.IStatisticService;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsInboxDao;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsReadySendDao;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author blueskybluesea
 */
@Service
public class StatisticServiceImpl implements IStatisticService {

    @Resource
    private MbnConfigMerchantIService MbnConfigMerchantIService;
    @Resource
    private MbnSmsInboxDao mbnSmsInboxDao;
    @Resource
    private MbnSmsReadySendDao mbnSmsReadySendDaoImpl;
    @Override
	public List<SmQueryResult> statisticQueryExport(Long merchantPin,
			Date startTime, Date endTime,Integer classify, String accessNumber, Long userId,
			String communicationWay, Long sendResult, String smType,
			String oppositeMobile, Integer intfType, Integer pageSize,
			Integer currentPageNo) {
        int nLength = this.signLength(merchantPin);
		HashMap<String, Object> param = this.assembleCondition(merchantPin,
				nLength, startTime, endTime, classify, accessNumber, userId,
				communicationWay, sendResult, smType, oppositeMobile, intfType);
        List<SmQueryResult> results = new ArrayList<SmQueryResult>();
        SmQueryResult result;
        int smItems = 0;
        try {
            if (communicationWay.equals("0")) {
                List<MbnSmsReadySend> sends  = mbnSmsReadySendDaoImpl.statisticSummary(param);
                if (sends != null && !sends.isEmpty()) {
                    for (MbnSmsReadySend send : sends) {
                        result = new SmQueryResult();
                        result.setAccountName(send.getTosName());
                        result.setCommunicationWay("发送");
                        result.setResult(send.getSendResult() == 2 ? "成功" : "失败");
                        switch(send.getSendResult()){
                        	case -1: result.setFailurReason("取消发送"); break;
                        	case 0: result.setFailurReason("未发送"); break;
                        	case 1: result.setFailurReason("发送中"); break;
                        	case 2: result.setFailurReason("无"); break;
                        	case 3: result.setFailurReason(send.getFailReason()); break;
                        	default: result.setFailurReason("发送失败");
                        }
                        smItems = this.smItems(send.getContent(), nLength);
                        if (smItems > 1) {
                            result.setSmType("长短信");
                        } else {
                            result.setSmType("普通短信");
                        }
                        result.setSmSegments(smItems);
                        result.setSmTime(send.getReadySendTime());
                        result.setOppositeMobile(send.getTos());
                        switch(send.getWebService()){
	                    	case 1: result.setIntfType("页面发送");break;
	                    	case 2: result.setIntfType("WebService");break;
	                    	case 3: result.setIntfType("数据库接口");break;
	                    	default: result.setIntfType("未知");
                        }
                        result.setContent(send.getContent());
                        results.add(result);
                    }
                }
            } else {
                List<MbnSmsInbox> inboxs = mbnSmsInboxDao.statisticSummary(param);
                if (inboxs != null && !inboxs.isEmpty()) {
                    for (MbnSmsInbox inbox : inboxs) {
                        result = new SmQueryResult();
                        result.setCommunicationWay("接收");
                        result.setResult("成功");
                        smItems = this.smItems(inbox.getContent(), nLength);
                        if (smItems > 1) {
                            result.setSmType("长短信");
                        } else {
                            result.setSmType("普通短信");
                        }
                        result.setFailurReason("无");
                        result.setSmSegments(smItems);
                        result.setSmTime(inbox.getReceiveTime());
                        result.setOppositeMobile(inbox.getSenderMobile());
                        result.setContent(inbox.getContent());
                        results.add(result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
    @Override
	public Page statisticQuery(Long merchantPin, Date startTime, Date endTime, Integer classify,
			String accessNumber, Long userId, String communicationWay,
			Long sendResult, String smType, String oppositeMobile,Integer intfType,
			Integer pageSize, Integer currentPageNo) {
        int nLength = this.signLength(merchantPin);
        HashMap<String, Object> param = this.assembleCondition(merchantPin, nLength, startTime, endTime, classify,
        		accessNumber, userId, communicationWay, sendResult, smType, oppositeMobile, intfType);
        param.put("startPage", currentPageNo);
        param.put("pageSize", pageSize);
        List<SmQueryResult> results = new ArrayList<SmQueryResult>();
        SmQueryResult result;
        int smItems = 0;
        Page page = null;
        try {
            if (communicationWay.equals("0")) {
                page = mbnSmsReadySendDaoImpl.statisticQuery(param);
                List<MbnSmsReadySend> sends = (List<MbnSmsReadySend>) page.getData();
                if (sends != null && !sends.isEmpty()) {
                    for (MbnSmsReadySend send : sends) {
                        result = new SmQueryResult();
                        result.setAccountName(send.getTosName());
                        result.setCommunicationWay("发送");
                        result.setResult(send.getSendResult() == 2 ? "成功" : "失败");
                        switch(send.getSendResult()){
                        	case -1: result.setFailurReason("取消发送"); break;
                        	case 0: result.setFailurReason("未发送"); break;
                        	case 1: result.setFailurReason("发送中"); break;
                        	case 2: result.setFailurReason("无"); break;
                        	case 3: result.setFailurReason(send.getFailReason()); break;
                        	default: result.setFailurReason("发送失败");
                        }
                        smItems = this.smItems(send.getContent(), nLength);
                        if (smItems > 1) {
                            result.setSmType("长短信");
                        } else {
                            result.setSmType("普通短信");
                        }
                        result.setSmSegments(smItems);
                        result.setSmTime(send.getReadySendTime());
                        result.setOppositeMobile(send.getTos());
                        switch(send.getWebService()){
                        	case 1: result.setIntfType("页面发送");break;
                        	case 2: result.setIntfType("WebService");break;
                        	case 3: result.setIntfType("数据库接口");break;
                        	default: result.setIntfType("未知");
                        }
                        results.add(result);
                    }
                    page.setData(results);
                }
            } else {
                page = mbnSmsInboxDao.statisticQuery(param);
                List<MbnSmsInbox> inboxs = (List<MbnSmsInbox>) page.getData();
                if (inboxs != null && !inboxs.isEmpty()) {
                    for (MbnSmsInbox inbox : inboxs) {
                        result = new SmQueryResult();
                        result.setCommunicationWay("接收");
                        result.setResult("成功");
                        smItems = this.smItems(inbox.getContent(), nLength);
                        if (smItems > 1) {
                            result.setSmType("长短信");
                        } else {
                            result.setSmType("普通短信");
                        }
                        result.setFailurReason("无");
                        result.setSmSegments(smItems);
                        result.setSmTime(inbox.getReceiveTime());
                        result.setOppositeMobile(inbox.getSenderMobile());
                        results.add(result);
                    }
                    page.setData(results);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return page;
    }

    @Override
	public SmSummary statisticSummary(Long merchantPin, Date startTime,
			Date endTime, Integer classify, String accessNumber, Long userId, Integer intfType, boolean isAdmin) {
        int signLength = this.signLength(merchantPin);
        HashMap<String, Object> sendParam = this.assembleCondition(merchantPin, signLength, 
        		startTime, endTime, classify, accessNumber, userId, intfType, isAdmin, true);
        List<MbnSmsReadySend> sends = this.mbnSmsReadySendDaoImpl.statisticSummary(sendParam);
        HashMap<String, Object> receiveParam = this.assembleCondition(merchantPin, signLength, 
        		startTime, endTime, classify, accessNumber, userId, intfType, isAdmin, false);
        List<MbnSmsInbox> inboxs = this.mbnSmsInboxDao.statisticSummary(receiveParam);
        SmSummary summary = new SmSummary();
        summary.setSmSendSummary(new Long(sends.size()));
        Long totalItems = 0L;
        Long totalSuccessItems = 0L;
        Long totalFailurItems = 0L;
        Long smItems = 0L;
        for (MbnSmsReadySend send : sends) {
            smItems = new Long(this.smItems(send.getContent(), signLength));
            if (send.getSendResult() == 2) {
                totalSuccessItems += smItems;
            } else {
                totalFailurItems += smItems;
            }
            totalItems += smItems;
        }
        summary.setSmSendSuccessSummary(totalSuccessItems);
        summary.setSmSendFailurSummary(totalFailurItems);
        summary.setSmSendSegmentsSummary(totalItems);
        if (totalItems != 0) {
            BigDecimal bdSuccess = new BigDecimal(totalSuccessItems);
            BigDecimal bdTotal = new BigDecimal(totalItems);
            BigDecimal result = bdSuccess.divide(bdTotal, 4, RoundingMode.UP);
            summary.setSmSuccessPercent(result.multiply(new BigDecimal(100)).floatValue());
            BigDecimal bdFailur = new BigDecimal(totalFailurItems);
            result = bdFailur.divide(bdTotal, 4, RoundingMode.UP);
            summary.setSmFailurPercent(result.multiply(new BigDecimal(100)).floatValue());
        } else {
            summary.setSmSuccessPercent(0);
            summary.setSmFailurPercent(0);
        }
        Long receiverItems = 0L;
        for (MbnSmsInbox inbox : inboxs) {
            receiverItems += new Long(this.smItems(inbox.getContent()));
        }
        summary.setSmReceiveSummary(receiverItems);
        return summary;
    }

    private HashMap<String, Object> assembleCondition(Long merchantPin, int signLength, Date startTime, Date endTime, Integer classify, String accessNumber, Long userId, String communicationWay, Long sendResult, String smType, String oppositeMobile, Integer intfType) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("merchantPin", merchantPin);
        if (!StringUtils.isEmpty(oppositeMobile)) {
            param.put("tos", oppositeMobile);
        }
        param.put("startDate", startTime);
        param.put("endDate", endTime);
        
        Calendar nowDate = Calendar.getInstance();
        Calendar setDate = Calendar.getInstance();
        nowDate.setTimeInMillis(System.currentTimeMillis());
        setDate.setTime(startTime);
        String monthTable = "";
        if( nowDate.get(Calendar.YEAR) != setDate.get(Calendar.YEAR) || nowDate.get(Calendar.MONTH) != setDate.get(Calendar.MONTH) ){
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            monthTable = "_"+sdf.format(startTime);
        }
        param.put("tableName", "mbn_sms_had_send"+monthTable);
        
        if (communicationWay.equals("0")) {
        	if(classify>0){
        		param.put("classify", classify);
        	}
//        	if (!accessNumber.equals("0")) {
//                param.put("accessNumber", accessNumber);
//            }
            if (smType.equals("1")) {
                param.put("smShort", signLength);
            }
            if (smType.equals("2")) {
                param.put("smLong", signLength);
            }
            if (sendResult != 0) {
                param.put("sendResult", sendResult);
            }
            if (userId != 0) {
                param.put("createBy", userId);
            }
            if( intfType > 0){
            	param.put("webService", intfType);
            }
        }else{
        	if(classify==1||classify==2||classify==3||classify==4||classify==5||classify==6||classify==13){
        	  if (!accessNumber.equals("0")) {
                  param.put("accessNumber", accessNumber);
              }
        	}else{
        		if(classify>0){
        			param.put("classify", classify);
        		}
        	}
        }
        return param;
    }

	private HashMap<String, Object> assembleCondition(Long merchantPin,
			int signLength, Date startTime, Date endTime, Integer classify, String accessNumber,
			Long userId, Integer intfType, boolean isAdmin, boolean isSend) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("merchantPin", merchantPin);
        if(isSend){
        	if(classify>0){
        		param.put("classify", classify);
        	}
        }else{
        	if(classify==1||classify==2||classify==3||classify==4||classify==5||classify==6||classify==13){
          	  if (!accessNumber.equals("0")) {
          		  param.put("accessNumber", accessNumber);
              }
          	}else{
          		if(classify>0){
          			param.put("classify", classify);
          		}
          	}
        }
        param.put("startDate", startTime);
        param.put("endDate", endTime);
        
        Calendar nowDate = Calendar.getInstance();
        Calendar setDate = Calendar.getInstance();
        nowDate.setTimeInMillis(System.currentTimeMillis());
        setDate.setTime(startTime);
        String monthTable = "";
        if( nowDate.get(Calendar.YEAR) != setDate.get(Calendar.YEAR) || nowDate.get(Calendar.MONTH) != setDate.get(Calendar.MONTH) ){
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            monthTable = "_"+sdf.format(startTime);
        }
        param.put("tableName", "mbn_sms_had_send"+monthTable);
        if( intfType > 0){
        	param.put("webService", intfType);
        }
        if(!isAdmin){
            param.put("createBy", userId);
        }
        return param;
    }

    private int smItems(String content, int signLength) {
        return (content.length() + signLength - 1) / 70 + 1;
    }

    private int smItems(String content) {
        return (content.length() - 1) / 70 + 1;
    }

    private int signLength(Long merchantPin) {
        int nLength = 0;
        MbnConfigMerchant signParam = this.MbnConfigMerchantIService.loadByMerchantPin(merchantPin, "sms_sign_content");
        if ((signParam != null) && (signParam.getItemValue() != null)) {
            nLength = signParam.getItemValue().length();
        }
        return nLength;
    }
}
