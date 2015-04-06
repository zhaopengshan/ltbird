package com.leadtone.mas.bizplug.lottery.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.lottery.bean.MasSmsAward;

@Component("masSmsAwardDao")
@SuppressWarnings("unchecked")
public class MasSmsAwardDaoImpl extends BaseDao implements MasSmsAwardDao {

	protected static String NAMESPACE = "Lottery";

	public int addAward(MasSmsAward award){
		int count=0;
		try{
			this.getSqlMapClientTemplate().insert(NAMESPACE + ".addSmsAward", award);
			count=1;
		}catch(Exception e){
			e.printStackTrace();
		}
		return count;
	}
	
	@Override
	public int deleteAward(String id) {
		String[] ids=id.split(",");
		return 	this.getSqlMapClientTemplate().delete(NAMESPACE + ".deleteSmsAward",ids);
	}
	
	@Override
	public List<MasSmsAward> queryAwardList(String id) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".selesctSmsAward",id);
	}
	
	@Override
	public int updateAward(MasSmsAward award) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().update(NAMESPACE+".updateSmsAward",award);
	}
}
