package com.leadtone.mas.bizplug.webservice.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.leadtone.delegatemas.tunnel.bean.MasTunnel;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.webservice.bean.AdminInfo;
import com.leadtone.mas.bizplug.webservice.bean.ConfigInfo;
import com.leadtone.mas.bizplug.webservice.bean.EntInfo;
import com.leadtone.mas.bizplug.webservice.bean.TunnelInfo;

public class BeanConvUtils {
	/**
	 * 配置信息转换
	 * @param config
	 * @return
	 */
	public static ConfigInfo conv(final MbnConfigMerchant config){
		ConfigInfo conf = new ConfigInfo();
		conf.setEntId(config.getMerchantPin());
		conf.setName(config.getName());
		conf.setItemValue(config.getItemValue());
		conf.setDesc(config.getDescription());
		conf.setValidFlag(config.getValidFlag());
		
		return conf;
	}
	public static MbnConfigMerchant convToMbnConfigMerchant(final ConfigInfo config){
		MbnConfigMerchant conf = new MbnConfigMerchant();
		conf.setMerchantPin(config.getEntId());
		conf.setName(config.getName());
		conf.setItemValue(config.getItemValue());
		conf.setDescription(config.getDesc());
		conf.setValidFlag(config.getValidFlag());
		conf.setId(PinGen.getBasePin());
		return conf;
	}
	/**
	 * 企业信息转换
	 * @param merchant
	 * @param confList
	 * @param tunnelList
	 * @return
	 */
	public static EntInfo conv(final MbnMerchantVip merchant){
		EntInfo entInfo = new EntInfo();
		entInfo.setEntId(merchant.getMerchantPin());
		entInfo.setName(merchant.getName());
		entInfo.setProvince(merchant.getProvince());
		entInfo.setCity(merchant.getCity());
		entInfo.setRegion(merchant.getRegion());
		entInfo.setPlatform(merchant.getPlatform());
		entInfo.setCorpId(merchant.getCorpId());
		entInfo.setCorpExt(merchant.getCorpExt());
		return entInfo;
	}
	/**
	 * 企业信息转换
	 * @param merchant
	 * @param confList
	 * @param tunnelList
	 * @return
	 */
	public static EntInfo conv(final MbnMerchantVip merchant, 
			final List<MbnConfigMerchant> confList, final List<SmsMbnTunnel> tunnelList){
		EntInfo entInfo = new EntInfo();
		entInfo.setEntId(merchant.getMerchantPin());
		entInfo.setName(merchant.getName());
		entInfo.setProvince(merchant.getProvince());
		entInfo.setCity(merchant.getCity());
		entInfo.setRegion(merchant.getRegion());
		entInfo.setPlatform(merchant.getPlatform());
		entInfo.setCorpId(merchant.getCorpId());
		entInfo.setCorpExt(merchant.getCorpExt());
		
		List<ConfigInfo> confInfoList = new ArrayList<ConfigInfo>();
		if( confList != null){
			for( MbnConfigMerchant conf: confList){
				ConfigInfo confInfo = conv(conf);
				confInfoList.add(confInfo);
			}
		}
		entInfo.setConfigInfoList(confInfoList);
		
		List<TunnelInfo> tunnelInfoList = new ArrayList<TunnelInfo>();
		if( tunnelList != null){
			for( SmsMbnTunnel tunnel: tunnelList){
				TunnelInfo tunnelInfo = conv(tunnel, merchant.getMerchantPin());
				tunnelInfoList.add(tunnelInfo);
			}
		}
		entInfo.setTunnelInfoList(tunnelInfoList);
		return entInfo;
	}
	public static MbnMerchantVip convToMbnMerchantVip(final EntInfo entInfo){
		MbnMerchantVip merchant = new MbnMerchantVip(entInfo.getEntId(),entInfo.getName(),entInfo.getProvince(),entInfo.getCity(),
				entInfo.getPlatform(), entInfo.getCorpExt(),entInfo.getCorpId());
		return merchant;
	}
	/**
	 * 通道信息转换
	 * @param tunnel
	 * @param merchantPin
	 * @return
	 */
	public static TunnelInfo conv(final SmsMbnTunnel tunnel, final Long merchantPin){
		TunnelInfo tunnelInfo = new TunnelInfo();
		// TODO 需要增加ignore
		BeanUtils.copyProperties(tunnel, tunnelInfo);
		tunnelInfo.setMerchantPin(merchantPin);
		return tunnelInfo;
	}
	
	/**
	 * 通道信息转换
	 * @param tunnel
	 * @param merchantPin
	 * @return
	 */
	public static TunnelInfo conv(final MasTunnel tunnel, final Long merchantPin){
		TunnelInfo tunnelInfo = new TunnelInfo();
		// TODO 需要增加ignore
		BeanUtils.copyProperties(tunnel, tunnelInfo);
		tunnelInfo.setMerchantPin(merchantPin);
		return tunnelInfo;
	}
	public static MasTunnel convToMasTunnel(final TunnelInfo tunnelInfo){
		MasTunnel masTunnel = new MasTunnel(tunnelInfo);
		return masTunnel;
	}
	/**
	 * 管理员信息转换
	 * @param user
	 * @return
	 */
	public static AdminInfo conv(final Users user){
		AdminInfo adminInfo = new AdminInfo();
		// TODO 需要增加ignore
		BeanUtils.copyProperties(user, adminInfo);
		return adminInfo;
	}
	
	/**
	 * 管理员信息转换
	 * @param user
	 * @return
	 */
	public static AdminInfo conv(final Users user, final PortalUserExtBean userExt){
		AdminInfo adminInfo = new AdminInfo();
		// TODO 需要增加ignore
		BeanUtils.copyProperties(user, adminInfo);
		adminInfo.setSmsLimit(userExt.getSmsLimit());
		adminInfo.setSmsLimitPeriod(userExt.getSmsLimitPeriod());
		adminInfo.setSmsLimitCount(userExt.getSmsLimitCount());
		adminInfo.setSmsPriority(userExt.getSmsPriority());
		adminInfo.setSmsMobile(userExt.getSmsMobile());
		return adminInfo;
	}
	
	public static UserVO conv(final AdminInfo adminInfo){
		UserVO userVo = new UserVO();
		BeanUtils.copyProperties(adminInfo, userVo);
		return userVo;
	}
	public static PortalUserExtBean convToPortalUserExt(final AdminInfo adminInfo){
		PortalUserExtBean portalUserExt = new PortalUserExtBean();
		portalUserExt.setId(adminInfo.getId());
		portalUserExt.setSmsLimit(adminInfo.getSmsLimit());
		portalUserExt.setSmsLimitPeriod(adminInfo.getSmsLimitPeriod());
		portalUserExt.setSmsLimitCount(adminInfo.getSmsLimitCount());
		portalUserExt.setSmsPriority(adminInfo.getSmsPriority());
		portalUserExt.setSmsMobile(adminInfo.getSmsMobile());
		return portalUserExt;
	}
}
