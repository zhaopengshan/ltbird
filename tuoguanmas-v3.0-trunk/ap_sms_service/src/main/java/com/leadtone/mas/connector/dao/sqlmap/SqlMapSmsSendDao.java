package com.leadtone.mas.connector.dao.sqlmap;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.leadtone.mas.connector.dao.SmsSendDao;
import com.leadtone.mas.connector.domain.PortalUser;
import com.leadtone.mas.connector.domain.SmsSend;
import com.leadtone.mas.connector.utils.StringUtil;

/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */

@Repository("smsSendDao")
public class SqlMapSmsSendDao extends SqlMapBaseDao<SmsSend,Integer> implements SmsSendDao {
	
	private static final Logger logger = LoggerFactory.getLogger(SqlMapSmsSendDao.class);
	
	
	//private String namespace = "SmsSend";
	@Override
	public void save(SmsSend msg) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".save");
			getSqlMapClientTemplate().insert(statementName, msg);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
	}


	@Override
	public String loadAccessNumByMobile(String merchant_pin, String mobileType) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadAccessNumByMobile");
			Map<String,Object> params = new HashMap<String,Object>();
			long lmerchant_pin = Long.parseLong(merchant_pin);
			params.put("merchant_pin", lmerchant_pin);
			int lmobileType = Integer.parseInt(mobileType);
			params.put("mobileType", lmobileType);
			String accessNum = (String)getSqlMapClientTemplate().queryForObject(statementName, params);
			return accessNum;
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}


	@Override
	public String loadMobileTypeByH3(String mobile_prefix) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadMobileTypeByH3");
			String mobileType = (String) getSqlMapClientTemplate().queryForObject(statementName, mobile_prefix);
			return mobileType;
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}

}
