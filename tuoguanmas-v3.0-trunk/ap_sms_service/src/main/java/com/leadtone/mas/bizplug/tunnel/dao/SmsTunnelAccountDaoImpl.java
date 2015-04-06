/**
 * 
 */
package com.leadtone.mas.bizplug.tunnel.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.leadtone.mas.bizplug.dao.BaseDao; 
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;

/**
 * @author PAN-Z-G
 *
 */
@Transactional
@Repository
@SuppressWarnings("unchecked")
public class SmsTunnelAccountDaoImpl extends BaseDao implements SmsTunnelAccountDao { 
	private static final String INSERT=".insert";
	private static final String UPDATE=".update";
	@Override
	public Integer update(SmsMbnTunnelAccountFlow accountFlow,
			SmsTunnelAccount tunnelAccount) throws SQLException {
		SqlMapClient sqlMapClient = getSqlMapClient();
		Integer resultCount = 0; 
		try { 
			//操作修改通道帐户信息
			resultCount = (Integer) sqlMapClient.update("SmsTunnelAccount"
					+ UPDATE, tunnelAccount); 
			//给’修改通道帐户信息‘操作添加流水信息
			resultCount = (Integer) sqlMapClient.insert(
					"SmsMbnTunnelAccountFlow" + INSERT, accountFlow);  
		 
		} catch (SQLException e) {
			throw e;
		}  
		return resultCount;
	}
	
	@Override
	public Integer insert(SmsTunnelAccount tunnelAccount){ 
		return (Integer) getSqlMapClientTemplate().insert("SmsTunnelAccount"+INSERT,tunnelAccount);
	}
 
	@Override
	public List<SmsTunnelAccount> getByTunnelIdList(Long tunnelId) { 
		return getSqlMapClientTemplate().queryForList("SmsTunnelAccount.getByTunnelId", tunnelId);
	}

	@Override
	public void updateSmsTunnelAccount(SmsTunnelAccount sta) {
		this.getSqlMapClientTemplate().update("SmsTunnelAccount.updateByTunnel", sta);
	}

	@Override
	public void insertTunnelAccountFlow(SmsMbnTunnelAccountFlow smta) {
		this.getSqlMapClientTemplate().insert("SmsMbnTunnelAccountFlow.insert", smta);
	}
 
}
