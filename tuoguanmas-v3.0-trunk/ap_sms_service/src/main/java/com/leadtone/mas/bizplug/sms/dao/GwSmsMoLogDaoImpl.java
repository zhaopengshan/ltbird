package com.leadtone.mas.bizplug.sms.dao;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBeanVO;

@Component("gwSmsMoLogDao")
public class GwSmsMoLogDaoImpl extends SqlMapClientDaoSupport implements GwSmsMoLogDao {

	private SqlMapClient sqlMapClient_gw;

	public SqlMapClient getSqlMapClient_gw() {
		return sqlMapClient_gw;
	}

	public void setSqlMapClient_gw(SqlMapClient sqlMapClient_gw) {
		this.sqlMapClient_gw = sqlMapClient_gw;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsMoLogBean> queryByStatus(int status) {
		return new SqlMapClientTemplate( sqlMapClient_gw).queryForList("GwSmsMoLog.queryByStatus", status);
	}

	@Override
	public List<SmsMoLogBean> queryLtdxByStatus(int status) {
		return new SqlMapClientTemplate( sqlMapClient_gw).queryForList("GwSmsMoLog.queryLtdxByStatus", status);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsMoLogBeanVO> queryByQxtStatusVo(int status) {
		return new SqlMapClientTemplate( sqlMapClient_gw).queryForList("GwSmsMoLog.queryByQxtStatusVo", status);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsMoLogBeanVO> queryByQxtNewStatusVo(int status) {
		return new SqlMapClientTemplate( sqlMapClient_gw).queryForList("GwSmsMoLog.queryByQxtNewStatusVo", status);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsMoLogBean> queryByEmppStatus(int status) {
		return new SqlMapClientTemplate( sqlMapClient_gw).queryForList("GwSmsMoLog.queryByEmppStatus", status);
	}
	@Override
	public void updateStatus(SmsMoLogBean bean) {
		new SqlMapClientTemplate( sqlMapClient_gw).update("GwSmsMoLog.updateStatus", bean);
	}
	@Override
	public void updateEmppStatus(SmsMoLogBean bean) {
		new SqlMapClientTemplate( sqlMapClient_gw).update("GwSmsMoLog.updateEmppStatus", bean);
	}
	public void updateLtdxStatus(SmsMoLogBean bean) {
		new SqlMapClientTemplate( sqlMapClient_gw).update("GwSmsMoLog.updateLtdxStatus", bean);
	}
	@Override
	public void updateQxtStatus(SmsMoLogBeanVO bean) {
		new SqlMapClientTemplate( sqlMapClient_gw).update("GwSmsMoLog.updateQxtStatus", bean);
	}
	@Override
	public void updateQxtNewStatus(SmsMoLogBeanVO bean) {
		new SqlMapClientTemplate( sqlMapClient_gw).update("GwSmsMoLog.updateQxtNewStatus", bean);
	}
}
