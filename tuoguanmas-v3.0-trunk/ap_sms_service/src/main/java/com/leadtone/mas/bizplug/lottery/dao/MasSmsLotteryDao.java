package com.leadtone.mas.bizplug.lottery.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;

public interface MasSmsLotteryDao {
	
	int addLottery(MasSmsLottery lottery);
	List<MasSmsLottery> queryLotteryById(Map<String, Object> paraMap,int type);
	int updateLottery(Map<String,Object> param);
	int deleteLottery(String id);
	List<MasSmsLottery> queryLotteryByNoSend(Map<String,Object> param);
	List<MasSmsLottery> queryLotteryByIsSend(Map<String,Object> param);
	List<MasSmsLottery> queryLotteryByIsLottery(Map<String,Object> param);
	int replySmsLottery(Map<String,Object> param);
	MasSmsLottery queryLotteryById(String id);
	List<MasSmsLottery> moSms(Map<String, Object> param);
	
}
