package com.leadtone.mas.bizplug.sms.dao;
 
 import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendVO;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseIDao;

/**
 * 
 * @author PAN-Z-G
 * 待发短信息接口
 */
 public interface MbnSmsReadySendDao extends SmsBaseIDao<MbnSmsReadySend,Serializable>{
	 
	 /**
	  * 信息导入
	  * @param pageUtil
	  * @return
	  */
	 Page extPortAll(PageUtil pageUtil);
	 
	 /**
	  * ids查询相关短信信息
	  * @param ids
	  * @return
	  */
	List<MbnSmsReadySend> getByPks(Long[] ids);
	/**
	 * 根据短信ids查询所在批次batchids 
	 * @param ids
	 * @return
	 */
	List<Long> getBatchIdsByPks(String[] ids);
	/**
	 * 取消 短信发送 
	 * @param cancelPro
	 * @return
	 */
	Integer cancelSend(HashMap<String, Object> cancelPro);
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
	 *混合分页查询 
	 * @param pageUtil
	 * @return
	 */
	Page pageVO(PageUtil pageUtil);
    /**
	 * 批次查询分页
	 * @param pageUtil
	 * @return
	 */
	Page batchPage(PageUtil pageUtil);
	/**
	 * 批次查询总数
	 * @param pageUtil
	 * @return
	 */
	Integer batchPageCount(PageUtil pageUtil);
	/**
	 * 批次查询回复信息
	 * @param pageUtil
	 * @return
	 */
	Page replyPage(PageUtil pageUtil);
	
	/**
	 * 上一条，下一条查询
	 * @param page
	 */
	List<MbnSmsReadySendVO> followPage(HashMap<String,Object> page);
    Page statisticQuery(HashMap<String,Object> param);
    List<MbnSmsReadySend> statisticSummary(HashMap<String,Object> param);
    
    Long getBatchIdByAcc(String acc);
    Long getBatchIdByAccFromHad(String acc);
    Long getBatchByServiceAndOpt(HashMap<String, Object> param);
    
    //根据驱动流水号，获取批次号,一般用于企信通上行
    Long getBatchIdByCpoid(String cpoid, Long merchantPin);
    Long getBatchIdByCpoidFromHad(String cpoid, Long merchantPin);
}
