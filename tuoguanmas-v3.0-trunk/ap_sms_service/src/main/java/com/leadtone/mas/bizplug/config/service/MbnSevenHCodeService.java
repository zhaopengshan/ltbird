package com.leadtone.mas.bizplug.config.service;

import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;

public interface MbnSevenHCodeService {
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
	MbnSevenHCode queryByPk(Long pk);

	/**
	 * 根据pk查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	MbnSevenHCode queryByBobilePrefix(String prefix);

	/**
	 * 插入数据
	 * 
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer insert(MbnSevenHCode mbnSevenHCode);

	/**
	 * 修改数据
	 * 
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer update(MbnSevenHCode mbnSevenHCode);

	/**
	 * 删除数据
	 * 
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer delete(MbnSevenHCode mbnSevenHCode);

	/**
	 * 批量删除操作
	 * 
	 * @param entitys
	 *            根据实体对象进行删除操作
	 * @return 删除操作所影响的行数
	 */
	public abstract Integer batchDeleteByList(List<MbnSevenHCode> entitys);

	/**
	 * 
	 * @param pks
	 * @return
	 */
	List<MbnSevenHCode> batchSelectByPks(Long[] pks);
}
