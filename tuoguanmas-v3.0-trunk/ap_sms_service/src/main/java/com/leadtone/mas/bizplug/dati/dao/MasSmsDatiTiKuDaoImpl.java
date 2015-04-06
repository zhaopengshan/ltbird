package com.leadtone.mas.bizplug.dati.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiTiKu;

@Component("smsDatiTiKuDao")
public class MasSmsDatiTiKuDaoImpl extends BaseDao implements
		MasSmsDatiTiKuDao {
	
	protected static String NAMESPACE = "SmsDati";
	
	@Override
	public void delete(MasSmsDatiTiKu mbnSmsDatiTiKu) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().delete(NAMESPACE + ".deleteTiKu", mbnSmsDatiTiKu);
	}

	@Override
	public void insert(MasSmsDatiTiKu mbnSmsDatiTiKu) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().insert(NAMESPACE + ".insertTiKu", mbnSmsDatiTiKu);
	}

	@Override
	public void update(MasSmsDatiTiKu mbnSmsDatiTiKu) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update(NAMESPACE + ".updateTiKu", mbnSmsDatiTiKu);
	}

	@Override
	public List<MasSmsDatiTiKu> getSmsDatiTiKuListById(long createdBy) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryAllTiKuByCreator", createdBy);
		
	}

	@Override
	public void delete(long tikuId, long creatorId) {
		// TODO Auto-generated method stub
		//deleteTiKuByIdAndCreator
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", tikuId);
		map.put("createBy", creatorId);
		this.getSqlMapClientTemplate().delete(NAMESPACE+".deleteTiKuByIdAndCreator",map);
	}

	@Override
	public List<MasSmsDatiTiKu> getSmsDatiTiKuListByKeywordAndCreatorForPage(
			long creatorId, String titleKeyword, String startDate,
			String endDate, int startPage, int pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("createdBy", creatorId);
		map.put("title", titleKeyword);
		map.put("startDate", startDate);
		map.put("endDate",endDate);
		map.put("startPage",startPage);
		map.put("pageSize",pageSize);
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryAllTiKuByKeyWordCreatorForPage", map);
		
	}

	@Override
	public Integer getSmsDatiTiKuCountByKeywordAndCreator(long creatorId,
			String titleKeyword, String startDate, String endDate) {
		// TODO Auto-generated method stub
		
        Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("createdBy", creatorId);
		map.put("title", titleKeyword);
		map.put("startDate", startDate);
		map.put("endDate",endDate);
		
		return (Integer)this.getSqlMapClientTemplate().queryForObject(NAMESPACE + ".queryAllTiKuByKeyWordCreatorCount", map);
	}

	@Override
	public Integer getSmsDatiTiKuCountByKeywordAndCreator(
			Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return (Integer)this.getSqlMapClientTemplate().queryForObject(NAMESPACE + ".queryAllTiKuByKeyWordCreatorCount", searchInfo);
	}

	@Override
	public List<MasSmsDatiTiKu> getSmsDatiTiKuListByKeywordAndCreatorForPage(
			Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryAllTiKuByKeyWordCreatorForPage", searchInfo);
	}

	@Override
	public void updateDeleteStatus(long tikuId, long creatorId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", tikuId);
		map.put("createBy", creatorId);
		this.getSqlMapClientTemplate().update(NAMESPACE+".updateTiKuDeleteStatus",map);
		
	}

	@Override
	public MasSmsDatiTiKu getTiKuRecordById(long tikuId, long creatorId) {
		// TODO Auto-generated method stub
		//queryTiKuByCreatorAndId
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createdBy",creatorId);
		map.put("tikuId", tikuId);
		return (MasSmsDatiTiKu)this.getSqlMapClientTemplate().queryForObject(NAMESPACE + ".queryTiKuByCreatorAndId", map);
		
	}

	@Override
	public void updateSmsDatiTiKuInfo(String question, String answer,
			int score, long tikuId,long createBy) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("question", question);
		map.put("answer", answer);
		map.put("score", score);
		map.put("modifyTime", new Date());
		map.put("tikuId", tikuId);
		map.put("createBy", createBy);
		this.getSqlMapClientTemplate().update(NAMESPACE+".updateTiKuShortInfo",map);
		
	}

	@Override
	public Integer getSmsDatiTiKuSumBySearchInfo(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return (Integer)this.getSqlMapClientTemplate().queryForObject(NAMESPACE + ".queryAllTiKuByCreatorSum", map);
	}

}
