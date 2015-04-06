package com.leadtone.mas.bizplug.sms.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository; 
import com.leadtone.mas.bizplug.sms.bean.MbnSmsDraft; 
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseDaoImpl;

@Repository
public class MbnSMsDraftDaoImpl extends SmsBaseDaoImpl<MbnSmsDraft,Long> implements
		MbnSmsDraftDao {
	protected static final String GETBYPKS = ".getByPks";
	
	protected static final String FOLLOWPAGE = ".followPage";
	 
	 @SuppressWarnings("unchecked")
	public List<MbnSmsDraft> followPage(HashMap<String,Object> page){
		 return  getSqlMapClientTemplate()
			.queryForList(this.sqlMapNamespace + FOLLOWPAGE, page);
	 }
	 
	@SuppressWarnings("unchecked")
	public List<MbnSmsDraft> getByPks(Long[] ids){
		return getSqlMapClientTemplate()
				.queryForList(this.sqlMapNamespace + GETBYPKS, ids);
	} 

}
