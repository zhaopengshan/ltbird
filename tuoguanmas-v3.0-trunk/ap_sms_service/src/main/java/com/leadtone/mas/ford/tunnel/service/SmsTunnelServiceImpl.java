/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.ford.tunnel.service;

import com.leadtone.delegatemas.hbs.bean.ScheduleMapping;
import com.leadtone.delegatemas.hbs.dao.IScheduleMappingDAO;
import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.dao.IRegionDAO;
import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.dao.UsersDao;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantTunnelRelationDaoImpl;
import com.leadtone.mas.bizplug.tunnel.dao.SmsMbnTunnelDao;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author blueskybluesea
 */
@Service(value = "smsFordTunnelService")
public class SmsTunnelServiceImpl implements SmsMbnTunnelService {
    @Resource(name="smsMbnTunnelService")
    private SmsMbnTunnelService tunnelService;
    @Resource(name = "regionDAOImpl")
    private IRegionDAO regionDAO;
    @Resource
    private SmsMbnTunnelDao smsMbnTunnelDao;
    @Resource(name = "mbnMerchantTunnelRelationDao")
    private MbnMerchantTunnelRelationDaoImpl merchantTunnelRelation;
    @Resource(name = "scheduleMappingDAOImpl")
    private IScheduleMappingDAO scheduleMappingDAO;
    @Resource
    private UsersDao usersDao;
    @Override
    public Page page(PageUtil pageUtil) throws Exception {
        return tunnelService.page(pageUtil);
    }

    @Override
    public Integer pageCount(PageUtil pageUtil) throws Exception {
        return tunnelService.pageCount(pageUtil);
    }

    @Override
    public Integer insert(SmsMbnTunnel param) throws Exception {
        return tunnelService.insert(param);
    }

    @Override
    public boolean insert(SmsMbnTunnel smsMbnTunnel, SmsTunnelAccount tunnelAccount) throws Exception {
        return tunnelService.insert(smsMbnTunnel, tunnelAccount);
    }

    @Override
    public Integer update(SmsMbnTunnel param) throws Exception {
        return tunnelService.update(param);
    }

    @Override
    public Integer delete(SmsMbnTunnel param) throws Exception {
        return tunnelService.delete(param);
    }

    @Override
    public SmsMbnTunnelVO queryByPk(Long pk) throws Exception {
        return tunnelService.queryByPk(pk);
    }

    @Override
    public Integer batchUpdateByList(List<SmsMbnTunnel> paramList) throws Exception {
        return tunnelService.batchUpdateByList(paramList);
    }

    @Override
    public boolean updateDel(HashMap<String, Object> pro) {
        return tunnelService.updateDel(pro);
    }

    @Override
    public boolean isTunnelName(String tunnelName) throws Exception {
        return tunnelService.isTunnelName(tunnelName);
    }

    @Override
    public Page pageConsumer(PageUtil pageUtil) {
        return tunnelService.pageConsumer(pageUtil);
    }

    @Override
    public SmsMbnTunnel getTunnelByMerchantPin(Long merchantPin) {
        return tunnelService.getTunnelByMerchantPin(merchantPin);
    }

    @Override
    public List<MasTunnel> getMmsTunnelByMerchantPin(Long merchantPin) {
        return tunnelService.getMmsTunnelByMerchantPin(merchantPin);
    }

    @Override
    public List<SmsMbnTunnel> getTunnelByName(String tunnelName) throws Exception {
        return tunnelService.getTunnelByName(tunnelName);
    }

