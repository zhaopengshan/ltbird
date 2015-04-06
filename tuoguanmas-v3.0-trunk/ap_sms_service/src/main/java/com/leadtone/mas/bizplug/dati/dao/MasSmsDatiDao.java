package com.leadtone.mas.bizplug.dati.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.dati.bean.MasSmsDati;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiBean;

public interface MasSmsDatiDao {
  public void insert(MasSmsDati masSmsDati);
  public void delete(MasSmsDati masSmsDati);
  public void update(MasSmsDati masSmsDati);
  public  List<MasSmsDati> getMasSmsDatiByCreatorId(long created_by);
  /**
   * TODO:查询用户自己创建的 根据关键字进行查询出试题列表
   * @param created_by 创建者id
   * @param title 标题
   * @param startPage 开始页数
   * @param pageSize 每页显示条数
   * */
  public List<MasSmsDati> getMasSmsDatiByCreatorIdAndSearchInfoForPage(long created_by,String title,int startPage, int pageSize );
  
  public List<MasSmsDati> getMasSmsDatiByCreatorIdAndSearchInfoForPage(Map<String,Object> searchInfo);
  
  /**
   * TODO:查询用户自己创建的根据关键这进行查询出数量
   * @param created_by 创建者id
   * @param title 标题
   * */
  public int getMasSmsDatiByCreatorIdAndSearchInfoCount(long created_by,String title);
  
  public int getMasSmsDatiByCreatorIdAndSearchInfoCount(Map<String,Object> searchInfo);
  
  /**
   * TODO:根据用户信息、用户手机号、任务编号查询出答题对象
   * @param mobile 用户手机号
   * @param pinInfo pin信息
   * @param taskNumber 任务编号
   * */
  public MasSmsDati getMasSmsDatiByPinAndMobileAndTaskNumberQueryInfo(String mobile,long pinInfo,String taskNumber);
  
  /**
   * TODO:根据pin信息来获取企业id
   * @param pinInfo pin信息
   * */
  public Long getEntIdByPinInfo(long pinInfo);
  
  /**
   * TODO:根据创建者id和id主键信息来更新删除状态
   * @param creatorId 创建者id
   * @param ids 短信答题id值
   * */
  public void updateDTDeleteStatusBySearchInfo(long creatorId,String... ids);
  
  public List<MasSmsDatiBean> getMasSmsDatiListBySearchInfo(Map<String,Object> searchInfo);
  
  /**
   * TODO:根据查询信息来获取短信答题信息
   * */
  public MasSmsDatiBean getMasSmsDatiBySearchInfo(Map<String,Object> searchInfo);
}
