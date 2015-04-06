package com.leadtone.mas.bizplug.dati.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDati;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiBean;
@Component("smsDatiDao")
public class MasSmsDatiImpl extends BaseDao implements MasSmsDatiDao {

	protected static String NAMESPACE = "SmsDati";
	protected static String INSERT_INFO = ".insertDT";
	protected static String UPDATE_INFO = ".updateDT";
	protected static String DELETE_INFO = ".deleteDT";
	protected static String QUERY_CREATOR_INFO = ".queryAllDTByCreator";
	protected static String QUERY_PAGE_INFO = ".queryAllDTByCreatorAndSearchInfoForPage";
	//protected static String QUERY_COUNT_INFO = ".queryAllDTByCreatorAndSearchInfoCount";
	protected static String QUERY_COUNT_INFO = ".queryAllDTAndCreatorNameByCreatorAndSearchInfoCount";
	protected static String QUERY_SEARCH_INFO = ".queryDTBySearchInfo";
	protected static String UPDATE_DT_DELETE_STATUS = ".updateDtDeleteStatus";
	@Override
	public void delete(MasSmsDati masSmsDati) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().delete(NAMESPACE+DELETE_INFO,masSmsDati);
	}

	@Override
	public List<MasSmsDati> getMasSmsDatiByCreatorId(long createdBy) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE+QUERY_CREATOR_INFO, createdBy);
		
	}

	@Override
	public void insert(MasSmsDati masSmsDati) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().insert(NAMESPACE+INSERT_INFO,masSmsDati);
	}

	@Override
	public void update(MasSmsDati masSmsDati) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update(NAMESPACE+UPDATE_INFO,masSmsDati);
	}

	@Override
	public int getMasSmsDatiByCreatorIdAndSearchInfoCount(long createdBy,
			String title) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createBy", createdBy);
		map.put("title", title);
		
		return (Integer)this.getSqlMapClientTemplate().queryForObject(NAMESPACE+QUERY_COUNT_INFO, map);
	}

	@Override
	public int getMasSmsDatiByCreatorIdAndSearchInfoCount(
			Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return (Integer)this.getSqlMapClientTemplate().queryForObject(NAMESPACE+QUERY_COUNT_INFO, searchInfo);
	}

	@Override
	public List<MasSmsDati> getMasSmsDatiByCreatorIdAndSearchInfoForPage(
			long createdBy, String title, int startPage, int pageSize) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("createBy", createdBy);
		map.put("title", title);
		map.put("startPage", startPage);
		map.put("pageSize", pageSize);
		return  this.getSqlMapClientTemplate().queryForList(NAMESPACE+QUERY_PAGE_INFO, map);
	}

	@Override
	public List<MasSmsDati> getMasSmsDatiByCreatorIdAndSearchInfoForPage(
			Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE+QUERY_PAGE_INFO, searchInfo);
	}

	@Override
	public MasSmsDati getMasSmsDatiByPinAndMobileAndTaskNumberQueryInfo(
			String mobile, long pinInfo, String taskNumber) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("mobile", mobile);
		map.put("pinInfo", pinInfo);
		map.put("taskNumber", taskNumber);
		return (MasSmsDati)this.getSqlMapClientTemplate().queryForObject(NAMESPACE+QUERY_SEARCH_INFO, map);
	}

	@Override
	public Long getEntIdByPinInfo(long pinInfo) {
		// TODO Auto-generated method stub
		return (Long)this.getSqlMapClientTemplate().queryForObject(NAMESPACE+".queryEntIdByPinInfo", pinInfo);
	}

	@Override
	public void updateDTDeleteStatusBySearchInfo(long creatorId, String... ids) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		List<Long> idList = new ArrayList<Long>();
		for(String id:ids){
			idList.add(Long.parseLong(id));
		}
		map.put("shiJuanIdList", idList);
		map.put("createBy", creatorId);
		this.getSqlMapClientTemplate().update(NAMESPACE+UPDATE_DT_DELETE_STATUS,map);
	}

	@Override
	public List<MasSmsDatiBean> getMasSmsDatiListBySearchInfo(
			Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE+".queryAllDTAndCreatorNameByCreatorAndSearchInfoForPage", searchInfo);
	}

	@Override
	public MasSmsDatiBean getMasSmsDatiBySearchInfo(
			Map<String, Object> searchInfo) {
		// TODO Auto-generated method stub
		
		return (MasSmsDatiBean)this.getSqlMapClientTemplate().queryForObject(NAMESPACE+".queryAllDTByCreatorAndSearchInfo", searchInfo);
	}

}
