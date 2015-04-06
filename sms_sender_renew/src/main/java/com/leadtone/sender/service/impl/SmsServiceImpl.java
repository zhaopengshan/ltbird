package com.leadtone.sender.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import com.leadtone.sender.bean.GatewaySmsBean;
import com.leadtone.sender.bean.MbnSmsInbox;
import com.leadtone.sender.bean.ModemSmsBean;
import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.bean.UTcomGatewaySmsBean;
import com.leadtone.sender.bean.User;
import com.leadtone.sender.bean.ZxtReceiveBean;
import com.leadtone.sender.dao.local.ISmsDao;
import com.leadtone.sender.dao.modem.IModemSmsDao;
import com.leadtone.sender.service.ISmsService;

public class SmsServiceImpl implements ISmsService {
    private ISmsDao smsDao;
    private IModemSmsDao modemSmsDao;


	@Override
	public void saveHttpSmsRsp(String taskId, Long smsId, Long userId, String status) {
		smsDao.saveHttpSmsRsp(taskId,smsId,userId,status);		
	}
    
	@Override
	public void savePukerSmsRsp(String taskId, Long smsId) {
		smsDao.savePukerSmsRsp(taskId,smsId);		
	}
	
	@Override
	public List getProvinceByTunnelType(int tunnelType, int sendResult, String readSendTime) {
		return smsDao.getProvinceByTunnelType(tunnelType,sendResult,readSendTime);
	}

	@Override
	public List<Long> getMerchantPinList(int tunnelType,int sendResult,String province, String readSendTime) {
		return smsDao.getMerchantPinList(tunnelType,sendResult,province,readSendTime);
	}
	@Override
	public List getMerchantPinList(int sendResult, String readSendTime) {
		return smsDao.getMerchantPinList(sendResult, readSendTime);
	}
	
	@Override
	public List<Integer> getMerchantPriorityList(Long merchantPin, int tunnelType, int sendResult,
			String province, String readSendTime) {
		return smsDao.getMerchantPriorityList(merchantPin, tunnelType, sendResult, province, readSendTime);
	}
	
	@Override
	public List<Long> getMerchantPriorityBatchList(Long merchantPin,
			int tunnelType, int sendResult, String readSendTime,
			int priority) {
		return smsDao.getMerchantPriorityBatchList(merchantPin, tunnelType, sendResult, readSendTime, priority);
	}
	
	@Override
	public List<Map<String, Object>> saveGatewaySms(List<GatewaySmsBean> list) {
		return smsDao.saveGatewaySms(list);
	}
	@Override
	public List<Map<String, Object>> saveEmppSms(List<GatewaySmsBean> list) {
		return smsDao.saveEmppSms(list);
	}
	
