package com.leadtone.mas.bizplug.vote.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.security.dao.base.PageBaseIDao;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaodiaocha;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaoxuanxiang;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaojieguo;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteExportList;

public interface MasVoteDao extends PageBaseIDao {
	public boolean insertVote(MasSmsToupiaodiaocha vote);
	
	public boolean insertVoteOption(MasSmsToupiaoxuanxiang option);
	
	public boolean insertBatchVoteOptions(final List<MasSmsToupiaoxuanxiang> options);
	
	public boolean closeVote(Long id);
	
	public boolean deleteVote(Long id);
	
	public boolean deleteBatchVote(final List<Long> deleList);
	
	public List<MasSmsToupiaoxuanxiang> getOptionList(Long id);
	
	public MasSmsToupiaodiaocha queryVoteById(Long id);
	

	public MasSmsToupiaodiaocha queryVoteByTaskNumber(Map<String,Object> paraMap);
	
	public List<MasSmsVoteExportList> exportSendVote(Map<String,Object> paraMap);
	public List<MasSmsVoteExportList> exportNotSendVote(Map<String,Object> paraMap);
	public List<MasSmsVoteExportList> exportReplyVote(Map<String,Object> paraMap);

}
