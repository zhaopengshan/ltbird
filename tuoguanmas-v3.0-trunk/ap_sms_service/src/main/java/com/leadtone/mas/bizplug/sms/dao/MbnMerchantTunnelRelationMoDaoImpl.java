package com.leadtone.mas.bizplug.sms.dao;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;

@Component("mbnMerchantTunnelRelationMoDao")
public class MbnMerchantTunnelRelationMoDaoImpl extends SqlMapClientDaoSupport implements
		MbnMerchantTunnelRelationMoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<MbnMerchantTunnelRelation> findByAccessNumber(String accessNumber) {
		return getSqlMapClientTemplate().queryForList("MbnMerchantTunnelRelation.findByAccessNumber", accessNumber);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
