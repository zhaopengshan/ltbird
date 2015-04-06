package com.leadtone.mas.bizplug.tunnel.dao.base;

import java.io.Serializable;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil; 

public interface TunnelBaseIDao<T,PK extends Serializable> {


	/**
	 * 查询分页/模糊查询分页
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Page page(PageUtil pageUtil);
	
	/**
	 *  count
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Integer pageCount(PageUtil pageUtil);
	
	/**
	 * 插入数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer insert(T param);
	
	/**
	 * 修改数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer update(T param);
	
	/**
	 * 删除数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer delete(T param);
	
	/**
	 * 根据pk查询对象 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	 Object queryByPk(Long pk); 
	 
	 /**
	 * 批量更新操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 public abstract Integer batchUpdateByList(List<T> paramList); 
}
