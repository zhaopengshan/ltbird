package com.leadtone.mas.bizplug.sms.dao;

import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsDraft;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseIDao;
 
public interface MbnSmsDraftDao extends SmsBaseIDao<MbnSmsDraft,Long> {
	/**
	 * 根据ids查询草稿短信列表
	 * @param ids
	 * @return
	 */
	List<MbnSmsDraft> getByPks(Long[] ids);
	
	/**
	 * 上一条，下一条查询
	 * @param page
	 */
	List<MbnSmsDraft> followPage(HashMap<String,Object> page);
}
