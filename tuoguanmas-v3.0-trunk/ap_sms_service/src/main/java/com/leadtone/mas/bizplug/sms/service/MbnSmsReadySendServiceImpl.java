/**
 * 
 */
package com.leadtone.mas.bizplug.sms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendContainer;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendVO;
import com.leadtone.mas.bizplug.sms.dao.MbnSmsReadySendDao;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsumeFlow;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantConsumeDao;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantConsumeFlowDao;

/**
 * @author PAN-Z-G
 * 
 */
@Service("mbnSmsReadySendService")
@Transactional
@SuppressWarnings("unchecked")
public class MbnSmsReadySendServiceImpl implements MbnSmsReadySendService {
	@Resource
	private MbnSmsReadySendDao mbnSmsReadySendDao;
	@Resource
	private MbnMerchantConsumeFlowDao mbnMerchantConsumeFlowDao;
	@Resource
	private MbnMerchantConsumeDao  mbnMerchantConsumeDao;

	public MbnSmsReadySendVO queryByPk(Long pk){
		return (MbnSmsReadySendVO) mbnSmsReadySendDao.queryByPk(pk);
	}
	public Page page(PageUtil pageUtil) {
		return mbnSmsReadySendDao.page(pageUtil);
	}

	@Override 
	public List<MbnSmsReadySend> mbnSmsReadySendByPks(long pk) {
		// 
		return (List<MbnSmsReadySend>) mbnSmsReadySendDao.queryByPk(pk);
	}

	@Override
	
	public MbnSmsReadySend mbnSmsReadySendByPk(long pk) {
		// 
//		List<MbnSmsReadySend> l = (List<MbnSmsReadySend>) mbnSmsReadySendDao
//				.queryByPk(pk);
//		return l.size() > 0 ? (MbnSmsReadySend) l.get(0) : null;
		MbnSmsReadySend mbnSmsReadySend = (MbnSmsReadySend) mbnSmsReadySendDao
		.queryByPk(pk);
		return mbnSmsReadySend;
	}
	

	@Override
	
	public List<MbnSmsReadySend> mbnSmsReadySendByPins(long pin) {
		// 
		return (List<MbnSmsReadySend>) mbnSmsReadySendDao.queryByPin(pin);
	}
	
	public List<Long> getBatchIdsByPks(String[] ids){
		return mbnSmsReadySendDao.getBatchIdsByPks(ids);
	}

	public List<MbnSmsReadySend> getByPks(Long[] ids){
		return mbnSmsReadySendDao.getByPks(ids);
	}
	public Integer cancelSend(HashMap<String, Object> cancelPro){
		return mbnSmsReadySendDao.cancelSend(cancelPro);
	}
	public List<MbnSmsReadySend> getByBatchId(Long batchId, Long mPin, Long createBy){
		return mbnSmsReadySendDao.getByBatchId( batchId,  mPin,  createBy);
	}
	
	public List<MbnSmsReadySend> getByBatchIds(HashMap<String,Object> batchIds){
		return mbnSmsReadySendDao.getByBatchIds(batchIds);
	}
	
	public Page batchPage(PageUtil pageUtil){
		return mbnSmsReadySendDao.batchPage(pageUtil);
	}
	
	public List<MbnSmsReadySendVO> followPage(HashMap<String,Object> page){
		return mbnSmsReadySendDao.followPage(page);
	}

	@Override
	
	public MbnSmsReadySend mbnSmsReadySendByPin(long pin) {

		List<MbnSmsReadySend> l = (List<MbnSmsReadySend>) mbnSmsReadySendDao
				.queryByPin(pin);
		return l.size() > 0 ? (MbnSmsReadySend) l.get(0) : null;
	}

	@Override
	public Integer pageCount(PageUtil pageUtil) {
		//
		return mbnSmsReadySendDao.pageCount(pageUtil);
	}

	
	@Override
	public Integer insert(MbnSmsReadySend mbnSmsHadSend) {
		//
		return mbnSmsReadySendDao.insert(mbnSmsHadSend);
	}

	
	@Override
	public Integer update(MbnSmsReadySend mbnSmsHadSend) {
		//
		return mbnSmsReadySendDao.update(mbnSmsHadSend);
	}

	
	@Override
	public Integer delete(MbnSmsReadySend mbnSmsHadSend) {
		//
		return mbnSmsReadySendDao.delete(mbnSmsHadSend);
	}

	/**
	 * 访问器
	 * 
	 * @return
	 */
	public MbnSmsReadySendDao getMbnSmsHasSendDao() {
		return mbnSmsReadySendDao;
	}

	public void setMbnSmsHasSendDao(MbnSmsReadySendDao mbnSmsReadySendDao) {
		this.mbnSmsReadySendDao = mbnSmsReadySendDao;
	}
	@Override
	public Integer batchUpdateByList(List<MbnSmsReadySend> paramList) {
		// TODO Auto-generated method stub
		return mbnSmsReadySendDao.batchUpdateByList(paramList);
	}


