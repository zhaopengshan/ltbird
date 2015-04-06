/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.admin.ford.corpmanage.action;

import com.leadtone.delegatemas.merchant.bean.PointsCityStatistic;
import com.leadtone.delegatemas.merchant.service.IMasMerchantService;
import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.admin.common.ApSmsConstants;
import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVipVO;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.MasPasswordTool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

/**
 *
 * @author blueskybluesea
 */
@ParentPackage("json-default")
@Namespace(value = "/corpManageAction")
public class FordCorpManageAction extends BaseAction {

    private MbnMerchantVip merchant;
    private List<MbnMerchantVip> merchants;
    private MasTunnel unicomTunnel;
    private MasTunnel mobileTunnel;
    private MasTunnel telecomTunnel;
    private Users loginUser = (Users) super.getSession().getAttribute(com.leadtone.mas.admin.common.ApSmsConstants.SESSION_USER_INFO);
    @Resource(name = "fordMerchantServiceImpl")
    private IMasMerchantService merchantService;
    @Resource(name = "MbnMerchantVipIService")
    private MbnMerchantVipIService mbnMerchantVipIService;
    @Resource(name = "smsFordTunnelService")
    private SmsMbnTunnelService smsMbnTunnelService;

    @Action(value = "pageFordCorp", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String paginateCorpList() throws UnsupportedEncodingException {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        MbnMerchantVip merchantOfCurrentUser = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
        switch (loginUser.getUserType()) {
            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
                if (StringUtils.isBlank(merchant.getCity())) {
                    paraMap.put("province", merchantOfCurrentUser.getProvince());
                }
                paraMap.put("region", merchant.getCity());
                break;
            case ApSmsConstants.USER_TYPE_CITY_ADMIN:
                paraMap.put("region", merchantOfCurrentUser.getCity());
                break;
            case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
                paraMap.put("region", merchant.getCity());
                break;
        }
        paraMap.put("name", URLDecoder.decode(merchant.getName(),"UTF-8"));
        paraMap.put("startPage", (page - 1) * 20);
        paraMap.put("pageSize", rows);
        Page page = merchantService.paginateMasMerchants(paraMap);
        List<MbnMerchantVipVO> datas = (List<MbnMerchantVipVO>) page.getData();
        for (MbnMerchantVipVO tempmerchant : datas) {
            if (tempmerchant.getUser() != null && !StringUtils.isBlank(tempmerchant.getUser().getAccount())) {
                tempmerchant.getUser().setPassword(MasPasswordTool.getDesString(tempmerchant.getUser().getPassword(), tempmerchant.getUser().getAccount()));
            }
        }
        entityMap = new HashMap<String, Object>();
        entityMap.put("total", page.getRecords());
        if (datas == null) {
            datas = new ArrayList<MbnMerchantVipVO>();
        }
        entityMap.put("rows", datas);
        entityMap.put("totalrecords", page.getTotal());
        entityMap.put("currpage", page.getStart());
        return SUCCESS;
    }

    @Action(value = "corpFordCountList", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String paginateCorpCountList() {
        try {
            MbnMerchantVip merchantOfCurrentUser = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
            List<PointsCityStatistic> corpCountList = merchantService.merchantCountByCity(Long.parseLong(merchantOfCurrentUser.getProvince()));
            for (PointsCityStatistic corpCount : corpCountList) {
                if (!StringUtils.isBlank(corpCount.getLoginAccount())) {
                    corpCount.setLoginPwd(MasPasswordTool.getDesString(corpCount.getLoginPwd(), corpCount.getLoginAccount()));
                }
            }
            entityMap = new HashMap<String, Object>();
            entityMap.put("corpCountList", corpCountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Action(value = "addCorpFordAndConfig", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String addCorpAndConfig() {
        entityMap = new HashMap<String, Object>();
        switch (loginUser.getUserType()) {
            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
                entityMap.put("resultInfo", "省管理员不能添加企业!");
                return SUCCESS;
            case ApSmsConstants.USER_TYPE_CITY_ADMIN:
                MbnMerchantVip merchantOfCurrentUser = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
                merchant.setProvince(merchantOfCurrentUser.getProvince());
                merchant.setCity(merchantOfCurrentUser.getCity());
                break;
            case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
                entityMap.put("resultInfo", "系统管理员不能添加企业!");
                return SUCCESS;
        }
        MbnMerchantVip tempMerchant = mbnMerchantVipIService.loadByName(merchant.getName());
        if (tempMerchant != null) {
            entityMap.put("resultInfo", "企业已存在,请重新填写!");
            return SUCCESS;
        }
        merchant.setPlatform(ApSmsConstants.NORMAL_CORP);
        merchantService.newMerchantAndMerchantConfig(merchant, null);
        List<MasTunnel> tunnels = new ArrayList<MasTunnel>();
        List<MbnMerchantTunnelRelation> relations = new ArrayList<MbnMerchantTunnelRelation>();
        Long smsTunnelId = PinGen.getSerialPin();
        if (!StringUtils.isBlank(unicomTunnel.getAccessNumber())) {
            unicomTunnel.setId(smsTunnelId);
            unicomTunnel.setName(unicomTunnel.getAccessNumber());
            unicomTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
            unicomTunnel.setClassify(ApSmsConstants.UNICOM_WHOLE_NETWORK);
            unicomTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
            unicomTunnel.setDelStatus(0);
            tunnels.add(unicomTunnel);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(smsTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(unicomTunnel.getAccessNumber());
            relation.setTunnelId(smsTunnelId);
            relation.setState(1);
            relations.add(relation);
        }
        Long mobileTunnelId = PinGen.getSerialPin();
        if (!StringUtils.isBlank(mobileTunnel.getAccessNumber())) {
            mobileTunnel.setId(mobileTunnelId);
            mobileTunnel.setName(mobileTunnel.getAccessNumber());
            mobileTunnel.setClassify(ApSmsConstants.MOBILE_WHOLE_NETWORK);
            mobileTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
            mobileTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
            mobileTunnel.setDelStatus(0);
            tunnels.add(mobileTunnel);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(mobileTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(mobileTunnel.getAccessNumber());
            relation.setTunnelId(mobileTunnel.getId());
            relation.setState(1);
            relations.add(relation);
        }
        Long mmTunnelId = PinGen.getSerialPin();
        if (!StringUtils.isBlank(telecomTunnel.getAccessNumber())) {
            telecomTunnel.setId(mmTunnelId);
            telecomTunnel.setName(telecomTunnel.getAccessNumber());
            telecomTunnel.setClassify(ApSmsConstants.TELECOM_WHOLE_NETWORK);
            telecomTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
            telecomTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
            telecomTunnel.setDelStatus(0);
            tunnels.add(telecomTunnel);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(mmTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(telecomTunnel.getAccessNumber());
            relation.setTunnelId(telecomTunnel.getId());
            relation.setState(1);
            relations.add(relation);
        }
        smsMbnTunnelService.addTunnelAndConsumeForMas(tunnels, null, relations, merchant);
        entityMap.put("resultInfo", "添加企业及其配置信息成功!");
        return SUCCESS;
    }

    @Action(value = "updateCorpFordAndConfig", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String updateCorpAndConfigs() {
        entityMap = new HashMap<String,Object>();
        switch (loginUser.getUserType()) {
            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
                entityMap.put("resultInfo", "省管理员不能更新企业!");
                return SUCCESS;
            case ApSmsConstants.USER_TYPE_CITY_ADMIN:
                MbnMerchantVip merchantOfCurrentUser = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
                merchant.setProvince(merchantOfCurrentUser.getProvince());
                merchant.setCity(merchantOfCurrentUser.getCity());
                break;
            case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
                entityMap.put("resultInfo", "系统管理员不能更新企业!");
                return SUCCESS;
        }
        MbnMerchantVip tempMerchant = mbnMerchantVipIService.loadByName(merchant.getName());
        if (tempMerchant != null && !tempMerchant.getMerchantPin().equals(merchant.getMerchantPin())) {
            entityMap.put("resultInfo", "企业已存在,请重新填写!");
            return SUCCESS;
        }
        merchantService.updateMerchantAndMerchantConfig(merchant, null);
        List<MasTunnel> tunnels = new ArrayList<MasTunnel>();
        List<MbnMerchantConsume> consumes = new ArrayList<MbnMerchantConsume>();
        if (!StringUtils.isBlank(unicomTunnel.getAccessNumber())) {
            unicomTunnel.setName(unicomTunnel.getAccessNumber());
            unicomTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
            unicomTunnel.setClassify(ApSmsConstants.UNICOM_WHOLE_NETWORK);
            unicomTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
            unicomTunnel.setDelStatus(0);
            tunnels.add(unicomTunnel);
        }
        if (!StringUtils.isBlank(mobileTunnel.getAccessNumber())) {
            mobileTunnel.setName(mobileTunnel.getAccessNumber());
            mobileTunnel.setClassify(ApSmsConstants.MOBILE_WHOLE_NETWORK);
            mobileTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
            mobileTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
            mobileTunnel.setDelStatus(0);
            tunnels.add(mobileTunnel);
        }
        if (!StringUtils.isBlank(telecomTunnel.getAccessNumber())) {
            telecomTunnel.setName(telecomTunnel.getAccessNumber());
            telecomTunnel.setClassify(ApSmsConstants.TELECOM_WHOLE_NETWORK);
            telecomTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
            telecomTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
            telecomTunnel.setDelStatus(0);
            tunnels.add(telecomTunnel);
        }
        smsMbnTunnelService.updateTunnelAndConsumeForMas(tunnels, consumes, merchant);
        entityMap = new HashMap<String,Object>();
        entityMap.put("resultInfo", "更新企业及其配置信息成功!");
        return SUCCESS;
    }

    @Action(value = "showCorpFordAndConfig", results = {
        @Result(name = SUCCESS, location = "/ford/corpmanage/corpadd.jsp"),
        @Result(name = "corpview", location = "/ford/corpmanage/corpview.jsp")
    })
    public String getCorpAndConfigs() {
        String returnLink = SUCCESS;
        switch (loginUser.getUserType()) {
            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
            case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
                returnLink = "corpview";
                break;
            case ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN:
                returnLink = "corpview";
                merchant.setMerchantPin(loginUser.getMerchantPin());
                break;
        }
        this.merchant = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
        List<MasTunnel> masTunnels = smsMbnTunnelService.getMasTunnelsByMerchantPin(merchant.getMerchantPin());
        for (MasTunnel tempTunnel : masTunnels) {
            switch (tempTunnel.getClassify()) {
                case ApSmsConstants.MOBILE_WHOLE_NETWORK:
                    mobileTunnel = tempTunnel;
                    break;
                case ApSmsConstants.UNICOM_WHOLE_NETWORK:
                    unicomTunnel = tempTunnel;
                    break;
                case ApSmsConstants.TELECOM_WHOLE_NETWORK:
                    telecomTunnel = tempTunnel;
                    break;
            }
        }
        return returnLink;
    }

    @Action(value = "removeCorpFordAndConfig", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})
    })
    public String removeCorpAndConfigs() {
        entityMap = new HashMap<String,Object>();
        switch (loginUser.getUserType()) {
            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
                entityMap.put("resultInfo", "省管理员不能删除企业!");
                return SUCCESS;
            case ApSmsConstants.USER_TYPE_CITY_ADMIN:
                break;
            case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
                entityMap.put("resultInfo", "系统管理员不能删除企业!");
                return SUCCESS;
        }
        for (MbnMerchantVip tempMerchant : merchants) {
            this.merchantService.removeMerchantAndConfigs(tempMerchant.getMerchantPin());
            this.smsMbnTunnelService.removerTunnelsAndConsumesForMas(tempMerchant.getMerchantPin());
        }
        entityMap.put("resultInfo", "删除企业及其相关配置成功");
        return SUCCESS;
    }

    public MbnMerchantVip getMerchant() {
        return merchant;
    }

    public void setMerchant(MbnMerchantVip merchant) {
        this.merchant = merchant;
    }

    public MasTunnel getUnicomTunnel() {
        return unicomTunnel;
    }

    public void setUnicomTunnel(MasTunnel unicomTunnel) {
        this.unicomTunnel = unicomTunnel;
    }

    public MasTunnel getMobileTunnel() {
        return mobileTunnel;
    }

    public void setMobileTunnel(MasTunnel mobileTunnel) {
        this.mobileTunnel = mobileTunnel;
    }

    public MasTunnel getTelecomTunnel() {
        return telecomTunnel;
    }

    public void setTelecomTunnel(MasTunnel telecomTunnel) {
        this.telecomTunnel = telecomTunnel;
    }

    public List<MbnMerchantVip> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<MbnMerchantVip> merchants) {
        this.merchants = merchants;
    }
}
