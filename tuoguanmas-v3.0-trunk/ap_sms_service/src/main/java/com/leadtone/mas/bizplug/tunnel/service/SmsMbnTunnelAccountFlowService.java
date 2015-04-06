package com.leadtone.mas.bizplug.tunnel.service; 
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelAccountFlow; 
/**
 * 
 * @author PAN-Z-G
 * 通道帐户流水服务
 */
public interface SmsMbnTunnelAccountFlowService {

	/**
	 * 查询分页/模糊查询分页
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Page page(PageUtil pageUtil)throws Exception;
	
	/**
	 *  count
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Integer pageCount(PageUtil pageUtil)throws Exception;
	
	/**
	 * 插入数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer insert(SmsMbnTunnelAccountFlow param)throws Exception;
	
	/**
	 * 修改数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer update(SmsMbnTunnelAccountFlow param)throws Exception;
	
	/**
	 * 删除数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer delete(SmsMbnTunnelAccountFlow param)throws Exception;
	
	/**
	 * 根据pk查询对象 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	SmsMbnTunnelAccountFlow queryByPk(Long pk)throws Exception; 
	 
	 /**
	 * 批量更新操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 public abstract Integer batchUpdateByList(List<SmsMbnTunnelAccountFlow> paramList)throws Exception; 
 
}
