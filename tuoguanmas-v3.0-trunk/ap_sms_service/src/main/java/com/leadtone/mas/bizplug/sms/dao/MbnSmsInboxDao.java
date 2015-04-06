/**
 * 
 */
package com.leadtone.mas.bizplug.sms.dao; 
import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseIDao;
 

/**
 * @author PAN-Z-G
 *
 */
 
public interface MbnSmsInboxDao extends SmsBaseIDao<MbnSmsInbox,Long> { 
	List<MbnSmsInbox> getByPks(Long[] ids);
	
	Page pageVO(PageUtil pageUtil);
	/**
	 * 上一条，下一条查询
	 * @param page
	 */
	List<MbnSmsInbox> followPage(HashMap<String,Object> page);
	
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
	 * 查询出所有未读上行短信
	 * */
	List<MbnSmsInbox> getInboxAllInfo();
        
        Page statisticQuery(HashMap<String,Object> param);
        List<MbnSmsInbox> statisticSummary(HashMap<String,Object> param);
	
 }
