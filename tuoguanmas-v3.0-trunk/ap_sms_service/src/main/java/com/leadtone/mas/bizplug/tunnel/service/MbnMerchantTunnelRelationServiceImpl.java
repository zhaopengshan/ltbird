package com.leadtone.mas.bizplug.tunnel.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsumeFlow;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantConsumeDao;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantConsumeFlowDao;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantTunnelRelationDao;
import com.leadtone.mas.bizplug.tunnel.dao.SmsMbnTunnelDao;
import com.leadtone.mas.bizplug.tunnel.dao.SmsTunnelAccountDao;

@Service(value = "mbnMerchantTunnelRelationService")
@Transactional
public class MbnMerchantTunnelRelationServiceImpl implements
		MbnMerchantTunnelRelationService {
	private static Logger logger = Logger.getLogger(MbnMerchantTunnelRelationServiceImpl.class.getName());
	@Resource
	private MbnMerchantTunnelRelationDao mbnMerchantTunnelRelationDao;
	@Resource
	private SmsMbnTunnelDao smsMbnTunnelDao;
	@Resource
	private SmsTunnelAccountDao smsTunnelAccountDao;
	@Resource
	private MbnMerchantConsumeDao mbnMerchantConsumeDao;
	@Resource
	private MbnMerchantConsumeFlowDao mbnMerchantConsumeFlowDao;

	public List<MbnMerchantTunnelRelation> findByTunnelType(Long merchantPin,
			Long type) {
		return mbnMerchantTunnelRelationDao.findByTunnelType(merchantPin, type);
	}

	public List<MbnMerchantTunnelRelation> findByClassify(Long merchantPin,
			Integer classify) {
		return mbnMerchantTunnelRelationDao.findByClassify(merchantPin, classify);
	}
	
	@Override
	public List<MbnMerchantTunnelRelation> findByClassifyAndType(Long merchantPin,
			Integer classify, Integer type) {
		return mbnMerchantTunnelRelationDao.findByClassifyAndType(merchantPin, classify, type);
	}

	
	@Override
	public List<MbnMerchantTunnelRelation> findByPin(Long merchantPin) {
		// TODO Auto-generated method stub
		return mbnMerchantTunnelRelationDao.findByPin(merchantPin);
	}

	@Override
	public MbnMerchantTunnelRelation findByPinAndTunnelId(Long merchantPin,
			Long tunnelId) {
		// TODO Auto-generated method stub
		return mbnMerchantTunnelRelationDao.findByPinAndTunnelId(merchantPin,
				tunnelId);
	}

	@Override
	public boolean add(MbnMerchantTunnelRelation mmtr) {
		this.mbnMerchantTunnelRelationDao.insert(mmtr);
		return true;
	}

	@Override
	public boolean update(MbnMerchantTunnelRelation mmtr) {
		this.mbnMerchantTunnelRelationDao.update(mmtr);
		return true;
	}

	/**
	 * 取得AccessNumber的扩展号
	 */
	@Override
	public String getAccessNumber(Long merchantPin, SmsMbnTunnel smt) {
                
        MbnMerchantTunnelRelation isExist=this.mbnMerchantTunnelRelationDao.findByPinAndTunnelId( merchantPin,smt.getId());
        if(isExist!=null){
             return isExist.getTunnelExtCode();
        }
		String max = "";
		for (int i = 0; i < smt.getCorpExtLen(); i++) {
			max += "9";
		}
		// 取得最大值
		MbnMerchantTunnelRelation mmtr = mbnMerchantTunnelRelationDao
				.getMaxUsed(smt.getId());

		// 未使用，返回AccessNumber后面补0
		if (mmtr == null) {
			String temp = "";
			for (int i = 0; i < smt.getCorpExtLen(); i++) {
				temp += "0";
			}
			return temp;
		}
		// 已使用，判断是否<1000
		if (!mmtr.getTunnelExtCode().equals(max)) {
			String tmp = mmtr.getTunnelExtCode();
			Long num = Long.parseLong(tmp);
			num++;
			return String.format("%0"+smt.getCorpExtLen()+"d", num);
		}
		// 查询已经过期的最小的一个
		MbnMerchantTunnelRelation lastmmtr = mbnMerchantTunnelRelationDao
				.getExpireLastUsed(smt.getId());
		if (lastmmtr != null) {
			mbnMerchantTunnelRelationDao.delete(lastmmtr);
			return lastmmtr.getTunnelExtCode();
		}
		// 如果没有可以使用的返回-1
		return "-1";
	}

	public MbnMerchantTunnelRelationDao getMbnMerchantTunnelRelationDao() {
		return mbnMerchantTunnelRelationDao;
	}

	public SmsMbnTunnelDao getSmsMbnTunnelDao() {
		return smsMbnTunnelDao;
	}

	@Override
	public void corpCharge(MbnMerchantTunnelRelation mmtr,
			MbnMerchantConsumeFlow mmcf, Map<String, Object> entityMap,Float chargePrice,Users user) {
		// mbnMerchantTunnelRelation;pin,tunnelid,
		// mbnMerchantConsumeFlow;operationType,number
		try {
			SmsMbnTunnelVO smt = smsMbnTunnelDao.queryByTunnelId(mmtr
					.getTunnelId());
			String ext_code = this.getAccessNumber(mmtr.getMerchantPin(), smt);
			if (ext_code.equals("-1")) {
				entityMap.put("flag", false);
				entityMap.put("resultMsg", "给定通到没有可用的扩展号");
				return;
			}
			mmtr.setTunnelExtCode(ext_code);
			mmtr.setAccessNumber(smt.getAccessNumber() + ext_code);
			mmtr.setPrice(0f);
			mmtr.setState(1);
			MbnMerchantTunnelRelation dbmmtr = this.mbnMerchantTunnelRelationDao
					.findByPinAndTunnelId(mmtr.getMerchantPin(),
							mmtr.getTunnelId());
			if (dbmmtr == null) {
				mmtr.setId(PinGen.getSerialPin());
				this.mbnMerchantTunnelRelationDao.insert(mmtr);
			} else {
				this.mbnMerchantTunnelRelationDao.update(mmtr);
			}
			SmsTunnelAccount sta = new SmsTunnelAccount();
			SmsMbnTunnelAccountFlow smta = new SmsMbnTunnelAccountFlow();
			MbnMerchantConsume mmc = new MbnMerchantConsume();
			// MbnMerchantConsumeFlow mmcf=null;
			if (mmcf.getOperationType() == 1 || mmcf.getOperationType() == 2) {// 客户充值
				List<SmsTunnelAccount> staList = this.smsTunnelAccountDao
						.getByTunnelIdList(mmtr.getTunnelId());
				if (staList.size() > 0) {
					sta = staList.get(0);
					sta.setBalanceNumber(sta.getBalanceNumber()
							- mmcf.getNumber());
					sta.setBalanceAmount(sta.getBalanceAmount()
							+ (chargePrice));
					sta.setModifyTime(new Date());
					// 更新
					if(sta.getBalanceNumber()<0){
						throw new RuntimeException();
					}
					this.smsTunnelAccountDao.updateSmsTunnelAccount(sta);
				} else {
					sta.setId(PinGen.getSerialPin());
					sta.setTunnelId(mmtr.getTunnelId());
					sta.setBalanceNumber(0 - mmcf.getNumber());
					sta.setBalanceAmount(0 + (chargePrice));
					sta.setModifyTime(new Date());
					// 增加
					if(sta.getBalanceNumber()<0){
						throw new RuntimeException();
					}
					this.smsTunnelAccountDao.insert(sta);
				}
				smta.setId(PinGen.getSerialPin());
				smta.setTunnelId(mmtr.getTunnelId());
				smta.setOperationType(4);
				smta.setNumber(mmcf.getNumber());
				smta.setAmount((chargePrice));
				smta.setModifyTime(new Date());
				smta.setModifyBy(user.getAccount());
				smta.setModifyByPin(user.getMerchantPin());
				smta.setBalanceNumber(sta.getBalanceNumber());
				// 增加
				this.smsTunnelAccountDao.insertTunnelAccountFlow(smta);
				MbnMerchantConsume dbmmc = this.mbnMerchantConsumeDao.findByTunnelId(mmtr.getMerchantPin(), mmtr.getTunnelId());
				if (dbmmc!=null) {
					mmc = dbmmc;
					mmc.setRemainNumber(mmc.getRemainNumber()
							+ mmcf.getNumber());
					mmc.setModifyTime(new Date());
					// 更新
					if(mmc.getRemainNumber()<0){
						throw new RuntimeException();
					}
					this.mbnMerchantConsumeDao.update(mmc);
				} else {
					mmc.setId(PinGen.getSerialPin());
					mmc.setMerchantPin(mmtr.getMerchantPin());
					mmc.setTunnelId(mmtr.getTunnelId());
					mmc.setRemainNumber(0 + mmcf.getNumber());
					mmc.setModifyTime(new Date());
					// 增加
					if(mmc.getRemainNumber()<0){
						throw new RuntimeException();
					}
					this.mbnMerchantConsumeDao.insert(mmc);
				}
				mmcf.setId(PinGen.getSerialPin());
				mmcf.setMerchantPin(mmtr.getMerchantPin());
				mmcf.setTunnelId(mmtr.getTunnelId());
				mmcf.setModifyTime(new Date());
				mmcf.setRemainNumber(mmc.getRemainNumber());
				// 增加
				this.mbnMerchantConsumeFlowDao.insert(mmcf);
				entityMap.put("flag", true);
				entityMap.put("resultMsg", "充值成功");
				return;
			} else if (mmcf.getOperationType() == 3
					|| mmcf.getOperationType() == 4) {// 客户退订
				List<SmsTunnelAccount> staList = this.smsTunnelAccountDao
						.getByTunnelIdList(mmtr.getTunnelId());
				if (staList.size() > 0) {
					sta = staList.get(0);
					sta.setBalanceNumber(sta.getBalanceNumber()
							+ mmcf.getNumber());
					sta.setBalanceAmount(sta.getBalanceAmount()
							- (chargePrice));
					sta.setModifyTime(new Date());
					// 更新
					if(sta.getBalanceNumber()<0){
						throw new RuntimeException();
					}
					this.smsTunnelAccountDao.updateSmsTunnelAccount(sta);
				} else {
					sta.setId(PinGen.getSerialPin());
					sta.setTunnelId(mmtr.getTunnelId());
					sta.setBalanceNumber(0 + mmcf.getNumber());
					sta.setBalanceAmount(0 - (chargePrice));
					sta.setModifyTime(new Date());
					// 增加
					if(sta.getBalanceNumber()<0){
                                            throw new RuntimeException();
					}
					this.smsTunnelAccountDao.insert(sta);
				}
				smta.setId(PinGen.getSerialPin());
				smta.setTunnelId(mmtr.getTunnelId());
				smta.setOperationType(5);//
				smta.setNumber(mmcf.getNumber());
				smta.setAmount((chargePrice));
				smta.setModifyTime(new Date());
				smta.setModifyBy(user.getAccount());
				smta.setModifyByPin(user.getMerchantPin());
				smta.setBalanceNumber(sta.getBalanceNumber());
				// 增加
				this.smsTunnelAccountDao.insertTunnelAccountFlow(smta);
				MbnMerchantConsume dbmmc = this.mbnMerchantConsumeDao.findByTunnelId(mmtr.getMerchantPin(), mmtr.getTunnelId());
				if (dbmmc!=null) {
					mmc = dbmmc;
					mmc.setRemainNumber(mmc.getRemainNumber()
							- mmcf.getNumber());
					mmc.setModifyTime(new Date());
					// 更新
					if(mmc.getRemainNumber()<0){
						throw new RuntimeException();
					}
					this.mbnMerchantConsumeDao.update(mmc);
				} else {
					mmc.setId(PinGen.getSerialPin());
					mmc.setMerchantPin(mmtr.getMerchantPin());
					mmc.setTunnelId(mmtr.getTunnelId());
					mmc.setRemainNumber(0 - mmcf.getNumber());
					mmc.setModifyTime(new Date());
					// 增加
					if(mmc.getRemainNumber()<0){
						throw new RuntimeException();
					}
					this.mbnMerchantConsumeDao.insert(mmc);
				}
				mmcf.setId(PinGen.getSerialPin());
				mmcf.setMerchantPin(mmtr.getMerchantPin());
				mmcf.setTunnelId(mmtr.getTunnelId());
				mmcf.setModifyTime(new Date());
				mmcf.setRemainNumber(mmc.getRemainNumber());
				// 增加
				this.mbnMerchantConsumeFlowDao.insert(mmcf);
				entityMap.put("flag", true);
				entityMap.put("resultMsg", "退订成功");
				return;
			} else {
				entityMap.put("flag", false);
				entityMap.put("resultMsg", "操作类型不正确");
				return;
			}
		}catch(RuntimeException e){
			entityMap.put("flag", false);
			entityMap.put("resultMsg", "您的余额不足或通道剩余短信条数不足，充值失败");
			return;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("充值失败："+e);
			entityMap.put("flag", false);
			entityMap.put("resultMsg", "充值失败");
			return;
		}
	}
	@Override
	public List<MbnMerchantTunnelRelation> findByAccessNumberAndType(Long merchantPin, 
			String accessNumber, Integer type){
		return mbnMerchantTunnelRelationDao.findByAccessNumberAndType(merchantPin, accessNumber, type);
	}
}
