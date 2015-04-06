/**
 * 
 */
package com.leadtone.mas.bizplug.tunnel.service;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;
import com.leadtone.mas.bizplug.tunnel.dao.SmsTunnelAccountDao;

/**
 * @author PAN-Z-G
 *
 */
@Service(value="smsTunnelAccountService")
public class SmsTunnelAccountServiceImpl implements SmsTunnelAccountService {
	@Resource
	private SmsTunnelAccountDao smsTunnelAccountDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public boolean update(SmsMbnTunnelAccountFlow accountFlow,
			SmsTunnelAccount tunnelAccount) throws SQLException {
		Integer count=0;
		try {
			smsTunnelAccountDao.update(accountFlow, tunnelAccount);
			count=1;
		} catch (SQLException e) {
			 throw e;
		}  
		return count>0?true:false;
	}

	@Override
	public boolean insert(SmsTunnelAccount tunnelAccount)throws Exception {
		Integer count=0;
		try {
			smsTunnelAccountDao.insert(tunnelAccount);
			count=1;
		} catch (Exception e) {
			 throw e;
		}  
		return count>0?true:false;
	}

	@Override
	public List<SmsTunnelAccount> getByTunnelIdList(Long tunnelId)
			throws Exception {
		List<SmsTunnelAccount> sms=null;
		try {
			sms=smsTunnelAccountDao.getByTunnelIdList(tunnelId);
		} catch (Exception e) {
			throw e;
		}
		return sms;
	}

 
}
