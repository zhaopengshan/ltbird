package com.leadtone.mas.bizplug.openaccount.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Component;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.dao.MbnConfigMerchantIDao;
@Component("mbnConfigMerchantIDao")
public class MbnConfigMerchantDaoImpl extends BaseDao implements
		MbnConfigMerchantIDao {
	private static final Log log = LogFactory.getLog(MbnConfigMerchantDaoImpl.class);
	@Override
	public boolean insert(MbnConfigMerchant mbnConfigMerchant) {
        getSqlMapClientTemplate().insert("MbnConfigMerchant.insert", mbnConfigMerchant);
        return true;
	}

	@Override
	public boolean update(MbnConfigMerchant mbnConfigMerhcant) {
		int i=getSqlMapClientTemplate().update("MbnConfigMerchant.update",mbnConfigMerhcant);
		if(i>0){
			return true;
		}
		return false;
	}

	@Override
	public boolean batchSave(final List<MbnConfigMerchant> list) {
	        int i= (Integer) getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {

	            public Integer doInSqlMapClient(SqlMapExecutor executor)
	                    throws SQLException {
	                int result = 0;
	                int executorCount = 0;
	                // TODO Auto-generated method stub
	                executor.startBatch();
	                for (Iterator<MbnConfigMerchant> iterator = list.iterator(); iterator.hasNext();) {
	                	MbnConfigMerchant t = iterator.next();
	                    executor.insert("MbnConfigMerchant.insert", t);
	                    executorCount++;
	                    if (executorCount % 1000 == 0) {
	                        result = executor.executeBatch();
	                    }
	                }
	                if (executorCount % 1000 != 0) {
	                    result = executor.executeBatch();
	                }
	                if (log.isDebugEnabled()) {
	                    log.debug("批量保存成功。");
	                }
	                return result;
	            }
	        });
	        if(i>0){
	        	return true;
	        }
	        return false;
	}

	@Override
	//通过merCharntPin查询参数
	public List<MbnConfigMerchant> list(MbnConfigMerchant mbnConfigMerhcant) {
		return (List)getSqlMapClientTemplate().queryForList("MbnConfigMerchant.list", mbnConfigMerhcant);
	}

	@Override
	public MbnConfigMerchant load(long merchantPin, String name) {
		Map<String, Object> m=new HashMap<String,Object>();
		m.put("merchantPin", merchantPin);
		m.put("name", name);
                MbnConfigMerchant mcm= (MbnConfigMerchant)this.getSqlMapClientTemplate().queryForObject("MbnConfigMerchant.load",m);
                return mcm;
        }

	@Override
	public boolean batchUpdate(final List<MbnConfigMerchant> list) {
		 int i= (Integer) getSqlMapClientTemplate().execute(new SqlMapClientCallback<Integer>() {

	            public Integer doInSqlMapClient(SqlMapExecutor executor)
	                    throws SQLException {
	                int result = 0;
	                int executorCount = 0;
	                // TODO Auto-generated method stub
	                executor.startBatch();
	                for (Iterator<MbnConfigMerchant> iterator = list.iterator(); iterator.hasNext();) {
	                	MbnConfigMerchant t = iterator.next();
	                    executor.update("MbnConfigMerchant.update", t);
	                    executorCount++;
	                    if (executorCount % 1000 == 0) {
	                        result = executor.executeBatch();
	                    }
	                }
	                if (executorCount % 1000 != 0) {
	                    result = executor.executeBatch();
	                }
	                if (log.isDebugEnabled()) {
	                    log.debug("批量更新成功。");
	                }
	                return result;
	            }
	        });
	        if(i>0){
	        	return true;
	        }
	        return false;
	}

}
