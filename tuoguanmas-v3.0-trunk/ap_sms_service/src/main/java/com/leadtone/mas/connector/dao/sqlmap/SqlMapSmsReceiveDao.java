package com.leadtone.mas.connector.dao.sqlmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.leadtone.mas.connector.dao.SmsGetReportDao;
import com.leadtone.mas.connector.dao.SmsReceiveDao;
import com.leadtone.mas.connector.domain.PortalUser;
import com.leadtone.mas.connector.domain.SmsGetReport;
import com.leadtone.mas.connector.domain.SmsReceive;
import com.leadtone.mas.connector.utils.StringUtil;

/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */

@Repository("smsReceiveDao")
public class SqlMapSmsReceiveDao extends SqlMapBaseDao<SmsReceive,Integer> implements SmsReceiveDao{

	//private String namespace = "SmsReceive";
	private static final Logger logger = LoggerFactory.getLogger(SqlMapSmsReceiveDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsReceive> loadByPk(String accessNumber) {
		try{
			HashMap<String, Object> temp = new HashMap<String, Object>();
			temp.put("accessNumber", accessNumber);
			String statementName = StringUtil.concat(namespace, ".loadByUid");
			return getSqlMapClientTemplate().queryForList(statementName, temp);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	@Override
	public List<SmsReceive> loadByQxtUserId(Integer userId){
		try{
			String statementName = StringUtil.concat(namespace, ".loadByQxtUserId");
			return getSqlMapClientTemplate().queryForList(statementName, userId);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> loadAccByUid(String uid) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadAccByUid");
			return getSqlMapClientTemplate().queryForList(statementName, uid);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	@Override
	public void updateSmsStatus(String Uid) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".update");
			getSqlMapClientTemplate().update(statementName, Uid);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
	}

	@Override
	public List<SmsReceive> loadDbSyncSmsList(List<String> accessNumberList) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadDbSyncSms");
			return getSqlMapClientTemplate().queryForList(statementName, accessNumberList);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}

	@Override
	public List<SmsReceive> loadDbSyncSmsByAccessNumber(String accessNumber,String type) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadDbSyncSmsByAccessNumber");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("accessNumber", accessNumber);
			parameters.put("type", type);
			return getSqlMapClientTemplate().queryForList(statementName, parameters);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	@Override
	public List<SmsReceive> loadDbSyncSmsByUid(Long userId,String type) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadDbSyncSmsByUid");
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", userId);
			parameters.put("type", type);
			return getSqlMapClientTemplate().queryForList(statementName, parameters);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}
	
	@Override
	public void updateDbSyncSmsStatus(Long id) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".updateDbSyncSmsStatus");
			getSqlMapClientTemplate().update(statementName, id);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
	}
	

}
