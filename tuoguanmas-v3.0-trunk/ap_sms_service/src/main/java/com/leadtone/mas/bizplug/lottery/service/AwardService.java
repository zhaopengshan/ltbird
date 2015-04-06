package com.leadtone.mas.bizplug.lottery.service;

import java.util.List;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsAward;


public interface AwardService {
	
	int addLottery(MasSmsAward award);
	
	List<MasSmsAward> awardList(String id);
	
}
