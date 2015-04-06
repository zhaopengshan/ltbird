package com.leadtone.mas.connector.dao.sqlmap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.leadtone.mas.connector.dao.SmsGetReportDao;
import com.leadtone.mas.connector.domain.PortalUser;
import com.leadtone.mas.connector.domain.SmsGetReport;
import com.leadtone.mas.connector.utils.StringUtil;

/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */

@Repository("smsGetReportDao")
public class SqlMapSmsGetReportDao extends SqlMapBaseDao<SmsGetReport,Integer> implements SmsGetReportDao{

	//private String namespace = "SmsGetReport";
	private static final Logger logger = LoggerFactory.getLogger(SqlMapSmsGetReportDao.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsGetReport> loadbyPk(List<String> uid) {
		// TODO Auto-generated method stub
		try{
			String statementName = StringUtil.concat(namespace, ".loadByUid");
			return getSqlMapClientTemplate().queryForList(statementName, uid);
		}
		catch(Exception e){
			logger.error(e.toString());
		}
		return null;
	}

	@Override
	public SmsGetReport loadById(Long id) {
		String statementName = StringUtil.concat(namespace, ".loadById");
		Object obj = getSqlMapClientTemplate().queryForObject(statementName, id);
		if( obj != null ){
			return (SmsGetReport)obj;
		}
		return null;
	}
	
	@Override
	public SmsGetReport loadByIdReady(Long id) {
		String statementName = StringUtil.concat(namespace, ".loadById");
		Object obj = getSqlMapClientTemplate().queryForObject(statementName, id);
		if( obj != null ){
			return (SmsGetReport)obj;
		}
		return null;
	}
}
