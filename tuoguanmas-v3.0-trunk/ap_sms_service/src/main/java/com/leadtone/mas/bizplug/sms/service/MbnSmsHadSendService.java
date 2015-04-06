package com.leadtone.mas.bizplug.sms.service;

import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSendVO;

public interface MbnSmsHadSendService {
	/**
	 * 根据id 查询对象
	 * @param pk
	 * @return
	 */
	List<MbnSmsHadSend> mbnSmsHasSendByPks(long pk);
	/**
	 * 根据id 查询对象
	 * @param pk
	 * @return
	 */
	MbnSmsHadSend mbnSmsHasSendByPk(long pk);
	/**
	 * 根据pin 查询集合
	 * @param pk
	 * @return
	 */
	List<MbnSmsHadSend> mbnSmsHasSendByPins(long pin);
	
	/**
	 * 根据pin 查询对象
	 * @param pk
	 * @return
	 */
	MbnSmsHadSend mbnSmsHasSendByPin(long pin);
	
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
	Integer insert(MbnSmsHadSend  mbnSmsHadSend);
	
	/**
	 * 修改数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer update(MbnSmsHadSend  mbnSmsHadSend);
	/**
	 * 删除数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer delete(MbnSmsHadSend  mbnSmsHadSend);
	 /**
	 * 批量更行操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 public abstract Integer batchUpdateByList(List<MbnSmsHadSend> paramList);
		 
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
	public abstract Integer batchDeleteByList(List<MbnSmsHadSend> entitys);
	/**
	 * 
	 * @param pks
	 * @return
	 */
	List<MbnSmsHadSend> batchSelectByPks(Long[] pks);
	/**
	 * 修改状态意思为删除
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer updateDel(String smsIds);
	
	/**
	 *混合分页查询 
	 * @param pageUtil
	 * @return
	 */
	Page pageVO(PageUtil pageUtil);
	/**
	 * 根据PK id 查询短信信息
	 * @param id
	 * @return
	 */
	MbnSmsHadSendVO queryByPk(Long id);
	/**
	 * 查询批次短信
	 * @param batchId
	 * @return
	 */
	List<MbnSmsHadSend> getByBatchId(Long batchId, Long mPin, Long createBy);
	/**
	 * 根据ids查询短信的详细信息
	 * @param ids
	 * @return
	 */
	List<MbnSmsHadSend> getByPks(Long[] ids);
	/**
	 * 根据批次ID查询已发短信信息
	 * @param pageUtil
	 * @return
	 */
	Page batchPage(PageUtil pageUtil);
	/**
	 * 根据批次数查询所有回复信息
	 * @param pageUtil
	 * @return
	 */
	 Page replyPage(PageUtil pageUtil); 
	 /**
	 * 查询下一页，或者上一页
	 * @param page
	 * @return
	 */
	List<MbnSmsHadSendVO> followPage(HashMap<String,Object> page); 
	
	/**\
	 * 信息导出
	 * @param pageUtil
	 * @return
	 */
 	public Page extPortAll(PageUtil pageUtil);
 	
 	/**
 	 * 批量更新删除状态
 	 * @param batchIds
 	 * @return
 	 */
 	public Integer updateDelByBatchIds(Long[] batchIds);
}
