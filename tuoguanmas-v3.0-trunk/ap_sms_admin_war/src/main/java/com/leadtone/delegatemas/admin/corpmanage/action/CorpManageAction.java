/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.admin.corpmanage.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.leadtone.delegatemas.admin.security.action.UserUtil;
import com.leadtone.delegatemas.merchant.bean.PointsCityStatistic;
import com.leadtone.delegatemas.merchant.service.IMasMerchantService;
import com.leadtone.delegatemas.node.bean.MbnNode;
import com.leadtone.delegatemas.node.bean.MbnNodeMerchantRelation;
import com.leadtone.delegatemas.node.bean.MbnNodeTO;
import com.leadtone.delegatemas.node.service.MbnNodeMerchantRelService;
import com.leadtone.delegatemas.node.service.MbnNodeService;
import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.admin.common.ApSmsConstants;
import com.leadtone.mas.admin.common.ZXTUserTool;
import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.admin.webservice.PackageUtils;
import com.leadtone.mas.admin.webservice.WebServiceConsts;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.lisence.MacGetterUtil;
import com.leadtone.mas.bizplug.lisence.bean.Lisence;
import com.leadtone.mas.bizplug.lisence.service.LisenceService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVipVO;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.HttpUtils;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
import com.leadtone.mas.bizplug.util.WebUtils;
import com.leadtone.mas.bizplug.webservice.bean.ConfigInfo;
import com.leadtone.mas.bizplug.webservice.bean.EntInfo;
import com.leadtone.mas.bizplug.webservice.bean.MasBodyPackage;
import com.leadtone.mas.bizplug.webservice.bean.MasHeadPackage;
import com.leadtone.mas.bizplug.webservice.bean.MasPackage;
import com.leadtone.mas.bizplug.webservice.bean.TunnelInfo;
import com.leadtone.mas.bizplug.webservice.util.BeanConvUtils;

/**
 *
 * @author blueskybluesea
 */
@ParentPackage("json-default")
@Namespace(value = "/corpManageAction")
public class CorpManageAction extends BaseAction {
	private static Logger logger = Logger.getLogger(CorpManageAction.class);
//    private List<MbnConfigMerchant> merchantConfigs;
    
    private MbnConfigMerchant smsSignContent;
    private MbnConfigMerchant isdelegate;
    private MbnConfigMerchant masserverip;
    private MbnConfigMerchant gatewaylimit;
    private MbnConfigMerchant corpLoginPort;
    private MbnConfigMerchant smsSendLimit;
    private MbnConfigMerchant mmtopport;
    
    private MbnMerchantVip merchant;
    private List<MbnMerchantVip> merchants;
    private MbnMerchantConsume consume;
    private MbnMerchantConsume mobileConsume;
    private MasTunnel tunnel; //移动
    private MasTunnel ltTunnel; // 联通
    private MasTunnel dxTunnel; // 电信
    private MasTunnel mobileTunnel; // 手机发送
    private MasTunnel mmTunnel; // 彩信
    private MasTunnel zxtTunnel; // 资信通
    private MasTunnel newQxtTunnel; // 新企信通
    private MasTunnel emppTunnel; // 上海移动empp
    private MasTunnel tunnelyw; //移动
    private Users loginUser = (Users) super.getSession().getAttribute(com.leadtone.mas.admin.common.ApSmsConstants.SESSION_USER_INFO);
    @Resource(name = "masMerchantServiceImpl")
    private IMasMerchantService merchantService;
    @Resource(name = "MbnMerchantVipIService")
    private MbnMerchantVipIService mbnMerchantVipIService;
    @Resource(name = "MbnConfigMerchantIService")
    private MbnConfigMerchantIService merchantConfig;
    @Resource(name = "smsMbnTunnelService")
    private SmsMbnTunnelService smsMbnTunnelService;
    @Resource(name = "mbnMerchantConsumeService")
    private MbnMerchantConsumeService consumeService;
    //企业统一管理
    @Resource(name = "mbnMerchantTunnelRelationService")
    private MbnMerchantTunnelRelationService mtrService;
    @Resource(name="mbnNodeService")
    private MbnNodeService mbnNodeService;
    @Resource(name="mbnNodeMerchantRelService")
    private MbnNodeMerchantRelService mbnNodeMerchantRelService;
    @Autowired
	private LisenceService lisenceService;
    private Long nodeId;
    private List<MbnNodeTO> mbnNodeTOes;
    private boolean nodeTOStatus = false;
    private HttpServletRequest request  = ServletActionContext.getRequest();
    private boolean registStatus = false;
    
    @Action(value = "showCorpTunnel", results = {
            @Result(type = "json", params = {
                "root", "entityMap", "contentType", "text/html"})})
        public String showCorpTunnel() {
    		
    		entityMap = new HashMap();
    		List<MasTunnel> corpTunnelList = smsMbnTunnelService.getMasTunnelsByMerchantPin(merchant.getMerchantPin());
    		
            if (corpTunnelList == null) {
            	corpTunnelList = new ArrayList();
            }
            entityMap.put("rows", corpTunnelList);
            entityMap.put("totalrecords", corpTunnelList.size());
//            entityMap.put("totalrecords", page.getTotal());
//            entityMap.put("currpage", page.getStart());
            return SUCCESS;
        }
    
    /**
	 * 随机生成zxtUserId，返回未使用项
	 * @return
	 */
	private String getZxtUserId(){
		while(true){
			String zxtUserId = UserUtil.getRandomZxtUserId();
			if( !mbnMerchantVipIService.checkZxtUserIdInUse(zxtUserId)){
				return zxtUserId;
			}
			try{
				Thread.sleep(50);
			}catch(Exception e){
				// IGNORE
			}
		}
	}

    @Action(value = "pageCorp", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String paginateCorpList() {
        Map<String, Object> paraMap = new HashMap<String, Object>();
        MbnMerchantVip merchantOfCurrentUser = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
        switch (loginUser.getUserType()) {
            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
                if(StringUtils.isBlank(merchant.getCity())) {
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
        paraMap.put("name", merchant.getName());
        paraMap.put("startPage", (page - 1) * 20);
        paraMap.put("pageSize", rows);
        Page page = merchantService.paginateMasMerchants(paraMap);
        List<MbnMerchantVipVO> datas = (List<MbnMerchantVipVO>) page.getData();
		if(datas!=null){
			for (MbnMerchantVipVO tempmerchant : datas) {
				if (tempmerchant.getUser() != null && !StringUtils.isBlank(tempmerchant.getUser().getAccount())) {
					tempmerchant.getUser().setPassword(MasPasswordTool.getDesString(tempmerchant.getUser().getPassword(), tempmerchant.getUser().getAccount()));
				}
			}
		}
        entityMap = new HashMap();
        entityMap.put("total", page.getRecords());
        if (datas == null) {
            datas = new ArrayList();
        }
        entityMap.put("rows", datas);
        entityMap.put("totalrecords", page.getTotal());
        entityMap.put("currpage", page.getStart());
        return SUCCESS;
    }

    @Action(value = "corpCountList", results = {
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
            entityMap = new HashMap();
            entityMap.put("corpCountList", corpCountList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }
    @Action(value = "addCorp", results = {
            @Result(type = "json", params = {
                "root", "entityMap", "contentType", "text/html"})})
    public String addCorp() {
        entityMap = new HashMap();
        switch (loginUser.getUserType()) {
            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
            	entityMap.put("flag", false);
                entityMap.put("resultInfo", "省管理员不能添加企业！");
                logger.error("省管理员不能添加企业！");
                return SUCCESS;
            case ApSmsConstants.USER_TYPE_CITY_ADMIN:
                MbnMerchantVip merchantOfCurrentUser = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
                merchant.setProvince(merchantOfCurrentUser.getProvince());
                merchant.setCity(merchantOfCurrentUser.getCity());
                break;
            case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
            	entityMap.put("flag", false);
                entityMap.put("resultInfo", "系统管理员不能添加企业！");
                logger.error("系统管理员不能添加企业！");
                return SUCCESS;
        }
        MbnMerchantVip tempMerchant = mbnMerchantVipIService.loadByName(merchant.getName());
        if (tempMerchant != null) {
        	entityMap.put("flag", false);
        	logger.error("企业已存在,请重新填写！");
            entityMap.put("resultInfo", "企业已存在,请重新填写！");
            return SUCCESS;
        }
        List<MbnConfigMerchant> validConfig = new ArrayList<MbnConfigMerchant>();
//        for(MbnConfigMerchant configBean :merchantConfigs){
//        	if(configBean!=null){
//        		validConfig.add(configBean);
//        	}
//        }
        this.corpChangeAction(validConfig);
        
        merchant.setPlatform(ApSmsConstants.NORMAL_CORP);
        merchantService.newMerchantAndMerchantConfig(merchant, validConfig);
        
        entityMap.put("flag", true);
        entityMap.put("resultInfo", "企业创建成功！");
        logger.error("企业创建成功！");
        entityMap.put("merchant", merchant);
        return SUCCESS;
    }
    
    @Action(value = "addConfig", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})})
    public String addCorpAndConfig() {
        entityMap = new HashMap();
        merchant = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
        List<MbnConfigMerchant> validConfig = new ArrayList<MbnConfigMerchant>();
//        for(MbnConfigMerchant configBean :merchantConfigs){
//        	if(configBean!=null){
//        		validConfig.add(configBean);
//        	}
//        }
        this.configChangeAction(validConfig);
        merchantService.updateMerchantAndMerchantConfig(merchant, validConfig);
        
        List<MasTunnel> tunnels = new ArrayList<MasTunnel>();
        List<MbnMerchantTunnelRelation> relations = new ArrayList<MbnMerchantTunnelRelation>();
        List<MbnMerchantConsume> consumes = new ArrayList<MbnMerchantConsume>();
        
//        private MasTunnel zxtTunnel; // 资信通
        if (zxtTunnel!=null&&StringUtils.isNotBlank(zxtTunnel.getUser())) {
        	if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.TUNNELALL))){
            	String tunnelAll = WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.TUNNELALL);           
            	if("true".equalsIgnoreCase(tunnelAll)){
            		String zxtExt = getZxtUserId();
            		merchant.setCorpExt(zxtExt);
            		int zxtUserId = ZXTUserTool.addCorpUser(zxtTunnel.getUser(), zxtTunnel.getPasswd(), zxtExt);
            		if( !(zxtUserId > 0) ){
            			 entityMap.put("flag", false);
            			 entityMap.put("resultInfo", "企信通开户失败，请联系厂商客服！");
            			 return SUCCESS;
            		}
            		merchant.setCorpId(zxtUserId);
            	}
            }
        	Long zxtTunnelId = PinGen.getSerialPin();
        	zxtTunnel.setId(zxtTunnelId);
			// tunnel.setName(tunnel.getAccessNumber());
        	zxtTunnel.setName("资信通");
        	zxtTunnel.setAccessNumber(merchant.getCorpId() != 0 ? String.valueOf( merchant.getCorpId() ):"911");
        	zxtTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
        	zxtTunnel.setClassify(ApSmsConstants.ZXT_TUNNEL_TYPE);
        	zxtTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
        	zxtTunnel.setDelStatus(0);
            tunnels.add(zxtTunnel);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(zxtTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(String.valueOf(merchant.getCorpId()));
            relation.setTunnelId(zxtTunnelId);
            relation.setState(1);
            relations.add(relation);
            // 初始化消费表
			MbnMerchantConsume smsConsume = buildConsume(
					merchant.getMerchantPin(), zxtTunnelId);
			consumes.add(smsConsume);
        }
        
