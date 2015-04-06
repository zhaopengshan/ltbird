/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.delegatemas.admin.security.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.service.IRegionService;
import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.PinGen; 
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.config.bean.MbnSevenHCode;
import com.leadtone.mas.bizplug.config.bean.MbnThreeHCode;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.security.action.BaseAction;
import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO; 
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsDraft;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsOperationClass;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySendContainer;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsTaskNumber;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.smssend.util.ContactsContainer;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantConsume;
import com.leadtone.mas.bizplug.tunnel.bean.MbnMerchantTunnelRelation;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnelVO;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.BizUtils;
import com.leadtone.mas.bizplug.util.LoginCheckUtil;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
import com.leadtone.mas.bizplug.util.ResourcesComparator;
import com.leadtone.mas.bizplug.util.SmsNumberArithmetic;
import com.leadtone.mas.bizplug.util.WebUtils;
import com.opensymphony.xwork2.ActionContext;

/**
 *
 * @author blueskybluesea
 */
@ParentPackage("json-default")
@Namespace(value = "/masLoginAction")
public class LoginAction extends BaseAction{
	
	@Resource
	private IRegionService iRegionService;
	private static Logger log = Logger.getLogger(LoginAction.class);
	private static final long serialVersionUID = 1L;
	private UserVO userVO;
	private List<Resources> resources;
	private String verifyCode;
	@Resource(name = "userService")
	private UserService userServiceImpl;
	//消息提示
	public  String message;
	//手机验证码
	public String mobileChecking;
	@Autowired
    private UserService userService;
	@Autowired
	private MbnConfigMerchantIService mbnConfigMerchantIService;
	
	/**
	 * 待发信息操作
	 */
	@Resource
	private MbnSmsReadySendService mbnSmsReadySendService;
	
	/**
	 * 商户操作
	 */
	@Resource
	private MbnMerchantVipIService mbnMerchantVipIService;
	
