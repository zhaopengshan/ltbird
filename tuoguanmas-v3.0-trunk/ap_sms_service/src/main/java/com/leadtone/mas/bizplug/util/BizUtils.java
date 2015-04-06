package com.leadtone.mas.bizplug.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.service.WebUtils;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;

public class BizUtils {
	/**
	 * 获取移动通道ID
	 * 
	 * @param merchantPin
	 * @return
	 */
	public static Long getYdTunnelId(
			MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService,
			Long merchantPin) {
		// 依次获取本省、全网移动通道。
		List<MbnMerchantTunnelRelation> tdRelList = mbnMerchantTunnelRelationService
				.findByClassify(merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_SELF_YD);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		tdRelList = mbnMerchantTunnelRelationService.findByClassify(
				merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_YD);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		return 0L;
	}
	/**
	 * 获取联通通道ID
	 * 
	 * @param merchantPin
	 * @return
	 */
	public static Long getLtTunnelId(
			MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService,
			Long merchantPin) {
		// 依次获取本省、全网移动通道。
		List<MbnMerchantTunnelRelation> tdRelList = mbnMerchantTunnelRelationService
				.findByClassify(merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_LT);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		return 0L;
	}
	/**
	 * 获取电信通道ID
	 * 
	 * @param merchantPin
	 * @return
	 */
	public static Long getDxTunnelId(
			MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService,
			Long merchantPin) {
		// 依次获取本省、全网移动通道。
		List<MbnMerchantTunnelRelation> tdRelList = mbnMerchantTunnelRelationService
				.findByClassify(merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_DX);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		return 0L;
	}
	/**
	 * 获取第三方通道ID
	 * 
	 * @param merchantPin
	 * @return
	 */
	public static Long getTdTunnelId(
			MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService,
			Long merchantPin) {
		// 依次获取资信通、企信通、猫池、TD，找到为止
		List<MbnMerchantTunnelRelation> tdRelList = mbnMerchantTunnelRelationService
				.findByClassify(merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_EMPP);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		tdRelList = mbnMerchantTunnelRelationService
				.findByClassify(merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_YDYW);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		tdRelList = mbnMerchantTunnelRelationService
				.findByClassify(merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_QXT_NEW);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		tdRelList = mbnMerchantTunnelRelationService
				.findByClassify(merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_ZXT);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		tdRelList = mbnMerchantTunnelRelationService.findByClassify(
				merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_QXT);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		tdRelList = mbnMerchantTunnelRelationService.findByClassify(
				merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_MODEM);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		tdRelList = mbnMerchantTunnelRelationService.findByClassify(
				merchantPin, ApSmsConstants.TUNNEL_CLASSIFY_TD);
		if (tdRelList != null && tdRelList.size() > 0) {
			return tdRelList.get(0).getTunnelId();
		}
		return 0L;

	}

	/**
	 * 构建SmsAccessNumber字段
	 * 
	 * @param accessNumber
	 *            通道表中的接入码
	 * @param userExtCode
	 *            用户扩展字段
	 * @param taskNumber
	 *            任务标识字段
	 * @return
	 */
	public static String buildAccessNumber(String accessNumber, String userExtCode,
			String taskNumber) {
		StringBuilder builder = new StringBuilder();
		builder.append(accessNumber);
		if (StringUtils.isNotBlank(userExtCode)) {
			builder.append(userExtCode);
		}
		if (WebUtils.getExtCodeStyle() != ApSmsConstants.OPERATION_EXT_CODE_TYPE && StringUtils.isNotBlank(taskNumber)) {
			builder.append(taskNumber);
		}
		return builder.toString();
	}
	
	/**
	 * 根据商户，获取通道信息
	 * @param merchantPin
	 * @return
	 */
	public static SmsMbnTunnelVO getChinaMobileTunnel(MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService,
			SmsMbnTunnelService smsMbnTunnelService,
			Long merchantPin){
		SmsMbnTunnelVO svo = null;
		List<MbnMerchantTunnelRelation> list = mbnMerchantTunnelRelationService.findByClassifyAndType(merchantPin, 1, 1);
		if (list != null && list.size() > 0) {
			try {
				svo = smsMbnTunnelService.queryByPk(list.get(0).getTunnelId());
			} catch (Exception e) {
				svo = null;
			}
		}
		if( svo == null){
			list = mbnMerchantTunnelRelationService.findByClassifyAndType(merchantPin, 2, 1);
			if (list != null && list.size() > 0) {
				try {
					svo = smsMbnTunnelService.queryByPk(list.get(0).getTunnelId());
				} catch (Exception e) {
					svo = null;
				}
			}
		}
		if( svo == null){
			list = mbnMerchantTunnelRelationService.findByClassifyAndType(merchantPin, 7, 1);
			if (list != null && list.size() > 0) {
				try {
					svo = smsMbnTunnelService.queryByPk(list.get(0).getTunnelId());
				} catch (Exception e) {
					svo = null;
				}
			}
		}
		return svo;
	}
}
