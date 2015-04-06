package com.leadtone.mas.bizplug.vote.dao;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaodiaocha;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaoxuanxiang;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteExportList;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteList;

@Component("masVoteDao")
public class MasVoteDaoImpl extends BaseDao implements MasVoteDao {
	
	private static final Log log = LogFactory.getLog(MasVoteDaoImpl.class);
	private static String NAMESPACE = "vote";
	private static String column1="";
	@Override
	public Page page(PageUtil pageUtil) {
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("title", 		pageUtil.getColumn2());
		paraMap.put("createby", 	pageUtil.getOperationId());
		paraMap.put("createByName",  	pageUtil.getCreateByName());
		paraMap.put("startDate", 		pageUtil.getStartDate());
        paraMap.put("endDate", 	pageUtil.getEndDate());
        if(!"export".equals(pageUtil.getColumn3())){
	        paraMap.put("startPage", 	(pageUtil.getStart()-1)*pageUtil.getPageSize());
	        paraMap.put("pageSize", 	pageUtil.getPageSize());
        }else{
	        paraMap.put("startPage", 	null);
	        paraMap.put("pageSize", 	null);
        }
        
        if(!"reply".equals(pageUtil.getColumn1())){
        	paraMap.put("nowDate", new Date());
        }
        column1=pageUtil.getColumn1();
        String tmp=".queryVoteBySend";
        if("reply".equals(pageUtil.getColumn1())){
        	tmp=".queryVoteByReply";
        }else if("send".equals(pageUtil.getColumn1())){
        	tmp=".queryVoteBySend";
        }else if("notSend".equals(pageUtil.getColumn1())){
        	tmp=".queryVoteByNotSend";
        }
        Integer recordes = this.pageCount(paraMap);
		List<MasSmsVoteList> results = null;
		if( recordes > 0 ){
			results = getSqlMapClientTemplate()
						.queryForList(NAMESPACE + tmp, paraMap);
		}
		paraMap.clear();
		return new Page( pageUtil.getPageSize(), pageUtil.getStart(), recordes, results);
	}
	
	@Override
	public Integer pageCount(Map<String, Object> paraMap) {
		String tmp="";
        if("reply".equals(column1)){
        	tmp=".queryVoteByReplyCount";
        }else if("send".equals(column1)){
        	tmp=".queryVoteBySendCount";
        }else if("notSend".equals(column1)){
        	tmp=".queryVoteByNotSendCount";
        }
        System.out.println(tmp);
        return (Integer)getSqlMapClientTemplate().queryForObject(NAMESPACE + tmp, paraMap);
	}

	@Override
	public boolean insertVote(MasSmsToupiaodiaocha vote) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert(NAMESPACE + ".insertVote", vote);
		return true;
	}

	@Override
	public boolean insertVoteOption(MasSmsToupiaoxuanxiang option) {
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().insert(NAMESPACE + ".insertVoteOption", option);
		return true;
		
	}
	@Override
	public boolean insertBatchVoteOptions(final List<MasSmsToupiaoxuanxiang> options) {
		// TODO Auto-generated method stub
		try {
			final String sqlName = NAMESPACE + ".insertVoteOption";
	        getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {
	            @Override
	            public Integer doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
	                int result = 0;
	                executor.startBatch();
	                for (Iterator<MasSmsToupiaoxuanxiang> iterator = options.iterator(); iterator.hasNext();) {
	                	MasSmsToupiaoxuanxiang option = iterator.next();
	                    executor.insert(sqlName, option);
	                    result++;
	                }
	                executor.executeBatch();
	                if (log.isDebugEnabled()) {
						log.debug("批量保存成功。");
					}
	                return result;
	            }
	            
	        });
	        return true;
		} catch (Exception e) {
			log.debug("批量保存失败。");
			return false;
		}
        
	}
	@Override
	public boolean closeVote(Long id) {
		 Map<String,Object> paraMap=new HashMap();
		  paraMap.put("id", id);
		  paraMap.put("end_time", new Date());
		// TODO Auto-generated method stub
		getSqlMapClientTemplate().update(NAMESPACE + ".closeVote", paraMap);
		return true;
	}

	@Override
	public boolean deleteVote(Long id) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", id);
		Date nowDate = Calendar.getInstance().getTime();
		param.put("end_time", nowDate);
		getSqlMapClientTemplate().update(NAMESPACE + ".deleteVote", id);
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean deleteBatchVote(final List<Long> deleList) {
		try{
		final String sqlName = NAMESPACE + ".deleteVote";
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
	        if (log.isDebugEnabled()) {
				log.debug("批量删除成功。");
			}
	        return true;
        
		}catch(Exception e){
			log.debug("批量删除失败。");
			return false;
		}
	}

	
	@Override
	public List<MasSmsToupiaoxuanxiang> getOptionList(Long id) {
		// TODO Auto-generated method stub
		List<MasSmsToupiaoxuanxiang> options=getSqlMapClientTemplate().queryForList(NAMESPACE + ".queryVoteOptionById", id);
		return options;
	}

	@Override
	public MasSmsToupiaodiaocha queryVoteById(Long id) {
		// TODO Auto-generated method stub
		MasSmsToupiaodiaocha vote=(MasSmsToupiaodiaocha)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".queryVoteById",id);
		return vote;
	}

	@Override
	public MasSmsToupiaodiaocha queryVoteByTaskNumber(
			Map<String, Object> paraMap) {
		MasSmsToupiaodiaocha vote=(MasSmsToupiaodiaocha)getSqlMapClientTemplate().queryForObject(NAMESPACE + ".queryVoteByTaskNumber",paraMap);
		return vote;
	}

	@Override
	public List<MasSmsVoteExportList> exportSendVote(Map<String,Object> paraMap) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".exportVoteBySend", paraMap);
	}

	@Override
	public List<MasSmsVoteExportList> exportNotSendVote(Map<String,Object> paraMap) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".exportVoteByNotSend", paraMap);
	}

	@Override
	public List<MasSmsVoteExportList> exportReplyVote(Map<String,Object> paraMap) {
		// TODO Auto-generated method stub
		return getSqlMapClientTemplate().queryForList(NAMESPACE + ".exportVoteByReply", paraMap);
	}	
}
