package com.leadtone.mas.bizplug.sms.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseIDao;

 public interface MbnSmsSelectedDao extends SmsBaseIDao<MbnSmsSelected, Serializable> {
	 /**
	  * 查询上一页，下一页
	  * @param page
	  * @return
	  */
	List<MbnSmsSelected> followPage(HashMap<String,Object> page);
}
