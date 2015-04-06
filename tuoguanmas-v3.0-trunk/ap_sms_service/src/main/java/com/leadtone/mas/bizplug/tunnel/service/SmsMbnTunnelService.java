package com.leadtone.mas.bizplug.tunnel.service;

import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
 import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;

/**
 * @author PAN-Z-G
 * 通道服务
 */
public interface SmsMbnTunnelService {

	/**
	 * 查询分页/模糊查询分页
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Page page(PageUtil pageUtil) throws Exception;
	
	/**
	 *  count
	 * @param pageUtil
	 * @return 结果（对象/集合）
	 */
	Integer pageCount(PageUtil pageUtil) throws Exception;
	
	/**
	 * 插入数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer insert(SmsMbnTunnel param) throws Exception;
	/**
	 * 插入数据的同时，添加通道帐户表
	 * @param mbnSmsInbox
	 * @return
	 */
	boolean insert(SmsMbnTunnel smsMbnTunnel,SmsTunnelAccount tunnelAccount) throws Exception;
	/**
	 * 修改数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer update(SmsMbnTunnel param) throws Exception;
	
	/**
	 * 删除数据
	 * @param mbnSmsInbox
	 * @return
	 */
	Integer delete(SmsMbnTunnel param) throws Exception;
	
	/**
	 * 根据pk查询对象 
	 * @param pk
	 * @return 结果（对象/集合）
	 */
	SmsMbnTunnelVO queryByPk(Long pk) throws Exception; 
	 
	 /**
	 * 批量更新操作
	 *
	 * @param paramList
	 *            所要更新的实体集合对象
	 */
	 Integer batchUpdateByList(List<SmsMbnTunnel> paramList) throws Exception; 
	 /**
	  * 删除 置标志位
	  * map 参数：delStatus（bigint）;updateTime(DateTime);ids:VARCHAR
	  * @param pro
	  */
	 boolean updateDel(HashMap<String,Object> pro);
	 /**
	  * 判断通道名称是否存在
	  * @param tunnelName
	  * @return
	  * @throws Exception
	  */
	 boolean isTunnelName(String tunnelName)throws Exception;
	/**
	 * 通道用户页分页
	 * @param pageUtil
	 * @return
	 */
	Page pageConsumer(PageUtil pageUtil);  
	
	/**
	 * 根据商户Pin码查询通道
	 * @return
	 */
	SmsMbnTunnel getTunnelByMerchantPin(Long merchantPin); 
	List<MasTunnel> getMmsTunnelByMerchantPin(Long merchantPin);
	
	List<SmsMbnTunnel> getTunnelByName(String tunnelName)throws Exception; 
        public void addTunnelAndConsumeForMas(List<MasTunnel> tunnels,List<MbnMerchantConsume> consumes,List<MbnMerchantTunnelRelation> merchantTunnelRelations,MbnMerchantVip merchant);
        public List<MasTunnel> getMasTunnelsByMerchantPin(Long merchantPin);
        public void updateTunnelAndConsumeForMas(List<MasTunnel> tunnels,List<MbnMerchantConsume> consumes,MbnMerchantVip merchant);
        public void removerTunnelsAndConsumesForMas(Long merchantPin);
        List<MasTunnel> getMerchantPinTunnels(Long merchantPin);
}
