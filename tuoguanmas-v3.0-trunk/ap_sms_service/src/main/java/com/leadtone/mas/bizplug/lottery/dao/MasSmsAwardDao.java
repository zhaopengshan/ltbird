package com.leadtone.mas.bizplug.lottery.dao;

import java.util.List;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsAward;

public interface MasSmsAwardDao {
	
	int addAward(MasSmsAward award);
	int deleteAward(String id);
	int updateAward(MasSmsAward award);
	List<MasSmsAward> queryAwardList(String id);
}
