package com.leadtone.mas.bizplug.lottery.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsLotteryUpshot;
import com.leadtone.mas.bizplug.lottery.dao.MasSmsLotteryUpshotDao;
@Service("lotteryUpshotService")
public class LotteryUpshotServiceImpl implements LotteryUpshotService {

	private static Log log = LogFactory.getLog(LotteryUpshotServiceImpl.class);
	
	@Resource
	private MasSmsLotteryUpshotDao masSmsLotteryUpshotDao;
	
	public MasSmsLotteryUpshotDao getMasSmsLotteryUpshotDao() {
		return masSmsLotteryUpshotDao;
	}

	public void setMasSmsLotteryUpshotDao(
			MasSmsLotteryUpshotDao masSmsLotteryUpshotDao) {
		this.masSmsLotteryUpshotDao = masSmsLotteryUpshotDao;
	}

	@Override
	public int addLotteryUpshot(MasSmsLotteryUpshot lotteryUpshot) {
		int count=0;
		try{
			masSmsLotteryUpshotDao.addLotteryUpshot(lotteryUpshot);
			count=1;
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return count;
	}

	@Override
	public List<MasSmsLotteryUpshot> queryLotteryUpshotById(Map<String, Object> paraMap) {
		return masSmsLotteryUpshotDao.queryLotteryUpshotList(paraMap);
	}
	
	@Override
	public int deleteLotteryUpshot(String ids) {
		int count=0;
		try{
			masSmsLotteryUpshotDao.deleteLotteryUpshot(ids);
			count=1;
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		return count;
	}
}