	@Override
	public Integer batchDeleteByPks(Long[] pks) {
		// TODO Auto-generated method stub
		return mbnSmsReadySendDao.batchDeleteByPks(pks);
	}


	@Override
	public Integer batchDeleteByList(List<MbnSmsReadySend> entitys) {
		// TODO Auto-generated method stub
		return mbnSmsReadySendDao.batchDeleteByList(entitys);
	}


	@Override
	public List<MbnSmsReadySend> batchSelectByPks(Long[] pks) {
		// TODO Auto-generated method stub
		return (List<MbnSmsReadySend>) mbnSmsReadySendDao.batchSelectByPks(pks);
	}

	@Override
	public Integer batchSaveByList(List<MbnSmsReadySend> entitys) {
		// TODO Auto-generated method stub
		return mbnSmsReadySendDao.batchSaveByList(entitys);
	}
	@Override
	public Page pageVO(PageUtil pageUtil) {
		// TODO Auto-generated method stub
		return mbnSmsReadySendDao.pageVO(pageUtil);
	}
	@Override
	public Page replyPage(PageUtil pageUtil) {
		
		
		// TODO Auto-generated method stub
		return mbnSmsReadySendDao.replyPage(pageUtil);
	}
	@Override
	public Integer batchSave(MbnSmsReadySendContainer smsContainer,Integer operationType) {
		if( smsContainer == null || smsContainer.getSmsHashMap() == null){
			return -1;
		}
		Integer result = 0;
		Long merchantPin = smsContainer.getMerchantPin();
		Map<Long, List<MbnSmsReadySend>> smsMap = smsContainer.getSmsHashMap();
		java.util.Iterator<Entry<Long, List<MbnSmsReadySend>>> it = smsMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<Long, List<MbnSmsReadySend>> entry = it.next();
			Long tunnelId = entry.getKey();
			List<MbnSmsReadySend> smsList = entry.getValue();
			if( tunnelId > 0 && smsList != null && smsList.size() > 0){
				// 记录余量
				MbnMerchantConsume consume = mbnMerchantConsumeDao.findByTunnelId(merchantPin, tunnelId);
				if( consume != null && consume.getId() > 0){
					consume.setRemainNumber(consume.getRemainNumber()-smsList.size());
					mbnMerchantConsumeDao.update(consume);
					// 记录流水
					MbnMerchantConsumeFlow flow = new MbnMerchantConsumeFlow();
					flow.setId(PinGen.getSerialPin());
					flow.setMerchantPin(merchantPin);
					flow.setModifyTime(new Date());
					flow.setNumber(Long.valueOf(smsList.size()));
					flow.setOperationType(operationType);
					flow.setTunnelId(tunnelId);
					flow.setRemainNumber(consume.getRemainNumber());
					mbnMerchantConsumeFlowDao.insert(flow);
				}
				// 记录短信发送
				result += mbnSmsReadySendDao.batchSaveByList(smsList);
			}
		}

		return result;
	}
	
	@Override
	public Integer batchSave(MbnSmsReadySendContainer smsContainer) {
		if( smsContainer == null || smsContainer.getSmsHashMap() == null){
			return -1;
		}
		Integer result = 0;
		Long merchantPin = smsContainer.getMerchantPin();
		Map<Long, List<MbnSmsReadySend>> smsMap = smsContainer.getSmsHashMap();
		java.util.Iterator<Entry<Long, List<MbnSmsReadySend>>> it = smsMap.entrySet().iterator();
		while(it.hasNext()){
			Entry<Long, List<MbnSmsReadySend>> entry = it.next();
			Long tunnelId = entry.getKey();
			List<MbnSmsReadySend> smsList = entry.getValue();
			if( tunnelId > 0 && smsList != null && smsList.size() > 0){
				// 记录余量
				MbnMerchantConsume consume = mbnMerchantConsumeDao.findByTunnelId(merchantPin, tunnelId);
				if( consume != null && consume.getId() > 0){
					consume.setRemainNumber(consume.getRemainNumber()-smsList.size());
					mbnMerchantConsumeDao.update(consume);
					// 记录流水
					MbnMerchantConsumeFlow flow = new MbnMerchantConsumeFlow();
					flow.setId(PinGen.getSerialPin());
					flow.setMerchantPin(merchantPin);
					flow.setModifyTime(new Date());
					flow.setNumber(Long.valueOf(smsList.size()));
					flow.setOperationType(2);
					flow.setTunnelId(tunnelId);
					flow.setRemainNumber(consume.getRemainNumber());
					mbnMerchantConsumeFlowDao.insert(flow);
				}
				// 记录短信发送
				result += mbnSmsReadySendDao.batchSaveByList(smsList);
			}
		}

		return result;
	}
	@Override
	public Page extPortAll(PageUtil pageUtil) {
		// TODO Auto-generated method stub
		return mbnSmsReadySendDao.extPortAll(pageUtil);
	}
 
 
}
