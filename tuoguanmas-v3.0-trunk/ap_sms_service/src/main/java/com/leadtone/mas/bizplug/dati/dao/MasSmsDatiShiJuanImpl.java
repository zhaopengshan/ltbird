package com.leadtone.mas.bizplug.dati.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiShiJuan;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiTiKuInfo;
@Component("smsDatiShiJuanDao")
public class MasSmsDatiShiJuanImpl extends BaseDao implements MasSmsDatiShiJuanDao {
    public static String NAMESPACE = "SmsDati";
    protected static String INSERT_INFO = ".insertSJ";
	protected static String UPDATE_INFO = ".updateSJ";
	protected static String DELETE_INFO = ".deleteSJ"; 
	protected static String QUERY_DXDTID_INFO = ".queryAllSJByDxDtId";
	protected static String INSERT_SELECT_INFO = ".insertSJSelectTiKu";
	protected static String DELETE_DXDT_INFO = ".deleteSJByDxdtId";
	protected static String QUERY_SEARCH_INFO = ".querySJBySearchInfo";
	@Override
	public void delete(MasSmsDatiShiJuan masSmsDatiShiJuan) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().delete(NAMESPACE+DELETE_INFO, masSmsDatiShiJuan);
	}

	@Override
	public List<MasSmsDatiShiJuan> getMasSmsDatiShiJuanByDatiId(long datiId) {
		// TODO Auto-generated method stub
		return this.getSqlMapClientTemplate().queryForList(NAMESPACE+QUERY_DXDTID_INFO, datiId);
		//return null;
	}

	@Override
	public void insert(MasSmsDatiShiJuan masSmsDatiShiJuan) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().insert(NAMESPACE+INSERT_INFO, masSmsDatiShiJuan);
	}

	@Override
	public void update(MasSmsDatiShiJuan masSmsDatiShiJuan) {
		// TODO Auto-generated method stub
		this.getSqlMapClientTemplate().update(NAMESPACE+UPDATE_INFO, masSmsDatiShiJuan);
	}

	

	@Override
	public void insertShiJuanSelectTiKu( long dxdtId, 
			long createBy, List<MasSmsDatiTiKuInfo> datiTiKuInfoList) {
		// TODO Auto-generated method stub
		for(MasSmsDatiTiKuInfo tikuInfo:datiTiKuInfoList){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", PinGen.getSerialPin());
			map.put("dxdtId", dxdtId);
			map.put("createTime", new Date());
			map.put("createBy", createBy);
			map.put("orderNumber", tikuInfo.getSerialId());
			map.put("tiKuId", tikuInfo.getTikuId());
			this.getSqlMapClientTemplate().insert(NAMESPACE+INSERT_SELECT_INFO,map);
		}
		
	}

	@Override
	public void delete(long dxdtId, long creatorId) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dxdtId", dxdtId);
		map.put("createBy", creatorId);
		this.getSqlMapClientTemplate().delete(NAMESPACE+DELETE_DXDT_INFO, map);
	}

	@Override
	public MasSmsDatiShiJuan getMasSmsDatiShiJuanBySearchInfo(long dxdtId,
			int orderNumber) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("dxdtId", dxdtId);
		map.put("orderNumber", orderNumber);
		
		return (MasSmsDatiShiJuan)this.getSqlMapClientTemplate().queryForObject(NAMESPACE+QUERY_SEARCH_INFO, map);
	}

}