        if (tunnel!=null&&StringUtils.isNotBlank(tunnel.getAccessNumber())) {
        	Long smsTunnelId = PinGen.getSerialPin();
            tunnel.setId(smsTunnelId);
			// tunnel.setName(tunnel.getAccessNumber());
            tunnel.setName("移动");
            tunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
            tunnel.setClassify(ApSmsConstants.SELF_PROVINCE_CLASSIFY);
            tunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
            tunnel.setDelStatus(0);
            tunnels.add(tunnel);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(smsTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(tunnel.getAccessNumber());
            relation.setTunnelId(smsTunnelId);
            relation.setState(1);
            relations.add(relation);
            // 初始化消费表
			MbnMerchantConsume smsConsume = buildConsume(
					merchant.getMerchantPin(), smsTunnelId);
			consumes.add(smsConsume);
        }
        if (tunnelyw!=null&&StringUtils.isNotBlank(tunnelyw.getAccessNumber())) {
        	Long smsTunnelId = PinGen.getSerialPin();
            tunnelyw.setId(smsTunnelId);
			// tunnel.setName(tunnel.getAccessNumber());
            tunnelyw.setName("辽宁CMPP异网");
            tunnelyw.setType(ApSmsConstants.SM_TUNNEL_TYPE);
            tunnelyw.setClassify(ApSmsConstants.TUNNEL_CLASSIFY_YDYW);
            tunnelyw.setState(ApSmsConstants.TUNNEL_STATE_VALID);
            tunnelyw.setDelStatus(0);
            tunnels.add(tunnelyw);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(smsTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(tunnelyw.getAccessNumber());
            relation.setTunnelId(smsTunnelId);
            relation.setState(1);
            relations.add(relation);
            // 初始化消费表
			MbnMerchantConsume smsConsume = buildConsume(
					merchant.getMerchantPin(), smsTunnelId);
			consumes.add(smsConsume);
        }
//        private MasTunnel emppTunnel; // 上海移动empp
        if (emppTunnel!=null&&StringUtils.isNotBlank(emppTunnel.getUser())) {
        	Long smsTunnelId = PinGen.getSerialPin();
        	emppTunnel.setId(smsTunnelId);
			// tunnel.setName(tunnel.getAccessNumber());
        	emppTunnel.setName("上海移动empp");
        	emppTunnel.setServiceId("EMPP");
        	emppTunnel.setSmsCorpIdent("EMPP");
        	emppTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
        	emppTunnel.setClassify(ApSmsConstants.EMPP_TUNNEL_TYPE);
        	emppTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
        	emppTunnel.setDelStatus(0);
            tunnels.add(emppTunnel);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(smsTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(emppTunnel.getUser());
            relation.setTunnelId(smsTunnelId);
            relation.setState(1);
            relations.add(relation);
            // 初始化消费表
			MbnMerchantConsume smsConsume = buildConsume(
					merchant.getMerchantPin(), smsTunnelId);
			consumes.add(smsConsume);
        }
        // 增加联通通道
        if (ltTunnel!=null&&StringUtils.isNotBlank(ltTunnel.getAccessNumber())) {
        	Long ltTunnelId = PinGen.getSerialPin();
        	ltTunnel.setId(ltTunnelId);
        	ltTunnel.setName("联通");
        	ltTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
        	ltTunnel.setClassify(ApSmsConstants.UNICOM_WHOLE_NETWORK);
        	ltTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
        	ltTunnel.setDelStatus(0);
            tunnels.add(ltTunnel);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(ltTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(ltTunnel.getAccessNumber());
            relation.setTunnelId(ltTunnelId);
            relation.setState(1);
            relations.add(relation);
            // 初始化消费表
			MbnMerchantConsume smsConsume = buildConsume(
					merchant.getMerchantPin(), ltTunnelId);
			consumes.add(smsConsume);
        }
        // 增加电信通道
        if (dxTunnel!=null&&StringUtils.isNotBlank(dxTunnel.getAccessNumber())) {
        	Long dxTunnelId = PinGen.getSerialPin();
        	dxTunnel.setId(dxTunnelId);
        	dxTunnel.setName("电信");
        	dxTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
        	dxTunnel.setClassify(ApSmsConstants.TELECOM_WHOLE_NETWORK);
        	dxTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
        	dxTunnel.setDelStatus(0);
            tunnels.add(dxTunnel);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(dxTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(ltTunnel.getAccessNumber());
            relation.setTunnelId(dxTunnelId);
            relation.setState(1);
            relations.add(relation);
			MbnMerchantConsume smsConsume = buildConsume(
					merchant.getMerchantPin(), dxTunnelId);
			consumes.add(smsConsume);
        }
        // 话机通道
        if (mobileTunnel!=null&&StringUtils.isNotBlank(mobileTunnel.getAccessNumber())) {
        	Long mobileTunnelId = PinGen.getSerialPin();
            mobileTunnel.setId(mobileTunnelId);
            // mobileTunnel.setName(mobileTunnel.getAccessNumber());
            mobileTunnel.setName("SIM卡");
            mobileTunnel.setClassify(ApSmsConstants.SIM_MODEM_CLASSIFY);
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
			MbnMerchantConsume smsConsume = buildConsume(
					merchant.getMerchantPin(), mobileTunnelId);
			consumes.add(smsConsume);
        }
        
//      private MasTunnel newQxtTunnel; // 新企信通
	      if (newQxtTunnel!=null&&StringUtils.isNotBlank(newQxtTunnel.getUser())) {
	      	Long newQxtTunnelId = PinGen.getSerialPin();
	      	newQxtTunnel.setId(newQxtTunnelId);
	      	newQxtTunnel.setName("新企信通");
	      	newQxtTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
	      	newQxtTunnel.setClassify(ApSmsConstants.NEW_QXT_TUNNEL_TYPE);
	      	newQxtTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
	      	newQxtTunnel.setAccessNumber(newQxtTunnel.getUser());
	      	newQxtTunnel.setDelStatus(0);
	          tunnels.add(newQxtTunnel);
	          MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
	          relation.setId(newQxtTunnelId);
	          relation.setMerchantPin(merchant.getMerchantPin());
	          relation.setAccessNumber(newQxtTunnel.getUser());
	          relation.setTunnelId(newQxtTunnelId);
	          relation.setState(1);
	          relations.add(relation);
	          // 初始化消费表
				MbnMerchantConsume smsConsume = buildConsume(
						merchant.getMerchantPin(), newQxtTunnelId);
				consumes.add(smsConsume);
	      }

        if (mmTunnel!=null&&StringUtils.isNotBlank(mmTunnel.getAccessNumber())) {
            Long mmTunnelId = PinGen.getSerialPin();
            mmTunnel.setId(mmTunnelId);
			// mmTunnel.setName(mmTunnel.getAccessNumber());
            mmTunnel.setName("彩信");
            mmTunnel.setClassify(ApSmsConstants.SELF_PROVINCE_CLASSIFY);
            mmTunnel.setType(ApSmsConstants.MM_TUNNEL_TYPE);
            mmTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
            mmTunnel.setDelStatus(0);
            tunnels.add(mmTunnel);
            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
            relation.setId(mmTunnelId);
            relation.setMerchantPin(merchant.getMerchantPin());
            relation.setAccessNumber(mmTunnel.getAccessNumber());
            relation.setTunnelId(mmTunnel.getId());
            relation.setState(1);
            relations.add(relation);
        }
        smsMbnTunnelService.addTunnelAndConsumeForMas(tunnels, consumes, relations, merchant);
        entityMap.put("resultInfo", "添加企业及其配置信息成功 ！节点开户操作失败！");
        /**
         * 河北tuoguanmas
         * 管理、业务 统一管理，统计管理
         */
        try{
        	//企业，节点关系
        	MbnNodeMerchantRelation mbnNodeMercRel = new MbnNodeMerchantRelation();
        	mbnNodeMercRel.setId(PinGen.getSerialPin());
        	mbnNodeMercRel.setMerchantPin(merchant.getMerchantPin());
        	mbnNodeMercRel.setNodeId(nodeId);
        	mbnNodeMerchantRelService.insert(mbnNodeMercRel);
        	
	        MasPackage reqPackage = new MasPackage();
	        // 获取企业信息
			MbnMerchantVip merchantTemp = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
			EntInfo entInfo = BeanConvUtils.conv(merchantTemp);
			List<EntInfo> entInfoList = new ArrayList<EntInfo>();
			// 获取配置信息,获取通道信息
			List<ConfigInfo> configInfoList = new ArrayList<ConfigInfo>();
			List<TunnelInfo> tunnelInfoList = new ArrayList<TunnelInfo>();
	
			List<MbnConfigMerchant> confList = merchantConfig
					.queryMerchantConfigList(merchant.getMerchantPin());
			for (MbnConfigMerchant conf : confList) {
				configInfoList.add(BeanConvUtils.conv(conf));
			}
			List<MasTunnel> tunnelList = smsMbnTunnelService.getMasTunnelsByMerchantPin(merchant.getMerchantPin());
			if( tunnelList != null){
				for(MasTunnel tunnel: tunnelList){
					tunnelInfoList.add(BeanConvUtils.conv(tunnel, merchant.getMerchantPin()));
				}
			}
			//			List<MbnMerchantTunnelRelation> relList = mtrService
			//					.findByPin(merchant.getMerchantPin());
			//			if (relList != null) {
			//				for (MbnMerchantTunnelRelation rel : relList) {
			//					try {
			//						SmsMbnTunnelVO svo = smsMbnTunnelService.queryByPk(rel
			//								.getTunnelId());
			//						tunnelInfoList.add(BeanConvUtils.conv(svo, rel
			//								.getMerchantPin()));
			//					} catch (Exception e) {
			//						logger.error("Get tunnel " + rel.getTunnelId()
			//								+ " fail.", e);
			//					}
			//				}
			//			}
			entInfo.setConfigInfoList(configInfoList);
			entInfo.setTunnelInfoList(tunnelInfoList);
			entInfoList.add(entInfo);
			
			MasBodyPackage body = new MasBodyPackage();
			body.setEntInfoList(entInfoList);
			
			MasHeadPackage head = new MasHeadPackage();
			head.setNodeId(nodeId.toString());
			MbnNode mbnNode = mbnNodeService.getByPk(nodeId);
			head.setPassword(mbnNode.getPassword());
			head.setEntId( merchant.getMerchantPin().toString() );
			head.setMethodName(WebServiceConsts.CREATEENT_METHOD);//"createEnt"
			
			reqPackage.setHead(head);
			reqPackage.setBody(body);
			
			String recv = HttpUtils.sendRequest(mbnNode.getWebServiceUrl(), PackageUtils.getXml(reqPackage), 60000);
			MasPackage respo = PackageUtils.getMasPackage(recv);
			switch(Integer.parseInt(respo.getHead().getReturnCode())){
				case 0: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户操作成功！");break;
				case 1001: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户请求错误！");break;
				case 1002: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户XML解析错误！");break;
				case 1003: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户鉴权错误！");break;
				case 1004: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户企业ID错误！");break;
			}
        }catch(Exception e){
        	logger.error("manager create corp to node error", e);
        }
        return SUCCESS;
    }
    
//    @Action(value = "addCorpAndConfig", results = {
//            @Result(type = "json", params = {
//                "root", "entityMap", "contentType", "text/html"})})
//    public String addCorpAndConfig() {
//        entityMap = new HashMap();
//        switch (loginUser.getUserType()) {
//            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
//                entityMap.put("resultInfo", "省管理员不能添加企业！");
//                return SUCCESS;
//            case ApSmsConstants.USER_TYPE_CITY_ADMIN:
//                MbnMerchantVip merchantOfCurrentUser = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
//                merchant.setProvince(merchantOfCurrentUser.getProvince());
//                merchant.setCity(merchantOfCurrentUser.getCity());
//                break;
//            case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
//                entityMap.put("resultInfo", "系统管理员不能添加企业！");
//                return SUCCESS;
//        }
//        MbnMerchantVip tempMerchant = mbnMerchantVipIService.loadByName(merchant.getName());
//        if (tempMerchant != null) {
//        	entityMap.put("flag", false);
//            entityMap.put("resultInfo", "企业已存在,请重新填写！");
//            return SUCCESS;
//        }
//        merchant.setPlatform(ApSmsConstants.NORMAL_CORP);
//        
//        merchantService.newMerchantAndMerchantConfig(merchant, merchantConfigs);
//        
//        //add corp config
//        
//        if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.TUNNELALL))){
//        	String tunnelAll = WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.TUNNELALL);           
//        	if("true".equalsIgnoreCase(tunnelAll)){
//        		String zxtExt = getZxtUserId();
//        		merchant.setCorpExt(zxtExt);
//        		int zxtUserId = ZXTUserTool.addCorpUser(merchant.getName(), merchant.getName(), zxtExt);
//        		if( !(zxtUserId > 0) ){
//        			 entityMap.put("resultInfo", "企信通开户失败，请联系厂商客服！");
//        			 return SUCCESS;
//        		}
//        		merchant.setCorpId(zxtUserId);
//        	}
//        }
//        
//        List<MasTunnel> tunnels = new ArrayList<MasTunnel>();
//        List<MbnMerchantTunnelRelation> relations = new ArrayList<MbnMerchantTunnelRelation>();
//        List<MbnMerchantConsume> consumes = new ArrayList<MbnMerchantConsume>();
//        if (StringUtils.isNotBlank(tunnel.getAccessNumber())) {
//        	Long smsTunnelId = PinGen.getSerialPin();
//            tunnel.setId(smsTunnelId);
//			// tunnel.setName(tunnel.getAccessNumber());
//            tunnel.setName("移动");
//            tunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
//            tunnel.setClassify(ApSmsConstants.SELF_PROVINCE_CLASSIFY);
//            tunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//            tunnel.setDelStatus(0);
//            tunnels.add(tunnel);
//            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
//            relation.setId(smsTunnelId);
//            relation.setMerchantPin(merchant.getMerchantPin());
//            relation.setAccessNumber(tunnel.getAccessNumber());
//            relation.setTunnelId(smsTunnelId);
//            relation.setState(1);
//            relations.add(relation);
//            // 初始化消费表
//			MbnMerchantConsume smsConsume = buildConsume(
//					merchant.getMerchantPin(), smsTunnelId);
//			consumes.add(smsConsume);
//        }
//        // 增加联通通道
//        if (StringUtils.isNotBlank(ltTunnel.getAccessNumber())) {
//        	Long ltTunnelId = PinGen.getSerialPin();
//        	ltTunnel.setId(ltTunnelId);
//        	ltTunnel.setName("联通");
//        	ltTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
//        	ltTunnel.setClassify(ApSmsConstants.UNICOM_WHOLE_NETWORK);
//        	ltTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//        	ltTunnel.setDelStatus(0);
//            tunnels.add(ltTunnel);
//            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
//            relation.setId(ltTunnelId);
//            relation.setMerchantPin(merchant.getMerchantPin());
//            relation.setAccessNumber(ltTunnel.getAccessNumber());
//            relation.setTunnelId(ltTunnelId);
//            relation.setState(1);
//            relations.add(relation);
//            // 初始化消费表
//			MbnMerchantConsume smsConsume = buildConsume(
//					merchant.getMerchantPin(), ltTunnelId);
//			consumes.add(smsConsume);
//        }
//        // 增加电信通道
//        if (StringUtils.isNotBlank(dxTunnel.getAccessNumber())) {
//        	Long dxTunnelId = PinGen.getSerialPin();
//        	dxTunnel.setId(dxTunnelId);
//        	dxTunnel.setName("电信");
//        	dxTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
//        	dxTunnel.setClassify(ApSmsConstants.TELECOM_WHOLE_NETWORK);
//        	dxTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//        	dxTunnel.setDelStatus(0);
//            tunnels.add(dxTunnel);
//            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
//            relation.setId(dxTunnelId);
//            relation.setMerchantPin(merchant.getMerchantPin());
//            relation.setAccessNumber(ltTunnel.getAccessNumber());
//            relation.setTunnelId(dxTunnelId);
//            relation.setState(1);
//            relations.add(relation);
//			MbnMerchantConsume smsConsume = buildConsume(
//					merchant.getMerchantPin(), dxTunnelId);
//			consumes.add(smsConsume);
//        }
//        // 话机通道
//        if (StringUtils.isNotBlank(mobileTunnel.getAccessNumber())) {
//        	Long mobileTunnelId = PinGen.getSerialPin();
//            mobileTunnel.setId(mobileTunnelId);
//            // mobileTunnel.setName(mobileTunnel.getAccessNumber());
//            mobileTunnel.setName("SIM卡");
//            mobileTunnel.setClassify(ApSmsConstants.SIM_MODEM_CLASSIFY);
//            mobileTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
//            mobileTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//            mobileTunnel.setDelStatus(0);
//            tunnels.add(mobileTunnel);
//            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
//            relation.setId(mobileTunnelId);
//            relation.setMerchantPin(merchant.getMerchantPin());
//            relation.setAccessNumber(mobileTunnel.getAccessNumber());
//            relation.setTunnelId(mobileTunnel.getId());
//            relation.setState(1);
//            relations.add(relation);
//			MbnMerchantConsume smsConsume = buildConsume(
//					merchant.getMerchantPin(), mobileTunnelId);
//			consumes.add(smsConsume);
//        }
//
//        if (StringUtils.isNotBlank(mmTunnel.getAccessNumber())) {
//            Long mmTunnelId = PinGen.getSerialPin();
//            mmTunnel.setId(mmTunnelId);
//			// mmTunnel.setName(mmTunnel.getAccessNumber());
//            mmTunnel.setName("彩信");
//            mmTunnel.setClassify(ApSmsConstants.SELF_PROVINCE_CLASSIFY);
//            mmTunnel.setType(ApSmsConstants.MM_TUNNEL_TYPE);
//            mmTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//            mmTunnel.setDelStatus(0);
//            tunnels.add(mmTunnel);
//            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
//            relation.setId(mmTunnelId);
//            relation.setMerchantPin(merchant.getMerchantPin());
//            relation.setAccessNumber(mmTunnel.getAccessNumber());
//            relation.setTunnelId(mmTunnel.getId());
//            relation.setState(1);
//            relations.add(relation);
//        }
//        smsMbnTunnelService.addTunnelAndConsumeForMas(tunnels, consumes, relations, merchant);
//        entityMap.put("resultInfo", "添加企业及其配置信息成功 ！节点开户操作失败！");
//        /**
//         * 河北tuoguanmas
//         * 管理、业务 统一管理，统计管理
//         */
//        try{
//        	//企业，节点关系
//        	MbnNodeMerchantRelation mbnNodeMercRel = new MbnNodeMerchantRelation();
//        	mbnNodeMercRel.setId(PinGen.getSerialPin());
//        	mbnNodeMercRel.setMerchantPin(merchant.getMerchantPin());
//        	mbnNodeMercRel.setNodeId(nodeId);
//        	mbnNodeMerchantRelService.insert(mbnNodeMercRel);
//        	
//	        MasPackage reqPackage = new MasPackage();
//	        // 获取企业信息
//			MbnMerchantVip merchantTemp = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
//			EntInfo entInfo = BeanConvUtils.conv(merchantTemp);
//			List<EntInfo> entInfoList = new ArrayList<EntInfo>();
//			// 获取配置信息,获取通道信息
//			List<ConfigInfo> configInfoList = new ArrayList<ConfigInfo>();
//			List<TunnelInfo> tunnelInfoList = new ArrayList<TunnelInfo>();
//	
//			List<MbnConfigMerchant> confList = merchantConfig
//					.queryMerchantConfigList(merchant.getMerchantPin());
//			for (MbnConfigMerchant conf : confList) {
//				configInfoList.add(BeanConvUtils.conv(conf));
//			}
//			List<MasTunnel> tunnelList = smsMbnTunnelService.getMasTunnelsByMerchantPin(merchant.getMerchantPin());
//			if( tunnelList != null){
//				for(MasTunnel tunnel: tunnelList){
//					tunnelInfoList.add(BeanConvUtils.conv(tunnel, merchant.getMerchantPin()));
//				}
//			}
//			//			List<MbnMerchantTunnelRelation> relList = mtrService
//			//					.findByPin(merchant.getMerchantPin());
//			//			if (relList != null) {
//			//				for (MbnMerchantTunnelRelation rel : relList) {
//			//					try {
//			//						SmsMbnTunnelVO svo = smsMbnTunnelService.queryByPk(rel
//			//								.getTunnelId());
//			//						tunnelInfoList.add(BeanConvUtils.conv(svo, rel
//			//								.getMerchantPin()));
//			//					} catch (Exception e) {
//			//						logger.error("Get tunnel " + rel.getTunnelId()
//			//								+ " fail.", e);
//			//					}
//			//				}
//			//			}
//			entInfo.setConfigInfoList(configInfoList);
//			entInfo.setTunnelInfoList(tunnelInfoList);
//			entInfoList.add(entInfo);
//			
//			MasBodyPackage body = new MasBodyPackage();
//			body.setEntInfoList(entInfoList);
//			
//			MasHeadPackage head = new MasHeadPackage();
//			head.setNodeId(nodeId.toString());
//			MbnNode mbnNode = mbnNodeService.getByPk(nodeId);
//			head.setPassword(mbnNode.getPassword());
//			head.setEntId( merchant.getMerchantPin().toString() );
//			head.setMethodName(WebServiceConsts.CREATEENT_METHOD);//"createEnt"
//			
//			reqPackage.setHead(head);
//			reqPackage.setBody(body);
//			
//			String recv = HttpUtils.sendRequest(mbnNode.getWebServiceUrl(), PackageUtils.getXml(reqPackage), 60000);
//			MasPackage respo = PackageUtils.getMasPackage(recv);
//			switch(Integer.parseInt(respo.getHead().getReturnCode())){
//				case 0: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户操作成功！");break;
//				case 1001: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户请求错误！");break;
//				case 1002: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户XML解析错误！");break;
//				case 1003: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户鉴权错误！");break;
//				case 1004: entityMap.put("resultInfo", "添加企业及其配置信息成功！节点开户企业ID错误！");break;
//			}
//        }catch(Exception e){
//        	logger.error("manager create corp to node error", e);
//        }
//        return SUCCESS;
//    }
    @Action(value = "updateConfigs", results = {
            @Result(type = "json", params = {
                "root", "entityMap", "contentType", "text/html"})})
        public String updateConfigs() {
            entityMap = new HashMap();
            merchant = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
            List<MbnConfigMerchant> validConfig = new ArrayList<MbnConfigMerchant>();
//            for(MbnConfigMerchant configBean :merchantConfigs){
//            	if(configBean!=null){
//            		validConfig.add(configBean);
//            	}
//            }
            List<MbnMerchantTunnelRelation> relations = new ArrayList<MbnMerchantTunnelRelation>();
            this.configChangeAction(validConfig);
            merchantService.updateMerchantAndMerchantConfig(merchant, validConfig);
            
            List<MasTunnel> tunnels = new ArrayList();
            List<MasTunnel> addTunnels = new ArrayList();
            List<MbnMerchantConsume> consumes = new ArrayList();
            List<MbnMerchantConsume> addConsumes = new ArrayList();
            
            if (tunnel!=null&&StringUtils.isNotBlank(tunnel.getAccessNumber())) {
            	// tunnel.setName(tunnel.getAccessNumber());
            	tunnel.setName("移动");
                tunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
                tunnel.setClassify(ApSmsConstants.SELF_PROVINCE_CLASSIFY);
                tunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
                tunnel.setDelStatus(0);
                tunnels.add(tunnel);
            }
            if (tunnelyw!=null&&StringUtils.isNotBlank(tunnelyw.getAccessNumber())) {
            	// tunnel.setName(tunnel.getAccessNumber());
            	if(tunnelyw.getId()==null){
            		Long smsTunnelId = PinGen.getSerialPin();
                    tunnelyw.setId(smsTunnelId);
        			// tunnel.setName(tunnel.getAccessNumber());
                    tunnelyw.setName("辽宁CMPP异网");
                    tunnelyw.setType(ApSmsConstants.SM_TUNNEL_TYPE);
                    tunnelyw.setClassify(ApSmsConstants.TUNNEL_CLASSIFY_YDYW);
                    tunnelyw.setState(ApSmsConstants.TUNNEL_STATE_VALID);
                    tunnelyw.setDelStatus(0);
                    addTunnels.add(tunnelyw);
                    MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
                    relation.setId(smsTunnelId);
                    relation.setMerchantPin(merchant.getMerchantPin());
                    relation.setAccessNumber(tunnelyw.getAccessNumber());
                    relation.setTunnelId(smsTunnelId);
                    relation.setState(1);
                    relations.add(relation);
                    // 初始化消费表
        			MbnMerchantConsume smsConsume = buildConsume(
        					merchant.getMerchantPin(), smsTunnelId);
        			addConsumes.add(smsConsume);
            	}else{
	            	tunnelyw.setName("辽宁CMPP异网");
	            	tunnelyw.setType(ApSmsConstants.SM_TUNNEL_TYPE);
	            	tunnelyw.setClassify(ApSmsConstants.TUNNEL_CLASSIFY_YDYW);
	            	tunnelyw.setState(ApSmsConstants.TUNNEL_STATE_VALID);
	            	tunnelyw.setDelStatus(0);
	                tunnels.add(tunnelyw);
            	}
            }
    		if (ltTunnel!=null&&StringUtils.isNotBlank(ltTunnel.getAccessNumber())) {
    			if(ltTunnel.getId()==null){
    				Long ltTunnelId = PinGen.getSerialPin();
    	        	ltTunnel.setId(ltTunnelId);
    	        	ltTunnel.setName("联通");
    	        	ltTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
    	        	ltTunnel.setClassify(ApSmsConstants.UNICOM_WHOLE_NETWORK);
    	        	ltTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
    	        	ltTunnel.setDelStatus(0);
    	            addTunnels.add(ltTunnel);
    	            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
    	            relation.setId(ltTunnelId);
    	            relation.setMerchantPin(merchant.getMerchantPin());
    	            relation.setAccessNumber(ltTunnel.getAccessNumber());
    	            relation.setTunnelId(ltTunnelId);
    	            relation.setState(1);
    	            relations.add(relation);
    	            // 初始化消费表
    				MbnMerchantConsume smsConsume = buildConsume(
    						merchant.getMerchantPin(), ltTunnelId);
    				addConsumes.add(smsConsume);
    			}else{
	    			ltTunnel.setName("联通");
	    			ltTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
	    			ltTunnel.setClassify(ApSmsConstants.UNICOM_WHOLE_NETWORK);
	    			ltTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
	    			ltTunnel.setDelStatus(0);
	    			tunnels.add(ltTunnel);
    			}
    		}
    		if (dxTunnel!=null&&StringUtils.isNotBlank(dxTunnel.getAccessNumber())) {
    			if(dxTunnel.getId()==null){
    				Long dxTunnelId = PinGen.getSerialPin();
    	        	dxTunnel.setId(dxTunnelId);
    	        	dxTunnel.setName("电信");
    	        	dxTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
    	        	dxTunnel.setClassify(ApSmsConstants.TELECOM_WHOLE_NETWORK);
    	        	dxTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
    	        	dxTunnel.setDelStatus(0);
    	            addTunnels.add(dxTunnel);
    	            MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
    	            relation.setId(dxTunnelId);
    	            relation.setMerchantPin(merchant.getMerchantPin());
    	            relation.setAccessNumber(ltTunnel.getAccessNumber());
    	            relation.setTunnelId(dxTunnelId);
    	            relation.setState(1);
    	            relations.add(relation);
    				MbnMerchantConsume smsConsume = buildConsume(
    						merchant.getMerchantPin(), dxTunnelId);
    				addConsumes.add(smsConsume);
    			}else{
	    			dxTunnel.setName("电信");
	    			dxTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
	    			dxTunnel.setClassify(ApSmsConstants.TELECOM_WHOLE_NETWORK);
	    			dxTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
	    			dxTunnel.setDelStatus(0);
	    			tunnels.add(dxTunnel);
    			}
    		}
            if (mobileTunnel!=null&&StringUtils.isNotBlank(mobileTunnel.getAccessNumber())) {
    			// mobileTunnel.setName(mobileTunnel.getAccessNumber());
            	if(mobileTunnel.getId()==null){
            		Long mobileTunnelId = PinGen.getSerialPin();
                    mobileTunnel.setId(mobileTunnelId);
                    // mobileTunnel.setName(mobileTunnel.getAccessNumber());
                    mobileTunnel.setName("SIM卡");
                    mobileTunnel.setClassify(ApSmsConstants.SIM_MODEM_CLASSIFY);
                    mobileTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
                    mobileTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
                    mobileTunnel.setDelStatus(0);
                    addTunnels.add(mobileTunnel);
                    MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
                    relation.setId(mobileTunnelId);
                    relation.setMerchantPin(merchant.getMerchantPin());
                    relation.setAccessNumber(mobileTunnel.getAccessNumber());
                    relation.setTunnelId(mobileTunnel.getId());
                    relation.setState(1);
                    relations.add(relation);
        			MbnMerchantConsume smsConsume = buildConsume(
        					merchant.getMerchantPin(), mobileTunnelId);
        			addConsumes.add(smsConsume);
            	}else{
            		mobileTunnel.setName("SIM卡");
                    mobileTunnel.setClassify(ApSmsConstants.SIM_MODEM_CLASSIFY);
                    mobileTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
                    mobileTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
                    mobileTunnel.setDelStatus(0);
                    tunnels.add(mobileTunnel);
                    mobileConsume.setMerchantPin(merchant.getMerchantPin());
                    mobileConsume.setTunnelId(mobileTunnel.getId());
                    mobileConsume.setModifyTime(Calendar.getInstance().getTime());
                    consumes.add(mobileConsume);
            	}
            }
            if (mmTunnel!=null&&StringUtils.isNotBlank(mmTunnel.getAccessNumber())) {
    			// mmTunnel.setName(mmTunnel.getAccessNumber());
            	mmTunnel.setName("彩信");
                mmTunnel.setClassify(ApSmsConstants.SELF_PROVINCE_CLASSIFY);
                mmTunnel.setType(ApSmsConstants.MM_TUNNEL_TYPE);
                mmTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
                mmTunnel.setDelStatus(0);
                tunnels.add(mmTunnel);
            }
            if (zxtTunnel!=null&&StringUtils.isNotBlank(zxtTunnel.getUser())) {
            	if(zxtTunnel.getId()==null){
	            	if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.TUNNELALL))){
	                	String tunnelAll = WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.TUNNELALL);           
	                	if("true".equalsIgnoreCase(tunnelAll)){
	                		String zxtExt = getZxtUserId();
	                		merchant.setCorpExt(zxtExt);
	                		int zxtUserId = ZXTUserTool.addCorpUser(zxtTunnel.getUser(), zxtTunnel.getPasswd(), zxtExt);
	                		if( !(zxtUserId > 0) ){
	                			 entityMap.put("flag", false);
	                			 entityMap.put("resultInfo", "企信通开户失败，请联系厂商客服！");
	                			 return SUCCESS;
	                		}
	                		merchant.setCorpId(zxtUserId);
	                	}
	                }
	            	Long zxtTunnelId = PinGen.getSerialPin();
	            	zxtTunnel.setId(zxtTunnelId);
	    			// tunnel.setName(tunnel.getAccessNumber());
	            	zxtTunnel.setName("资信通");
	            	zxtTunnel.setAccessNumber(merchant.getCorpId() != 0 ? String.valueOf( merchant.getCorpId() ):"911");
	            	zxtTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
	            	zxtTunnel.setClassify(ApSmsConstants.ZXT_TUNNEL_TYPE);
	            	zxtTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
	            	zxtTunnel.setDelStatus(0);
	            	addTunnels.add(zxtTunnel);
	                MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
	                relation.setId(zxtTunnelId);
	                relation.setMerchantPin(merchant.getMerchantPin());
	                relation.setAccessNumber(String.valueOf(merchant.getCorpId()));
	                relation.setTunnelId(zxtTunnelId);
	                relation.setState(1);
	                relations.add(relation);
	                // 初始化消费表
	    			MbnMerchantConsume smsConsume = buildConsume(
	    					merchant.getMerchantPin(), zxtTunnelId);
	    			addConsumes.add(smsConsume);
            	}else{
//            	Long zxtTunnelId = PinGen.getSerialPin();
//            	zxtTunnel.setId(zxtTunnelId);
	    			// tunnel.setName(tunnel.getAccessNumber());
	            	zxtTunnel.setName("资信通");
	            	zxtTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
	            	zxtTunnel.setClassify(ApSmsConstants.ZXT_TUNNEL_TYPE);
	            	zxtTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
	            	zxtTunnel.setAccessNumber(merchant.getCorpId() != 0 ? String.valueOf( merchant.getCorpId() ):"911");
	            	zxtTunnel.setDelStatus(0);
	                tunnels.add(zxtTunnel);
            	}
            }
            if (emppTunnel!=null&&StringUtils.isNotBlank(emppTunnel.getUser())) {
            	if(emppTunnel.getId()==null){
            		Long smsTunnelId = PinGen.getSerialPin();
                	emppTunnel.setId(smsTunnelId);
        			// tunnel.setName(tunnel.getAccessNumber());
                	emppTunnel.setName("上海移动empp");
                	emppTunnel.setServiceId("EMPP");
                	emppTunnel.setSmsCorpIdent("EMPP");
                	emppTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
                	emppTunnel.setClassify(ApSmsConstants.EMPP_TUNNEL_TYPE);
                	emppTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
                	emppTunnel.setDelStatus(0);
                    addTunnels.add(emppTunnel);
                    MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
                    relation.setId(smsTunnelId);
                    relation.setMerchantPin(merchant.getMerchantPin());
                    relation.setAccessNumber(emppTunnel.getUser());
                    relation.setTunnelId(smsTunnelId);
                    relation.setState(1);
                    relations.add(relation);
                    // 初始化消费表
        			MbnMerchantConsume smsConsume = buildConsume(
        					merchant.getMerchantPin(), smsTunnelId);
        			addConsumes.add(smsConsume);
            	}else{
	            	emppTunnel.setName("上海移动empp");
	            	emppTunnel.setServiceId("EMPP");
	            	emppTunnel.setSmsCorpIdent("EMPP");
	            	emppTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
	            	emppTunnel.setClassify(ApSmsConstants.EMPP_TUNNEL_TYPE);
	            	emppTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
	            	emppTunnel.setDelStatus(0);
	                tunnels.add(emppTunnel);
            	}
            }
            if (newQxtTunnel!=null&&StringUtils.isNotBlank(newQxtTunnel.getUser())) {
            	if(newQxtTunnel.getId()==null){
            		Long newQxtTunnelId = PinGen.getSerialPin();
        	      	newQxtTunnel.setId(newQxtTunnelId);
        	      	newQxtTunnel.setName("新企信通");
        	      	newQxtTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
        	      	newQxtTunnel.setClassify(ApSmsConstants.NEW_QXT_TUNNEL_TYPE);
        	      	newQxtTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
        	      	newQxtTunnel.setAccessNumber(newQxtTunnel.getUser());
        	      	newQxtTunnel.setDelStatus(0);
        	        addTunnels.add(newQxtTunnel);
        	        MbnMerchantTunnelRelation relation = new MbnMerchantTunnelRelation();
        	        relation.setId(newQxtTunnelId);
        	        relation.setMerchantPin(merchant.getMerchantPin());
        	        relation.setAccessNumber(newQxtTunnel.getUser());
        	        relation.setTunnelId(newQxtTunnelId);
        	        relation.setState(1);
        	        relations.add(relation);
        	        // 初始化消费表
        			MbnMerchantConsume smsConsume = buildConsume(merchant.getMerchantPin(), newQxtTunnelId);
        			addConsumes.add(smsConsume);
            	}else{
	    	      	newQxtTunnel.setName("新企信通");
	    	      	newQxtTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
	    	      	newQxtTunnel.setClassify(ApSmsConstants.NEW_QXT_TUNNEL_TYPE);
	    	      	newQxtTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
	    	      	newQxtTunnel.setDelStatus(0);
	    	        tunnels.add(newQxtTunnel);
            	}
    	      }
            smsMbnTunnelService.addTunnelAndConsumeForMas(addTunnels, addConsumes, relations, merchant);
            smsMbnTunnelService.updateTunnelAndConsumeForMas(tunnels, consumes, merchant);
            entityMap.put("resultInfo", "更新企业及其配置信息成功!");
            /**
             * 河北tuoguanmas
             * 管理、业务 统一管理，统计管理
             */
            
            /**
             * 河北tuoguanmas
             * 管理、业务 统一管理，统计管理
             */
            try{
    	        MasPackage reqPackage = new MasPackage();
    	        // 更新企业信息
    			MasHeadPackage head = new MasHeadPackage();
    			head.setNodeId(nodeId.toString());
    			MbnNode mbnNode = mbnNodeService.getByPk(nodeId);
    			head.setPassword(mbnNode.getPassword());
    			head.setEntId( merchant.getMerchantPin().toString() );
    			head.setMethodName(WebServiceConsts.SYNCENTINFO_METHOD);//"syncEntInfo"
    			
    	        // 获取企业信息
    			MbnMerchantVip merchantTemp = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
    			EntInfo entInfo = BeanConvUtils.conv(merchantTemp);
    			List<EntInfo> entInfoList = new ArrayList<EntInfo>();
    			// 获取配置信息,获取通道信息
    			List<ConfigInfo> configInfoList = new ArrayList<ConfigInfo>();
    			List<TunnelInfo> tunnelInfoList = new ArrayList<TunnelInfo>();
    	
    			List<MbnConfigMerchant> confList = merchantConfig
    					.queryMerchantConfigList(merchant.getMerchantPin());
    			for (MbnConfigMerchant conf : confList) {
    				configInfoList.add(BeanConvUtils.conv(conf));
    			}
    			List<MasTunnel> tunnelList = smsMbnTunnelService.getMasTunnelsByMerchantPin(merchant.getMerchantPin());
    			if( tunnelList != null){
    				for(MasTunnel tunnel: tunnelList){
    					tunnelInfoList.add(BeanConvUtils.conv(tunnel, merchant.getMerchantPin()));
    				}
    			}
    			//			List<MbnMerchantTunnelRelation> relList = mtrService
    			//					.findByPin(merchant.getMerchantPin());
    			//			if (relList != null) {
    			//				for (MbnMerchantTunnelRelation rel : relList) {
    			//					try {
    			//						SmsMbnTunnelVO svo = smsMbnTunnelService.queryByPk(rel
    			//								.getTunnelId());
    			//						tunnelInfoList.add(BeanConvUtils.conv(svo, rel
    			//								.getMerchantPin()));
    			//					} catch (Exception e) {
    			//						logger.error("Get tunnel " + rel.getTunnelId()
    			//								+ " fail.", e);
    			//					}
    			//				}
    			//			}
    			entInfo.setConfigInfoList(configInfoList);
    			entInfo.setTunnelInfoList(tunnelInfoList);
    			entInfoList.add(entInfo);
    			
    			MasBodyPackage body = new MasBodyPackage();
    			body.setEntInfoList(entInfoList);
    			
    			reqPackage.setHead(head);
    			reqPackage.setBody(body);
    			HttpUtils.sendRequest(mbnNode.getWebServiceUrl(), PackageUtils.getXml(reqPackage), 60000);
    			entityMap.put("resultInfo", "更新企业及其配置信息成功 ！发送节点更新操作成功！");
            }catch(Exception e){
            	logger.error("manager create corp to node error", e);
    			entityMap.put("resultInfo", "更新企业及其配置信息成功 ！发送节点更新操作失败！");
            }
            return SUCCESS;
        }
    private void corpChangeAction(List<MbnConfigMerchant> validConfig){
//    	smsSignContent = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "sms_sign_content");
//        isdelegate = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "isdelegate");
//        masserverip = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "masserverip");
//        gatewaylimit = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "gatewaylimit");
//        corpLoginPort = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "corp_login_port");
        validConfig.add(smsSignContent);
        validConfig.add(isdelegate);
        validConfig.add(masserverip);
        validConfig.add(gatewaylimit);
        validConfig.add(corpLoginPort);
//        smsSendLimit = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "sms_send_limit");
//        mmtopport = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "mmtopport");
    }
    private void configChangeAction(List<MbnConfigMerchant> validConfig){
//        smsSendLimit = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "sms_send_limit");
//        mmtopport = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "mmtopport");
    	if(smsSendLimit!=null){
    		validConfig.add(smsSendLimit);
    	}
        if(mmtopport!=null){
        	validConfig.add(mmtopport);
        }
    }
    @Action(value = "updateCorp", results = {
            @Result(type = "json", params = {
                "root", "entityMap", "contentType", "text/html"})})
	public String updateCorp() {
	    entityMap = new HashMap();
//	    MbnMerchantVip merchantTemp = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
	    switch (loginUser.getUserType()) {
	        case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
	        	entityMap.put("flag", false);
	            entityMap.put("resultInfo", "省管理员不能更新企业!");
	            return SUCCESS;
	        case ApSmsConstants.USER_TYPE_CITY_ADMIN:
	            MbnMerchantVip merchantOfCurrentUser = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
	            merchant.setProvince(merchantOfCurrentUser.getProvince());
	            merchant.setCity(merchantOfCurrentUser.getCity());
	            break;
	        case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
	        	entityMap.put("flag", false);
	            entityMap.put("resultInfo", "系统管理员不能更新企业!");
	            return SUCCESS;
	    }
	    MbnMerchantVip tempMerchant = mbnMerchantVipIService.loadByName(merchant.getName());
	    if (tempMerchant != null && !tempMerchant.getMerchantPin().equals(merchant.getMerchantPin())) {
	    	entityMap.put("flag", false);
	        entityMap.put("resultInfo", "企业已存在,请重新填写!");
	        return SUCCESS;
	    }
	    List<MbnConfigMerchant> validConfig = new ArrayList<MbnConfigMerchant>();
//        for(MbnConfigMerchant configBean :merchantConfigs){
//        	if(configBean!=null){
//        		validConfig.add(configBean);
//        	}
//        }
	    this.corpChangeAction(validConfig);
	    merchantService.updateMerchantAndMerchantConfig(merchant, validConfig);
	    
//	    merchant = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
//        List<MbnConfigMerchant> validConfig = new ArrayList<MbnConfigMerchant>();
//        for(MbnConfigMerchant configBean :merchantConfigs){
//        	if(configBean!=null){
//        		validConfig.add(configBean);
//        	}
//        }
//        merchantService.updateMerchantAndMerchantConfig(merchant, validConfig);
	    
	    entityMap.put("flag", true);
        entityMap.put("resultInfo", "更新企业信息成功！");
        logger.error("更新企业信息成功！");
        entityMap.put("merchant", merchant);
	    return SUCCESS;
	}
//    @Action(value = "updateCorpAndConfig", results = {
//        @Result(type = "json", params = {
//            "root", "entityMap", "contentType", "text/html"})})
//    public String updateCorpAndConfigs() {
//        entityMap = new HashMap();
//        switch (loginUser.getUserType()) {
//            case ApSmsConstants.USER_TYPE_PROVINCE_ADMIN:
//                entityMap.put("resultInfo", "省管理员不能更新企业!");
//                return SUCCESS;
//            case ApSmsConstants.USER_TYPE_CITY_ADMIN:
//                MbnMerchantVip merchantOfCurrentUser = mbnMerchantVipIService.loadByMerchantPin(loginUser.getMerchantPin());
//                merchant.setProvince(merchantOfCurrentUser.getProvince());
//                merchant.setCity(merchantOfCurrentUser.getCity());
//                break;
//            case ApSmsConstants.USER_TYPE_SUPER_ADMIN:
//                entityMap.put("resultInfo", "系统管理员不能更新企业!");
//                return SUCCESS;
//        }
//        MbnMerchantVip tempMerchant = mbnMerchantVipIService.loadByName(merchant.getName());
//        if (tempMerchant != null && !tempMerchant.getMerchantPin().equals(merchant.getMerchantPin())) {
//            entityMap.put("resultInfo", "企业已存在,请重新填写!");
//            return SUCCESS;
//        }
//        merchantService.updateMerchantAndMerchantConfig(merchant, merchantConfigs);
//        List<MasTunnel> tunnels = new ArrayList();
//        List<MbnMerchantConsume> consumes = new ArrayList();
//        if (tunnel!=null&&StringUtils.isNotBlank(tunnel.getAccessNumber())) {
//        	// tunnel.setName(tunnel.getAccessNumber());
//        	tunnel.setName("移动");
//            tunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
//            tunnel.setClassify(ApSmsConstants.SELF_PROVINCE_CLASSIFY);
//            tunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//            tunnel.setDelStatus(0);
//            tunnels.add(tunnel);
//        }
//		if (ltTunnel!=null&&StringUtils.isNotBlank(ltTunnel.getAccessNumber())) {
//			ltTunnel.setName("联通");
//			ltTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
//			ltTunnel.setClassify(ApSmsConstants.UNICOM_WHOLE_NETWORK);
//			ltTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//			ltTunnel.setDelStatus(0);
//			tunnels.add(ltTunnel);
//		}
//		if (dxTunnel!=null&&StringUtils.isNotBlank(dxTunnel.getAccessNumber())) {
//			dxTunnel.setName("电信");
//			dxTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
//			dxTunnel.setClassify(ApSmsConstants.TELECOM_WHOLE_NETWORK);
//			dxTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//			dxTunnel.setDelStatus(0);
//			tunnels.add(dxTunnel);
//		}
//        if (mobileTunnel!=null&&StringUtils.isNotBlank(mobileTunnel.getAccessNumber())) {
//			// mobileTunnel.setName(mobileTunnel.getAccessNumber());
//        	mobileTunnel.setName("SIM卡");
//            mobileTunnel.setClassify(ApSmsConstants.SIM_MODEM_CLASSIFY);
//            mobileTunnel.setType(ApSmsConstants.SM_TUNNEL_TYPE);
//            mobileTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//            mobileTunnel.setDelStatus(0);
//            tunnels.add(mobileTunnel);
//            mobileConsume.setMerchantPin(merchant.getMerchantPin());
//            mobileConsume.setTunnelId(mobileTunnel.getId());
//            mobileConsume.setModifyTime(Calendar.getInstance().getTime());
//            consumes.add(mobileConsume);
//        }
//        if (mmTunnel!=null&&StringUtils.isNotBlank(mmTunnel.getAccessNumber())) {
//			// mmTunnel.setName(mmTunnel.getAccessNumber());
//        	mmTunnel.setName("彩信");
//            mmTunnel.setClassify(ApSmsConstants.SELF_PROVINCE_CLASSIFY);
//            mmTunnel.setType(ApSmsConstants.MM_TUNNEL_TYPE);
//            mmTunnel.setState(ApSmsConstants.TUNNEL_STATE_VALID);
//            mmTunnel.setDelStatus(0);
//            tunnels.add(mmTunnel);
//        }
//        smsMbnTunnelService.updateTunnelAndConsumeForMas(tunnels, consumes, merchant);
//        entityMap = new HashMap();
//        entityMap.put("resultInfo", "更新企业及其配置信息成功!");
//        /**
//         * 河北tuoguanmas
//         * 管理、业务 统一管理，统计管理
//         */
//        
//        /**
//         * 河北tuoguanmas
//         * 管理、业务 统一管理，统计管理
//         */
//        try{
//	        MasPackage reqPackage = new MasPackage();
//	        // 更新企业信息
//			MasHeadPackage head = new MasHeadPackage();
//			head.setNodeId(nodeId.toString());
//			MbnNode mbnNode = mbnNodeService.getByPk(nodeId);
//			head.setPassword(mbnNode.getPassword());
//			head.setEntId( merchant.getMerchantPin().toString() );
//			head.setMethodName(WebServiceConsts.SYNCENTINFO_METHOD);//"syncEntInfo"
//			
//	        // 获取企业信息
//			MbnMerchantVip merchantTemp = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
//			EntInfo entInfo = BeanConvUtils.conv(merchantTemp);
//			List<EntInfo> entInfoList = new ArrayList<EntInfo>();
//			// 获取配置信息,获取通道信息
//			List<ConfigInfo> configInfoList = new ArrayList<ConfigInfo>();
//			List<TunnelInfo> tunnelInfoList = new ArrayList<TunnelInfo>();
//	
//			List<MbnConfigMerchant> confList = merchantConfig
//					.queryMerchantConfigList(merchant.getMerchantPin());
//			for (MbnConfigMerchant conf : confList) {
//				configInfoList.add(BeanConvUtils.conv(conf));
//			}
//			List<MasTunnel> tunnelList = smsMbnTunnelService.getMasTunnelsByMerchantPin(merchant.getMerchantPin());
//			if( tunnelList != null){
//				for(MasTunnel tunnel: tunnelList){
//					tunnelInfoList.add(BeanConvUtils.conv(tunnel, merchant.getMerchantPin()));
//				}
//			}
//			//			List<MbnMerchantTunnelRelation> relList = mtrService
//			//					.findByPin(merchant.getMerchantPin());
//			//			if (relList != null) {
//			//				for (MbnMerchantTunnelRelation rel : relList) {
//			//					try {
//			//						SmsMbnTunnelVO svo = smsMbnTunnelService.queryByPk(rel
//			//								.getTunnelId());
//			//						tunnelInfoList.add(BeanConvUtils.conv(svo, rel
//			//								.getMerchantPin()));
//			//					} catch (Exception e) {
//			//						logger.error("Get tunnel " + rel.getTunnelId()
//			//								+ " fail.", e);
//			//					}
//			//				}
//			//			}
//			entInfo.setConfigInfoList(configInfoList);
//			entInfo.setTunnelInfoList(tunnelInfoList);
//			entInfoList.add(entInfo);
//			
//			MasBodyPackage body = new MasBodyPackage();
//			body.setEntInfoList(entInfoList);
//			
//			reqPackage.setHead(head);
//			reqPackage.setBody(body);
//			HttpUtils.sendRequest(mbnNode.getWebServiceUrl(), PackageUtils.getXml(reqPackage), 60000);
//			// entityMap.put("resultInfo", "更新企业及其配置信息成功 ！发送节点更新操作成功！");
//        }catch(Exception e){
//        	logger.error("manager create corp to node error", e);
//			// entityMap.put("resultInfo", "更新企业及其配置信息成功 ！发送节点更新操作失败！");
//        }
//        return SUCCESS;
//    }

