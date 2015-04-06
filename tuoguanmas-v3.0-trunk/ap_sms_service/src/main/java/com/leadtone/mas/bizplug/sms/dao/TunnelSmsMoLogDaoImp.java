package com.leadtone.mas.bizplug.sms.dao;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;

@Component("tunnelSmsMoLogDao")
public class TunnelSmsMoLogDaoImp extends SqlMapClientDaoSupport implements TunnelSmsMoLogDao {

	private SqlMapClient sqlMapClient_tunnel;

	public SqlMapClient getSqlMapClient_tunnel() {
		return sqlMapClient_tunnel;
	}

	public void setSqlMapClient_tunnel(SqlMapClient sqlMapClient_tunnel) {
		this.sqlMapClient_tunnel = sqlMapClient_tunnel;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsMoLogBean> queryAll() {
		return new SqlMapClientTemplate( sqlMapClient_tunnel).queryForList("TunnelSmsMoLog.queryAll");
	}

	@Override
	public void delete(SmsMoLogBean bean) {
		new SqlMapClientTemplate( sqlMapClient_tunnel).delete("TunnelSmsMoLog.delete", bean);
	}
}
