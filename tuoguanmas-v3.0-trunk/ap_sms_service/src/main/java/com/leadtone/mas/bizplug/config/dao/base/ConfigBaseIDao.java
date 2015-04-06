package com.leadtone.mas.bizplug.config.dao.base;


import java.io.Serializable;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil; 

public interface ConfigBaseIDao<T,PK extends Serializable> {


	/**
	 * 查询分页/模糊查询分页
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Page page(PageUtil pageUtil);
	
	/**
	 * 查询分页/模糊查询分页 count
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
	 T queryByPk(Long pk);
	
	/**
	 * 根据pk查询对象 
	 * @param pin
	 * @return 结果（对象/集合）
	 */
	 T queryByPin(Long pin);
	 
	 /**
	 * 批量更行操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 public abstract Integer batchUpdateByList(List<T> paramList);
		 
	/**
	 * 批量删除操作
	 *
	 * @param paramArrayOfSerializable
	 *            根据主键数组批量删除记录
	 */
	public abstract Integer batchDeleteByPks(PK[] pks);

	/**
	 * 批量删除操作
	 *
	 * @param entitys
	 *            根据实体对象进行删除操作
	 * @return 删除操作所影响的行数
	 */
	public abstract Integer batchDeleteByList(List<T> entitys);
	/**
	 * 批量保存操作
	 *
	 * @param entitys
	 *            根据实体对象进行保存操作
	 * @return 保存操作的结果
	 */
	public abstract Integer batchSaveByList(List<T> entitys);
	/**
	 * 
	 * @param pks
	 * @return
	 */
	public abstract T batchSelectByPks(PK[] pks);
	 
}
