package com.leadtone.mas.connector.dao.sqlmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.leadtone.mas.connector.core.CoreInterface;
import com.leadtone.mas.connector.dao.PortalUserDao;
import com.leadtone.mas.connector.domain.PortalUser;
import com.leadtone.mas.connector.utils.StringUtil;

/**
 * 
 * @author hejiyong
 * date:2013-1-22
 * 
 */

@Repository("portalUserDao")
public class SqlMapPortalUserDao extends SqlMapBaseDao<PortalUser,Integer> implements PortalUserDao{

	private static final Logger logger = LoggerFactory.getLogger(SqlMapPortalUserDao.class);
	
	//private String namespace = "PortalUser";
	
	@Override
	public PortalUser loadbyUserNameAndPwd(String name, String pwd,String merchant_pin) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadByNameAndPwd");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("UserName", name);
			parameters.put("Pwd", pwd);
			long lmerchant_pin = Long.parseLong(merchant_pin);
			parameters.put("merchant_pin", lmerchant_pin);
			PortalUser portalUser = null;
			Object result = getSqlMapClientTemplate().queryForObject(statementName, parameters);
			if(result != null)
				portalUser = (PortalUser)result;
			return portalUser;
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}

	@Override
	public PortalUser loadbyUid(String Uid) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadByUid");
			if(logger.isDebugEnabled())
				logger.debug("sqlmap uid is:{},statement:{}",Uid,statementName);
			long lUid = Long.parseLong(Uid);
			Object result = getSqlMapClientTemplate().queryForObject(statementName, lUid);
			if(logger.isDebugEnabled())
				logger.debug(result.toString());
			assert(result instanceof PortalUser);
			PortalUser portuser = (PortalUser)result;
			if(logger.isDebugEnabled())
				logger.debug(portuser.getId()+"");
			return portuser;
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
		//return (PortalUser) getSqlMapClientTemplate().queryForObject(statementName, Uid);
	}

	@Override
	public List<PortalUser> loadAllUser() {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadAllUser");
			if(logger.isDebugEnabled())
				logger.debug("sqlmap statement:{}",statementName);
			@SuppressWarnings("unchecked")
			List<PortalUser> test = getSqlMapClientTemplate().queryForList(statementName);
			if(logger.isDebugEnabled())
				logger.debug("size is:{}",test.size());
			return test;
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}

	@Override
	public PortalUser loadbyUserName(String accountId, Long merchantPin) {

		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadByName");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("UserName", accountId);
			parameters.put("merchant_pin", merchantPin);
			PortalUser portalUser = null;
			Object result = getSqlMapClientTemplate().queryForObject(statementName, parameters);
			if(result != null)
				portalUser = (PortalUser)result;
			return portalUser;
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	
	}

	@Override
	public List<Long> loadAllMerchantPin() {
		String statementName = StringUtil.concat(namespace, ".loadAllMerchantPin");
		Map<String, Object> parameters = new HashMap<String, Object>();
		return getSqlMapClientTemplate().queryForList(statementName);
	}
	
	@Override
	public List<PortalUser> loadAllMerchantPinDBUser(Long merchantPin) {
		String statementName = StringUtil.concat(namespace, ".loadAllMerchantPinDBUser");
//		Map<String, Object> parameters = new HashMap<String, Object>();
//		parameters.put("merchant_pin", merchantPin);
		return getSqlMapClientTemplate().queryForList(statementName, merchantPin);
	}
	
}
