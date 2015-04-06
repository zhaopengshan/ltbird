package com.leadtone.zxt.dao.sqlmap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.leadtone.zxt.bean.ZXTUser;
import com.leadtone.zxt.dao.ZXTUserDao;

@Component("zxtUserDao")
public class SqlMapZXTUserDao extends SqlMapBaseDao<ZXTUser, Integer> implements
		ZXTUserDao {

	private static final Logger logger = Logger
			.getLogger(SqlMapZXTUserDao.class);

	public int addUser(ZXTUser user) {
		int ret = 0;
		try {
			getSqlMapClientTemplate().insert(namespace + ".addUser", user);
			logger.info("addUser ret=" + ret);
		} catch (Exception e) {
			logger.error("addUser :" + e.getMessage());
		}
		return ret;
	}

	public int delUser(ZXTUser user) {
		int ret = 0;
		try {
			getSqlMapClientTemplate().insert(namespace + ".delUserByAcount",
					user);
			logger.info("delUser ret=" + ret);
		} catch (Exception e) {
			logger.error("delUser :" + e.getMessage());
		}
		return ret;
	}

	public int getIdByAccount(String account) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace + ".getIdByAccount", account);
	}

}
