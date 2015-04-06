package com.leadtone.mas.bizplug.tunnel.service;

import java.sql.SQLException;
import java.util.List;

import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;

public interface SmsTunnelAccountService {
	/**
	 * 修改(修改通道帐户的同时添加通道帐户流水信息，涉及事务处理)
	 * @param mbnSmsInbox
	 * @return
	 */
	boolean update(SmsMbnTunnelAccountFlow accountFlow,SmsTunnelAccount tunnelAccount) throws SQLException;
	/**
	 * 插入数据(插入通道帐户，涉及事务处理)
	 * @param mbnSmsInbox
	 * @return
	 */
	boolean insert(SmsTunnelAccount tunnelAccount)throws Exception;
	/**
	 * 根据通道标识查询
	 * @param tunnelId
	 * @return
	 */
	List<SmsTunnelAccount> getByTunnelIdList(Long tunnelId)throws Exception;
}
