package com.leadtone.mas.bizplug.tunnel.service;
  
import java.util.List; 
import javax.annotation.Resource; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;  
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow; 
import com.leadtone.mas.bizplug.tunnel.dao.SmsMbnTunnelAccountFlowDao;
 
@Service(value="smsMbnTunnelAccountFlowService") 
@Transactional
public class SmsMbnTunnelAccountFlowServiceImpl implements SmsMbnTunnelAccountFlowService{
	
	@Resource
	private SmsMbnTunnelAccountFlowDao smsMbnTunnelAccountFlowDao;
	
	
	@Override
	public Page page(PageUtil pageUtil) throws Exception{ 
		Page page=null;
		try {
			page =  smsMbnTunnelAccountFlowDao.page(pageUtil); 
		} catch (Exception e) {
			throw e;
		} 
		// 
		return page;
	}

	@Override
	public Integer pageCount(PageUtil pageUtil) throws Exception{
		Integer count=0;
		try {
			count= smsMbnTunnelAccountFlowDao.pageCount(pageUtil);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public Integer insert(SmsMbnTunnelAccountFlow param) throws Exception{
		Integer count=0;
		try {
			count= smsMbnTunnelAccountFlowDao.insert(param);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public Integer update(SmsMbnTunnelAccountFlow param) throws Exception{
		Integer count=0;
		try {
			count= smsMbnTunnelAccountFlowDao.update(param);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public Integer delete(SmsMbnTunnelAccountFlow param) throws Exception{
		Integer count=0;
		try {
			count= smsMbnTunnelAccountFlowDao.delete(param);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}

	@Override
	public SmsMbnTunnelAccountFlow queryByPk(Long pk) throws Exception{
		Object object=null;
		try {
			object= smsMbnTunnelAccountFlowDao.queryByPk(pk);
		} catch (Exception e) {
			throw e;
		}
		return object!=null?(SmsMbnTunnelAccountFlow)object:null;
	}

	@Override
	public Integer batchUpdateByList(List<SmsMbnTunnelAccountFlow> paramList) throws Exception{
		Integer count=0;
		try {
			count= smsMbnTunnelAccountFlowDao.batchUpdateByList(paramList);
		} catch (Exception e) {
			throw e;
		}
		return count;
	}
 
}
