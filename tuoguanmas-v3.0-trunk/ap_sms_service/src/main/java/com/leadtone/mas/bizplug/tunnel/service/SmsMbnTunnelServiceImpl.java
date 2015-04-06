/**
 *
 */
package com.leadtone.mas.bizplug.tunnel.service;

import com.leadtone.delegatemas.hbs.bean.ScheduleMapping;
import com.leadtone.delegatemas.hbs.dao.IScheduleMappingDAO;
import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.dao.IRegionDAO;
import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantConsumeDao;
import com.leadtone.mas.bizplug.tunnel.dao.MbnMerchantTunnelRelationDaoImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.security.dao.UsersDao;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.bean.SmsTunnelAccount;
import com.leadtone.mas.bizplug.tunnel.dao.SmsMbnTunnelDao;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;

/**
 * @author PAN-Z-G 通道服务
 */
@Service(value = "smsMbnTunnelService")
public class SmsMbnTunnelServiceImpl implements SmsMbnTunnelService {

    @Resource
    private SmsMbnTunnelDao smsMbnTunnelDao;
    @Resource(name = "mbnMerchantTunnelRelationDao")
    private MbnMerchantTunnelRelationDaoImpl merchantTunnelRelation;
    @Resource
    private MbnMerchantConsumeDao mbnMerchantConsumeDaoImpl;
    @Resource(name = "scheduleMappingDAOImpl")
    private IScheduleMappingDAO scheduleMappingDAO;
    @Resource(name = "regionDAOImpl")
    private IRegionDAO regionDAO;
    @Resource
    private UsersDao usersDao;

    public List<SmsMbnTunnel> getTunnelByName(String tunnelName) throws Exception {
        return smsMbnTunnelDao.getTunnelByName(tunnelName);
    }

    @Override
    public Page page(PageUtil pageUtil) throws Exception {

        Page page = null;
        try {
            page = smsMbnTunnelDao.page(pageUtil);
        } catch (Exception e) {
            throw e;
        }
        //
        return page;
    }

    @Override
    public Integer pageCount(PageUtil pageUtil) throws Exception {
        //
        Integer count = 0;
        try {
            count = smsMbnTunnelDao.pageCount(pageUtil);
        } catch (Exception e) {
            throw e;
        }
        return count;
    }

    @Override
    public Integer insert(SmsMbnTunnel param) throws Exception {
        Integer count = 0;
        try {
            count = smsMbnTunnelDao.insert(param);
        } catch (Exception e) {
            throw e;
        }
        return count;
    }

    @Override
    public Integer update(SmsMbnTunnel param) throws Exception {
        Integer count = 0;
        try {
            count = smsMbnTunnelDao.update(param);
        } catch (Exception e) {
            throw e;
        }
        return count;
    }

    @Override
    public Integer delete(SmsMbnTunnel param) throws Exception {
        Integer count = 0;
        try {
            count = smsMbnTunnelDao.delete(param);
        } catch (Exception e) {
            throw e;
        }
        return count;
    }

    @Override
    public SmsMbnTunnelVO queryByPk(Long pk) throws Exception {
        Object object = null;
        try {
            object = smsMbnTunnelDao.queryByPk(pk);
        } catch (Exception e) {
            throw e;
        }
        return object != null ? (SmsMbnTunnelVO) object : null;
    }

    @Override
    public Integer batchUpdateByList(List<SmsMbnTunnel> smsMbnTunnels)
            throws Exception {
        Integer count = 0;
        try {
            count = smsMbnTunnelDao.batchUpdateByList(smsMbnTunnels);
        } catch (Exception e) {
            throw e;
        }
        return count;
    }

    public boolean updateDel(HashMap<String, Object> pro) {
        return smsMbnTunnelDao.updateDel(pro);
    }