    private void getCorpConfig(){
//    	corpLoginPort = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "corp_login_port");
    	smsSignContent = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "sms_sign_content");
        isdelegate = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "isdelegate");
        masserverip = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "masserverip");
        gatewaylimit = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "gatewaylimit");
        corpLoginPort = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "corp_login_port");
        smsSendLimit = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "sms_send_limit");
        mmtopport = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "mmtopport");
    }
    @Action(value = "showCorpAndConfig", results = {
        @Result(name = SUCCESS, location = "/delegatemas/corpmanage/corpadd.jsp"),
        @Result(name = "corpview", location = "/delegatemas/corpmanage/corpview.jsp")
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
        registStatus = checkLisence();
        this.merchant = mbnMerchantVipIService.loadByMerchantPin(merchant.getMerchantPin());
//        this.merchantConfigs = merchantConfig.queryMerchantConfigList(merchant.getMerchantPin());
//        corpLoginPort = merchantConfig.loadByMerchantPin(merchant.getMerchantPin(), "corp_login_port");
        //企业配置信息
        getCorpConfig();
        if(corpLoginPort==null||corpLoginPort.getItemValue().equals("")){
        	String corpPort = String.valueOf(PinGen.getPinPort());
            request.setAttribute("corpPort", corpPort);
        }
        List<MasTunnel> masTunnels = smsMbnTunnelService.getMasTunnelsByMerchantPin(merchant.getMerchantPin());
        for (MasTunnel tempTunnel : masTunnels) {
            if (tempTunnel.getType() == ApSmsConstants.SM_TUNNEL_TYPE) {
                if (tempTunnel.getClassify() == ApSmsConstants.SIM_MODEM_CLASSIFY) {
                    mobileTunnel = tempTunnel;
                    mobileConsume = consumeService.findByTunnelId(merchant.getMerchantPin(), mobileTunnel.getId());
                } else if(tempTunnel.getClassify() == ApSmsConstants.UNICOM_WHOLE_NETWORK) {
                	ltTunnel = tempTunnel;
                } else if(tempTunnel.getClassify() == ApSmsConstants.TELECOM_WHOLE_NETWORK) {
                	dxTunnel = tempTunnel;
                } else if(tempTunnel.getClassify() == ApSmsConstants.ZXT_TUNNEL_TYPE) {
                	zxtTunnel = tempTunnel;
                } else if(tempTunnel.getClassify() == ApSmsConstants.NEW_QXT_TUNNEL_TYPE) {
                	newQxtTunnel = tempTunnel;
                } else if(tempTunnel.getClassify() == ApSmsConstants.EMPP_TUNNEL_TYPE) {
                	emppTunnel = tempTunnel;
                } else if(tempTunnel.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_YDYW){
                	tunnelyw = tempTunnel;
                }else{
                	tunnel = tempTunnel;
                }
            } else {
                mmTunnel = tempTunnel;
            }
        }
        //业务节点 列表，业务节点企业关系列表
        //mbnNodeTOes
        mbnNodeTOes = new ArrayList<MbnNodeTO>();
        List<MbnNode> mbnNodes = mbnNodeService.getNodeList();
        List<MbnNodeMerchantRelation> mbnNodeMerchantRelation = mbnNodeMerchantRelService.getByMerchantPin(merchant.getMerchantPin());
        MbnNodeMerchantRelation nodeMerRel = null;
        if( mbnNodeMerchantRelation!=null && mbnNodeMerchantRelation.size()>0 ){
        	nodeMerRel = mbnNodeMerchantRelation.get(0);
		}
        if( mbnNodes != null && mbnNodes.size() >0 ){
        	for(MbnNode nodeTemp :mbnNodes){
        		MbnNodeTO nodeTO = new MbnNodeTO();
        		nodeTO.setId(nodeTemp.getId());
        		nodeTO.setName(nodeTemp.getName());
        		if( nodeMerRel != null && nodeMerRel.getNodeId().equals(nodeTemp.getId()) ){
        			nodeTOStatus = true;
        			nodeTO.setSelected(true);
        		}else{
        			nodeTO.setSelected(false);
        		}
        		mbnNodeTOes.add(nodeTO);
            }
        }
        return returnLink;
    }
    
    private boolean checkLisence(){
		List<Lisence> lisenceList = lisenceService.getAllLisence();
		if( lisenceList == null || lisenceList.size() ==0 ){
			return false;
		}else{
			List<String> localList = MacGetterUtil.getMACLisence();
			Iterator<Lisence> lisenceIterator = lisenceList.iterator();
			while(lisenceIterator.hasNext()){
				Lisence tempLisence = lisenceIterator.next();
				String lisence = tempLisence.getLisenceValue();
				if(lisence!=null){
					for(int i=0; i < localList.size(); i++){
						if(localList.get(i).equals(lisence)){
							return true;
						}
					}
				}else{
					continue;
				}
			}
		}
		return false;
	}
    
    @Action(value = "preCorpConfigInfoNodes", results = {
            @Result(name = SUCCESS, location = "/delegatemas/corpmanage/corpadd.jsp"),
            @Result(name = "corpview", location = "/delegatemas/corpmanage/corpview.jsp")
        })
        public String preCorpConfigInfoNodes() {
    		String returnLink = SUCCESS;
            //业务节点 列表，业务节点企业关系列表
            //mbnNodeTOes
            mbnNodeTOes = new ArrayList<MbnNodeTO>();
            String corpPort = String.valueOf(PinGen.getPinPort());
            request.setAttribute("corpPort", corpPort);
            List<MbnNode> mbnNodes = mbnNodeService.getNodeList();
            registStatus = checkLisence();
            if( mbnNodes != null && mbnNodes.size() >0 ){
            	for(MbnNode nodeTemp :mbnNodes){
            		MbnNodeTO nodeTO = new MbnNodeTO();
            		nodeTO.setId(nodeTemp.getId());
            		nodeTO.setName(nodeTemp.getName());
            		nodeTO.setSelected(false);
            		mbnNodeTOes.add(nodeTO);
                }
            }
            return returnLink;
        }

    @Action(value = "removeCorpAndConfig", results = {
        @Result(type = "json", params = {
            "root", "entityMap", "contentType", "text/html"})
    })
    public String removeCorpAndConfigs() {
        entityMap = new HashMap();
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
    
    private MbnMerchantConsume buildConsume(Long merchantPin, Long tunnelId){
        MbnMerchantConsume t = new MbnMerchantConsume();
        t.setId(PinGen.getSerialPin());
        t.setMerchantPin(merchantPin);
        t.setTunnelId(tunnelId);
        t.setRemainNumber(9999999L);
        t.setModifyTime(new Date());
    	return t;
    }

//    public List<MbnConfigMerchant> getMerchantConfigs() {
//        return this.merchantConfigs;
//    }
//
//    public void setMerchantConfigs(List<MbnConfigMerchant> merchantConfigs) {
//        this.merchantConfigs = merchantConfigs;
//    }

    public MbnMerchantVip getMerchant() {
        return merchant;
    }

    public void setMerchant(MbnMerchantVip merchant) {
        this.merchant = merchant;
    }

    public MbnMerchantConsume getConsume() {
        return consume;
    }

    public void setConsume(MbnMerchantConsume consume) {
        this.consume = consume;
    }

    public MasTunnel getTunnel() {
        return tunnel;
    }

    public void setTunnel(MasTunnel tunnel) {
        this.tunnel = tunnel;
    }

    public MasTunnel getLtTunnel() {
		return ltTunnel;
	}

	public void setLtTunnel(MasTunnel ltTunnel) {
		this.ltTunnel = ltTunnel;
	}

	public MasTunnel getDxTunnel() {
		return dxTunnel;
	}

	public void setDxTunnel(MasTunnel dxTunnel) {
		this.dxTunnel = dxTunnel;
	}

	public MasTunnel getMobileTunnel() {
        return mobileTunnel;
    }

    public void setMobileTunnel(MasTunnel mobileTunnel) {
        this.mobileTunnel = mobileTunnel;
    }

    public MasTunnel getMmTunnel() {
        return mmTunnel;
    }

    public void setMmTunnel(MasTunnel mmTunnel) {
        this.mmTunnel = mmTunnel;
    }

    public MbnMerchantConsume getMobileConsume() {
        return mobileConsume;
    }

    public void setMobileConsume(MbnMerchantConsume mobileConsume) {
        this.mobileConsume = mobileConsume;
    }

    public List<MbnMerchantVip> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<MbnMerchantVip> merchants) {
        this.merchants = merchants;
    }

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public List<MbnNodeTO> getMbnNodeTOes() {
		return mbnNodeTOes;
	}

	public void setMbnNodeTOes(List<MbnNodeTO> mbnNodeTOes) {
		this.mbnNodeTOes = mbnNodeTOes;
	}

	public boolean isNodeTOStatus() {
		return nodeTOStatus;
	}

	public void setNodeTOStatus(boolean nodeTOStatus) {
		this.nodeTOStatus = nodeTOStatus;
	}

	public MasTunnel getZxtTunnel() {
		return zxtTunnel;
	}

	public void setZxtTunnel(MasTunnel zxtTunnel) {
		this.zxtTunnel = zxtTunnel;
	}

	public MasTunnel getNewQxtTunnel() {
		return newQxtTunnel;
	}

	public void setNewQxtTunnel(MasTunnel newQxtTunnel) {
		this.newQxtTunnel = newQxtTunnel;
	}

	public MasTunnel getEmppTunnel() {
		return emppTunnel;
	}

	public void setEmppTunnel(MasTunnel emppTunnel) {
		this.emppTunnel = emppTunnel;
	}

	public boolean isRegistStatus() {
		return registStatus;
	}

	public void setRegistStatus(boolean registStatus) {
		this.registStatus = registStatus;
	}

	public MbnConfigMerchant getSmsSignContent() {
		return smsSignContent;
	}

	public void setSmsSignContent(MbnConfigMerchant smsSignContent) {
		this.smsSignContent = smsSignContent;
	}

	public MbnConfigMerchant getIsdelegate() {
		return isdelegate;
	}

	public void setIsdelegate(MbnConfigMerchant isdelegate) {
		this.isdelegate = isdelegate;
	}

	public MbnConfigMerchant getMasserverip() {
		return masserverip;
	}

	public void setMasserverip(MbnConfigMerchant masserverip) {
		this.masserverip = masserverip;
	}

	public MbnConfigMerchant getGatewaylimit() {
		return gatewaylimit;
	}

	public void setGatewaylimit(MbnConfigMerchant gatewaylimit) {
		this.gatewaylimit = gatewaylimit;
	}

	public MbnConfigMerchant getCorpLoginPort() {
		return corpLoginPort;
	}

	public void setCorpLoginPort(MbnConfigMerchant corpLoginPort) {
		this.corpLoginPort = corpLoginPort;
	}

	public MbnConfigMerchant getSmsSendLimit() {
		return smsSendLimit;
	}

	public void setSmsSendLimit(MbnConfigMerchant smsSendLimit) {
		this.smsSendLimit = smsSendLimit;
	}

	public MbnConfigMerchant getMmtopport() {
		return mmtopport;
	}

	public void setMmtopport(MbnConfigMerchant mmtopport) {
		this.mmtopport = mmtopport;
	}

	public MasTunnel getTunnelyw() {
		return tunnelyw;
	}

	public void setTunnelyw(MasTunnel tunnelyw) {
		this.tunnelyw = tunnelyw;
	}
}