	@Override
	public List<Map<String, Object>> saveModemSms(List<ModemSmsBean> list){
		return modemSmsDao.saveModemSms(list);
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
	public void updateSmsSendRestlt(List<Map<String, Object>> list) {
		 smsDao.updateSmsSendRestlt(list);		
	}
	
	@Override
	public void updateGatewaySms(List<Map<String, Object>> list) {
		smsDao.updateGatewaySms(list);
		
	}
	@Override
	public void updateEmppSms(List<Map<String, Object>> list) {
		smsDao.updateEmppSms(list);
		
	}
	@Override
	public void updateLtdxGatewaySms(List<Map<String, Object>> list) {
		smsDao.updateLtdxGatewaySms(list);
		
	}
	
	@Override
	public List getGatewaySmsResult() {
		return smsDao.getGatewaySmsResult(100);
	}
	
	@Override
	public List getEmppSmsResult() {
		return smsDao.getEmppSmsResult(100);
	}
	
	@Override
	public List getLtdxGatewaySmsResult() {
		return smsDao.getLtdxGatewaySmsResult(100);
	}
	
	@Override
	public List getHttpSmsResult() {
		return smsDao.getHttpSmsResult(100);
	}
	
	@Override
	public List getModemSmsResult() {
		return modemSmsDao.getModemSmsResult(100);
	}
	@Override
	public List<SmsBean> getZxtSmsResult(){
		return smsDao.getZxtSmsResult(100);
	}
	
	@Override
	public List<SmsBean> getQxtSmsResult(){
		return smsDao.getQxtSmsResult(100);
	}
	
	@Override
	public void updateZxtSmsSendRestlt(List<SmsBean> list){
		smsDao.updateZxtSmsSendRestlt(list);
	}
	@Override
	public void updateQxtSmsSendRestlt(List<SmsBean> list){
		smsDao.updateQxtSmsSendRestlt(list);
	}
	@Override
	public void updateQxtNewSmsSendRestlt(List<SmsBean> list){
		smsDao.updateQxtNewSmsSendRestlt(list);
	}
	@Override
	public List<ZxtReceiveBean> getZxtMoBean(){
		return smsDao.getZxtMoBean(100);
	}
	@Override
	public void updateZxtMoBean(List<ZxtReceiveBean> list){
		smsDao.updateZxtMoBean(list);
	}
	@Override
	public void updateZxtMoRestlt(List<SmsBean> list){
		smsDao.updateZxtMoRestlt(list);
	}
	@Override
	public void updateQxtMoRestlt(List<SmsBean> list){
		smsDao.updateQxtMoRestlt(list);
	}
	@Override
	public void updateQxtNewProcRestlt(List<SmsBean> list){
		smsDao.updateQxtNewProcRestlt(list);
	}
	
	@Override
	public void deleteSendedSms(List<Map<String, Object>> list) {
		modemSmsDao.deleteSendedSms(list);	
	}
	
	public IModemSmsDao getModemSmsDao() {
		return modemSmsDao;
	}

	public void setModemSmsDao(IModemSmsDao modemSmsDao) {
		this.modemSmsDao = modemSmsDao;
	}

	public ISmsDao getSmsDao() {
		return smsDao;
	}

	public void setSmsDao(ISmsDao smsDao) {
		this.smsDao = smsDao;
	}

	@Override
	public List<Long> getsmsHttpRsp(Long taskId) {
		return smsDao.getsmsHttpRsp(taskId);
	}

	@Override
	public void updateSmsHttp(Long taskId,String status) {
		smsDao.updateSmsHttp(taskId,status);
	}

	@Override
	public User getUserByZXTId(String zxtUserId) {
		return smsDao.getUserByZXTId(zxtUserId);
	}
	
	@Override
	public  List<User> getUsers() {
		return smsDao.getUsers();
	}
	
	@Override
	public Integer addUpSms(Long id,String sender,String receiver,String content,Timestamp createTime){
		return smsDao.addUpSms(id,sender,receiver,content,createTime);
		
	}
	
	@Override
	public void getBackLimit(String userId){
		smsDao.getBackLimit(userId);
	}
	
	@Override
	public List<Map<String, Object>> saveUTcomGatewaySms(
			List<UTcomGatewaySmsBean> list) {
		return smsDao.saveUTcomGatewaySms(list);
	}

	@Override
	public List<SmsBean> getReadySendSmsByPriority(Long merchantPin,
			int tunnelType, int sendResult, String readSendTime, int limit,
			int priority) {
		return smsDao.getReadySendSmsByPriority(merchantPin, tunnelType, sendResult, readSendTime, limit, priority);
	}

	@Override
	public List<SmsBean> getReadySendSmsByPriorityBatch(Long merchantPin,
			int tunnelType, int sendResult, String readSendTime, int limit,
			int priority, Long batchId) {
		return smsDao.getReadySendSmsByPriorityBatch(merchantPin, tunnelType, sendResult, readSendTime, limit, priority, batchId);
	}

	@Override
	public List<Map<String, Object>> saveZxtDriverSms(List<SmsBean> list) {
		return smsDao.saveZxtDriverSms(list);
	}
	@Override
	public List<Map<String, Object>> saveQxtDriverSms(List<SmsBean> list) {
		return smsDao.saveQxtDriverSms(list);
	}
	@Override
	public void updateSmsSendCancel(List<SmsBean> list){
		smsDao.updateSmsSendCancel(list);
	}
	
	@Override
	public void saveZxtMoToInbox(List<MbnSmsInbox> list){
		smsDao.saveZxtMoToInbox(list);
	}
	
	@Override
	public List<Map<String, Object>> saveQxtNewDriverSms(List<SmsBean> list) {
		return smsDao.saveQxtNewDriverSms(list);
	}
	@Override
	public List<SmsBean> getQxtNewSmsResult(){
		return smsDao.getQxtNewSmsResult(100);
	}
}
