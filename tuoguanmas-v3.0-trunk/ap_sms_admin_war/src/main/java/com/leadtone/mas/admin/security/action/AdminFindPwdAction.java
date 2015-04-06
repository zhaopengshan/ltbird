package com.leadtone.mas.admin.security.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.leadtone.delegatemas.admin.security.common.FindPwdTimeTask;
import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.service.IRegionService;
import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendContainer;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.BizUtils;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
import com.leadtone.mas.bizplug.util.SmsNumberArithmetic;

@ParentPackage("json-default")
@Namespace(value = "/adminFindPwdAction")
public class AdminFindPwdAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Log LOG = LogFactory.getLog(AdminFindPwdAction.class);
	// 用户信息
	private UserVO userVO;
	//短信验证码
	private String smsNumber; 
	private List<UserVO> users = new ArrayList<UserVO>();
	@Autowired
	private IRegionService iRegionService; 
	@Autowired
	private SmsMbnTunnelService smsMbnTunnelService;
	@Resource
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Autowired
	private UserService userService;
	@Autowired
	private MbnSmsReadySendService mbnSmsReadySendService;
	@Autowired
	private MbnThreeHCodeService mbnThreeHCodeService;
	@Autowired
	private MbnSevenHCodeService mbnSevenHCodeService;
	
	@Action(value = "userChelcking", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String userChelcking(){
		entityMap = new HashMap<String, Object>();
		try {
			users = userService.findByUsers(userVO);
			if (users != null && users.size() > 0 && users.get(0).getMobile().equals(userVO.getMobile())) {
				if(smsFindPwdSend(true)){
					entityMap.put("message", "信息验证码已发送到您的手机，请注意查收!");
					return SUCCESS;
				}else{
					entityMap.put("message", "验证码发送失败,请您联系管理员修改密码!");
					return SUCCESS;
				}
				
			} else {
				entityMap.put("message", "信息有误!");
			}
		} catch (Exception e) {
			entityMap.put("message", "系统繁忙!");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Action(value = "sendPwd", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String sendPwd(){
		entityMap = new HashMap<String, Object>();
		if(getSession().getAttribute( ApSmsConstants.SESSION_SMS_CHECKING_NUMBER)==null){
			entityMap.put("message", "请获取短信验证码!");
			return SUCCESS;
		}
		String sessionNum=getSession().getAttribute(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER).toString();
		if(!sessionNum.equals(getSmsNumber())){
			entityMap.put("message",  "短信验证码错误!");
			return SUCCESS;
		}
		users = userService.findByUsers(userVO);
		try {
			if (users != null && users.size() > 0) {
				if (users.get(0).getMobile().equals(userVO.getMobile())) { 
					smsFindPwdSend(false);
					entityMap.put("message", "信息验证成功，密码已发送到您的手机，请注意查收!");
					entityMap.put("success", "success");
					getSession().removeAttribute(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);
					return SUCCESS;
				}
			}  
				entityMap.put("message", "输入信息有误!");
				return SUCCESS;
		} catch (Exception e) {
			entityMap.put("message", "系统繁忙!");
			e.printStackTrace();
		}
		return SUCCESS;
	}
	//sendSms
	public boolean smsFindPwdSend(boolean isChecked){ 
			MbnSmsReadySend m = new MbnSmsReadySend();
			// 根据用户名称及密码取出用户对象
			String checkNumber = null;
			try {
				if(isChecked){
					checkNumber = SmsNumberArithmetic.random();// 获取手机验证码
					getSession().setAttribute( ApSmsConstants.SESSION_SMS_CHECKING_NUMBER,checkNumber);// 缓存手机验证码
					getSession().setMaxInactiveInterval(1020);// 设置此验证过期时间为17分钟
				}
				Long tunnelId = 0L;
				MbnThreeHCode hcode = mbnThreeHCodeService.queryByBobilePrefix(StringUtil.getShortPrefix(users.get(0).getMobile()));
				if( hcode!=null ){
					String vonderCode = hcode.getCorp();
					if(ApSmsConstants.YD_CODE.equalsIgnoreCase(vonderCode)){
						tunnelId = BizUtils.getYdTunnelId(mbnMerchantTunnelRelationService, users.get(0).getMerchantPin());
					}else if(ApSmsConstants.LT_CODE.equalsIgnoreCase(vonderCode)){
						tunnelId = BizUtils.getLtTunnelId(mbnMerchantTunnelRelationService, users.get(0).getMerchantPin());
					}else if(ApSmsConstants.DX_CODE.equalsIgnoreCase(vonderCode)){
						tunnelId = BizUtils.getDxTunnelId(mbnMerchantTunnelRelationService, users.get(0).getMerchantPin());
					}
				}
				if( tunnelId <= 0){
					tunnelId = BizUtils.getTdTunnelId(mbnMerchantTunnelRelationService, users.get(0).getMerchantPin());
				}
				SmsMbnTunnelVO svo = null;
				MbnMerchantTunnelRelation mmtr = null;
				try {
					mmtr = mbnMerchantTunnelRelationService.findByPinAndTunnelId(users.get(0).getMerchantPin(), tunnelId);
					svo = smsMbnTunnelService.queryByPk(tunnelId);
				} catch (Exception e) {
					LOG.error(e);
				}
				if (mmtr != null ) {
					m.setSelfMobile(mmtr.getAccessNumber());
					//福特 未 分配 用户扩展号，此处 直接取
			        m.setSmsAccessNumber(mmtr.getAccessNumber());
				}else{
					return false;
				}
				
				m.setId(PinGen.getSerialPin());
				m.setReadySendTime(new Date());
				Region portalRegion = new Region();// 配置省份编号
				portalRegion = iRegionService.findByProvinceId(Long.parseLong(users.get(0).getProvince()));
				m.setProvince(portalRegion.getCode());
				m.setTunnelType(svo.getClassify());
				m.setOperationId(1L);// 业务编号，0:不可见状态~
				m.setBatchId(PinGen.getSerialPin());// 生成批次号
				m.setPriorityLevel(5);// 设置优先级
				m.setSendResult(0);// 设置发送状态
				m.setMerchantPin(users.get(0).getMerchantPin());// 用户pin码
				if(isChecked){
					m.setContent("密码找回身份验证,验证码为：" + checkNumber);
					m.setTitle("密码找回验证");
				}else {
					String findPwd=MasPasswordTool.getDesString(users.get(0).getPassword(), users.get(0).getAccount());
					m.setContent("您已通过系统帐号验证,帐号'"+userVO.getAccount()+"'的密码为：" + findPwd);
					m.setTitle("发送密码");
				}
				m.setTos(users.get(0).getMobile());
				m.setTosName(users.get(0).getName());
				m.setCommitTime(new Date());
				m.setCutApartNumber(1000); 
				MbnSmsReadySendContainer smsContainer=new MbnSmsReadySendContainer();
				Map<Long, List<MbnSmsReadySend>> map=new HashMap<Long, List<MbnSmsReadySend>>();
				List<MbnSmsReadySend> mbnSmsReadySends=new ArrayList<MbnSmsReadySend>();
				mbnSmsReadySends.add(m);
				map.put(svo.getId(), mbnSmsReadySends);
				smsContainer.setMerchantPin(users.get(0).getMerchantPin()); 
				smsContainer.setSmsHashMap(map);
				mbnSmsReadySendService.batchSave(smsContainer);
				// 验证码超时设置
				if(isChecked){
					FindPwdTimeTask lTask = new FindPwdTimeTask();
					lTask.executeTime(lTask, 1000, 1000,"5", getSession());
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
	}
	public UserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}

	public String getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}

	 

}
