package com.leadtone.mas.bizplug.config.service;

import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.config.bean.MbnConfigProvinceItemValue;

public interface MbnConfigProvinceItemValueService {
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
	MbnConfigProvinceItemValue queryByPk(Long pk);

	/**
	 * 根据pk查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	List< MbnConfigProvinceItemValue> queryByPks(Long pk);

	/**
	 * 根据pk查询对象
	 * 
	 * @param pin
	 * @return 结果（对象/集合）
	 */
	MbnConfigProvinceItemValue queryByPin(Long pin);

	/**
	 * 根据pk查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	List< MbnConfigProvinceItemValue> queryByPins(Long pk);

	/**
	 * 插入数据
	 * 
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer insert( MbnConfigProvinceItemValue  MbnConfigProvinceItemValue);

	/**
	 * 修改数据
	 * 
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer update( MbnConfigProvinceItemValue  MbnConfigProvinceItemValue);

	/**
	 * 删除数据
	 * 
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer delete( MbnConfigProvinceItemValue  MbnConfigProvinceItemValue);
	
	/**
	 * 批量更行操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 public abstract Integer batchUpdateByList(List<MbnConfigProvinceItemValue> paramList);
		 
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
	public abstract Integer batchDeleteByList(List<MbnConfigProvinceItemValue> entitys);
	/**
	 * 
	 * @param pks
	 * @return
	 */
	List<MbnConfigProvinceItemValue> batchSelectByPks(Long[] pks);
	/**
	 * 根据ids查询 相应的List
	 * @param pks
	 * @return
	 */
	List<MbnConfigProvinceItemValue> getByPks(String ids);

	Integer batchSaveByList(List<MbnConfigProvinceItemValue> entitys);
}