	/**
	 * 通道操作
	 */
	@Resource
	private SmsMbnTunnelService smsMbnTunnelService;
	@Autowired
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	@Autowired
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	@Autowired
	private MbnSevenHCodeService mbnSevenHCodeService;
	@Autowired
	private MbnThreeHCodeService mbnThreeHCodeService;
	@Autowired
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	@Autowired
	private MbnSmsOperationClassService mbnSmsOperationClassService;
	@Autowired
	private PortalUserExtService portalUserExtService;
	@Autowired
	private IRegionService regionService;
	private Long ydTunnel=0L;
//	private Long ltTunnel=0L;
//	private Long dxTunnel=0L;
	private Long tdTunnel=0L;
	private String corpAccessNumber;
	
	
	/**
	 * 先根据用户输入的用户名获取用户信息，然后再决定是否允许用户登录
	 * 
	 * @return
	 */
//	@SuppressWarnings("static-access")
	@Action(value = "login", results = {
			@Result(name = SUCCESS, type = "redirect", location = "/main.jsp"),
			@Result(name = ERROR, location = "/login.jsp") })
	public String login() {
		MasPasswordTool pwdTool = new MasPasswordTool();
 		try {
 			//验证码
			if (!StringUtils.endsWithIgnoreCase(
					this.getVerifyCode(),
					(String) ActionContext.getContext().getSession()
							.get("verifyCode"))) {
				this.getRequest().setAttribute("message", "验证码错误!");
				return ERROR;
			}
 			 
			//System.out.println(pwdTool.getDesString(userVO.getPassword(),userVO.getAccount()));
//			List<UserVO> users = userServiceImpl.validateLogin(userVO);
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("account", userVO.getAccount());
			paraMap.put("systype", 1);
			List<UserVO> users = userServiceImpl.validateLogin(paraMap);
			
			// 登录次数限制及验证
			if (users == null || users.isEmpty()) {
				this.getRequest().setAttribute("message", "用户名或密码错误!");
				return ERROR;
			} else if (!MasPasswordTool.getDesString(users.get(0).getPassword(),
					users.get(0).getAccount()).equals(userVO.getPassword())) {
				String message = "用户名或密码错误!";
				if (users.get(0).getLimitTryCount()!=0 
						&& LoginCheckUtil.isAccountlock(getSession(), users.get(0))) {
					UserVO uvo = users.get(0);
					uvo.setLockFlag(1);
					uvo.setPassword(pwdTool.getDesString(users.get(0).getPassword(),users.get(0).getAccount()));
					userServiceImpl.updateUser(uvo);
					message = "用户已被锁定，请联系管理员!";
				}
				this.getRequest().setAttribute("message", message);
				return ERROR;
			} else if (users.get(0).getLockFlag() == 1) {
				this.getRequest().setAttribute("message", "用户已被锁定，请联系管理员!");
				return ERROR;
			}
			if(users.get(0).getWebService()!=null)
				if(users.get(0).getWebService()!=com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_NO){
	                this.getRequest().setAttribute("message", "您当前的帐户不支持此登录方式!");
	                    return ERROR;
	            } 
			// 组织系统菜单
			UserVO sessinUser = users.get(0);
			Set<RoleVO> roleVOs = sessinUser.getRoles();
			Set<Resources> tempResources = null;
			for (RoleVO roleVO : roleVOs) {
				Set<Resources> parentResources = roleVO.getResources();
				if (tempResources == null) {
					tempResources = parentResources;
				} else {
					tempResources.addAll(parentResources);
					for (Resources resource : tempResources) {
						for (Resources undoResource : parentResources) {
							if (resource.getId() == undoResource.getId()) {
								resource.getSubResources().addAll(
										undoResource.getSubResources());
							}
						}
					}
				}
			}
			if (tempResources == null || tempResources.isEmpty()) {
				this.getRequest().setAttribute("message", "对不起,用户未被赋予访问系统权限!");
				return ERROR;
			}
			resources = new ArrayList<Resources>(tempResources);
			ResourcesComparator comparator = new ResourcesComparator();
			Collections.sort(resources, comparator);
                        List<Resources> subResources = null;
                        for(Resources tempResource : resources) {

                            subResources = new ArrayList<Resources>(tempResource.getSubResources());

                            Collections.sort(subResources,comparator);
                            tempResource.setSortedSubRes(subResources);
                        }
			ActionContext.getContext().getSession()
					.put(ApSmsConstants.SESSION_USER_INFO, sessinUser);
			ActionContext.getContext().getSession().put("resources", resources);
		} catch (Exception e) {
			e.printStackTrace();
			this.entityMap = new HashMap<String, Object>();
			entityMap.put("message", "用户名或密码错误!");
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 首登验证程序
	 * 
	 * @return
	 */
	@Action(value = "adminFirstloginUser", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String firstloginUser() {
		entityMap = new HashMap<String, Object>();
		
		List<UserVO> usersList = null;
		try {
//			usersList = userService.validateLogin(userVO); 
			Map<String,Object> paraMap = new HashMap<String,Object>();
			paraMap.put("account", userVO.getAccount());
			paraMap.put("systype", 1);
			usersList = userService.validateLogin(paraMap); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!StringUtils.endsWithIgnoreCase( this.getVerifyCode(), (String) ActionContext.getContext().getSession().get("verifyCode"))){
			entityMap.put("existFlag", false);
			entityMap.put("existMessage","验证码错误!");
			return SUCCESS;
		}
		ActionContext.getContext().getSession().remove("pwd_security_policy");
		// 登录次数限制及验证
		if (usersList==null || usersList.size()==0) {
			entityMap.put("existFlag", false);
			entityMap.put("existMessage", userVO.getAccount()+"用户不存在!");
			return SUCCESS;
		}else{
			
			if (!MasPasswordTool.getDesString(usersList.get(0).getPassword(),usersList.get(0).getAccount()).equals(userVO.getPassword())){
				entityMap.put("existFlag", false);
				entityMap.put("existMessage", "密码错误!");
				return SUCCESS;
			}
			
			entityMap.put("existFlag", true);
			String pas = MasPasswordTool.getEncString(userVO.getPassword(),
					userVO.getAccount());
			userVO.setPassword(pas);
			Users users = (Users) userService.validateUser(userVO);
			entityMap.put("users", users);
			// 首登是否检验
			if (users != null) {
				//IP地址锁定
//				MbnConfigMerchant ipLock = this.configMerchantValue(users, ApSmsConstants.IP_LIMIT_LOCK);
//				entityMap.put(ApSmsConstants.IP_LIMIT_LOCK, ipLock == null ? 2:ipLock.getValidFlag());
				//密码过期判断
				MbnConfigMerchant pwdModify = this.configMerchantValue(users,ApSmsConstants.PWD_MODIFY_PERIOD);
				if( pwdModify !=null && pwdModify.getValidFlag() == 1 ){
					int itemValue = Integer.parseInt( pwdModify.getItemValue() );
					Date endDate = users.getUpdateTime();//.getNextPwdModifyTime();
					Date curDate = new Date();
					if( endDate !=null && (curDate.getTime() - endDate.getTime())/(24*3600*1000)  >= itemValue ){
						entityMap.put("modifyPeriod", true);
						entityMap.put("modifyPeriodValue", pwdModify.getItemValue());
						ActionContext.getContext().getSession().put("pwd_security_policy", users.getId());
					}else{
						entityMap.put("modifyPeriod", false);
					}
				}else{
					//未设置 密码过期 天
					entityMap.put("modifyPeriod", false);
				}
//				entityMap.put("modifyPeriod", this.configMerchantValue(users,
//						ApSmsConstants.PWD_MODIFY_PERIOD));
				//首次登陆验证
				MbnConfigMerchant firstLogin = this.configMerchantValue(users, ApSmsConstants.FIRST_LOGIN_MODIFY_PWD_FLAG);
				if(firstLogin != null){
					entityMap.put("firstFlag", firstLogin.getValidFlag());
					ActionContext.getContext().getSession().put("pwd_security_policy", users.getId());
				}else{
					entityMap.put("firstFlag", 2);
				}
			}
//			entityMap.put("remoteIp", MasCommonFunction.getIpAddr(getRequest()));
			return SUCCESS;
		}
	}
	
	private MbnConfigMerchant configMerchantValue(Users users, String str) {
		MbnConfigMerchant mbnConfigMerchant = mbnConfigMerchantIService.loadByMerchantPin(users.getMerchantPin(), str);
		return mbnConfigMerchant;
	}
	
	 /**
     * 手机验证码
     * @return
     */
 	@Action(value = "checkingNumber", results = {
			@Result(type = "json", params = {"root","message","contentType","text/html"})})
	public String checkingNumber(){
		MbnSmsReadySend m=new MbnSmsReadySend();
		//验证码
		if (!StringUtils.equalsIgnoreCase(
				this.getVerifyCode(),
				(String) ActionContext.getContext().getSession()
						.get("verifyCode"))) {
			message= "图形验证码错误!";
			return SUCCESS;
		}
		//根据用户名称及密码取出用户对象
		if(userVO.getAccount()!=null && userVO.getPassword()!=null){
			Users users=null;
			try{
				//根据名称查询用户信息
//				 users=userService.getUserByAccount(userVO.getAccount());
				String portLock= WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.LOGINPORTLOCK);
				boolean bool = org.apache.commons.lang3.StringUtils.isBlank(portLock);
				if(!bool && portLock.equals(com.leadtone.mas.admin.common.ApSmsConstants.LOCK_FLAG_TRUE)){
					userVO.setCorpAccessNumber(corpAccessNumber);
				}
				userVO.setPassword(MasPasswordTool.getEncString(userVO.getPassword(),
						userVO.getAccount()));
				UserVO userTemp = new UserVO();
				userTemp.setAccount(userVO.getAccount());
				userTemp.setCorpAccessNumber(userVO.getCorpAccessNumber());
				List<UserVO> uservoList = userService.findByUsers(userTemp);
				if(uservoList==null||uservoList.size()==0){
					message= "帐号不存在,请输入有效帐号 ！";
					return SUCCESS;
				}
				users = uservoList.get(0);
				if(users.getActiveFlag()==0 || users.getLockFlag()==1){
					message= "此帐号被锁定,请联系管理员！";
					return SUCCESS;
				}
			}catch (Exception e) {
				e.printStackTrace();
				message= "帐号不存在,请输入有效帐号 ！";
				return SUCCESS;
			}
			
			//验证通过，执行短信发送
			if(users!=null && users.getPassword().equals(userVO.getPassword())){
				if(users.getLoginType()==1){
					message= "此帐户不需要短信验证服务！";
 					return SUCCESS;
				}
				//获取手机验证码
				String checkNumber=SmsNumberArithmetic.random(); 
				//缓存手机验证码
 				getSession().setAttribute(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER, checkNumber);
 				//设置此验证过期时间为17分钟
 				getSession().setMaxInactiveInterval(1020);
 				//根据商户pin码查询商户对象
 				//MbnMerchantVip mbnMerchantVip=null;
// 				try{
//	//			    mbnMerchantVip= mbnMerchantVipIService.loadByMerchantPin(users.getMerchantPin()); 
//	// 				if(mbnMerchantVip==null){
//	// 					message="验证发送失败，请重试 ！";
//	// 					return SUCCESS;
//	//				}
//	 				SmsMbnTunnel mbnTunnel=smsMbnTunnelService.getTunnelByMerchantPin(users.getMerchantPin());
//	 				//插入特服号
//	 				m.setSmsAccessNumber(mbnTunnel.getAccessNumber());
//	 				m.setSelfMobile(mbnTunnel.getAccessNumber()); 
//	 				//设置 ID
//	 				m.setId(PinGen.getSerialPin());
//	 				//设置发送时间 
//	 				m.setReadySendTime(new Date()); 
//	 				//配置省份编号
//					Region portalRegion=new Region();
//					portalRegion=iRegionService.findByProvinceId(Long.parseLong(users.getProvince()));
//					m.setProvince(portalRegion.getCode());
// 				}catch (Exception e) {
// 					e.printStackTrace();
// 					message= "验证发送失败，请重试 ！";
// 					return SUCCESS;
// 				}
// 				//通道类型:硬编码：2网关
// 				m.setTunnelType(ApSmsConstants.SMS_TUNNEL_TYPE_WG);
// 				//业务编号，0:不可见状态~
// 				m.setOperationId(0L);
//				//生成批次号
//				m.setBatchId(PinGen.getSerialPin());
//				//设置优先级
//				m.setPriorityLevel(5);	
//				//设置发送状态
//				m.setSendResult(0);
//				//用户pin码
//				m.setMerchantPin(users.getMerchantPin());
//				m.setContent("本次验证码是："+checkNumber);
//				m.setTitle("短信验证");
//				m.setTos(users.getMobile());
//				m.setTosName(users.getName());
//				m.setCommitTime(new Date());
//				m.setCutApartNumber(1000);
//				try {
//					mbnSmsReadySendService.insert(m);
//				} catch (Exception e) {
//					e.printStackTrace();
//					message= "验证发送失败，请重试 !";
//					return SUCCESS;
//				}
 				String content = "本次验证码是【"+checkNumber+"】";
 				try {
 					//查询移动通道
 	 				ydTunnel = BizUtils.getYdTunnelId(mbnMerchantTunnelRelationService, users.getMerchantPin());
 					// 查询企业省码
 					MbnMerchantVip merchant = mbnMerchantVipIService.loadByMerchantPin(users.getMerchantPin());
 					String prov = merchant.getProvince();
 					Region region = regionService.findByProvinceId(Long.parseLong(prov));
 					String provCode = region.getCode();
 					ActionContext.getContext().getSession().put("prov_code", provCode);
 					
 					Set<Contacts> userSet = new HashSet<Contacts>();
 	 				Contacts c = new Contacts(users.getMobile().trim(), users.getName());
 	 				userSet.add(c);
 	 				PortalUserExtBean userExt = portalUserExtService.getByPk(users.getId());
 	 				Date readySendTime = new Date();
 	 				long batchId = PinGen.getSerialPin();
 	 				// 获取业务优先级
 	 				MbnSmsOperationClass mbnSmsOperationClass 
 	 					= mbnSmsOperationClassService.findByCoding(ApSmsConstants.OPERATION_CODING_HD);
 	 				int priority = mbnSmsOperationClass.getPriorityLevel().intValue();
 	 				
 	 				if("true".endsWith(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.SMS_PRIORITY_FROM_USER))){
 	 					try{
 	 						priority = userExt.getSmsPriority();//get priory from userExt
 	 					} catch(Exception e){
 	 						logger.info("User sms priority :" + e.getMessage());
 	 					}
 	 				}
 					//设定是否为托管mas处理方式
 			        if(WebUtils.isHostMas()) {
 			            // 获取第三方通道ID
 			        	tdTunnel = BizUtils.getTdTunnelId(mbnMerchantTunnelRelationService, users.getMerchantPin());
 			            if( tdTunnel == null || tdTunnel <= 0){
 			                entityMap = new HashMap<String, Object>();
 			                entityMap.put("resultcode", "error" );
 			                entityMap.put("message", "短信通道未配置，请联系管理员！");
 			                this.getRequest().setAttribute("entityMap", entityMap);
 			                List<SmsMbnTunnelVO>  tunnelList = getTunnelList(users.getMerchantPin());
 			                this.getRequest().setAttribute("tunnelList", tunnelList);
 			                return SUCCESS;
 			            }
 			        }
 			        // 把用户按运营商分类
 					ContactsContainer container = new ContactsContainer();
 					for (Contacts info : userSet) {
 						MbnThreeHCode hcode = mbnThreeHCodeService.queryByBobilePrefix(StringUtil.getShortPrefix(info.getMobile()));
 						if( hcode != null){
 							container.addContacts(info, hcode.getCorp());
 						}
 					}
 					
 					if(WebUtils.isHostMas()) {
 			            String[] result = rebuildContainerForHostMas(users, container);
 			            if( Boolean.parseBoolean(result[0])){
 							// 检查成功
// 			            	String taskNumber = "";
// 			            	if( WebUtils.getExtCodeStyle() == com.leadtone.mas.admin.common.ApSmsConstants.OPERATION_EXT_CODE_TYPE){
 			            		// 根据业务类型生成扩展码
// 				                if( getReplyCode() != null && getReplyCode().length()>0){
// 				                    MbnSmsTaskNumber num = new MbnSmsTaskNumber();
// 				                    num.setId(PinGen.getSerialPin());
// 				                    num.setMerchantPin(loginUser.getMerchantPin());
// 				                    num.setOperationCoding(ApSmsConstants.OPERATION_CODING_HD);
// 				                    num.setBatchId(batchId);
// 				                    num.setTaskNumber(getReplyCode());
// 				                    num.setBeginTime(new Date());
// 				                    num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
// 				                    num.setState(1);
// 				                    taskNumber = num.getTaskNumber();
// 				                    mbnSmsTaskNumberService.addTaskNumber(num);
// 				                }
// 			            	}else{
 			            		// 根据用户来生成2位扩展码
// 				                taskNumber = mbnSmsTaskNumberService.getTaskNumber2(users.getMerchantPin(), users.getUserExtCode());
 								// 记录扩展码
 								MbnSmsTaskNumber num = new MbnSmsTaskNumber();
 								num.setId(PinGen.getSerialPin());
 								num.setMerchantPin(users.getMerchantPin());
 								num.setOperationCoding(users.getUserExtCode()); //使用用户的两位扩展
 								num.setBatchId(batchId);
// 								num.setTaskNumber(taskNumber);
 								num.setBeginTime(new Date());
 								num.setEndTime(SmsNumberArithmetic.reEndTime(new Date(), mbnSmsOperationClass.getExpireTime().intValue()));
 								num.setState(1);
// 								taskNumber = num.getTaskNumber();
// 								mbnSmsTaskNumberService.addTaskNumber(num);
// 			            	}
	 							MbnSmsReadySendContainer smsContainer = buildContainerForHostMas(
	 									users, container, batchId, readySendTime, priority,
	 									null, null, content);
	 							mbnSmsReadySendService.batchSave(smsContainer);
 			            }else{
 				            // 检查失败
 			            	message= "验证发送失败，请重试 !";
 							return SUCCESS;
 			            }
 					}else{
 						message= "非托管MAS,请核实后再试!";
						return SUCCESS;
 					}
				} catch (Exception e) {
					e.printStackTrace();
					message= "验证发送失败，请重试 !";
					return SUCCESS;
				}
			}else {
				message= "用户名或密码错误,请核实后再试!";
				return SUCCESS;
			}
		} 
		message= "验证发送成功，请注意查收 ！";
		return SUCCESS;
	} 
 	/**
	 * 构建待发送操作
	 * @param loginUser
	 * @param container
	 * @param batchId
	 * @param readySendTime
	 * @param priority
	 * @return
	 */
	private MbnSmsReadySendContainer buildContainerForHostMas(Users loginUser, ContactsContainer container, 
			Long batchId, Date readySendTime, Integer priority, List<String> colsList, String taskNumber, String content ){
		MbnSmsReadySendContainer smsContainer = new MbnSmsReadySendContainer();
		smsContainer.setMerchantPin(loginUser.getMerchantPin());
		// 构建移动通道短信列表
		if( container.getYdList() != null &&  container.getYdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getYdList(), batchId, readySendTime, priority, ydTunnel, colsList, taskNumber, content);
			smsContainer.addSmsMap(ydTunnel, msrsList);
		}
		// 构建联通通道短信列表
		if( container.getLtList() != null &&  container.getLtList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getLtList(), batchId, readySendTime, priority, tdTunnel, colsList, taskNumber, content);
			smsContainer.addSmsMap(tdTunnel, msrsList);
		}
		// 构建电信通道短信列表
		if( container.getDxList() != null &&  container.getDxList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getDxList(), batchId, readySendTime, priority, tdTunnel, colsList, taskNumber, content);
			smsContainer.addSmsMap(tdTunnel, msrsList);
		}
		// 构建电信通道短信列表
		if( container.getTdList() != null &&  container.getTdList().size() > 0){
			List<MbnSmsReadySend> msrsList = buildSmsList(loginUser, 
					container.getTdList(), batchId, readySendTime, priority, tdTunnel, colsList, taskNumber, content);
			smsContainer.addSmsMap(tdTunnel, msrsList);
		}
		
		return smsContainer;
	}
	/**
	 * 构建待发送短信列表
	 * @param loginUser
	 * @param list
	 * @param batchId
	 * @param readySendTime
	 * @param priority
	 * @param tunnelId
	 * @return
	 */
	private List<MbnSmsReadySend> buildSmsList(Users loginUser, List<Contacts> list, 
			Long batchId, Date readySendTime, Integer priority, Long tunnelId, List<String> colNameList, String taskNumber, String content){

		SmsMbnTunnelVO svo = null;
		MbnMerchantTunnelRelation mmtr = null;
		try {
			mmtr = mbnMerchantTunnelRelationService.findByPinAndTunnelId(loginUser.getMerchantPin(), tunnelId);
			svo = smsMbnTunnelService.queryByPk(tunnelId);
		} catch (Exception e) {
			log.error(e);
		}
		List<MbnSmsReadySend> msrsList = new ArrayList<MbnSmsReadySend>();
		MbnSmsOperationClass smsOperationClass = mbnSmsOperationClassService.findByCoding(ApSmsConstants.OPERATION_CODING_HD);
		for(Contacts info: list ){
			MbnSmsReadySend msrs = new MbnSmsReadySend();
			//PAN-Z-G 修改webservice信息
			if(loginUser.getWebService()!=com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_NO)
				msrs.setWebService(com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_YES);
			else {
				msrs.setWebService(com.leadtone.mas.admin.common.ApSmsConstants.USER_WEBSERVICE_NO);
			}
			
			msrs.setId(PinGen.getSerialPin());
			msrs.setMerchantPin(loginUser.getMerchantPin());
			msrs.setOperationId(smsOperationClass.getId());
			msrs.setBatchId(batchId);
			msrs.setTitle("短信验证码");
			msrs.setProvince(super.getSession().getAttribute("prov_code").toString());
			msrs.setTos(info.getMobile());
			msrs.setTosName(info.getName());
//			msrs.setTaskNumber(taskNumber);
//			setSmsText(content.replaceAll("\r", ""));
//			if(null != flag && "dynamic".equals(flag)){
//				// 如果是发送动态短信，则替换短信内容里的变量
//				String _smsText = smsText;
//				for(String colName : colNameList){
//					try{
//						_smsText = _smsText.replace("%"+ colName+ "%", info.get(colName));
//					}catch (Exception e) {
//						System.out.println("没有["+ colName+"]列");
//						continue;
//					}
//				}
//                if(svo.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_TD || svo.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_MODEM) {
//                    msrs.setContent(_smsText + (getReplyText()==null?"":getReplyText()) + (getEntSign()==null?"":getEntSign()));
//                } else {
//                    msrs.setContent(_smsText + (getReplyText()==null?"":getReplyText()));
//                }
//			} else{
                if(svo.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_TD || svo.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_MODEM
                	|| svo.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_ZXT|| svo.getClassify() == ApSmsConstants.TUNNEL_CLASSIFY_QXT) {
//                	msrs.setContent(_smsText + (getReplyText()==null?"":getReplyText()) + (getEntSign()==null?"":getEntSign()));
                	MbnConfigMerchant pwdModify = this.configMerchantValue(loginUser,ApSmsConstants.SMS_SIGN_CONTENT);
                	msrs.setContent(content+ pwdModify==null?"":pwdModify.getItemValue());
                } else {
                    msrs.setContent(content);
                }
//			}
			msrs.setCommitTime(new Date());
			msrs.setReadySendTime(readySendTime);
            msrs.setTunnelType(svo.getClassify());

            // 设置通道类型
            int tunnelType = svo.getClassify();
            
            if( tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_ZXT 
            		|| tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_TD 
            		|| tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_MODEM){
            	// 资信通, TD, MODEM
            	msrs.setSmsAccessNumber(loginUser.getZxtUserId());
            }
            else if( tunnelType == ApSmsConstants.TUNNEL_CLASSIFY_QXT){
            	// 企信通
            	msrs.setSmsAccessNumber(String.valueOf(loginUser.getZxtId()));
            }
            else{
            	//20130904 还需要增加 taskNumber
            	String accessNumber = BizUtils.buildAccessNumber(mmtr.getAccessNumber(), loginUser.getUserExtCode(), msrs.getTaskNumber());
            	msrs.setSmsAccessNumber(accessNumber);
            }
			msrs.setPriorityLevel(priority);
			msrs.setSendResult(0);
			msrs.setCreateBy(loginUser.getId());
			msrs.setSelfMobile(mmtr.getAccessNumber());
			msrs.setCutApartNumber(1000);
			msrsList.add(msrs);
		}
		
		return msrsList;
	}
 	/**
	 * 拖管MAS 重建发送集，非本省、联通、电信移到td通道
	 * @param loginUser
	 * @param container
	 * @return
	 */
	private String[] rebuildContainerForHostMas(Users loginUser, ContactsContainer container){
		String[] result = new String[]{Boolean.TRUE.toString(), "OK","OK","OK"};
		String provinceCode = "";
		SmsMbnTunnelVO svo = null;
		try {
			svo = smsMbnTunnelService.queryByPk(ydTunnel);
			provinceCode = svo.getProvince();
		} catch (Exception e) {
			logger.error("Find " + ydTunnel + " FAIL", e);
			// 20130904 增加错误处理
			result = new String[]{Boolean.FALSE.toString(), "OK","OK","OK"};
			return result;
		}
		
		if( container.getYdList().size() > 0){
			for( Contacts contacts: container.getYdList()){
				MbnSevenHCode code = mbnSevenHCodeService.queryByBobilePrefix(StringUtil.getLongPrefix(contacts.getMobile()));
				if( code == null || !provinceCode.equalsIgnoreCase(code.getProvinceCoding())){
					if(svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_YD 
							||svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_GLOBAL){
					//	container.addYdContacts(contacts);
					} else {
						container.addTdContacts(contacts);
					}
				}
			}
		}
		// 将猫池发送从YD列表中删除
		for( Contacts contacts: container.getTdList()){
			container.removeContacts(contacts, ApSmsConstants.YD_CODE);
		}
		// 联通移至TD列表
		if( container.getLtList().size() > 0){
			for( Contacts contacts: container.getLtList()){
				if(svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_GLOBAL){
					container.addYdContacts(contacts);
				} else{
					container.addTdContacts(contacts);
				}				
			}
		}
		container.setLtList(new ArrayList<Contacts>());
		// 电信移至TD列表
		if( container.getDxList().size() > 0){
			for( Contacts contacts: container.getDxList()){
				if(svo.getClassify()==ApSmsConstants.TUNNEL_CLASSIFY_GLOBAL){
					container.addYdContacts(contacts);
				} else{
					container.addTdContacts(contacts);
				}
			}
		}
		container.setDxList(new ArrayList<Contacts>());
		return result;
	}
 	/**
	 * 
	 * @param merchantPin
	 * @return
	 */
	private List<SmsMbnTunnelVO> getTunnelList(Long merchantPin){
        // 获取商户通道列表
		List<MbnMerchantTunnelRelation> relList = mbnMerchantTunnelRelationService.findByPin(merchantPin);
		List<SmsMbnTunnelVO>  tunnelList = new ArrayList<SmsMbnTunnelVO>();
		for( MbnMerchantTunnelRelation rel : relList){
			SmsMbnTunnelVO tvo = null;
			try {
				tvo = smsMbnTunnelService.queryByPk(rel.getTunnelId());
			} catch (Exception e) {
				// ignore
			}
			if( tvo != null){
				MbnMerchantConsume consume = mbnMerchantConsumeService.findByTunnelId(merchantPin, tvo.getId());
				if( consume!=null){
					tvo.setSmsNumber(consume.getRemainNumber());
				}else{
					tvo.setSmsNumber(0L);
				}
				tunnelList.add(tvo);
			}
		}
		return tunnelList;
	}
	
	public UserVO getUserVO() {
		return userVO;
	}
	public void setUserVO(UserVO userVO) {
		this.userVO = userVO;
	}
	 
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public String getMobileChecking() {
		return mobileChecking;
	}

	public void setMobileChecking(String mobileChecking) {
		this.mobileChecking = mobileChecking;
	}
	public String getVerifyCode() {
		return verifyCode;
	} 
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public IRegionService getiRegionService() {
		return iRegionService;
	}

	public void setiRegionService(IRegionService iRegionService) {
		this.iRegionService = iRegionService;
	}

	public String getCorpAccessNumber() {
		return corpAccessNumber;
	}

	public void setCorpAccessNumber(String corpAccessNumber) {
		this.corpAccessNumber = corpAccessNumber;
	} 
}
