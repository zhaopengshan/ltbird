package com.leadtone.mas.bizplug.dati.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiResult;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiResultBean;

public interface MasSmsDatiResultDao {
   /**
    *TODO:新增结果
    *@param  masSmsDatiResult 结果对象
    * */
	public void insert(MasSmsDatiResult masSmsDatiResult);
	
	public List<MasSmsDatiResult> getMosSmsDtResultByIdAndMobileAndOrder(long dxdtId,String mobile,int orderNumber);
	
	/**
	 * TODO:根据答题id、手机号码、创建者来查询出当前所答的最大题号
	 * @param dxdtId 短信答题id
	 * @param mobile 手机号码
	 * @param createdBy 创建者id
	 * */
	public Integer getMosSmsDtMaxNumResultBySearch(long dxdtId,String mobile,long createdBy);
	
	public List<MasSmsDatiResultBean> getMosSmsDtResultListBySearch(long dxdtId,long createdBy);
	public List<MasSmsDatiResultBean> getMosSmsDtResultListBySearchInfo(Map<String,Object> mapInfo);
	public Integer getMosSmsDtResultCountBySearchInfo(Map<String,Object> mapInfo);
	public void updateMosSmsResultDeleteStatus(Map<String,Object> mapInfo);
}
