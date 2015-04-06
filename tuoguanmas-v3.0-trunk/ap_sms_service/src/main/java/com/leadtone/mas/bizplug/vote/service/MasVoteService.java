package com.leadtone.mas.bizplug.vote.service;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaodiaocha;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaojieguo;
import com.leadtone.mas.bizplug.vote.bean.MasSmsToupiaoxuanxiang;
import com.leadtone.mas.bizplug.vote.bean.MasSmsVoteExportList;

public interface MasVoteService {
	/**新建投票
	 * @param vote
	 * @return
	 */
	public boolean insertVote(MasSmsToupiaodiaocha vote);
	
	/**新建投票选项
	 * @param option
	 * @return
	 */
	public boolean insertVoteOption(MasSmsToupiaoxuanxiang option);
	
	/**批量新建投票选项
	 * @param options
	 * @return
	 */
	public boolean insertBatchVoteOptions(final List<MasSmsToupiaoxuanxiang> options);
	
	/**关闭投票
	 * @param id
	 * @return
	 */
	public boolean closeVote(Long id);
	
	/**删除投票
	 * @param id
	 * @return
	 */
	public boolean deleteVote(Long id);
	
	/**批量删除投票
	 * @param deleList
	 * @return
	 */
	public boolean deleteBatchVote(final List<Long> deleList);
	
	/**根据投票ID获取投票选项
	 * @param id
	 * @return
	 */
	public List<MasSmsToupiaoxuanxiang> getOptionList(Long id);
	/**根据投票ID获取投票调查
	 * @param id
	 * @return
	 */
	public MasSmsToupiaodiaocha queryVoteById(Long id);
	/**删除投票结果
	 * @param id
	 * @return
	 */
	public boolean delteVoteResult(Long id);
	/**批量删除投票结果
	 * @param deleList
	 * @return
	 */
	public boolean deleteBatchVoteResult(final List<Long> deleList);
	/**新建投票结果
	 * @param result
	 * @return
	 */
	public boolean insertVoteResult(MasSmsToupiaojieguo result);
	/**批量新建投票结果
	 * @param results
	 * @return
	 */
	public boolean insertBatchVoteResult(final List<MasSmsToupiaojieguo> results);
	/**获取投票结果
	 * @param id
	 * @return
	 */
	public List<MasSmsToupiaojieguo> getVoteResultByID(Long id);
	/**投票翻页
	 * @param pageUtil
	 * @return
	 */
	public Page page(PageUtil pageUtil);
	/**投票结果翻页
	 * @param pageUtil
	 * @return
	 */
	public Page pageResult(PageUtil pageUtil);
	
	public MasSmsToupiaodiaocha queryVoteByTaskNumber(Map<String,Object> paraMap);
	public List<MasSmsToupiaojieguo> queryVoteResultByNum(Map<String,Object> paraMap);
	
	public void handleVoteInbox(MbnSmsInbox inbox);
	
	public List<MasSmsVoteExportList> exportVoteList(String type,Map<String,Object> paraMap);
	
}
