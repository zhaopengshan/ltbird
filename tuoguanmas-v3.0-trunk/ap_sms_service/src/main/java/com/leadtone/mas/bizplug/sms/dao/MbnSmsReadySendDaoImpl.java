package com.leadtone.mas.bizplug.sms.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendVO;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseDaoImpl;

@Repository
@SuppressWarnings("unchecked")
public class MbnSmsReadySendDaoImpl extends
		SmsBaseDaoImpl<MbnSmsReadySend, Serializable> implements
		MbnSmsReadySendDao {
	// getByPks
	protected static final String GETBYPKS = ".getByPks";
	protected static final String GETBATCHIDSBYPKS = ".getBatchIdsByPks";
	protected static final String CANCELSEND = ".cancelSend";
	protected static final String GETBYBATCHID = ".getByBatchId";
	protected static final String GETBYBATCHIDS = ".getByBatchIds";
	protected static final String REPLYPAGE = ".replyPage";
	protected static final String BATCHPAGE = ".batchPage";
	protected static final String BATCHPAGECOUNT = ".batchPageCount";
	protected static final String REPLYPAGECOUNT = ".replyPageCount";
	protected static final String FOLLOWPAGE = ".followPage";
	protected static final String INSERT=".insert";
	protected static final String EXPORT = ".exPort";
	protected static final String GETBATCHBYACC = ".getBatchByAcc";
	protected static final String GETBATCHBYACCFROMHAD = ".getBatchByAccFromHad";
	protected static final String GETBATCHBYSERVICEANDOPT = ".getBatchByServiceAndOpt";
	
	protected static final String GETBATCHBYCPOID = ".getBatchByCpoid";
	protected static final String GETBATCHBYCPOIDFROMHAD = ".getBatchByCpoidFromHad";
	
	@Override
	public Long getBatchIdByCpoid(String cpoid, Long merchantPin) {
		HashMap<String,Object> smsPro = new HashMap<String,Object>();
		smsPro.put("cpoid",cpoid);
		smsPro.put("merchantPin", merchantPin);
		
		return (Long) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + GETBATCHBYCPOID, smsPro);
	}

	@Override
	public Long getBatchIdByCpoidFromHad(String cpoid, Long merchantPin) {
		HashMap<String,Object> smsPro = new HashMap<String,Object>();
		smsPro.put("cpoid",cpoid);
		smsPro.put("merchantPin", merchantPin);
		
		return (Long) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + GETBATCHBYCPOIDFROMHAD, smsPro);
	}
	
	public List<MbnSmsReadySendVO> followPage(HashMap<String, Object> page) {
		return getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + FOLLOWPAGE, page);
	}

	public List<MbnSmsReadySend> getByPks(Long[] ids) {
		return (List<MbnSmsReadySend>) getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + GETBYPKS, ids);
	}

	public List<MbnSmsReadySend> getByBatchIds(HashMap<String, Object> batchIds) {
		return (List<MbnSmsReadySend>) getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + GETBYBATCHIDS, batchIds);
	}

	public List<MbnSmsReadySend> getByBatchId(Long batchId, Long mPin, Long createBy) {
		
		HashMap<String,Object> smsPro = new HashMap<String,Object>();
		smsPro.put("batchId",batchId);
		smsPro.put("mPin", mPin);
		smsPro.put("createBy", createBy);
		
		return (List<MbnSmsReadySend>) getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + GETBYBATCHID, smsPro);
	}

	public List<Long> getBatchIdsByPks(String[] ids) {
		return (List<Long>) getSqlMapClientTemplate().queryForList(
				this.sqlMapNamespace + GETBATCHIDSBYPKS, ids);
	}

	public Integer cancelSend(HashMap<String, Object> cancelPro) {
		return getSqlMapClientTemplate().update(
				this.sqlMapNamespace + CANCELSEND, cancelPro);
	}

	public Page page(PageUtil pageUtil) {
		Integer recordes = this.pageCount(pageUtil);
		List<MbnSmsReadySend> results = null;
		if (recordes > 0) {
			results = (List<MbnSmsReadySend>) getSqlMapClientTemplate()
					.queryForList("MbnSmsReadySend.page", pageUtil);
		}
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes,
				results);
	}

	@Override
	public Integer batchDeleteByList(List<MbnSmsReadySend> paramList) {
		//
		return super.batchDeleteByList(paramList);
	}

	@Override
	public Page pageVO(PageUtil pageUtil) {
		Integer recordes = this.pageCount(pageUtil);
		Object results = null;
		if (recordes > 0) {
			results = getSqlMapClientTemplate().queryForList(
					this.sqlMapNamespace + ".pageVO", pageUtil);
		}

		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes,
				results);
	}

	public Page batchPage(PageUtil pageUtil) {

		Integer recordes = this.batchPageCount(pageUtil);
		List<MbnSmsReadySend> results = null;
		if (recordes > 0) {
			results = (List<MbnSmsReadySend>) getSqlMapClientTemplate()
					.queryForList(this.sqlMapNamespace + BATCHPAGE, pageUtil);
		}
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes,
				results);
	}

	public Integer batchPageCount(PageUtil pageUtil) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + BATCHPAGECOUNT, pageUtil);
	}

	@Override
	public Page replyPage(PageUtil pageUtil) {
		int recount = (Integer) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + REPLYPAGECOUNT, pageUtil);
		List<MbnSmsReadySendVO> date = null;
		if (recount > 0) {
			date = getSqlMapClientTemplate().queryForList(
					this.sqlMapNamespace + REPLYPAGE, pageUtil);
		}
		//
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recount,
				date);
	}

	@Override
	public Page extPortAll(PageUtil pageUtil) {
		Object date=null;
			date = getSqlMapClientTemplate().queryForList(
					this.sqlMapNamespace + EXPORT, pageUtil);
		 return new Page(pageUtil.getPageSize(), pageUtil.getStart(), 0,
				date);
	}

    @Override
    public Page statisticQuery(HashMap<String, Object> param) {
    	Integer recordes = 0;
    	try{
    		recordes = (Integer) getSqlMapClientTemplate().queryForObject(
                    this.sqlMapNamespace + ".pageCountSummary", param);
    	}catch(Exception e){
    		recordes = 0;
    	}
        Object results = null;
        if (recordes > 0) {
            results = getSqlMapClientTemplate()
                    .queryForList(this.sqlMapNamespace + ".pageSummary", param);
        }
        return new Page((Integer) param.get("pageSize"), (Integer) param.get("startPage"), recordes, results);
    }

    @Override
    public List<MbnSmsReadySend> statisticSummary(HashMap<String, Object> param) {
    	try{
    		return getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + ".pageSummary", param);
    	}catch(Exception e){
    		return new ArrayList<MbnSmsReadySend>();
    	}
    }

	@Override
	public Long getBatchIdByAcc(String acc) {
		// TODO Auto-generated method stub
		return (Long) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + GETBATCHBYACC, acc);
	}
	@Override
	public Long getBatchIdByAccFromHad(String acc) {
		// TODO Auto-generated method stub
		return (Long) getSqlMapClientTemplate().queryForObject(
				this.sqlMapNamespace + GETBATCHBYACCFROMHAD, acc);
	}
	@Override
	 public Long getBatchByServiceAndOpt(HashMap<String, Object> param) {

	        return (Long) getSqlMapClientTemplate().queryForObject(
					this.sqlMapNamespace + GETBATCHBYSERVICEANDOPT, param);
	    }
}
