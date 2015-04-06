/**
 * 
 */
package com.leadtone.mas.bizplug.sms.service;

import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil; 
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;

/**
 * @author PAN-Z-G
 * 
 */
public interface MbnSmsInboxService {
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
	MbnSmsInbox queryByPk(Long pk);

	/**
	 * 根据pk查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	List<MbnSmsInbox> queryByPks(Long pk);

	/**
	 * 根据pk查询对象
	 * 
	 * @param pin
	 * @return 结果（对象/集合）
	 */
	MbnSmsInbox queryByPin(Long pin);

	/**
	 * 根据pk查询对象
	 * 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	List<MbnSmsInbox> queryByPins(Long pk);

	/**
	 * 插入数据
	 * 
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer insert(MbnSmsInbox mbnSmsInbox);

	/**
	 * 修改数据
	 * 
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer update(MbnSmsInbox mbnSmsInbox);

	/**
	 * 删除数据
	 * 
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer delete(MbnSmsInbox mbnSmsInbox);
	
	/**
	 * 批量更行操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 public abstract Integer batchUpdateByList(List<MbnSmsInbox> paramList);
		 
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
	public abstract Integer batchDeleteByList(List<MbnSmsInbox> entitys);
	/**
	 * 
	 * @param pks
	 * @return
	 */
	List<MbnSmsInbox> batchSelectByPks(Long[] pks);
	/**
	 * 根据ids查询 相应的List
	 * @param pks
	 * @return
	 */
	List<MbnSmsInbox> getByPks(Long[] ids);

	Page pageVO(PageUtil pageUtil);
	/**
	 * 根据批次号查询所有回复info
	 * @param pageUtil
	 * @return
	 */
	Page replyPage(PageUtil pageUtil);
	/**
	 * 查询下一页，或者上一页
	 * @param page
	 * @return
	 */
	List<MbnSmsInbox> followPage(HashMap<String,Object> page);
	/**
	 * 根据业务编码查询未读上行短信
	 * @param pageUtil
	 * @return
	 */
	List<MbnSmsInbox> getIndoxBycoding(String coding);
 	
	/**
	 * 信息导出
	 * @param pageUtil
	 * @return
	 */
	Page export(PageUtil pageUtil);
 	
	
	/**
	 * TODO:得到所有inbox中的信息
	 * @author chenxuezheng
	 * 
	 * */
	List<MbnSmsInbox> getInboxAllInfo();
 }
