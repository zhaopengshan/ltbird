package com.leadtone.mas.bizplug.sms.dao;
 
 import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;

import java.util.HashMap;
 import java.util.List;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSendVO;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseIDao;

/**
 * 
 * @author PAN-Z-G
 * 已发短信息接口
 */
 public interface MbnSmsHadSendDao extends SmsBaseIDao<MbnSmsHadSend,Long> {
 
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
	 * 根据 批次ID查询本批次短信条数
	 * @param pageUtil
	 * @return
	 */
	Integer batchPageCount(PageUtil pageUtil);
	
	/**
	 * 根据批次号查询所有回复info
	 * @param pageUtil
	 * @return
	 */
	Page replyPage(PageUtil pageUtil);
	/**
	 * 根据批次号查询所有回复info
	 * @param pageUtil
	 * @return
	 */
	Integer replyPageCount(PageUtil pageUtil);
	
	/**
	 * 上一条，下一条查询
	 * @param page
	 */
	List<MbnSmsHadSendVO> followPage(HashMap<String,Object> page);
	/**
	 * 信息导出 
	 * @param pageUtil
	 * @return
	 */
	Page extport(PageUtil pageUtil);
	
	 /**
	 * 修改状态意思为删除
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer updateDelByBatchId(Long[] batchIds);
}
