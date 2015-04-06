package com.leadtone.mas.admin.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.leadtone.delegatemas.node.bean.MbnNode;
import com.leadtone.delegatemas.node.bean.MbnNodeMerchantRelation;
import com.leadtone.delegatemas.node.service.MbnNodeMerchantRelService;
import com.leadtone.delegatemas.node.service.MbnNodeService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.ConvertUtil;
import com.leadtone.mas.bizplug.util.Xml_Bean;
import com.leadtone.mas.bizplug.webservice.bean.ConfigInfo;
import com.leadtone.mas.bizplug.webservice.bean.EntInfo;
import com.leadtone.mas.bizplug.webservice.bean.MasBodyPackage;
import com.leadtone.mas.bizplug.webservice.bean.MasPackage;
import com.leadtone.mas.bizplug.webservice.bean.TunnelInfo;
import com.leadtone.mas.bizplug.webservice.util.BeanConvUtils;
import com.leadtone.mas.connector.utils.SpringUtils;

public class MgrWebServiceServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(MgrWebServiceServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -9062553092941114189L;

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
		// 检查请求命令
		// register,heartBeat,syncEntInfo,syncEntConfig,bizReport,countReport
		String reqXml = PackageUtils.getRequestXml(request);
		if (StringUtils.isBlank(reqXml)) {
			// 返回请求错误
			MasPackage rspPackage = PackageUtils.buildPackage("", "", "",
					WebServiceConsts.BAD_REQUEST_RETURN_CODE,
					WebServiceConsts.BAD_REQUEST_RETURN_MESSAGE);
			PackageUtils.writeRsp(response, rspPackage);
			return;
		}

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
		MbnNodeService nodeService = (MbnNodeService) SpringUtils
				.getBean("mbnNodeService");
		MbnNodeMerchantRelService nodeRelService = (MbnNodeMerchantRelService) SpringUtils
				.getBean("mbnNodeMerchantRelService");
		MbnConfigMerchantIService confService = (MbnConfigMerchantIService) SpringUtils
				.getBean("MbnConfigMerchantIService");
		MbnMerchantTunnelRelationService mtrService = (MbnMerchantTunnelRelationService) SpringUtils
				.getBean("mbnMerchantTunnelRelationService");
		SmsMbnTunnelService tunnelService = (SmsMbnTunnelService) SpringUtils
				.getBean("smsMbnTunnelService");
		MbnMerchantVipIService merchantService = (MbnMerchantVipIService) SpringUtils
				.getBean("MbnMerchantVipIService");

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
		if ("register".equalsIgnoreCase(reqMethod)) {
			// 更新node
			String webServiceUrl = masPackage.getBody().getRegister()
					.getWebServiceUrl();
			String ip = masPackage.getBody().getRegister().getIp();
			MbnNode node = new MbnNode();
			node.setId(Long.parseLong(nodeId));
			node.setWebServiceUrl(webServiceUrl);
			node.setIp(ip);
			node.setStatus(1);
			node.setUpdateTime(new Date());
			nodeService.update(node);
			rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
		} else if ("heartBeat".equalsIgnoreCase(reqMethod)) {
			// 心跳
			rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
		} else if ("syncEntInfo".equalsIgnoreCase(reqMethod)) {
			// 返回企业信息
			rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
			String entId = masPackage.getHead().getEntId();
			Long[] entIdArray = getEntIdArray(nodeRelService, nodeId, entId);
			List<EntInfo> entInfoList = new ArrayList<EntInfo>();

			for (Long id : entIdArray) {
				// 获取企业信息
				MbnMerchantVip merchant = merchantService.loadByMerchantPin(id);
				EntInfo entInfo = BeanConvUtils.conv(merchant);
				// 获取配置信息,获取通道信息
				List<ConfigInfo> configInfoList = new ArrayList<ConfigInfo>();
				List<TunnelInfo> tunnelInfoList = new ArrayList<TunnelInfo>();

				List<MbnConfigMerchant> confList = confService
						.queryMerchantConfigList(id);
				for (MbnConfigMerchant conf : confList) {
					configInfoList.add(BeanConvUtils.conv(conf));
				}
				List<MbnMerchantTunnelRelation> relList = mtrService
						.findByPin(id);
				if (relList != null) {
					for (MbnMerchantTunnelRelation rel : relList) {
						try {
							SmsMbnTunnelVO svo = tunnelService.queryByPk(rel
									.getTunnelId());
							tunnelInfoList.add(BeanConvUtils.conv(svo, rel
									.getMerchantPin()));
						} catch (Exception e) {
							logger.error("Get tunnel " + rel.getTunnelId()
									+ " fail.", e);
						}
					}
				}
				entInfo.setConfigInfoList(configInfoList);
				entInfo.setTunnelInfoList(tunnelInfoList);
				entInfoList.add(entInfo);
			}
			MasBodyPackage body = new MasBodyPackage();
			body.setEntInfoList(entInfoList);
			rspPackage.setBody(body);
		} else if ("syncEntConfig".equalsIgnoreCase(reqMethod)) {
			// 返回信息配置信息
			rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
			String entId = masPackage.getHead().getEntId();
			List<ConfigInfo> configInfoList = new ArrayList<ConfigInfo>();
			Long[] entIdArray = getEntIdArray(nodeRelService, nodeId, entId);
			for (Long id : entIdArray) {
				List<MbnConfigMerchant> confList = confService
						.queryMerchantConfigList(id);
				for (MbnConfigMerchant conf : confList) {
					configInfoList.add(BeanConvUtils.conv(conf));
				}
			}
			MasBodyPackage body = new MasBodyPackage();
			body.setConfigInfoList(configInfoList);
			rspPackage.setBody(body);
		} else if ("bizReport".equalsIgnoreCase(reqMethod)) {
			// TODO:
			rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
		} else if ("countReport".equalsIgnoreCase(reqMethod)) {
			// TODO:
			rspPackage = PackageUtils.buildSuccessPackage(nodeId, reqMethod);
		} else {
			// 不支持的指令
			rspPackage = PackageUtils.buildPackage("", "", "",
					WebServiceConsts.UNSUPPORT_METHOD_RETURN_CODE,
					WebServiceConsts.UNSUPPORT_METHOD_RETURN_MESSAGE);
		}
		return rspPackage;
	}

	private Long[] getEntIdArray(MbnNodeMerchantRelService nodeRelService,
			String nodeId, String entId) {
		Long[] entIdArray = null;
		if (StringUtils.isBlank(entId)) {
			// 获取所有商户
			List<MbnNodeMerchantRelation> merchantList = nodeRelService
					.getByNodeId(Long.parseLong(nodeId));
			List<Long> entList = new ArrayList<Long>();
			if (merchantList != null) {
				for (MbnNodeMerchantRelation merchant : merchantList) {
					entList.add(merchant.getMerchantPin());
				}
				entIdArray = entList.toArray(new Long[0]);
			}
		} else {
			String[] entStringArray = entId.split(",");
			entIdArray = ConvertUtil.arrStringToLong(entStringArray);
		}
		return entIdArray;
	}

	private boolean checNodePass(String nodeId, String password) {
		boolean result = false;
		MbnNodeService nodeService = (MbnNodeService) SpringUtils
				.getBean("mbnNodeService");
		MbnNode node = nodeService.getByPk(Long.parseLong(nodeId));
		if (node != null && password.equalsIgnoreCase(node.getPassword())) {
			result = true;
		}
		return result;
	}



}
