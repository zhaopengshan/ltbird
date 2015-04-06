package com.leadtone.mas.bizplug.lottery.service;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;

public interface LotteryService {
	/**
	 * 新增抽奖任务
	 * @param lottery
	 * @return
	 */
	int addLottery(MasSmsLottery lottery);
	
	/**
	 * 根据用户查询任务列表
	 * @param paraMap
	 * @return
	 */
	List<MasSmsLottery> queryLotteryById(Map<String, Object> paraMap,int type);
	
	int deleteLottery(String id);
	
	
	List<MasSmsLottery> filterLottery(Map<String,Object> param);
	
	int replySmsLottery(Map<String,Object> param);
	
	boolean handleLottery(MbnSmsInbox inbox); 
	
	MasSmsLottery queryLotteryById(String id);
	
	List<MasSmsLottery> moSms(Map<String,Object> param);

	int updateLottery(Map<String,Object> param);
	
}
