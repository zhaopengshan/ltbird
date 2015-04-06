package com.leadtone.mas.bizplug.vote.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.dao.BaseDao;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaojieguo;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteList;

@Component("masVoteResultDao")
public class MasVoteResultDaoImpl extends BaseDao implements MasVoteResultDao {
	private static final Log log = LogFactory.getLog(MasVoteResultDaoImpl.class);
	private static String NAMESPACE = "vote";

	@Override
	public Page page(PageUtil pageUtil) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("tpdc_id", 		pageUtil.getBatchId());
		paraMap.put("name", 	pageUtil.getContactName());
		paraMap.put("mobile",  	pageUtil.getMobile());
		paraMap.put("startDate", 		pageUtil.getStartDate());
        paraMap.put("endDate", 	pageUtil.getEndDate());
        if("export".equals(pageUtil.getColumn3())){
        	paraMap.put("startPage", 	null);
            paraMap.put("pageSize", 	null);
        }else{
	        paraMap.put("startPage", 	(pageUtil.getStart()-1)*pageUtil.getPageSize());
	        paraMap.put("pageSize", 	pageUtil.getPageSize());
        }
        Integer recordes = this.pageCount(paraMap);
		List<MasSmsToupiaojieguo> results = null;
		if( recordes > 0 ){
			results = getSqlMapClientTemplate()
						.queryForList(NAMESPACE + ".queryVoteResultById", paraMap);
		}
		paraMap.clear();
		return new Page( pageUtil.getPageSize(), pageUtil.getStart(), recordes, results);
	}

	@Override
	public Integer pageCount(Map<String, Object> paraMap) {
		// TODO Auto-generated method stub
		return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".queryVoteResultByIdCount", paraMap);
	}


	@Override
	public boolean delteVoteResult(Long id) {
		getSqlMapClientTemplate().delete(NAMESPACE + ".deleteVoteResult", id);
		return true;
	}

	@Override
	public boolean deleteBatchVoteResult(final List<Long> deleList) {
		final String sqlName = NAMESPACE + ".deleteVoteResult";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                for (Iterator<Long> iterator = deleList.iterator(); iterator.hasNext();) {
                    Long id = iterator.next();
                    executor.delete(sqlName, id);
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });
        return true;
	}

	@Override
	public boolean insertVoteResult(MasSmsToupiaojieguo result) {
		getSqlMapClientTemplate().insert(NAMESPACE + ".insertVoteResult", result);
		 return true;
	}

	@Override
	public boolean insertBatchVoteResult(final List<MasSmsToupiaojieguo> results) {
		final String sqlName = NAMESPACE + ".insertVoteResult";
        getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {
            @Override
            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                int result = 0;
                executor.startBatch();
                for (Iterator<MasSmsToupiaojieguo> iterator = results.iterator(); iterator.hasNext();) {
                	MasSmsToupiaojieguo jieguo = iterator.next();
                    executor.insert(sqlName, jieguo);
                    result++;
                }
                executor.executeBatch();
                return result;
            }
        });	
        return true;
	}

	@Override
	public List<MasSmsToupiaojieguo> getVoteResultByID(Long id) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryVoteResultList", id);
	}

	@Override
	public List<MasSmsToupiaojieguo> queryVoteResultByNum(Map<String, Object> paraMap) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryVoteResultByNum", paraMap);
	}

}
