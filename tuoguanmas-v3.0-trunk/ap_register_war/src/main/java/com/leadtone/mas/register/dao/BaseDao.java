package com.leadtone.mas.register.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
@SuppressWarnings("unused")
public class BaseDao extends SqlMapClientDaoSupport{
	@Autowired
	private SqlMapClient sqlMapClient;
}
