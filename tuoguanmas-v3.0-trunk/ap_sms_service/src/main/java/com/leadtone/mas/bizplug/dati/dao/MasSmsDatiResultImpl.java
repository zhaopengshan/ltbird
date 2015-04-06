package com.leadtone.mas.bizplug.dati.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiResult;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiResultBean;
@Component("smsDatiResultDao")
public class MasSmsDatiResultImpl extends BaseDao implements
		MasSmsDatiResultDao {

	protected static String NAMESPACE = "SmsDati"; 
	protected static String INSERT_INFO = ".insertResult";
	protected static String QUERY_RESULT_INFO = ".queryAllResultByDxDtIdAndMobileAndNumber";
	protected static String QUERY_MAX_NUMBER_INFO = ".queryMaxNumberByMobileInfo";
	protected static String QUERY_GROUP_RESULT = ".queryAllGroupResultByDxDtId";
	protected static String QUERY_GROUP_RESULT_COUNT = ".queryAllGroupResultCountByDxDtId";
	protected static String UPDATE_RESULT_DELETE_STATUS = ".updatedTResultDeleteStatus";
	@Override
	public void insert(MasSmsDatiResult masSmsDatiResult) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().insert(NAMESPACE + INSERT_INFO, masSmsDatiResult);
	}
	@Override
	public List<MasSmsDatiResult> getMosSmsDtResultByIdAndMobileAndOrder(
			long dxdtId, String mobile, int orderNumber) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dxdtId", dxdtId);
		map.put("mobile", mobile);
		map.put("orderNumber", orderNumber);
		
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE+QUERY_RESULT_INFO, map);
		
	}
	@Override
	public Integer getMosSmsDtMaxNumResultBySearch(long dxdtId, String mobile,
			long createdBy) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dxdtId", dxdtId);
		map.put("mobile", mobile);
		map.put("createdBy", createdBy);
		return (Integer)this.getSqlMapClientTemplate().queryForObject(NAMESPACE+QUERY_MAX_NUMBER_INFO,map);
	}
	@Override
	public List<MasSmsDatiResultBean> getMosSmsDtResultListBySearch(long dxdtId,
			long createdBy) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dxdtId", dxdtId);
		map.put("createBy", createdBy);
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE+QUERY_GROUP_RESULT, map);
	}
	@Override
	public Integer getMosSmsDtResultCountBySearchInfo(
			Map<String, Object> mapInfo) {
		// TODO Auto-generated method stub
		return (Integer)this.getSqlMapClientTemplate().queryForObject(NAMESPACE+QUERY_GROUP_RESULT_COUNT, mapInfo);
	}
	@Override
	public List<MasSmsDatiResultBean> getMosSmsDtResultListBySearchInfo(
			Map<String, Object> mapInfo) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE+QUERY_GROUP_RESULT, mapInfo);
	}
	@Override
	public void updateMosSmsResultDeleteStatus(Map<String, Object> mapInfo) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update(NAMESPACE+UPDATE_RESULT_DELETE_STATUS, mapInfo);
	}

}