    @Override
    public void addTunnelAndConsumeForMas(List<MasTunnel> tunnels, List<MbnMerchantConsume> consumes, List<MbnMerchantTunnelRelation> merchantTunnelRelations, MbnMerchantVip merchant) {
        try {
            List<Region> regions = regionDAO.queryRegionsByProvinceId(Long.parseLong(merchant.getProvince()));
            List<ScheduleMapping> mappings = new ArrayList<ScheduleMapping>();
            for (MasTunnel tunnel : tunnels) {
                if (tunnel.getId() != null) {
                    tunnel.setProvince(regions.get(0).getCode());
                    if (tunnel.getClassify() == 2) {
                        ScheduleMapping mapping = new ScheduleMapping();
                        mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
                        mapping.setGatewayId("SM" + tunnel.getId());
                        mapping.setGwIp(tunnel.getGatewayAddr());
                        mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
                        mapping.setGwUser(tunnel.getUser());
                        mapping.setGwPasswd(tunnel.getPasswd());
                        mapping.setProtocolVersion(tunnel.getProtocalVersion());
                        mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_" + tunnel.getSmsCorpIdent());
                        mapping.setSenderCode(tunnel.getAccessNumber());
                        mapping.setServiceCode(tunnel.getServiceId());
                        mappings.add(mapping);
                    }                   
                    smsMbnTunnelDao.insertMasTunnel(tunnel);
                }
            }

            for (MbnMerchantTunnelRelation merchantTunnelRelation : merchantTunnelRelations) {
                this.merchantTunnelRelation.insert(merchantTunnelRelation);
            }            
            if (mappings.size() > 0) {
                this.scheduleMappingDAO.batchSave(mappings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<MasTunnel> getMasTunnelsByMerchantPin(Long merchantPin) {
        return tunnelService.getMasTunnelsByMerchantPin(merchantPin);
    }

    @Override
    public void updateTunnelAndConsumeForMas(List<MasTunnel> tunnels, List<MbnMerchantConsume> consumes, MbnMerchantVip merchant) {
        try {
            List<Region> regions = regionDAO.queryRegionsByProvinceId(Long.parseLong(merchant.getProvince()));
            List<ScheduleMapping> addMappings = new ArrayList<ScheduleMapping>();
            List<ScheduleMapping> updateMappings = new ArrayList<ScheduleMapping>();
            String corpAccessNumber = null;
            for (MasTunnel tunnel : tunnels) {
                if (tunnel.getId() == null && !StringUtils.isBlank(tunnel.getAccessNumber())) {
                    tunnel.setId(PinGen.getSerialPin());
                    tunnel.setProvince(regions.get(0).getCode());
                    MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
                    relation.setId(PinGen.getSerialPin());
                    relation.setMerchantPin(merchant.getMerchantPin());
                    relation.setAccessNumber(tunnel.getAccessNumber());
                    relation.setTunnelId(tunnel.getId());
                    relation.setState(1);                    
                    if (tunnel.getClassify() == 2) {
                        corpAccessNumber = tunnel.getAccessNumber();
                        ScheduleMapping mapping = new ScheduleMapping();
                        mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
                        mapping.setGatewayId("SM" + tunnel.getId());
                        mapping.setGwIp(tunnel.getGatewayAddr());
                        mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
                        mapping.setGwUser(tunnel.getUser());
                        mapping.setGwPasswd(tunnel.getPasswd());
                        mapping.setProtocolVersion(tunnel.getProtocalVersion());
                        mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_" + tunnel.getSmsCorpIdent());
                        mapping.setSenderCode(tunnel.getAccessNumber());
                        mapping.setServiceCode(tunnel.getServiceId());
                        addMappings.add(mapping);
                    }                   
                    smsMbnTunnelDao.insertMasTunnel(tunnel);
                    this.merchantTunnelRelation.insert(relation);
                } else {
                    smsMbnTunnelDao.updateMasTunnel(tunnel);
                    if (tunnel.getClassify() == 2) {
                        corpAccessNumber = tunnel.getAccessNumber();
                        ScheduleMapping mapping = new ScheduleMapping();
                        mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
                        mapping.setGatewayId("SM" + tunnel.getId());
                        mapping.setGwIp(tunnel.getGatewayAddr());
                        mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
                        mapping.setGwUser(tunnel.getUser());
                        mapping.setGwPasswd(tunnel.getPasswd());
                        mapping.setProtocolVersion(tunnel.getProtocalVersion());
                        mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_" + tunnel.getSmsCorpIdent());
                        mapping.setSenderCode(tunnel.getAccessNumber());
                        mapping.setServiceCode(tunnel.getServiceId());
                        updateMappings.add(mapping);
                    }                   
                    MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
                    relation.setMerchantPin(merchant.getMerchantPin());
                    relation.setAccessNumber(tunnel.getAccessNumber());
                    relation.setTunnelId(tunnel.getId());
                    relation.setState(1);
                    this.merchantTunnelRelation.update(relation);
                }
            }            
            if (addMappings.size() > 0) {
                this.scheduleMappingDAO.batchSave(addMappings);
            }
            if (updateMappings.size() > 0) {
                this.scheduleMappingDAO.batchUpdate(updateMappings);
            }
            UserVO userVO = new UserVO();
            userVO.setCorpAccessNumber(corpAccessNumber);
            userVO.setMerchantPin(merchant.getMerchantPin());
            usersDao.updateUserByAccount(userVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removerTunnelsAndConsumesForMas(Long merchantPin) {
        List<MasTunnel> tunnels = smsMbnTunnelDao.queryMasTunnelsByMerchantPin(merchantPin);
        List<ScheduleMapping> deleteMappings = new ArrayList<ScheduleMapping>();
        for (MasTunnel tunnel : tunnels) {
            if (tunnel.getClassify() == 2) {
                ScheduleMapping mapping = new ScheduleMapping();
                mapping.setGatewayId("SM" + tunnel.getId());
                deleteMappings.add(mapping);
            }           
        }
        if (deleteMappings.size() > 0) {
            this.scheduleMappingDAO.batchDelete(deleteMappings);
        }
        this.smsMbnTunnelDao.deleteMasTunnel(merchantPin);
        this.merchantTunnelRelation.delete(merchantPin);
    }

	@Override
	public List<MasTunnel> getMerchantPinTunnels(Long merchantPin) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
