package com.leadtone.mas.bizplug.lottery.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsLotteryUpshot;

public interface MasSmsLotteryUpshotDao {
	
	int addLotteryUpshot(MasSmsLotteryUpshot Upshot);
	List<MasSmsLotteryUpshot> queryLotteryUpshotList(Map<String, Object> paraMap);
	int updateLotteryUpshot(MasSmsLotteryUpshot lotteryUpshot);
	int deleteLotteryUpshot(String id);
}
