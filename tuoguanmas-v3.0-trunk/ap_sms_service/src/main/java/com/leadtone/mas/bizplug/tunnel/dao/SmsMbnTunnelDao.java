/**
 *
 */
package com.leadtone.mas.bizplug.tunnel.dao;

import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import java.util.HashMap;
import java.util.List;

import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;
import com.leadtone.mas.bizplug.tunnel.dao.base.TunnelBaseIDao;

/**
 * @author PAN-Z-G
 *
 */
public interface SmsMbnTunnelDao extends TunnelBaseIDao<SmsMbnTunnel, java.lang.Long> {

    /**
     * 删除 置标志位 map 参数：delStatus（bigint）;updateTime(DateTime);ids:VARCHAR
     *
     * @param pro
     */
    boolean updateDel(HashMap<String, Object> pro);

    /**
     * 根据通道名称查询通道个数
     *
     * @param tunnelName
     * @return
     */
    Integer getByName(String tunnelName) throws Exception;

    /**
     * 根据通道名称查询通道
     *
     * @param tunnelName
     * @return
     */
    List<SmsMbnTunnel> getTunnelByName(String tunnelName) throws Exception;

    /**
     * 插入通道的同时添加通道帐户信息
     *
     * @param smsMbnTunnel
     * @param tunnelAccount
     * @return
     * @throws Exception
     */
    Integer insert(SmsMbnTunnel smsMbnTunnel, SmsTunnelAccount tunnelAccount) throws Exception;

    /**
     * 根据商户对通道分页
     *
     * @param pageUtil
     * @return
     */
    Page pageConsumer(PageUtil pageUtil);

    /**
     * 统计符合条件的列数
     *
     * @param pageUtil
     * @return
     */
    public Integer pageConsumerCount(PageUtil pageUtil);

    /**
     * 更加通道ID查询出通道的内容
     *
     * @param tunnelId
     * @return
     */
    SmsMbnTunnelVO queryByTunnelId(Long tunnelId);

    /**
     * 根据商户Pin码查询通道
     *
     * @return
     */
    SmsMbnTunnel getTunnelByMerchantPin(Long merchantPin);
    MasTunnel getByTunnelPk(Long id);

    public void insertMasTunnel(MasTunnel tunnel);

    public List<MasTunnel> queryMasTunnelsByMerchantPin(Long merchantPin);

    public void updateMasTunnel(MasTunnel tunnel);

    public void deleteMasTunnel(Long merchantPin);

    List<MasTunnel> getMmsTunnelByMerchantPin(Long merchantPin);
    List<MasTunnel> getMerchantPinTunnels(Long merchantPin);
}
