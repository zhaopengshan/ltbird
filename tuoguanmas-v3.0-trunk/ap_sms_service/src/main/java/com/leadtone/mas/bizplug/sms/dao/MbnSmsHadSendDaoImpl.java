package com.leadtone.mas.bizplug.sms.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil; 
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsHadSendVO;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseDaoImpl;
 @Repository 
 @SuppressWarnings("unchecked")
public class MbnSmsHadSendDaoImpl  extends SmsBaseDaoImpl<MbnSmsHadSend,Long> implements MbnSmsHadSendDao{
	 
	 protected static final String UPDATEDEL = ".updateDel";
	 protected static final String GETBYBATCHID = ".getByBatchId";
	 protected static final String GETBYPKS = ".getByPks";
	 protected static final String BATCHPAGE = ".batchPage";
	 protected static final String BATCHAGECOUNT = ".batchPageCount";
	 protected static final String REPLYPAGE=".replyPage";
	 protected static final String REPLYPAGECOUNT=".replyPageCount";

	 protected static final String FOLLOWPAGE = ".followPage";
	 protected static final String UPDATE_DEL_BY_BATCH_ID = ".updateDelByBatchId";
	 
	 public List<MbnSmsHadSendVO> followPage(HashMap<String,Object> page){
		 return  getSqlMapClientTemplate()
			.queryForList(this.sqlMapNamespace + FOLLOWPAGE, page);
	 }
	 
	 public Page batchPage(PageUtil pageUtil){
		Integer recordes = this.batchPageCount(pageUtil);
		List<MbnSmsHadSend> results = null;
		if (recordes > 0) {
			results = (List<MbnSmsHadSend>)getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + BATCHPAGE,pageUtil);
		}
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes,
				results);
	 }
	 public Integer batchPageCount(PageUtil pageUtil){
		return (Integer) getSqlMapClientTemplate().queryForObject(this.sqlMapNamespace + BATCHAGECOUNT,pageUtil);
	 }
	 public List<MbnSmsHadSend> getByPks(Long[] ids){
		 return (List<MbnSmsHadSend>)getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + GETBYPKS,ids);
	 }
	 
	 public	Integer updateDel(String smsIds){
		 return (Integer) getSqlMapClientTemplate().update(
					this.sqlMapNamespace + UPDATEDEL, smsIds);
	 }
	 /**
		 * 查询批次短信
		 * @param batchId
		 * @return
		 */
 	public List<MbnSmsHadSend> getByBatchId(Long batchId, Long mPin, Long createBy){
 		HashMap<String,Object> smsPro = new HashMap<String,Object>();
		smsPro.put("batchId",batchId);
		smsPro.put("mPin", mPin);
		smsPro.put("createBy", createBy);
		return (List<MbnSmsHadSend>)getSqlMapClientTemplate().queryForList(this.sqlMapNamespace + GETBYBATCHID,smsPro);
	}

	 
	
	@Override
	public Page pageVO(PageUtil pageUtil) {
		Integer recordes = this.pageCount(pageUtil);
		List<MbnSmsHadSendVO> results = null;
		if (recordes > 0) {
			results = (List<MbnSmsHadSendVO>) getSqlMapClientTemplate()
					.queryForList(this.sqlMapNamespace + ".pageVO", pageUtil);
		}
		
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), recordes,results);
	}
	@Override
	public Page replyPage(PageUtil pageUtil) {
		// 总数
		int resultCount=replyPageCount(pageUtil);
		List<MbnSmsHadSendVO> data=null;
		if(resultCount!=0)
 			data=getSqlMapClientTemplate().queryForList(this.sqlMapNamespace+REPLYPAGE,pageUtil);
		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), resultCount, data);
	}
	@Override
	public Integer replyPageCount(PageUtil pageUtil) {
		return (Integer) getSqlMapClientTemplate().queryForObject(this.sqlMapNamespace+REPLYPAGECOUNT, pageUtil);
	}

	@Override
	public Page extport(PageUtil pageUtil) {
		Object data=getSqlMapClientTemplate().queryForList(this.sqlMapNamespace+".export",pageUtil);
 		return new Page(pageUtil.getPageSize(), pageUtil.getStart(), 0, data);
	}

	@Override
	public Integer updateDelByBatchId(Long[] batchIds) {
		 return (Integer) getSqlMapClientTemplate().update(
					this.sqlMapNamespace + UPDATE_DEL_BY_BATCH_ID, batchIds);
	}
}
