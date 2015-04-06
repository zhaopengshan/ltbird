/**
 * 
 */
package com.leadtone.mas.bizplug.sms.service;

import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;

/**
 * @author R
 * 
 */
public interface MbnSmsSelectedService {
	/**
	 * 查询分页/模糊查询分页 count
	 * 
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Integer pageCount(PageUtil pageUtil);

	/**
	 * 查询分页/模糊查询分页
	 * 
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Page page(PageUtil pageUtil);

	/**
	 * 根据pk查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	MbnSmsSelected queryByPk(Long pk);

	/**
	 * 插入数据
	 * 
	 * @param MbnSmsSelected
	 * @return
	 */
	Integer insert(MbnSmsSelected mbnSmsSelected);

	/**
	 * 修改数据
	 * 
	 * @param MbnSmsSelected
	 * @return
	 */
	Integer update(MbnSmsSelected mbnSmsSelected);

	/**
	 * 删除数据
	 * 
	 * @param MbnSmsSelected
	 * @return
	 */
	Integer delete(MbnSmsSelected mbnSmsSelected);
	
	 /**
	 * 批量更行操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 public abstract Integer batchUpdateByList(List<MbnSmsSelected> paramList);
		 
	/**
	 * 批量删除操作
	 *
	 * @param paramArrayOfSerializable
	 *            根据主键数组批量删除记录
	 */
	public abstract Integer batchDeleteByPks(Long[] pks);

	/**
	 * 批量删除操作
	 *
	 * @param entitys
	 *            根据实体对象进行删除操作
	 * @return 删除操作所影响的行数
	 */
	public abstract Integer batchDeleteByList(List<MbnSmsSelected> entitys);
	/**
	 * 
	 * @param pks
	 * @return
	 */
	public abstract List<MbnSmsSelected> batchSelectByPks(Long[] pks);
	
	/**
	 * 批量保存 操作
	 * @param entitys
	 * @return
	 */
	public Integer batchSaveByList( List<MbnSmsSelected> entitys);
	/**
	 * 查询下一页，或者上一页
	 * @param page
	 * @return
	 */
	List<MbnSmsSelected> followPage(HashMap<String,Object> page);
}
