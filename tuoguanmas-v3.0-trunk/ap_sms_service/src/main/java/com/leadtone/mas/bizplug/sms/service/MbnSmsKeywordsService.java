/**
 * 
 */
package com.leadtone.mas.bizplug.sms.service; 
import java.util.List; 
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsKeywords;

/**
 * @author R
 * 
 */
public interface MbnSmsKeywordsService {
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
	MbnSmsKeywords queryByPk(Long pk);

	/**
	 * 插入数据
	 * 
	 * @param MbnSmsKeywords
	 * @return
	 */
	Integer insert(MbnSmsKeywords mbnSmsSelected);

	/**
	 * 修改数据
	 * 
	 * @param MbnSmsKeywords
	 * @return
	 */
	Integer update(MbnSmsKeywords mbnSmsSelected);

	/**
	 * 删除数据
	 * 
	 * @param MbnSmsKeywords
	 * @return
	 */
	Integer delete(MbnSmsKeywords mbnSmsSelected);
	
	 /**
	 * 批量更行操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 public abstract Integer batchUpdateByList(List<MbnSmsKeywords> paramList);
		 
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
	public abstract Integer batchDeleteByList(List<MbnSmsKeywords> entitys);
	/**
	 * 
	 * @param pks
	 * @return
	 */
	public abstract List<MbnSmsKeywords> batchSelectByPks(Long[] pks);
	
	/**
	 * 批量保存 操作
	 * @param entitys
	 * @return
	 */
	public Integer batchSaveByList( List<MbnSmsKeywords> entitys);

	/**
	 * 过滤SMS
	 * @param pin 商户PIN
	 * @param smsText
	 * @return 包含敏感词 false 其它 true
	 */
	public boolean checkSms(Long pin, String smsText);
}
