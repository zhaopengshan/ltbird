package com.leadtone.mas.bizplug.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.leadtone.delegatemas.merchant.service.IMasMerchantService;
import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.admin.common.ApSmsConstants;
import com.leadtone.mas.admin.webservice.PackageUtils;
import com.leadtone.mas.admin.webservice.WebServiceConsts;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.service.WebUtils;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.HttpUtils;
import com.leadtone.mas.bizplug.webservice.bean.AdminInfo;
import com.leadtone.mas.bizplug.webservice.bean.ConfigInfo;
import com.leadtone.mas.bizplug.webservice.bean.EntInfo;
import com.leadtone.mas.bizplug.webservice.bean.MasBodyPackage;
import com.leadtone.mas.bizplug.webservice.bean.MasHeadPackage;
import com.leadtone.mas.bizplug.webservice.bean.MasPackage;
import com.leadtone.mas.bizplug.webservice.bean.Register;
import com.leadtone.mas.bizplug.webservice.bean.TunnelInfo;
import com.leadtone.mas.bizplug.webservice.util.BeanConvUtils;
import com.leadtone.mas.connector.utils.SpringUtils;

public class PlugWebServiceServlet extends HttpServlet {
	private static Logger logger = Logger
			.getLogger(PlugWebServiceServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -9062553092941114189L;

	@Override
	public void init() {
		logger.info("PlugWebServiceServlet init start.");
		//判断是否需要向管理系统注册
		if( "true".equalsIgnoreCase(WebUtils.getPropertyByName("use_webservice"))){
			String nodeId = WebUtils.getPropertyByName("nodeid");
			String pass = WebUtils.getPropertyByName("nodepass");
			String webServiceUrl = WebUtils.getPropertyByName("webserviceurl");
			String ip = WebUtils.getPropertyByName("ip");
			
			String mgrWebServiceUrl = WebUtils.getPropertyByName("mgr_webserviceurl");
			String encptyPass = DigestUtils.md5Hex(pass);
			
			Register register = new Register();
			register.setWebServiceUrl(webServiceUrl);
			register.setIp(ip);
			
			MasPackage masPack = new MasPackage();
			MasHeadPackage head = new MasHeadPackage();
			head.setNodeId(nodeId);
			head.setPassword(encptyPass);
			head.setMethodName(WebServiceConsts.REGISTER_METHOD);
			
			MasBodyPackage body = new MasBodyPackage();
			body.setRegister(register);
			
			masPack.setHead(head);
			masPack.setBody(body);
			masPack.setVersion("1.0");
			
			String xml = PackageUtils.getXml(masPack);
			if( StringUtils.isNotBlank(xml)){
				String resp = HttpUtils.sendRequest(mgrWebServiceUrl, xml, 30000);
				if( StringUtils.isNotBlank(resp)){
					MasPackage respPack = PackageUtils.getMasPackage(resp);
					if( respPack != null){
						logger.info("Register result is " + respPack.getHead().getReturnCode());
					}
				}
			}
		}
		logger.info("PlugWebServiceServlet init end.");
	}
	
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 解析请求
		// 判断请求是否有效
		String reqXml = PackageUtils.getRequestXml(request);
		if (StringUtils.isBlank(reqXml)) {
			// 返回请求错误
			MasPackage rspPackage = PackageUtils.buildPackage("", "", "",
					WebServiceConsts.BAD_REQUEST_RETURN_CODE,
					WebServiceConsts.BAD_REQUEST_RETURN_MESSAGE);
			PackageUtils.writeRsp(response, rspPackage);
			return;
		}

		logger.info("recv:" + reqXml);
		MasPackage masPackage = PackageUtils.getMasPackage(reqXml);
		if (masPackage == null) {
			// XML解析异常
			MasPackage rspPackage = PackageUtils.buildPackage("", "", "",
					WebServiceConsts.XML_PARSE_FAIL_RETURN_CODE,
					WebServiceConsts.XML_PARSE_FAIL_RETURN_MESSAGE);
			PackageUtils.writeRsp(response, rspPackage);
			return;
		}
		// 业务处理
		MasPackage rspPackage = bizProcess(masPackage);
		// 返回处理结果
		PackageUtils.writeRsp(response, rspPackage);
	}

