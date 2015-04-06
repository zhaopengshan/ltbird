package com.leadtone.mas.bizplug.vote.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.security.dao.base.PageBaseIDao;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaojieguo;

public interface MasVoteResultDao  extends PageBaseIDao{
	public boolean delteVoteResult(Long id);
	public boolean deleteBatchVoteResult(final List<Long> deleList);
	public boolean insertVoteResult(MasSmsToupiaojieguo result);
	public boolean insertBatchVoteResult(final List<MasSmsToupiaojieguo> results);
	public List<MasSmsToupiaojieguo> getVoteResultByID(Long id);
	public List<MasSmsToupiaojieguo> queryVoteResultByNum(Map<String,Object> paraMap);
}
