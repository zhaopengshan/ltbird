package com.leadtone.mas.bizplug.dati.dao;

import java.util.Date;
import java.util.List;

import com.leadtone.mas.bizplug.dati.bean.MasSmsDatiShiJuan;
import com.leadtone.mas.bizplug.dati.pojo.MasSmsDatiTiKuInfo;

public interface MasSmsDatiShiJuanDao {
  public void insert(MasSmsDatiShiJuan masSmsDatiShiJuan);
  public void update(MasSmsDatiShiJuan masSmsDatiShiJuan);
  public void delete(MasSmsDatiShiJuan masSmsDatiShiJuan);
  public void delete(long dxdtId,long creatorId);
  public List<MasSmsDatiShiJuan> getMasSmsDatiShiJuanByDatiId(long datiId);
  public void insertShiJuanSelectTiKu(long dxdt_id,long createBy,List<MasSmsDatiTiKuInfo> datiTiKuInfoList);
  public MasSmsDatiShiJuan getMasSmsDatiShiJuanBySearchInfo(long dxdtId,int orderNumber);
}
