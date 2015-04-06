package com.leadtone.mas.bizplug.tunnel.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.dao.base.TunnelBaseDaoImpl;

@Repository
@SuppressWarnings("unchecked")
public class MbnMerchantConsumeDaoImpl extends
		TunnelBaseDaoImpl<MbnMerchantConsume, java.lang.Long> implements
		MbnMerchantConsumeDao {
	private String FINDBYTUNNELID = ".findByTunnelId";
	private String FINDBYPIN = ".findByPin";

	@Override
	public List<MbnMerchantConsume> findByPin(Long merchantPin) {
		return this.getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + FINDBYPIN, merchantPin);
	}

	@Override
	public MbnMerchantConsume findByTunnelId(Long merchantPin, Long tunnelId) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("merchantPin", merchantPin);
		map.put("tunnelId", tunnelId);
		return (MbnMerchantConsume) this.getSqlMapClientTemplate()
				.queryForObject(this.sqlMapNamespace + FINDBYTUNNELID, map);
	}

    @Override
    public void deleteConsumesByMerchantPin(Long merchantPin) {
        this.getSqlMapClientTemplate().delete(sqlMapNamespace+".deleteConsumeByMerchantPin", merchantPin);
    }

}
