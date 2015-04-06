package com.leadtone.mas.connector.dao.sqlmap;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * 
 * @author hejiyong
 * date:2013-1-22
 * 
 */
public class SqlMapBaseDao<T, PK extends Serializable> extends SqlMapClientDaoSupport{
	//@Autowired
	//private SqlMapClient sqlMapClient;
	static final Logger logger = LoggerFactory.getLogger(SqlMapBaseDao.class);
	protected String namespace;

	
	@Autowired
	public void setSqlMapClientAround(@Qualifier("sqlMapClient") SqlMapClient sqlMapClient) {
		this.setSqlMapClient(sqlMapClient);
		ParameterizedType t2 = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<?> clazz = (Class<?>) t2.getActualTypeArguments()[0];
		this.namespace = clazz.getSimpleName();
		if (logger.isDebugEnabled()) {
			logger.debug("inject sqlMapClient instance [{}]", sqlMapClient);
			logger.debug("sqlMapNamespace: {}", namespace);
		}
	}
}
