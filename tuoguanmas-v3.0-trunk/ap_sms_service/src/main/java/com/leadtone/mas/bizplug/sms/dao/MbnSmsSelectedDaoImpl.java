package com.leadtone.mas.bizplug.sms.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
/*import java.util.List;
*/
import org.springframework.stereotype.Repository;
/*
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;*/
import com.leadtone.mas.bizplug.sms.bean.MbnSmsSelected;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseDaoImpl;

 @Repository
public class MbnSmsSelectedDaoImpl extends SmsBaseDaoImpl<MbnSmsSelected, Serializable> implements
		MbnSmsSelectedDao {
 
	 protected static final String FOLLOWPAGE = ".followPage";
	 
	 @SuppressWarnings("unchecked")
	public List<MbnSmsSelected> followPage(HashMap<String,Object> page){
		 return  getSqlMapClientTemplate()
			.queryForList(this.sqlMapNamespace + FOLLOWPAGE, page);
	 }
}
