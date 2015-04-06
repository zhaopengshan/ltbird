package com.leadtone.mas.bizplug.lottery.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsAward;
import com.leadtone.mas.bizplug.lottery.dao.MasSmsAwardDao;
@Service("awardService")
public class AwardServiceImpl implements AwardService{

	private static Log log = LogFactory.getLog(AwardServiceImpl.class);
	
	@Resource
	private MasSmsAwardDao masSmsAwardDao;
	
	public MasSmsAwardDao getMasSmsAwardDao() {
		return masSmsAwardDao;
	}

	public void setMasSmsAwardDao(MasSmsAwardDao masSmsAwardDao) {
		this.masSmsAwardDao = masSmsAwardDao;
	}

	@Override
	public int addLottery(MasSmsAward award) {
		if(award==null){
			log.info("添加award为null");
			return 0;
		}
		return masSmsAwardDao.addAward(award);
		
	}
	
	
	@Override
	public List<MasSmsAward> awardList(String id) {
		
		return masSmsAwardDao.queryAwardList(id);
	}
}
