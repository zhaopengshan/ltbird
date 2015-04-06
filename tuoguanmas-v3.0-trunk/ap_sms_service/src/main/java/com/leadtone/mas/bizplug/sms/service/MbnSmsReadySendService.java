package com.leadtone.mas.bizplug.sms.service;

import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendContainer;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendVO;

public interface MbnSmsReadySendService {
	/**
	 * 根据id 查询对象
	 * @param pk
	 * @return
	 */
	List<MbnSmsReadySend> mbnSmsReadySendByPks(long pk);
	/**
	 * 根据id 查询对象
	 * @param pk
	 * @return
	 */
	MbnSmsReadySend mbnSmsReadySendByPk(long pk);
	/**
	 * 根据pin 查询集合
	 * @param pk
	 * @return
	 */
	List<MbnSmsReadySend> mbnSmsReadySendByPins(long pin);
	
	/**
	 * 根据pin 查询对象
	 * @param pk
	 * @return
	 */
	MbnSmsReadySend mbnSmsReadySendByPin(long pin);
	
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
	Integer insert(MbnSmsReadySend  mbnSmsReadySend);
	
	/**
	 * 修改数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer update(MbnSmsReadySend  mbnSmsReadySend);
	/**
	 * 删除数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer delete(MbnSmsReadySend  mbnSmsReadySend);
	 /**
	 * 批量更行操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 public abstract Integer batchUpdateByList(List<MbnSmsReadySend> paramList);
		 
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
	public abstract Integer batchDeleteByList(List<MbnSmsReadySend> entitys);
	/**
	 * 
	 * @param pks
	 * @return
	 */
	public abstract List<MbnSmsReadySend> batchSelectByPks(Long[] pks);
	/**
	 * 根据ids查询批次ids
	 * @param ids
	 * @return
	 */
	List<Long> getBatchIdsByPks(String[] ids);
	
	/**
	 * 取消未发送短信
	 * @param cancelPro ： ids,send_result,complete_time,fail_reason,
	 * @return 操作结果
	 */
	Integer cancelSend(HashMap<String, Object> cancelPro);
	
	/**
	 * 批量保存操作
	 * @param entitys : 实体列表
	 * @return
	 */
	Integer batchSaveByList( List<MbnSmsReadySend> entitys);
	/**
	 * 查询短信详情
	 * @param pk
	 * @return
	 */
	MbnSmsReadySendVO queryByPk(Long pk);
	/**
	 * 查询同批次短信
	 * @param batchId
	 * @return
	 */
	List<MbnSmsReadySend> getByBatchId(Long batchId, Long mPin, Long createBy);
	
	/**
	 * 查询多批次短信
	 * @return
	 */
	List<MbnSmsReadySend> getByBatchIds(HashMap<String,Object> batchIds);
	/**
	 * 根据ids查询短信信息
	 * @param ids
	 * @return
	 */
	List<MbnSmsReadySend> getByPks(Long[] ids);
	
	Page pageVO(PageUtil pageUtil);
	/**
	 * 批次查询分页
	 * @param pageUtil
	 * @return
	 */
	Page batchPage(PageUtil pageUtil);
	/**
	 * 根据批次号查询所有回复信息
	 * @param pageUtil
	 * @return
	 */
	Page replyPage(PageUtil pageUtil);
	/**
	 * 查询下一页，或者上一页
	 * @param page
	 * @return
	 */
	List<MbnSmsReadySendVO> followPage(HashMap<String,Object> page);
	
	
	/**
	 * 批量保存操作
	 * @param entitys : 实体列表
	 * @return
	 */
	Integer batchSave( MbnSmsReadySendContainer smsContainer);
	/** 
	 * 批量保存操作
	 * @param smsContainer
	 * @param operationType 消费流水操作类型	1增加（购买）；2增加（赠送）;3减少（撤销购买）;4减少（撤销赠送）
	 * @return
	 */
	Integer batchSave(MbnSmsReadySendContainer smsContainer,Integer operationType);
	
	/**\
	 * 信息导出
	 * @param pageUtil
	 * @return
	 */
 	public Page extPortAll(PageUtil pageUtil);
}
