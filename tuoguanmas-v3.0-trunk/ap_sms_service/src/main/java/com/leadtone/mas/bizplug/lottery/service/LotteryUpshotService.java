package com.leadtone.mas.bizplug.lottery.service;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsLotteryUpshot;

public interface LotteryUpshotService {
	/**
	 * 新增抽奖任务
	 * @param lottery
	 * @return
	 */
	int addLotteryUpshot(MasSmsLotteryUpshot lotteryUpshot);

	List<MasSmsLotteryUpshot> queryLotteryUpshotById(Map<String, Object> paraMap);
	
	int deleteLotteryUpshot(String ids);
	
}