	/**
	 * 
	 * @param masPackage
	 * @return
	 */
	private MasPackage bizProcess(MasPackage masPackage) {
		MbnConfigMerchantIService confService = (MbnConfigMerchantIService) SpringUtils
				.getBean("MbnConfigMerchantIService");
		MbnMerchantTunnelRelationService mtrService = (MbnMerchantTunnelRelationService) SpringUtils
				.getBean("mbnMerchantTunnelRelationService");
		SmsMbnTunnelService tunnelService = (SmsMbnTunnelService) SpringUtils
				.getBean("smsMbnTunnelService");
		MbnMerchantVipIService merchantService = (MbnMerchantVipIService) SpringUtils
				.getBean("MbnMerchantVipIService");
		UserService userService = (UserService) SpringUtils.getBean("userService"); 
		PortalUserExtService portalUserExtService = (PortalUserExtService) SpringUtils.getBean("portalUserExtService"); ;
		IMasMerchantService merchantInfoService = (IMasMerchantService) SpringUtils
				.getBean("masMerchantServiceImpl");
		MasPackage rspPackage = null;
		String nodeId = masPackage.getHead().getNodeId();
		String password = masPackage.getHead().getPassword();
		String reqMethod = masPackage.getHead().getMethodName();
		if (!checNodePass(nodeId, password)) {
			rspPackage = PackageUtils.buildPackage(nodeId, reqMethod, "",
					WebServiceConsts.NODE_VALID_FAIL_RETURN_CODE,
					WebServiceConsts.NODE_VALID_FAIL_RETURN_MESSAGE);
			return rspPackage;
		}
		if ("createEnt".equalsIgnoreCase(reqMethod)) {
			try{
				//创建企业
				EntInfo entInfo = masPackage.getBody().getEntInfoList().get(0);
				List<ConfigInfo> configInfoList = entInfo.getConfigInfoList();
				List<TunnelInfo> tunnelInfoList = entInfo.getTunnelInfoList();
		        
				MbnMerchantVip merchant = BeanConvUtils.convToMbnMerchantVip( entInfo );
				List<MbnConfigMerchant> merchantConfigs = new ArrayList<MbnConfigMerchant>();;
				
				if( configInfoList!=null && configInfoList.size()>0 ){
					for(ConfigInfo tempConfigInfo : configInfoList){
						MbnConfigMerchant mbnConfigs = BeanConvUtils.convToMbnConfigMerchant(tempConfigInfo);
						merchantConfigs.add(mbnConfigs);
					}
				}
				
				List<MasTunnel> tunnels = new ArrayList<MasTunnel>();
		        List<MbnMerchantTunnelRelation> relations = new ArrayList<MbnMerchantTunnelRelation>();
		        List<MbnMerchantConsume> consumes = new ArrayList<MbnMerchantConsume>();
				
		        if( tunnelInfoList!=null && tunnelInfoList.size()>0 ){
					for(TunnelInfo tempTunnelInfo : tunnelInfoList){
						MasTunnel masTunnel = BeanConvUtils.convToMasTunnel(tempTunnelInfo);
						tunnels.add(masTunnel);
						
						MbnMerchantTunnelRelation mbnMerTunnRel = new MbnMerchantTunnelRelation(PinGen.getBasePin(),merchant.getMerchantPin(),
								masTunnel.getId(), masTunnel.getAccessNumber(), null, null, 1, null );
						relations.add(mbnMerTunnRel);
						
						if(masTunnel.getClassify().equals(ApSmsConstants.SIM_MODEM_CLASSIFY)){
							MbnMerchantConsume mobileConsume = new MbnMerchantConsume();
							mobileConsume.setId(masTunnel.getId());
				            mobileConsume.setMerchantPin(merchant.getMerchantPin());
				            mobileConsume.setTunnelId(masTunnel.getId());
				            mobileConsume.setModifyTime(Calendar.getInstance().getTime());
				            mobileConsume.setRemainNumber(500L);
				            consumes.add(mobileConsume);
						}
					}
				}
		        merchantInfoService.createMerchantAndMerchantConfig(merchant, merchantConfigs);
		        tunnelService.addTunnelAndConsumeForMas(tunnels, consumes, relations, merchant);
		        rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
			}catch(Exception e){
				logger.error("createEnt fail.", e);
				rspPackage = PackageUtils.buildPackage("", "", "",
						WebServiceConsts.XML_PARSE_FAIL_RETURN_CODE,
						WebServiceConsts.XML_PARSE_FAIL_RETURN_MESSAGE);
			}
		} else if ("createEntAdmin".equalsIgnoreCase(reqMethod)) {
			try{
				//创建企业管理员
				rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
				List<AdminInfo> list = masPackage.getBody().getAdminInfoList();
				if( list != null){
					for(AdminInfo admin: list){
						// 构建用户信息
						UserVO userVo = BeanConvUtils.conv(admin);
						// 构建用户扩展信息
						PortalUserExtBean portalUserExt = BeanConvUtils.convToPortalUserExt(admin);
						Set<RoleVO> rSet = new HashSet<RoleVO>();
						RoleVO roleVO = new RoleVO();
						roleVO.setId(ApSmsConstants.UNION_CORP_ADMIN_ROLEID);
						roleVO.setCreateBy(userVo.getCreateBy());
						rSet.add(roleVO);
						userVo.setRoles(rSet);
						
						UserVO seekVo = new UserVO();
						seekVo.setId(userVo.getId());
						
						// 判断用户是否存在
						Users u = userService.validateUser(seekVo);
						if( u != null){
							userService.update(userVo);
							portalUserExtService.update(portalUserExt);
						}else{
							userService.addUserWebservice(userVo);
							portalUserExtService.save(portalUserExt);
						}
					}
				}
			}catch(Exception e){
				logger.error("createEnt fail.", e);
				rspPackage = PackageUtils.buildPackage("", "", "",
						WebServiceConsts.XML_PARSE_FAIL_RETURN_CODE,
						WebServiceConsts.XML_PARSE_FAIL_RETURN_MESSAGE);
			}
		} else if("syncEntInfo".equalsIgnoreCase(reqMethod)){
			try{
				//更新企业
				EntInfo entInfo = masPackage.getBody().getEntInfoList().get(0);
				List<ConfigInfo> configInfoList = entInfo.getConfigInfoList();
				List<TunnelInfo> tunnelInfoList = entInfo.getTunnelInfoList();
		        
				MbnMerchantVip merchant = BeanConvUtils.convToMbnMerchantVip( entInfo );
				List<MbnConfigMerchant> merchantConfigs = new ArrayList<MbnConfigMerchant>();
				
				if( configInfoList!=null && configInfoList.size()>0 ){
					for(ConfigInfo tempConfigInfo : configInfoList){
						MbnConfigMerchant mbnConfigs = BeanConvUtils.convToMbnConfigMerchant(tempConfigInfo);
						merchantConfigs.add(mbnConfigs);
					}
				}
				
				List<MasTunnel> tunnels = new ArrayList<MasTunnel>();
		        List<MbnMerchantConsume> consumes = new ArrayList<MbnMerchantConsume>();
				
		        if( tunnelInfoList!=null && tunnelInfoList.size()>0 ){
					for(TunnelInfo tempTunnelInfo : tunnelInfoList){
						MasTunnel masTunnel = BeanConvUtils.convToMasTunnel(tempTunnelInfo);
						tunnels.add(masTunnel);
						if(masTunnel.getClassify().equals(ApSmsConstants.SIM_MODEM_CLASSIFY)){
							MbnMerchantConsume mobileConsume = new MbnMerchantConsume();
							mobileConsume.setId(masTunnel.getId());
				            mobileConsume.setMerchantPin(merchant.getMerchantPin());
				            mobileConsume.setTunnelId(masTunnel.getId());
				            mobileConsume.setModifyTime(Calendar.getInstance().getTime());
				            mobileConsume.setRemainNumber(500L);
				            consumes.add(mobileConsume);
						}
					}
				}
		        merchantInfoService.updateMerchantAndMerchantConfig(merchant, merchantConfigs);
		        tunnelService.updateTunnelAndConsumeForMas(tunnels, consumes, merchant);
		        rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
			}catch(Exception e){
				logger.error("syncEntInfo fail.", e);
				rspPackage = PackageUtils.buildPackage("", "", "",
						WebServiceConsts.XML_PARSE_FAIL_RETURN_CODE,
						WebServiceConsts.XML_PARSE_FAIL_RETURN_MESSAGE);
			}		
		} else if ("ctrl".equalsIgnoreCase(reqMethod)) {
			//控制指令
			rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
			// sync* 
		} else if ("updateEntAdminExt".equalsIgnoreCase(reqMethod)) {
			try{
				//创建企业管理员
				rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
				List<AdminInfo> list = masPackage.getBody().getAdminInfoList();
				if( list != null){
					for(AdminInfo admin: list){
						PortalUserExtBean portalUserExt = BeanConvUtils.convToPortalUserExt(admin);
							portalUserExtService.update(portalUserExt);
					}
				}
			}catch(Exception e){
				logger.error("updateEntAdminExt fail.", e);
				rspPackage = PackageUtils.buildPackage("", "", "",
						WebServiceConsts.XML_PARSE_FAIL_RETURN_CODE,
						WebServiceConsts.XML_PARSE_FAIL_RETURN_MESSAGE);
			}
		}else {
			// 不支持的指令
			rspPackage = PackageUtils.buildPackage("", "", "",
					WebServiceConsts.UNSUPPORT_METHOD_RETURN_CODE,
					WebServiceConsts.UNSUPPORT_METHOD_RETURN_MESSAGE);
		}
		return rspPackage;
	}

	private boolean checNodePass(String nodeId, String password) {
		boolean result = false;
		String localPass = WebUtils.getPropertyByName("nodepass");
		String md5 = DigestUtils.md5Hex(localPass);
		if (md5.equalsIgnoreCase(password)) {
			result = true;
		}
		return result;
	}
}
