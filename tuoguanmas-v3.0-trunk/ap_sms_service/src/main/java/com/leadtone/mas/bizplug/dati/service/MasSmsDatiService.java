package com.leadtone.mas.bizplug.dati.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.dati.bean.MasSmsDati;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiResult;
import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiTiKu;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiBean;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiResultBean;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiTiKuInfo;

/**
 * @author Chenxuezheng
 * TODO:短信答题service接口层
 * */
public interface MasSmsDatiService {
    /**
     * TODO:创建答题题库
     * @param masSmsDatiTiku 题库实例
     * */
	public void createDatiTiKu(MasSmsDatiTiKu masSmsDatiTiku);
	
	/**
	 * TODO:更改答题题库至删除状态
	 * @param tikuId 题库id
	 * @param creatorId 创建者id
	 * */
	public void updateTiKuDeleteStatus(long tikuId,long creatorId);
	
	public void updateTiKuShortInfo(String question, String answer,
			int score, long tikuId,long createBy);
	
	/**
	 *TODO:根据创建者id来获取所有题库
	 *@param  creatorId 创建者id
	 * */
	public List<MasSmsDatiTiKu> getAllTiKuByCreatorId(long creatorId);
	
	/**
	 * TODO:根据题库id、创建者id来获取题库对象
	 * @param tikuId 题库id
	 * @param creatorId 创建者id
	 * */
	public MasSmsDatiTiKu getTiKuById(long tikuId,long creatorId);
	
	
	/**
	 * TODO:根据创建者id、关键字、指定创建时间、页数信息来获取记录集
	 * @param creatorId 创建者id
	 * @param keyword 关键字
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param startPage 起始条数
	 * @param pageSize 每页显示条数
	 * */
	public List<MasSmsDatiTiKu> getAllTiKuListByCreatorIdAndKeywordAndTime(int creatorId,String keyword,String startTime,String endTime,int startPage,int pageSize);
	
	/**
	 * 根据查询信息来获取记录集
	 * @param searchInfo 查询信息
	 * */
	public List<MasSmsDatiTiKu> getAllTiKuListByCreatorIdAndKeywordAndTime(Map<String,Object> searchInfo);
	
	/**
	 * TODO:根据创建者id、关键字、指定创建时间来获取总条数
	 * @param creatorId 创建者id
	 * @param keyword 关键字
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * */
	public Integer getAllTiKuCountByCreatorIdAndKeywordAndTime(int creatorId,String keyword,String startTime,String endTime);
	
	/**
	 * TODO:根据查询信息来获取记录集
	 * @param searchInfo 查询信息
	 * */
	public Integer getAllTiKuCountByCreatorIdAndKeywordAndTime(Map<String,Object> searchInfo);
	
	/**
	 * TODO:根据id来删除题库
	 * @param tikuId 题库id
	 * */
	public void deleteTiKuById(long tikuId);
	
	/**
	 * TODO:根据id来删除题库
	 * @param tikuId 题库id
	 * @param creatorId 创建者id
	 * */
	public void deleteTiKuById(long tikuId,long creatorId);
	
	/**
	 * TODO:添加短信答题   其中包括答题任务、答题试卷
	 * @param  masSmsDati 短信答题任务信息 
	 * @param datiTiKuInfoList 答题试卷中题目编号信息
	 * */
	public void createDatiInfo(MasSmsDati masSmsDati,List<MasSmsDatiTiKuInfo> datiTiKuInfoList);
	
	/**
	 * TODO:编辑短信答题   其中包括短信答题任务、答题试卷
	 * @param  masSmsDati 短信答题任务信息 
	 * @param datiTiKuInfoList 答题试卷中题目编号信息
	 * */
	public void updateDatiInfo(MasSmsDati masSmsDati,List<MasSmsDatiTiKuInfo> datiTiKuInfoList);
	
	/**
	 * TODO:新建短信答题结果记录
	 * @param datiResult 答题结果信息
	 * */
	public void createDatiResultInfo(MasSmsDatiResult datiResult);
	
	/**
	 * TODO:按照答题编号、手机号、题目号来查询短信答题结果
	 * @param dxdtId 短信答题id
	 * @param mobile 手机号码
	 * @param record 题目编号
	 * */
	public List<MasSmsDatiResult> getDatiResultInfoByIdAndMobileAndRecord(long dxdtId,String mobile,int record);
	
	public List<MasSmsDatiResultBean> getDatiResultGroupInfoListBySearchInfo(Map<String,Object> mapInfo);
	public Integer getDatiResultGroupCountBySearchInfo(Map<String,Object> mapInfo);
	
	/**
	 * TODO:根据创建者来查看题库信息
	 * @param creatorId 创建者id
	 * */
	public List<MasSmsDati> getMasSmsDatiByCreatorId(long creatorId);
	
	/**
	 * TODO:根据创建者和关键字来查询答题试卷list
	 * @param creatorId 创建者id
	 * @param keyword 关键字
	 * @param startPage 开始页数
	 * @param pageSize 每页显示条数
	 * */
	public List<MasSmsDati> getMasSmsDatiByCreatorIdAndSearchInfo(long creatorId,String keyword,int startPage,int pageSize);
	
	/**
	 * TODO:根据搜索信息来查询list信息
	 * @param searchMap 查询map
	 * */
	public List<MasSmsDati> getMasSmsDatiBySearchMap(Map<String,Object> searchMap);
	
	public List<MasSmsDatiBean> getMasSmsDatiBeanListBySearchMap(Map<String,Object> searchMap);
	
	
	/**
	 * TODO:根据搜索map来查询记录总数
	 * @param map 查询条件map
	 * */
	public Integer getMasSmsDatiCountBySearchMap(Map<String,Object> map);
	
	/**
	 * TODO:根据创建者和关键字来查询总条数
	 * @param creatorId 创建者id
	 * @param keyword 查询关键字
	 * */
	public Integer getMasSmsDatiCountByCreatorIdAndSearchInfo(long creatorId,String keyword);
	
	/**
	 * TODO:根据查询信息来获取总分数
	 * @param mapInfo 查询信息
	 * */
	public Integer getMasSmsDatiSumBySearchInfo(Map<String,Object> mapInfo);
	
	/**
	 * TODO:根据用户手机号、回复内容、企业pin码来更新答题结果信息
	 * @param mobile 用户手机号
	 * @param replyContent 回复内容
	 * @param entPin 企业pin码
	 * @param sendName 发送人姓名
	 * @param taskNumber 任务码
	 * @param commitTime 提交时间
	 * */
	public void updateSmsDatiResultInfo(String mobile,String replyContent,Long entPin,String sendName,String taskNumber,Date commitTime);
	
	/**
	 * 
	 * */
	public void updateSmsDatiDeleteStatus(long createBy,String... ids);
	
	/**
	 * TODO:根据试卷来更改用户结果的删除状态
	 * @param mapInfo 条件信息
	 * */
	public void updateSmsDatiResultDeleteStatus(Map<String,Object> mapInfo);
	
	public MasSmsDatiBean getMasSmsDatiBySearchInfo(Map<String,Object> mapInfo);
	
}
