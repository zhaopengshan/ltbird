package com.leadtone.mas.bizplug.lottery.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsLottery;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsLotteryUpshot;

@Component("masSmsLotteryUpshotDao")
@SuppressWarnings("unchecked")
public class MasSmsLotteryUpshotDaoImpl extends BaseDao implements MasSmsLotteryUpshotDao {

	protected static String NAMESPACE = "Lottery";

	@Override
	public int addLotteryUpshot(MasSmsLotteryUpshot lotteryUpshot){
		String count=this.getSqlMapClientTemplate().insert(NAMESPACE + ".addSmsLotteryUpshot", lotteryUpshot).toString();
		return  Integer.parseInt(count);
	}
	
	@Override
	public List<MasSmsLotteryUpshot> queryLotteryUpshotList(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".selesctSmsLotteryUpshot",paraMap);
	}
	
	@Override
	public int updateLotteryUpshot(MasSmsLotteryUpshot lotteryUpshot) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().update(NAMESPACE + ".updateSmsLotteryUpshot",lotteryUpshot);
	}
	
	@Override
	public int deleteLotteryUpshot(String id) {
		String[] ids=id.split(",");
		return 	this.getSqlMapClientTemplate().delete(NAMESPACE + ".deleteSmsLotteryUpshot",ids);
	}
}
