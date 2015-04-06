package com.leadtone.mas.bizplug.sms.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBeanVO;

@Component("smsMoLogDao")
public class SmsMoLogDaoImp extends SqlMapClientDaoSupport implements SmsMoLogDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SmsMoLogBean> queryByStatus(int status, String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("type", type);
		return getSqlMapClientTemplate().queryForList("SmsMoLog.queryByStatus", map);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SmsMoLogBeanVO> queryByStatusVo(int status, String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("type", type);
		return getSqlMapClientTemplate().queryForList("SmsMoLog.queryByStatusVo", map);
	}

	@Override
	public void updateStatus(SmsMoLogBean bean) {
		getSqlMapClientTemplate().update("SmsMoLog.updateStatus", bean);
	}

	@Override
	public void insert(SmsMoLogBean bean) {
		getSqlMapClientTemplate().insert("SmsMoLog.insert", bean);
	}
	@Override
	public void insertVo(SmsMoLogBeanVO bean) {
		getSqlMapClientTemplate().insert("SmsMoLog.insertVo", bean);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
