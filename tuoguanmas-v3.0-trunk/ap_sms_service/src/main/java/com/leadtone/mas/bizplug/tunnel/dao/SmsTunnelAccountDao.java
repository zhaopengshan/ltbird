package com.leadtone.mas.bizplug.tunnel.dao; 

import java.sql.SQLException;
import java.util.List;

import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;

public interface SmsTunnelAccountDao {
	/**
	 * 修改(修改通道帐户的同时添加通道帐户流水信息，涉及事务处理)
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer update(SmsMbnTunnelAccountFlow accountFlow,SmsTunnelAccount tunnelAccount) throws SQLException;
	/**
	 * 插入数据(插入通道帐户，涉及事务处理)
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer insert(SmsTunnelAccount tunnelAccount);
	/**
	 * 根据通道标识查询
	 * @param tunnelId
	 * @return
	 */
	List<SmsTunnelAccount> getByTunnelIdList(Long tunnelId);
	/**
	 * 修改SmsTunnelAccount 根据 通道号
	 * @param sta
	 */
	void updateSmsTunnelAccount(SmsTunnelAccount sta);
	/**
	 * 添加一条账号流水记录
	 * @param smta
	 */
	void insertTunnelAccountFlow(SmsMbnTunnelAccountFlow smta);
	
}