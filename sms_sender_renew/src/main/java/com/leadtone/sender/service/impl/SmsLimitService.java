package com.leadtone.sender.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.leadtone.sender.bean.ConfigParam;
import com.leadtone.sender.bean.Consume;
import com.leadtone.sender.bean.SmsLimitBean;
import com.leadtone.sender.dao.local.IMerchantDao;
import com.leadtone.sender.dao.local.ISmsDao;
import com.leadtone.sender.service.ISmsLimitService;
import com.leadtone.util.DateUtil;
import com.leadtone.util.ProperUtil;

public class SmsLimitService implements ISmsLimitService {
	Logger logger = Logger.getLogger(SmsLimitService.class);

    private ISmsDao smsDao;
    private IMerchantDao merchantDao;

    /**
     * 网关可发送条数计算器
     * @param merchantPin 商户pin码
     * @return 
     */
	@Override
	public Map<String,Object> gatewayLimitCalculator(String province, Long merchantPin) {
		Map<String,Object> limitMap = new HashMap<String,Object>();
		ConfigParam configParam = merchantDao.getConfigParam(merchantPin, "sms_send_limit");
		//根据type(1是短信，2是彩信)，classify(1本省移动，2移动，3本省联通，4联通，5本省短信，6电信，7全网，8服务端猫，9TD话机)，来查询该商户的通道consume
		Consume consume = merchantDao.getConsume(merchantPin, 1, 1);
		Date currentDate = Calendar.getInstance().getTime();
		if(consume!=null){
		    limitMap.put("consumeId", consume.getId());
		    if(!DateUtil.isSameHalfHour(consume.getModifyTime(), currentDate)){
			    //上次发送时间与当前时间做比较，若间隔已超过30分钟内，将mbn_merchant_consume表的remain_number初始化
			    merchantDao.updateConsume(consume.getId(), configParam==null?50:Integer.parseInt(configParam.getItemValue()));
			    consume.setRemainNumber(configParam==null?50:Integer.parseInt(configParam.getItemValue()));
			    limitMap.put("remainNumber", consume.getRemainNumber());
		    }else{
		    	consume.setRemainNumber(this.getSystemLimit(province));
		    }
		    limitMap.put("remainNumber", consume.getRemainNumber());
		}else{
		    limitMap.put("remainNumber", 100);
		}
		logger.info("Gateway短信，每半小时可发送短信条数为：["+configParam==null?50:Integer.parseInt(configParam.getItemValue())+"]，当前半小时内还允许发送短信条数为：["+limitMap.get("remainNumber")+"]");
		return limitMap;
	}

    /**
     * 服务端猫可发送条数计算器
     * @param merchantPin 商户pin码
     * @return 
     */
	@Override
	public Map<String,Object> serverModemLimitCalculator(String province, Long merchantPin) {
		Map<String,Object> limitMap = new HashMap<String,Object>();
		ConfigParam configParam = merchantDao.getConfigParam(merchantPin, "sms_send_limit");
		limitMap.put("configParam", configParam==null?50:Integer.parseInt(configParam.getItemValue()));
		//根据type(1是短信，2是彩信)，classify(1本省移动，2移动，3本省联通，4联通，5本省短信，6电信，7全网，8服务端猫，9TD话机)，来查询该商户的通道consume
		Consume consume = merchantDao.getConsume(merchantPin, 1, 8);
		Date currentDate = Calendar.getInstance().getTime();
		if(consume!=null){
		    limitMap.put("consumeId", consume.getId());
		    if(!DateUtil.isSameHalfHour(consume.getModifyTime(), currentDate)){
			    //上次发送时间与当前时间做比较，若间隔已超过30分钟内，将mbn_merchant_consume表的remain_number初始化
			    merchantDao.updateConsume(consume.getId(), configParam==null?50:Integer.parseInt(configParam.getItemValue()));
			    consume.setRemainNumber(configParam==null?50:Integer.parseInt(configParam.getItemValue()));
		    }
		    limitMap.put("remainNumber", consume.getRemainNumber());
		}else{
		    limitMap.put("remainNumber", 100);
		}
		return limitMap;
	}

	@Override
	public void updateConsume(Long consumeId, int count) {
		merchantDao.updateConsume(consumeId, count);
	}
	
	@Override
	public List getProvinceLimit() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public SmsLimitBean getMerchantLimit(Long merchantPin) {
		return smsDao.getLimitByMerchantPin(merchantPin);
	}
	
	private Integer getSystemLimit(String province) {
		String limit = ProperUtil.readValue("sms.system."+province+".limit");
		if(limit ==null || "".equals(limit.trim())){
			limit = ProperUtil.readValue("sms.system.common.limit")==null?"100":ProperUtil.readValue("sms.system.common.limit");
		}
		return Integer.parseInt(limit);
	}
	
	@Override
	public List getCorpLimit(Long merchantPin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getTDLimitCount(Long merchantPin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getGateWayLimitCount(Long merchantPin) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISmsDao getSmsDao() {
		return smsDao;
	}

	public void setSmsDao(ISmsDao smsDao) {
		this.smsDao = smsDao;
	}

	public IMerchantDao getMerchantDao() {
		return merchantDao;
	}

	public void setMerchantDao(IMerchantDao merchantDao) {
		this.merchantDao = merchantDao;
	}

	
}
