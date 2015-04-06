/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.hbs.dao.impl;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.delegatemas.hbs.bean.ScheduleMapping;
import com.leadtone.delegatemas.hbs.dao.IScheduleMappingDAO;
import com.leadtone.mas.bizplug.dao.BaseDao;
import java.sql.SQLException;
import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Repository;

/**
 *
 * @author blueskybluesea
 */
@Repository("scheduleMappingDAOImpl")
public class ScheduleMappingDAOImpl extends BaseDao implements IScheduleMappingDAO {
    private String namespace = "schedule";
    @Override
    public void batchSave(final List<ScheduleMapping> mappings) {
       final String sqlName = namespace + ".createMapping";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                for (ScheduleMapping mapping : mappings) {
                    executor.insert(sqlName, mapping);
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
    }

    @Override
    public void batchUpdate(final List<ScheduleMapping> mappings) {
        final String sqlName = namespace + ".updateMapping";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                for (ScheduleMapping mapping : mappings) {
                    executor.update(sqlName, mapping);
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
    }

    @Override
    public void batchDelete(final List<ScheduleMapping> mappings) {
        final String sqlName = namespace + ".deleteMappging";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                for (ScheduleMapping mapping : mappings) {
                    executor.delete(sqlName, mapping.getGatewayId());
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
    }
    
}
