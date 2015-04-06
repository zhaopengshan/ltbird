/**
 * 
 */
package com.leadtone.mas.bizplug.sms.service;

import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsDraft;

/**
 * @author R
 * 
 */
public interface MbnSmsDraftService {
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
	MbnSmsDraft queryByPk(Long pk);

	/**
	 * 插入数据
	 * 
	 * @param MbnSmsDraft
	 * @return
	 */
	Integer insert(MbnSmsDraft mbnSmsDraft);

	/**
	 * 修改数据
	 * 
	 * @param MbnSmsDraft
	 * @return
	 */
	Integer update(MbnSmsDraft mbnSmsDraft);

	/**
	 * 删除数据
	 * 
	 * @param MbnSmsDraft
	 * @return
	 */
	Integer delete(MbnSmsDraft mbnSmsDraft);

	/**
	 * 批量更行操作
	 * 
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	Integer batchUpdateByList(List<MbnSmsDraft> paramList);

	/**
	 * 批量删除操作
	 * 
	 * @param paramArrayOfSerializable
	 *            根据主键数组批量删除记录
	 */
	Integer batchDeleteByPks(Long[] pks);

	/**
	 * 批量删除操作
	 * 
	 * @param entitys
	 *            根据实体对象进行删除操作
	 * @return 删除操作所影响的行数
	 */
	Integer batchDeleteByList(List<MbnSmsDraft> entitys);

	/**
	 * 
	 * @param pks
	 * @return
	 */
	List<MbnSmsDraft> batchSelectByPks(Long[] pks);
	/**
	 * 根据ids查询草稿短信列表
	 * @param ids
	 * @return
	 */
	List<MbnSmsDraft> getByPks(Long[] ids);
	
	/**
	 * 查询下一页，或者上一页
	 * @param page
	 * @return
	 */
	List<MbnSmsDraft> followPage(HashMap<String,Object> page);
}
