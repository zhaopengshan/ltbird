package com.leadtone.mas.bizplug.dati.dao;

import java.util.List;
import java.util.Map;

import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiTiKu;
/**
 * @author Chenxuezheng
 * 短信答题题库DAO层
 * */
public interface MasSmsDatiTiKuDao {
  /**
   * TODO:插入短信题库
   * @param mbnSmsDatiTiKu 题库对象
   * */
  public void insert(MasSmsDatiTiKu mbnSmsDatiTiKu);
  /**
   * TODO:删除短信题库
   * @param mbnSmsDatiTiKu 题库对象
   * */
  public void delete(MasSmsDatiTiKu mbnSmsDatiTiKu);
  
  /**
   * TODO:根据题库id和用户id来删除题库
   * @param tikuId 题库id
   * @param creatorId 创建者id
   * */
  public void delete(long tikuId, long creatorId);
  
  /**
   * TODO:更新短信题库
   * @param mbnSmsDatiTiKu 题库对象
   * */
  public void update(MasSmsDatiTiKu mbnSmsDatiTiKu);
  
  /**
   * TODO:更新题库简短信息
   * 
   * */
  public void updateSmsDatiTiKuInfo(String question,String answer,int score,long tikuId,long createBy);
  
  /**
   * TODO:更新题库删除状态  更改为-1
   * @param tikuId 题库id
   * @param creatorId 创建者id
   * */
  public void updateDeleteStatus(long tikuId,long creatorId);
  
  /**
   * TODO:根据创建者id、题库id来获取题库对象
   * @param tikuId 题库id
   * @param creatorId 创建者id
   * */
  public MasSmsDatiTiKu getTiKuRecordById(long tikuId,long creatorId);
  
  /**
   * TODO:根据用户id来获取题库信息
   * */
  public List<MasSmsDatiTiKu> getSmsDatiTiKuListById(long createdBy);
  
  /**
   * TODO:根据title关键字、创建人id、创建时间来对题库进行分页显示
   * @param creatorId 创建人id
   * @param titleKeyword 标题关键字
   * @param startDate 开始时间
   * @param endDate 结束时间
   * @param startPage 开始记录条数
   * @param pageSize 每页显示记录条数
   * */
  public List<MasSmsDatiTiKu> getSmsDatiTiKuListByKeywordAndCreatorForPage(long creatorId,String titleKeyword,String startDate,String endDate,int startPage,int pageSize);
  /**
   * TODO:根据查询map来获取分页信息
   * @param searchInfo 查询条件信息
   * */
  public List<MasSmsDatiTiKu> getSmsDatiTiKuListByKeywordAndCreatorForPage(Map<String,Object> searchInfo);
  
  
  /**
   * TODO:根据title关键字、创建人id、创建时间来统计总条数
   * @param creatorId 创建人id
   * @param titleKeyword 标题关键字
   * @param startDate 开始时间
   * @param endDate 结束时间
   * */
  public Integer getSmsDatiTiKuCountByKeywordAndCreator(long creatorId,String titleKeyword,String startDate,String endDate);
  /**
   * TODO:根据查询条件来获取总的条数
   * @param searchInfo 查询条件Map
   * */
  public Integer getSmsDatiTiKuCountByKeywordAndCreator(Map<String,Object> searchInfo);
  
  /**
   * TODO:根据创建者、题库id来查询出总分数
   * @param map 查询条件map
   * */
  public Integer getSmsDatiTiKuSumBySearchInfo(Map<String,Object> map);
  
}
