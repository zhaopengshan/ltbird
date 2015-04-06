package com.leadtone.mas.bizplug.tunnel.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.dao.base.TunnelBaseDaoImpl;

@Repository(value="mbnMerchantTunnelRelationDao")
@SuppressWarnings("unchecked")
public class MbnMerchantTunnelRelationDaoImpl extends
		TunnelBaseDaoImpl<MbnMerchantTunnelRelation, java.lang.Long> implements
		MbnMerchantTunnelRelationDao {
	private final String FINDBYTUNNELTYPE = ".findByTunnelType";
	private final String FINDBYPIN = ".findByPin";
	private final String FINDBYPINANDTUNNELID = ".findByPinAndTunnelId";
	private final String FINDBYACCESSNUMBER = ".findByAccessNumber";
	private final String FIND_BY_CLASSIFY = ".findByClassify";
	private final String FIND_BY_CLASSIFY_AND_TYPE = ".findByClassifyAndType";
	

	@Override
	public List<MbnMerchantTunnelRelation> findByTunnelType(Long merchantPin,
			Long type) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("merchantPin", merchantPin);
		map.put("type", type);
		return this.getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + FINDBYTUNNELTYPE, map);
	}


	@Override
	public List<MbnMerchantTunnelRelation> findByPin(Long merchantPin) {
		return this.getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + FINDBYPIN, merchantPin);
	}


	@Override
	public MbnMerchantTunnelRelation findByPinAndTunnelId(Long merchantPin,
			Long tunnelId) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("merchantPin", merchantPin);
		map.put("tunnelId", tunnelId);
		List<MbnMerchantTunnelRelation> list=  this.getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + FINDBYPINANDTUNNELID, map);
		if( list != null && list.size()>0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public List<MbnMerchantTunnelRelation> findByAccessNumberAndType(Long merchantPin,
			String accessNumber, Integer type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantPin", merchantPin);
		map.put("accessNumber", accessNumber);
		map.put("type", type);
		return this.getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + FINDBYACCESSNUMBER, map);
	}


	@Override
	public MbnMerchantTunnelRelation getExpireLastUsed(Long tunnelId) {
		return (MbnMerchantTunnelRelation) this.getSqlMapClientTemplate().queryForObject(this.sqlMapNamespace+".queryExpireLastUsed", tunnelId);
	}


	@Override
	public MbnMerchantTunnelRelation getMaxUsed(Long tunnelId) {
		return (MbnMerchantTunnelRelation) this.getSqlMapClientTemplate().queryForObject(this.sqlMapNamespace+".queryMaxInUsed",tunnelId);
	}


	@Override
	public List<MbnMerchantTunnelRelation> findByClassify(Long merchantPin,
			Integer classify) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("merchantPin", merchantPin);
		map.put("classify", classify.longValue());
		return this.getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + FIND_BY_CLASSIFY, map);
	}
	
	@Override
	public List<MbnMerchantTunnelRelation> findByClassifyAndType(Long merchantPin,
			Integer classify, Integer type) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("merchantPin", merchantPin);
		map.put("classify", classify.longValue());
		map.put("type", type.longValue());
		
		return this.getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + FIND_BY_CLASSIFY_AND_TYPE, map);
	}

    public void delete(Long merchantPin) {
        this.getSqlMapClientTemplate().delete(sqlMapNamespace+".delete", merchantPin);
    }
}