    @Override
    public boolean isTunnelName(String tunnelName) throws Exception {
        boolean isTrue;
        try {
            isTrue = smsMbnTunnelDao.getByName(tunnelName) > 0 ? true : false;
        } catch (Exception e) {
            throw e;
        }
        return isTrue;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public boolean insert(SmsMbnTunnel smsMbnTunnel,
            SmsTunnelAccount tunnelAccount) throws Exception {
        boolean isTrue = false;
        try {
            smsMbnTunnelDao.insert(smsMbnTunnel, tunnelAccount);
            isTrue = true;
        } catch (Exception e) {
            throw e;
        }
        return isTrue;
    }

    @Override
    public Page pageConsumer(PageUtil pageUtil) {
        return smsMbnTunnelDao.pageConsumer(pageUtil);
    }

    @Override
    public SmsMbnTunnel getTunnelByMerchantPin(Long merchantPin) {
        return smsMbnTunnelDao.getTunnelByMerchantPin(merchantPin);
    }

    public List<MasTunnel> getMmsTunnelByMerchantPin(Long merchantPin) {
        return smsMbnTunnelDao.getMmsTunnelByMerchantPin(merchantPin);
    }
    public void addTunnelAndConsumeForMasObject( MasTunnel tunnel, MbnMerchantVip merchant) {
        try {
            List<Region> regions = regionDAO.queryRegionsByProvinceId(Long.parseLong(merchant.getProvince()));
            List<ScheduleMapping> mappings = new ArrayList<ScheduleMapping>();
            if (tunnel.getId() != null) {
                tunnel.setProvince(regions.get(0).getCode());
                if (tunnel.getType() == 1 /*&& tunnel.getClassify() != 8*/) {
                	if(tunnel.getClassify()==1||tunnel.getClassify()==2
                			||tunnel.getClassify()==3||tunnel.getClassify()==4||tunnel.getClassify()==5
                			||tunnel.getClassify()==6||tunnel.getClassify()==13){
	                    ScheduleMapping mapping = new ScheduleMapping();
	                    mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
	                    mapping.setGatewayId("SM" + tunnel.getId());
	                    mapping.setGwIp(tunnel.getGatewayAddr());
	                    mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
	                    mapping.setGwUser(tunnel.getUser());
	                    mapping.setGwPasswd(tunnel.getPasswd());
	                    mapping.setProtocolVersion(tunnel.getProtocalVersion());
	                    if(tunnel.getClassify() == 13){
	                    	mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_yw_" + tunnel.getSmsCorpIdent());
	                    }else{
	                    	mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_" + tunnel.getSmsCorpIdent());
	                    }
	                    mapping.setSenderCode(tunnel.getAccessNumber());
	                    mapping.setServiceCode(tunnel.getServiceId());
	                    mappings.add(mapping);
                	}
                }
                if (tunnel.getType() == 2) {
                    ScheduleMapping mapping = new ScheduleMapping();
                    mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
                    mapping.setGatewayId("MM" + tunnel.getId());
                    mapping.setGwIp(tunnel.getGatewayAddr());
                    mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
                    mapping.setGwUser(tunnel.getUser());
                    mapping.setGwPasswd(tunnel.getPasswd());
                    mapping.setProtocolVersion("mm7");
                    mapping.setProvinceCode("mas_" + tunnel.getSmsCorpIdent());
                    mapping.setSenderCode(tunnel.getAccessNumber());
                    mapping.setServiceCode(tunnel.getServiceId());
                    mappings.add(mapping);
                }
                smsMbnTunnelDao.insertMasTunnel(tunnel);
            }else
            	return;

            if (mappings.size() > 0) {
                this.scheduleMappingDAO.batchSave(mappings);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void addTunnelAndConsumeForMas(List<MasTunnel> tunnels, List<MbnMerchantConsume> consumes, 
    		List<MbnMerchantTunnelRelation> merchantTunnelRelations, MbnMerchantVip merchant) {
        try {
            List<Region> regions = regionDAO.queryRegionsByProvinceId(Long.parseLong(merchant.getProvince()));
            List<ScheduleMapping> mappings = new ArrayList<ScheduleMapping>();
            for (MasTunnel tunnel : tunnels) {
                if (tunnel.getId() != null) {
                    tunnel.setProvince(regions.get(0).getCode());
                    if (tunnel.getType() == 1 /*&& tunnel.getClassify() != 8*/) {
                    	if(tunnel.getClassify()==1||tunnel.getClassify()==2
                    			||tunnel.getClassify()==3||tunnel.getClassify()==4||tunnel.getClassify()==5
                    			||tunnel.getClassify()==6||tunnel.getClassify()==13){
	                        ScheduleMapping mapping = new ScheduleMapping();
	                        mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
	                        mapping.setGatewayId("SM" + tunnel.getId());
	                        mapping.setGwIp(tunnel.getGatewayAddr());
	                        mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
	                        mapping.setGwUser(tunnel.getUser());
	                        mapping.setGwPasswd(tunnel.getPasswd());
	                        mapping.setProtocolVersion(tunnel.getProtocalVersion());
	                        if(tunnel.getClassify() == 13){
	                        	mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_yw_" + tunnel.getSmsCorpIdent());
	                        }else{
	                        	mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_" + tunnel.getSmsCorpIdent());
	                        }
	                        mapping.setSenderCode(tunnel.getAccessNumber());
	                        mapping.setServiceCode(tunnel.getServiceId());
	                        mappings.add(mapping);
                    	}
                    }
                    if (tunnel.getType() == 2) {
                        ScheduleMapping mapping = new ScheduleMapping();
                        mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
                        mapping.setGatewayId("MM" + tunnel.getId());
                        mapping.setGwIp(tunnel.getGatewayAddr());
                        mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
                        mapping.setGwUser(tunnel.getUser());
                        mapping.setGwPasswd(tunnel.getPasswd());
                        mapping.setProtocolVersion("mm7");
                        mapping.setProvinceCode("mas_" + tunnel.getSmsCorpIdent());
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
            for (MbnMerchantConsume consume : consumes) {
                if (consume.getId() != null) {
                    mbnMerchantConsumeDaoImpl.insert(consume);
                }
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
        return this.smsMbnTunnelDao.queryMasTunnelsByMerchantPin(merchantPin);
    }
    @Override
    public List<MasTunnel> getMerchantPinTunnels(Long merchantPin){
    	return smsMbnTunnelDao.getMerchantPinTunnels(merchantPin);
    }

    @Override
    public void updateTunnelAndConsumeForMas(List<MasTunnel> tunnels, List<MbnMerchantConsume> consumes, MbnMerchantVip merchant) {
        try {
            boolean consumeFlag = false;
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
                    if (tunnel.getClassify() == 8) {
                        consumes.get(0).setId(PinGen.getSerialPin());
                        consumes.get(0).setTunnelId(tunnel.getId());
                        consumeFlag = true;
                    }
                    if (tunnel.getType() == 1 /*&& tunnel.getClassify() != 8*/) {
                    	if(tunnel.getClassify()==1||tunnel.getClassify()==2
                    			||tunnel.getClassify()==3||tunnel.getClassify()==4||tunnel.getClassify()==5
                    			||tunnel.getClassify()==6||tunnel.getClassify()==13){
	                        corpAccessNumber = tunnel.getAccessNumber();
	                        ScheduleMapping mapping = new ScheduleMapping();
	                        mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
	                        mapping.setGatewayId("SM" + tunnel.getId());
	                        mapping.setGwIp(tunnel.getGatewayAddr());
	                        mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
	                        mapping.setGwUser(tunnel.getUser());
	                        mapping.setGwPasswd(tunnel.getPasswd());
	                        mapping.setProtocolVersion(tunnel.getProtocalVersion());
	                        if(tunnel.getClassify() == 13){
	                        	mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_yw_" + tunnel.getSmsCorpIdent());
	                        }else{
	                        	mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_" + tunnel.getSmsCorpIdent());
	                        }
	//                        mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_" + tunnel.getSmsCorpIdent());
	                        mapping.setSenderCode(tunnel.getAccessNumber());
	                        mapping.setServiceCode(tunnel.getServiceId());
	                        addMappings.add(mapping);
                    	}
                    }
                    if (tunnel.getType() == 2) {
                        ScheduleMapping mapping = new ScheduleMapping();
                        mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
                        mapping.setGatewayId("MM" + tunnel.getId());
                        mapping.setGwIp(tunnel.getGatewayAddr());
                        mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
                        mapping.setGwUser(tunnel.getUser());
                        mapping.setGwPasswd(tunnel.getPasswd());
                        mapping.setProtocolVersion("mm7");
                        mapping.setProvinceCode("mas_" + tunnel.getSmsCorpIdent());
                        mapping.setSenderCode(tunnel.getAccessNumber());
                        mapping.setServiceCode(tunnel.getServiceId());
                        addMappings.add(mapping);
                    }
                    smsMbnTunnelDao.insertMasTunnel(tunnel);
                    this.merchantTunnelRelation.insert(relation);
                } else {
                	//addTunnelAndConsumeForMasObject
                	MasTunnel existBean = smsMbnTunnelDao.getByTunnelPk(tunnel.getId());
                	if(existBean == null){
                		addTunnelAndConsumeForMasObject(tunnel, merchant);
                		MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
                		relation.setId(tunnel.getId());
                        relation.setMerchantPin(merchant.getMerchantPin());
                        relation.setAccessNumber(tunnel.getAccessNumber());
                        relation.setTunnelId(tunnel.getId());
                        relation.setState(1);
	                    this.merchantTunnelRelation.insert(relation);
                	}else{
	                    smsMbnTunnelDao.updateMasTunnel(tunnel);
	                    if (tunnel.getType() == 1/* && tunnel.getClassify() != 8*/) {
	                    	if(tunnel.getClassify()==1||tunnel.getClassify()==2
	                    			||tunnel.getClassify()==3||tunnel.getClassify()==4||tunnel.getClassify()==5
	                    			||tunnel.getClassify()==6||tunnel.getClassify()==13){
		                        corpAccessNumber = tunnel.getAccessNumber();
		                        ScheduleMapping mapping = new ScheduleMapping();
		                        mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
		                        mapping.setGatewayId("SM" + tunnel.getId());
		                        mapping.setGwIp(tunnel.getGatewayAddr());
		                        mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
		                        mapping.setGwUser(tunnel.getUser());
		                        mapping.setGwPasswd(tunnel.getPasswd());
		                        mapping.setProtocolVersion(tunnel.getProtocalVersion());
		                        if(tunnel.getClassify() == 13){
		                        	mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_yw_" + tunnel.getSmsCorpIdent());
		                        }else{
		                        	mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_" + tunnel.getSmsCorpIdent());
		                        }
		//                        mapping.setProvinceCode("mas_" + regions.get(0).getCode() + "_" + tunnel.getSmsCorpIdent());
		                        mapping.setSenderCode(tunnel.getAccessNumber());
		                        mapping.setServiceCode(tunnel.getServiceId());
		                        updateMappings.add(mapping);
	                    	}
	                    }
	                    if (tunnel.getType() == 2) {
	                        ScheduleMapping mapping = new ScheduleMapping();
	                        mapping.setEnterpriseCode(tunnel.getSmsCorpIdent());
	                        mapping.setGatewayId("MM" + tunnel.getId());
	                        mapping.setGwIp(tunnel.getGatewayAddr());
	                        mapping.setGwPort(tunnel.getGatewayPort() != null ? tunnel.getGatewayPort().intValue() : 0);
	                        mapping.setGwUser(tunnel.getUser());
	                        mapping.setGwPasswd(tunnel.getPasswd());
	                        mapping.setProtocolVersion("mm7");
	                        mapping.setProvinceCode("mas_" + tunnel.getSmsCorpIdent());
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
            }
            for (MbnMerchantConsume consume : consumes) {
                if (consumeFlag && consume.getId()!=null) {
                    mbnMerchantConsumeDaoImpl.insert(consume);
                } else {
                	MbnMerchantConsume consumeBean = mbnMerchantConsumeDaoImpl.findByTunnelId(consume.getMerchantPin(), consume.getTunnelId());
                	if(consumeBean==null){
                		mbnMerchantConsumeDaoImpl.insert(consume);
                	}else{
                		mbnMerchantConsumeDaoImpl.update(consume);
                	}
                }
            }
            if (addMappings.size() > 0) {
                this.scheduleMappingDAO.batchSave(addMappings);
            }
            if (updateMappings.size() > 0) {
                this.scheduleMappingDAO.batchUpdate(updateMappings);
            }
//            UserVO userVO = new UserVO();
//            userVO.setCorpAccessNumber(corpAccessNumber);
//            userVO.setMerchantPin(merchant.getMerchantPin());
//            usersDao.updateUserByAccount(userVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removerTunnelsAndConsumesForMas(Long merchantPin) {
        List<MasTunnel> tunnels = smsMbnTunnelDao.queryMasTunnelsByMerchantPin(merchantPin);
        List<ScheduleMapping> deleteMappings = new ArrayList<ScheduleMapping>();
        for (MasTunnel tunnel : tunnels) {
            if (tunnel.getType() == 1 && tunnel.getClassify() != 8) {
                ScheduleMapping mapping = new ScheduleMapping();
                mapping.setGatewayId("SM" + tunnel.getId());
                deleteMappings.add(mapping);
            }
            if (tunnel.getType() == 2) {
                ScheduleMapping mapping = new ScheduleMapping();
                mapping.setGatewayId("MM" + tunnel.getId());
                deleteMappings.add(mapping);
            }
        }
        if (deleteMappings.size() > 0) {
            this.scheduleMappingDAO.batchDelete(deleteMappings);
        }
        this.smsMbnTunnelDao.deleteMasTunnel(merchantPin);
        this.merchantTunnelRelation.delete(merchantPin);
        this.mbnMerchantConsumeDaoImpl.deleteConsumesByMerchantPin(merchantPin);
    }
}
