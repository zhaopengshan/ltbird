package com.leadtone.mas.admin.security.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.leadtone.delegatemas.admin.security.action.UserUtil;
import com.leadtone.delegatemas.node.bean.MbnNode;
import com.leadtone.delegatemas.node.bean.MbnNodeMerchantRelation;
import com.leadtone.delegatemas.node.service.MbnNodeMerchantRelService;
import com.leadtone.delegatemas.node.service.MbnNodeService;
import com.leadtone.delegatemas.security.bean.Region;
import com.leadtone.delegatemas.security.service.IRegionService;
import com.leadtone.mas.admin.common.ApSmsConstants;
import com.leadtone.mas.admin.common.ZXTUserTool;
import com.leadtone.mas.admin.util.ConvertUtil;
import com.leadtone.mas.admin.webservice.PackageUtils;
import com.leadtone.mas.admin.webservice.WebServiceConsts;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.bean.MbnMerchantVip;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.openaccount.service.MbnMerchantVipIService;
import com.leadtone.mas.bizplug.security.bean.PortalUserExtBean;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.tunnel.bean.SmsMbnTunnel;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;
import com.leadtone.mas.bizplug.util.HttpUtils;
import com.leadtone.mas.bizplug.util.MasPasswordTool;
import com.leadtone.mas.bizplug.util.WebUtils;
import com.leadtone.mas.bizplug.webservice.bean.AdminInfo;
import com.leadtone.mas.bizplug.webservice.bean.MasBodyPackage;
import com.leadtone.mas.bizplug.webservice.bean.MasHeadPackage;
import com.leadtone.mas.bizplug.webservice.bean.MasPackage;
import com.leadtone.mas.bizplug.webservice.util.BeanConvUtils;
import com.opensymphony.xwork2.ActionContext;
@SuppressWarnings("static-access")
@ParentPackage("json-default")
@Namespace(value = "/userAction")
public class UserAction extends BaseAction {
	//信息回馈采集
	public int message;
	
	private String flag; 
	// 用户实体类
	private UserVO portalUser;
	
	// 页面选择多个角色
	private String multiUserRoles;
	
	// 用户扩展信息
	private PortalUserExtBean portalUserExt;
	private Users userInfo;
	
	private Map<String, Object> entityMap = new HashMap<String, Object>();
	
	private static  Logger logger = Logger.getLogger(UserAction.class.getName());
	private HttpServletRequest request  = ServletActionContext.getRequest();
 
	@Resource
	private UserService userService; 
	@Resource
	private MbnMerchantVipIService MbnMerchantVipIService;
	@Resource
	private IRegionService regionService;
	@Resource
	private SmsMbnTunnelService smsMbnTunnelService;
	@Resource
	private PortalUserExtService portalUserExtService;
	@Resource
	private MbnNodeMerchantRelService mbnNodeMerchantRelService;
	@Resource
	private MbnNodeService mbnNodeService;
	@Autowired
	private MbnConfigMerchantIService mbnConfigMerchantIService;
	
	
	private static final long serialVersionUID = 1L;
	
	private Users users = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
	
	private String smsCheckContent;
	private String userPsw;
	private String userSmsCheckPhone;
	
	/**
	 * 解绑短信验证码
	 * @return
	 */
	@Action(value="smsCancelSetting" ,results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String smsCancelSetting(){
		try{
			entityMap = new HashMap<String, Object>();
			String smsCheckServer = (String) getSession().getAttribute(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);//.get(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);
			if(!smsCheckServer.equalsIgnoreCase(smsCheckContent)){
				entityMap.put("flag", false );
	            entityMap.put("resultMsg", "短信验证码错误！");
	            return SUCCESS;
			}
			if(!users.getPassword().equalsIgnoreCase(MasPasswordTool.getEncString(userPsw, users.getAccount()))){
				entityMap.put("flag", false );
	            entityMap.put("resultMsg", "管理员密码错误！");
	            return SUCCESS;
			}
			PortalUserExtBean tempUserExt = portalUserExtService.getByPk(portalUserExt.getId());
			tempUserExt.setSmsMobile("");
			portalUserExtService.update(tempUserExt);
			ActionContext.getContext().getSession().remove(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);
			// 地市管理员更新企业管理员，需要同步至业务节点 20130926
			if(users.getUserType() == ApSmsConstants.USER_TYPE_CITY_ADMIN){
				try{
					updateUserExtToNode(portalUser, tempUserExt );
				}catch(Exception e){
					logger.error("Add sms mobile to node faile", e);
				}
			}
			entityMap.put("flag", true );
            entityMap.put("resultMsg", "短信验证码解绑成功！");
		}catch (Exception e) {
			logger.error("delete user error", e);
		}
		
		return SUCCESS;
	}
	/**
	 * 设置短信验证码
	 * @return
	 */
	@Action(value="smsCheckSetting" ,results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String smsCheckSetting(){
		try{
			entityMap = new HashMap<String, Object>();
			String smsCheckServer = (String) getSession().getAttribute(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);//.get(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);
			if(!smsCheckServer.equalsIgnoreCase(smsCheckContent)){
				entityMap.put("flag", false );
	            entityMap.put("resultMsg", "短信验证码错误！");
	            return SUCCESS;
			}
//			UserVO sessionUser = (UserVO) getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
			if(!users.getPassword().equalsIgnoreCase(MasPasswordTool.getEncString(userPsw, users.getAccount()))){
				entityMap.put("flag", false );
	            entityMap.put("resultMsg", "管理员密码错误！");
	            return SUCCESS;
			}
			PortalUserExtBean tempUserExt = portalUserExtService.getByPk(portalUserExt.getId());
			tempUserExt.setSmsMobile(portalUserExt.getSmsMobile());
			portalUserExtService.update(tempUserExt);
			ActionContext.getContext().getSession().remove(ApSmsConstants.SESSION_SMS_CHECKING_NUMBER);
			// 地市管理员更新企业管理员，需要同步至业务节点 20130926
			if(users.getUserType() == ApSmsConstants.USER_TYPE_CITY_ADMIN){
				try{
					updateUserExtToNode(portalUser, tempUserExt);
				}catch(Exception e){
					logger.error("Add sms mobile to node faile", e);
				}
			}
			entityMap.put("flag", true );
            entityMap.put("resultMsg", "短信验证码绑定成功！");
		}catch (Exception e) {
			logger.error("delete user error", e);
		}
		
		return SUCCESS;
	}
	/**
	 * 用户管理短信验证设置，初始化数据
	 * @return
	 */
	@Action(value = "smsCheckSettingForward", results = { @Result(name = SUCCESS, location = "/ap/user/smsCheckDialog.jsp"),
			@Result(name = ERROR, location = "/error.jsp")})
	public String smsCheckSettingForward(){
		try{
			portalUserExt = portalUserExtService.getByPk(portalUser.getId());
//			List<Role> rolesList = userService.getAllRoles();
//			logger.info("qeury allRoles: " + rolesList);
//			request.setAttribute("rolesList", rolesList);
			// 查询用户列表
			//query();
		}catch (Exception e) {
			logger.error("query error, ", e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	@Action(value = "webserviceInfoForward", results = { @Result(name = SUCCESS, location = "/ap/user/userWebServiceInfoDialog.jsp"),
			@Result(name = ERROR, location = "/error.jsp")})
	public String webserviceInfoForward(){
		try{
			userInfo = userService.queryByUserId(portalUser.getId());
//			List<Role> rolesList = userService.getAllRoles();
//			logger.info("qeury allRoles: " + rolesList);
//			request.setAttribute("rolesList", rolesList);
			// 查询用户列表
			//query();
		}catch (Exception e) {
			logger.error("query error, ", e);
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 初始化新增页面
	 * @return
	 */
	@Action(value = "forward", results = {@Result(name = "forward", location = "/ap/user/useradd.jsp"),
			@Result(name = ERROR, location = "/error.jsp"),
			@Result(name = INPUT, location = "/ap/user/useradd.jsp")})
	public String forward(){
		try {
			List<Role> roleList = userService.getRolesByMerchantPin(users.getMerchantPin());
			// 初次进入新增页面，得初始化角色
			request.setAttribute("rolesList", roleList);
			
			// REX@20130111 判断管理员类型 0:超级管理员 1:省系统管理员；2：地市管理员3、企业超级管理员4、企业用户
			// 0返回省列表
			// 1返回省代码，地市列表
			// 2,3,4 返回省代码、地市代码
			int userType = users.getUserType();
			if(userType == ApSmsConstants.USER_TYPE_SUPER_ADMIN){
				List<Region> list = regionService.findProvinces();
				request.setAttribute("regionList", list);
			}else if(userType == ApSmsConstants.USER_TYPE_PROVINCE_ADMIN){
				Long prov =Long.parseLong(users.getProvince()) ;
				List<Region> list = regionService.findCityByProvinceId(prov);
				request.setAttribute("regionList", list);
				request.setAttribute("province", users.getProvince());
			}else if(userType == ApSmsConstants.USER_TYPE_CITY_ADMIN){//地市管理员。列出企业。
				Long prov =Long.parseLong(users.getProvince()) ;
				List<MbnMerchantVip> list = MbnMerchantVipIService.loadByProvinceAndCity( users.getProvince(),users.getCity());
				request.setAttribute("merchantList", list);
				request.setAttribute("province", users.getProvince());
				request.setAttribute("city", users.getCity());
//			}else if(userType == ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN){
//				request.setAttribute("entAdmin", true);
//				request.setAttribute("province", users.getProvince());
//				request.setAttribute("city", users.getCity());
			}else{
				MbnConfigMerchant mbnConfigMerchant = mbnConfigMerchantIService.loadByMerchantPin(users.getMerchantPin(), "corp_login_port");
				if( mbnConfigMerchant!=null ){
					request.setAttribute("corpLoginPort", mbnConfigMerchant.getItemValue());
				}
				request.setAttribute("province", users.getProvince());
				request.setAttribute("city", users.getCity());
			}
		
			logger.debug(" inser into useradd.jsp before -->flag: "+flag);
			if("addForward".equals(flag)){
				
				return "forward";
			}else if("updateForward".equals(flag)){
				request.setAttribute("allList", userService.getAllRoles());
				// 修改用户之前的初始化新增页面角色，查询要修改的用户信息
				List<UserVO> uList = userService.queryUserLikeAccount(portalUser);
				logger.info("query updateForward user: "+ portalUser);
				//将查询出来的用户，解密
				UserVO userVO = uList.get(0);
				if(userType == ApSmsConstants.USER_TYPE_CITY_ADMIN){
					MbnConfigMerchant mbnConfigMerchant = mbnConfigMerchantIService.loadByMerchantPin(userVO.getMerchantPin(), "corp_login_port");
					if(mbnConfigMerchant!=null){
						request.setAttribute("corpLoginPort", mbnConfigMerchant.getItemValue());
					}
				}
				userVO.setPassword(new MasPasswordTool()
					.getDesString(userVO.getPassword(), userVO.getAccount()));
				// 从全部角色里去掉此用户已经有的角色
				List<Role> delList = new ArrayList<Role>();
				//List<Long> longs = userVO.getRoles();
				Set<Role> userRoles = userVO.getRole();
				//logger.info("------userRoles-"+ userRoles);
				// 从所有的用户里删除此角色拥有的用户，待选 用户里显示
				for(Iterator<Role> uIterator = userRoles.iterator(); uIterator.hasNext();){
					Role _roleUser = uIterator.next();
					//logger.debug("; roleUsers: "+ _roleUser.getId()+"；");
					for(Role _role : roleList){
						if(_roleUser.getId().equals(_role.getId())){
							//logger.debug("into le.");
							delList.add(_role);
						}
					}	
				}
				roleList.removeAll(delList);
				// 增加扩展信息
				PortalUserExtBean bean = portalUserExtService.getByPk(userVO.getId());
				
				Users temp = userService.queryByUserId(userVO.getId());
				userVO.setWebService(temp.getWebService());
				request.setAttribute("portalUserExt", bean);
			
				// 初次进入新增页面，得初始化角色
				request.setAttribute("rolesList", roleList);
				request.setAttribute("userVO", userVO);
				return "forward";
			}
		
		} catch (Exception e) {
			logger.error("", e);
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 新增用户		
	 * @return
	 * @throws Exception
	 */
	@Action(value = "adduser", results = {  @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String adduser() {
		try{
			logger.info("add user: 	"+ portalUser);
			entityMap = new HashMap<String, Object>();
			Long merchantPin = users.getMerchantPin();
			portalUser.setLockFlag(ApSmsConstants.LOCK_FLAG_UNLOCKED);
			portalUser.setWebService(portalUser.getWebService()==null?1:portalUser.getWebService());
			int userType = users.getUserType();
			if(userType == ApSmsConstants.USER_TYPE_SUPER_ADMIN){
				// 超级管理员创建省管理员
				portalUser.setUserType(ApSmsConstants.USER_TYPE_PROVINCE_ADMIN);
				MbnMerchantVip vProvinceMerchant = MbnMerchantVipIService
						.loadVirtualProvinceMerchant(portalUser.getProvince(),
								ApSmsConstants.MERCHANT_PROVINCE_VIRTUAL_TYPE);
				// 判断省虚拟企业是否存在，不存在增加
				if(vProvinceMerchant  == null){
					MbnMerchantVip merchant = new MbnMerchantVip();
					merchantPin = PinGen.getMerchantPin();
					merchant.setProvince(portalUser.getProvince());
					merchant.setMerchantPin(merchantPin);
					merchant.setName("V_P_"+portalUser.getProvince());
					merchant.setGroupCode(String.valueOf( merchantPin));
					merchant.setPlatform(ApSmsConstants.MERCHANT_PROVINCE_VIRTUAL_TYPE);
					merchant.setCreateTime(new Date());
					boolean result = MbnMerchantVipIService.insertMerchant(merchant);
					logger.info("Province [" + portalUser.getProvince() + "] add virtual merchant [" + merchantPin +"] result:" + result);
				} else {
					merchantPin = vProvinceMerchant.getMerchantPin();
				}
			}
			else if(userType == ApSmsConstants.USER_TYPE_PROVINCE_ADMIN){
				// 省管理员创建市管理员
				portalUser.setUserType(ApSmsConstants.USER_TYPE_CITY_ADMIN);
                                MbnMerchantVip vCityMerchant=  MbnMerchantVipIService.loadVirtualProvinceMerchant(portalUser.getCity(),ApSmsConstants.MERCHANT_CITY_VIRTUAL_TYPE);
				// 判断地市虚拟企业是否存在，不存在增加
				if(vCityMerchant == null){
					MbnMerchantVip merchant = new MbnMerchantVip();
					merchantPin = PinGen.getMerchantPin();
					merchant.setProvince(portalUser.getProvince());
					merchant.setCity(portalUser.getCity());
					merchant.setMerchantPin(merchantPin);
					merchant.setName("V_C_" + portalUser.getCity());
					merchant.setGroupCode(String.valueOf( merchantPin));
					merchant.setPlatform(ApSmsConstants.MERCHANT_CITY_VIRTUAL_TYPE);
					merchant.setCreateTime(new Date());
					boolean result = MbnMerchantVipIService.insertMerchant(merchant);
					logger.info("Province [" + portalUser.getProvince() + "] City [" + portalUser.getCity() + "] " +
							"add virtual merchant [" + merchantPin +"] result:" + result);
				} else {
					merchantPin = vCityMerchant.getMerchantPin();
				}
			}else if(userType == ApSmsConstants.USER_TYPE_CITY_ADMIN){
				// 市管理员创建企业管理员
				portalUser.setUserType(ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN);
				merchantPin = portalUser.getMerchantPin();
			}else if(userType == ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN){
				// 企业管理员创建企业用户
				portalUser.setUserType(ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL);
			}else{
				// 默认企业用户
				portalUser.setUserType(ApSmsConstants.USER_TYPE_ENTERPRISE_NORMAL);
			}
			portalUser.setCreateBy(users.getId());
			// 设定是否为托管mas处理方式
			if (WebUtils.isHostMas()) {
				// 20130427 为用户随机生成zxtUserId
				String zxtUserId = getZxtUserId();
				portalUser.setZxtUserId(zxtUserId);
				portalUser.setMerchantPin(merchantPin);
			}
			Users existUser = userService.validateUser(portalUser);
			if (existUser != null) {
				entityMap.put("flag", "用户账号已存在,请重新填写");
				return SUCCESS;
			}
			// add wangyu
			portalUser.setMerchantPin(merchantPin);
			// 地市管理员创建用户分配角色硬编码
			Set<RoleVO> rSet = new HashSet<RoleVO>();
			if (userType == ApSmsConstants.USER_TYPE_CITY_ADMIN) {
				RoleVO roleVO = new RoleVO();
				roleVO.setId(ApSmsConstants.UNION_CORP_ADMIN_ROLEID);
				roleVO.setCreateBy(users.getId());
				rSet.add(roleVO);
			} else {
				String[] roles = multiUserRoles.split(", ");
				RoleVO roleVO = null;
				for (String _role : roles) {
					roleVO = new RoleVO();
					roleVO.setId(Long.parseLong(_role));
					roleVO.setCreateBy(users.getId());
					rSet.add(roleVO);
				}
			}
			portalUser.setRoles(rSet);
			//添加解锁~加锁
			isLockFlag(portalUser);
			portalUser.setFirstLoginFlag(0);
			if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.TUNNELALL))){
	        	String tunnelAll = WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.TUNNELALL);           
	        	if("true".equalsIgnoreCase(tunnelAll)){
	        		Integer corpId = MbnMerchantVipIService.getCorpZXTId(merchantPin);
	        		int userId = ZXTUserTool.addUser(portalUser.getAccount(), portalUser.getPassword(), corpId.toString(), portalUser.getZxtUserId());
	        		portalUser.setZxtId(userId);
	        		portalUser.setZxtLoginAcount(portalUser.getAccount());
	        		portalUser.setZxtPwd(portalUser.getPassword());
	        	}
			}
			portalUser.setId(PinGen.getSerialPin());
			if(StringUtil.isEmpty(portalUser.getZxtUserId())){
				//TODO
				portalUser.setZxtUserId(portalUser.getId().toString());
			}
			userService.addUser(portalUser);
			// 增加扩展信息
			portalUserExt.setId(portalUser.getId());
			portalUserExtService.save(portalUserExt);
			
			// 地市管理员创建企业管理员，需要同步至业务节点 20130926
			if(userType == ApSmsConstants.USER_TYPE_CITY_ADMIN){
				try{
					addUserToNode(portalUser, portalUserExt);
				}catch(Exception e){
					logger.error("Add user to node faile", e);
				}
			}

			entityMap.put("flag", SUCCESS);
			logger.info("add user success");
		}catch(Exception exception){
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", ERROR);
			logger.error("add user error: ", exception);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * Ajax 验证用户是否存在，页面右侧通讯录的查询用户
	 * @throws Exception
	 */
	
	@Action(value = "queryUserExist", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String queryUserExist(){
		try{
			logger.info("queryUserExist: flag->"+flag);
			if("add".equals(flag)){
				/*PAN-Z-G 添加当前登录的商户pin码，用于唯一性用户验证*/
				long merchantPin=users.getMerchantPin();
				// 新增页面验证用户
                //设定是否为托管mas处理方式
                if(WebUtils.isHostMas()) {
                    portalUser.setMerchantPin(merchantPin);
                }
				Users users = userService.validateUser(portalUser);	// cnt >0表示此用户已经存在，否则不存在
				if(users!=null){
					entityMap.put("flag", 1);
				}else{
					entityMap.put("flag", 0);
				}
				// 新增页面验证用户是否存在
				logger.info("queryUser: "+ entityMap);
			}else if("query".equals(flag)){
				// 新增页面右侧搜索框 
				portalUser.setMerchantPin(users.getMerchantPin());
				List<UserVO> uList = userService.queryUserLikeAccount(portalUser);
				List<UserVO> tList = new ArrayList<UserVO>();
				for(int i=0, len = uList.size(); i<len; i++){
					UserVO userVO = uList.get(i);
					userVO.setPassword(new MasPasswordTool()
						.getDesString(userVO.getPassword(), userVO.getAccount()));
					tList.add(userVO);
				}
				entityMap.put("users", tList);
				logger.info("query from add right address: "+ entityMap);
			}
			
		}catch(Exception e){
			logger.error("validate user erorr: "+ e);
		}
		return SUCCESS;
	}
	
	/**
	 * 初始化用户列表页面，查询右侧搜索框的角色列表
	 * 因为查询结果是以ajax显示的，所以这里单开一个方法
	 * @return
	 */
	@Action(value = "queryForward", results = { @Result(name = SUCCESS, location = "/ap/user/userlist.jsp"),
			@Result(name = ERROR, location = "/error.jsp")})
	public String queryForward(){
		
		try{
			List<Role> rolesList = userService.getAllRoles();
			logger.info("qeury allRoles: " + rolesList);
			request.setAttribute("rolesList", rolesList);
			// 查询用户列表
			//query();
		}catch (Exception e) {
			logger.error("query AllRols error, ", e);
			return ERROR;
		}
		return SUCCESS;
	}
	


	/**
	 * 查询用户信息<未完成的，分页列表>
	 * @return
	 * @throws Exception
	 */
	@Action(value = "query", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String query(){
		try{
			PageUtil pageUtil = new PageUtil();
			pageUtil.setStart(page);
			pageUtil.setPageSize(rows);
			pageUtil.setMerchantPin(users.getMerchantPin());
			if(portalUser != null){
				pageUtil.setAccount(portalUser.getAccount());
				pageUtil.setMobile(portalUser.getMobile());
				pageUtil.setEmail(portalUser.getEmail());
				//Integer activeFlag = 
				//		portalUser.getActiveFlag()== -99 ? null : portalUser.getActiveFlag();
				pageUtil.setActiveFlag(portalUser.getActiveFlag());
				String roleId = request.getParameter("roleId");
				if(roleId != null && !"-99".equals(roleId))
					pageUtil.setRoleId(Long.parseLong(roleId));
			}
			logger.info("role query portalUser:" + portalUser);
			logger.info("role query pageUtil:" + pageUtil);
			
			// REX@20130112  判断登陆用户类型
			if( users.getUserType() == ApSmsConstants.USER_TYPE_SUPER_ADMIN){
				//在省、地市 管理员的时候，不需要pin码
				pageUtil.setMerchantPin(null);
				String[] provArray = null;
				List<Region> regionList  = regionService.findProvinces();
				if( regionList != null){
					provArray = new String[regionList.size()];
					for( int i=0;i<regionList.size();i++){
						provArray[i] = String.valueOf(regionList.get(i).getId());
					}
				}
				// 增加全省列表
				pageUtil.setAreaRange(provArray);
				// 设置用户类型为省管理员
				pageUtil.setUserType(users.getUserType());
			}else if(users.getUserType() == ApSmsConstants.USER_TYPE_PROVINCE_ADMIN){
				//在省、地市 管理员的时候，不需要pin码
				pageUtil.setMerchantPin(null);
				Long provinceId = 0L;
				provinceId = Long.parseLong(users.getProvince());
				String[] cityArray = null;
				List<Region> regionList  = regionService.findCityByProvinceId(provinceId);
				if( regionList != null){
					cityArray = new String[regionList.size()];
					for( int i=0;i<regionList.size();i++){
						cityArray[i] = String.valueOf(regionList.get(i).getId());
					}
				}
				// 增加地区列表
				pageUtil.setAreaRange(cityArray);
				// 设置用户类型为地市管理员
				pageUtil.setUserType(users.getUserType());
			}else if(users.getUserType() == ApSmsConstants.USER_TYPE_CITY_ADMIN){
				pageUtil.setMerchantPin(null);
				// 增加地区列表
				pageUtil.setAreaRange(new String[]{users.getCity()});
				// 设置用户类型为企业管理员
				pageUtil.setUserType(users.getUserType());
 
			}else if(users.getUserType() == ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN){
				// 设置用户类型为企业管理员
				
				pageUtil.setUserType(users.getUserType());
 
			}else{
				pageUtil.setUserType(users.getUserType());
 
			}
			Page page = userService.page(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<UserVO> datas = (List<UserVO>) page.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", page.getRecords());
	            if( datas == null ){
	            	datas = new ArrayList<UserVO>();
	            }
	            entityMap.put("rows", datas);
	            entityMap.put("totalrecords", page.getTotal());
	            entityMap.put("currpage", page.getStart());
			}
			logger.info("query user page: " + entityMap);
        }catch(Exception e){
            e.printStackTrace();
            return ERROR;
        }
		return SUCCESS;
	}
	
	/**
	 * 修改用户锁定标识
	 * @return
	 */
	@Action(value="updateLockFlag", results={@Result(type = "json", params = {
			"root","entityMap","contentType","text/html" }) })
	public String updateLockFlag(){
		try{
			entityMap = new HashMap<String, Object>();
			// 多个id以逗号分隔
			String Ids = request.getParameter("userId");
			logger.info("update user's id: "+ Ids);
			String[] deleteIds =  Ids.split(",");
			if(0 <= Arrays.binarySearch(deleteIds, ""+ users.getId())){
				// 如果修改的包含自己，将自己的id从修改列表里清除
				List<String> list = new ArrayList<String>();
				for(String dId : deleteIds)
					list.add(dId);
				list.remove(""+ users.getId());
				deleteIds = (String[])list.toArray(new String[list.size()]);
			}
			Long[] deleteIdLongs = ConvertUtil.arrStringToLong(deleteIds);
			for(Long id : deleteIdLongs){
				System.out.println("update sql -->"+ id);
				UserVO userVO=new UserVO();
				userVO.setId(id);
				userVO.setLockFlag(0);
				userVO.setActiveFlag(1);
				userService.update(userVO);
			}
			entityMap.put("message", "解锁成功!");
			
		}catch (Exception e) {
			logger.error("update user error", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改用户
	 * @return
	 */
	@Action(value="updateUser", results={@Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String updateUser(){
		try{
			logger.debug("userRoles	:"+ multiUserRoles);
			logger.debug("begin update user "+ portalUser);
            //地市管理员编辑用户分配角色硬编码
            Set<RoleVO> rSet = new HashSet<RoleVO>();
            if(users.getUserType() == ApSmsConstants.USER_TYPE_CITY_ADMIN){
                RoleVO roleVO = new RoleVO();
                roleVO.setId(ApSmsConstants.UNION_CORP_ADMIN_ROLEID);
                roleVO.setCreateBy(users.getId());
                rSet.add(roleVO);
            } else {
                String[] roles = multiUserRoles.split(", ");			
                RoleVO roleVO = null;
                for(String _role: roles){
                        roleVO = new RoleVO();
                        roleVO.setId(Long.parseLong(_role));
                        roleVO.setCreateBy(users.getId());
                        rSet.add(roleVO);
                }
            }
			//添加解锁~加锁
			isLockFlag(portalUser);
			
			portalUser.setRoles(rSet);
			portalUser.setUpdateBy(users.getId());//这里是登录用户的ID;
			userService.updateUser(portalUser);
			
			// 更新用户扩展信息(不含发送条数,统计时间)
			portalUserExt.setId(portalUser.getId());
			portalUserExt.setSmsSendCount(null);
			portalUserExt.setCountTime(null);
			portalUserExtService.update(portalUserExt);

			// 地市管理员更新企业管理员，需要同步至业务节点 20130926
			 if(users.getUserType() == ApSmsConstants.USER_TYPE_CITY_ADMIN){
				 addUserToNode(portalUser, portalUserExt);
			 }
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", SUCCESS);
		}catch(Exception exception){
			entityMap = new HashMap<String, Object>() ;
			entityMap.put("flag", ERROR);
			logger.error("update user error", exception);
			return ERROR;
		}
		return SUCCESS;
	}
	public void isLockFlag(UserVO userVO){
		/*
		 * PAN-Z-G
		 * 当active_flag为锁定状态时（0），将锁定标识修改为1为锁定 
		 * */
		if(userVO!=null && userVO.getActiveFlag()==0){
			userVO.setLockFlag(1);
		}else if(userVO!=null && userVO.getActiveFlag()==1){
			userVO.setLockFlag(0);
		}
		
	}
	/**
	 * 删除用户(含删除多个用户情况)
	 * @return
	 */
	@Action(value="deleteUser" ,results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String deleteUser(){
		try{
			entityMap = new HashMap<String, Object>();
			// 多个id以逗号分隔
			String Ids = request.getParameter("userId");
			logger.info("delete user's id: "+ Ids);
			String[] deleteIds =  Ids.split(",");
			if(0 <= Arrays.binarySearch(deleteIds, ""+ users.getId())){
				// 如果删除的包含自己，将自己的id从删除列表里清除
				List<String> list = new ArrayList<String>();
				for(String dId : deleteIds)
					list.add(dId);
				list.remove(""+ users.getId());
				deleteIds = (String[])list.toArray(new String[list.size()]);
			}
			Long[] deleteIdLongs = ConvertUtil.arrStringToLong(deleteIds);
			for(Long id : deleteIdLongs){
				logger.info("delete portal_user  -->"+ id);
			}
			userService.deleteUser(deleteIdLongs);
			for( Long id: deleteIdLongs){
				logger.info("delete portal_user_ext -->"+ id);
				portalUserExtService.delete(id);
			}
		}catch (Exception e) {
			logger.error("delete user error", e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@Action(value = "updatePwdFirst",results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String updatePwdFirst(){
		try{
			// 从session里取出当前登录用户的密码;
			Long userId = (Long) ActionContext.getContext().getSession().get("pwd_security_policy");
			Users user = userService.queryByUserId(userId);
			String pagePwd = request.getParameter("pwd"); 
			pagePwd = new MasPasswordTool().getEncString(pagePwd, user.getAccount());
			if(!pagePwd.equals(user.getPassword())){
				entityMap.put("flag", "error");
				entityMap.put("message", "原密码错误！");
			}else{
				UserVO uservo = new UserVO();
				uservo.setId(userId);
				uservo.setFirstLoginFlag(1);
				uservo.setUpdateTime(new Date());
				uservo.setAccount(user.getAccount());
				uservo.setPassword(portalUser.getPassword());
				logger.info("update pwd: "+ uservo);
				userService.updatePwd(uservo);
				entityMap.put("flag", "success");
				entityMap.put("message", "密码修改成功!请重新登录系统!");
			}
			logger.info("validatePWD: entityMap->"+ entityMap);
		}catch(Exception e){
			logger.error("validate password error", e);
			entityMap.put("flag", "error");
			entityMap.put("message", "密码修改失败，请联系管理员");
		}
		return SUCCESS;
	}
	
	@Action(value="validatePwd" ,results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String validatePwd(){
		try{
			// 从session里取出当前登录用户的密码;
			String userAccount = users.getAccount(); // get value from session
			// 页面输入的原密码
			String pagePwd = request.getParameter("pwd"); 
			// 用登录账号加密
			pagePwd = new MasPasswordTool().getEncString(pagePwd, userAccount);	
			portalUser = new UserVO();
			portalUser.setAccount(userAccount);
			Users user = userService.validateUser(portalUser);
			if(user.getPassword().equals(pagePwd)){
				entityMap.put("flag", "success");
			}else{
				entityMap.put("flag", "error");
			}
			logger.info("validatePWD: entityMap->"+ entityMap);
		}catch(Exception e){
			logger.error("validate password error", e);
		}
		return SUCCESS;
	}
	/**
	 * 修改密码
	 * @return
	 */
	@Action(value = "updatePwd",results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String updatePwd(){
		try{
			portalUser.setUpdateBy(users.getId());
			//修改密码时，将首次登录标识改为1，即为已经登录过
			portalUser.setFirstLoginFlag(1);
			portalUser.setUpdateTime(new Date());
			logger.info("update pwd: "+ portalUser);
			userService.updatePwd(portalUser);
			entityMap.put("flag", "success");
		}catch (Exception e) {
			logger.error("update password error", e);
			entityMap.put("flag", "error");
		}
		return SUCCESS;
	}
	
	/**
	 * 检测接入号是否存在 
	 * @return
	 */
	@Action(value = "checkCorpAccessNumber", results = {  @Result(type = "json", params = {
			"root","message", "contentType", "text/html" }) })
	public String isCorpAccessNumber(){
			message=0;
		if(UserUtil.isCorpAccessNumber(userService,portalUser))
			message=1;
		  return SUCCESS;
	} 
	@Action(value="getSmTunnelByMerchantPin", results = {
			@Result(type = "json", params = {"root", "entityMap","contentType","text/html"})})
        public String getSmTunnelInfo(){
            entityMap = new HashMap<String, Object>();
//            SmsMbnTunnel tunnel = this.smsMbnTunnelService.getTunnelByMerchantPin(portalUser.getMerchantPin());
            MbnConfigMerchant mbnConfigMerchant = mbnConfigMerchantIService.loadByMerchantPin(portalUser.getMerchantPin(), "corp_login_port");
            entityMap.put("resultData", mbnConfigMerchant);
            return SUCCESS;
        }
	/**
	 * 随机生成zxtUserId，返回未使用项
	 * @return
	 */
	private String getZxtUserId(){
		while(true){
			String zxtUserId = UserUtil.getRandomZxtUserId();
			if( !userService.checkZxtUserIdInUse(zxtUserId)){
				return zxtUserId;
			}
			try{
				Thread.sleep(50);
			}catch(Exception e){
				// IGNORE
			}
		}
	}
	/**
	 * 
	 * @param userExtBean
	 * @return
	 */
	private boolean updateUserExtToNode( UserVO user, PortalUserExtBean userExtBean ){
		Long merchantPin = user.getMerchantPin();
		List<MbnNodeMerchantRelation> list = mbnNodeMerchantRelService.getByMerchantPin(merchantPin);
		if( list == null || list.size() == 0){
			return false;
		}
		MbnNodeMerchantRelation rel = list.get(0);
		MbnNode node = mbnNodeService.getByPk(rel.getNodeId());
		if( node.getUseWebService() == null || node.getUseWebService() == 0){
			logger.info("Node " + node.getName() + " do NOT use webservice.");
			return true;
		}
		String nodeServiceUrl = node.getWebServiceUrl();
	
		MasPackage masPack = new MasPackage();
		MasHeadPackage head = new MasHeadPackage();
		head.setNodeId(String.valueOf(node.getId()));
		head.setPassword(node.getPassword());
		head.setMethodName(WebServiceConsts.UPDATEENTADMINEXT_METHOD);
		
		AdminInfo adminInfo = BeanConvUtils.conv(user, userExtBean);
		List<AdminInfo> adminInfoList = new ArrayList<AdminInfo>();
		adminInfoList.add(adminInfo);
		
		MasBodyPackage body = new MasBodyPackage();
		body.setAdminInfoList(adminInfoList);
		
		masPack.setHead(head);
		masPack.setBody(body);
		masPack.setVersion("1.0");
		
		String xml = PackageUtils.getXml(masPack);
		if( StringUtils.isNotBlank(xml)){
			String resp = HttpUtils.sendRequest(nodeServiceUrl, xml, 30000);
			if( StringUtils.isNotBlank(resp)){
				MasPackage respPack = PackageUtils.getMasPackage(resp);
				if( respPack != null){
					logger.info("CreateEntAdmin result is " + respPack.getHead().getReturnCode());
					if( WebServiceConsts.OK_RETURN_CODE.equals(respPack.getHead().getReturnCode())){
						return true;
					}
				}
			}
		}
		return false;
	}
	/**
	 * 同步用户信息至对应节点
	 * @param user
	 * @param userExtBean
	 * @return
	 */
	private boolean addUserToNode(UserVO user, PortalUserExtBean userExtBean){
		Long merchantPin = user.getMerchantPin();
		List<MbnNodeMerchantRelation> list = mbnNodeMerchantRelService.getByMerchantPin(merchantPin);
		if( list == null || list.size() == 0){
			return false;
		}
		MbnNodeMerchantRelation rel = list.get(0);
		MbnNode node = mbnNodeService.getByPk(rel.getNodeId());
		if( node.getUseWebService() == null || node.getUseWebService() == 0){
			logger.info("Node " + node.getName() + " do NOT use webservice.");
			return true;
		}
		String nodeServiceUrl = node.getWebServiceUrl();
	
		MasPackage masPack = new MasPackage();
		MasHeadPackage head = new MasHeadPackage();
		head.setNodeId(String.valueOf(node.getId()));
		head.setPassword(node.getPassword());
		head.setMethodName(WebServiceConsts.CREATEENTADMIN_METHOD);
		
		AdminInfo adminInfo = BeanConvUtils.conv(user, userExtBean);
		List<AdminInfo> adminInfoList = new ArrayList<AdminInfo>();
		adminInfoList.add(adminInfo);
		
		MasBodyPackage body = new MasBodyPackage();
		body.setAdminInfoList(adminInfoList);
		
		masPack.setHead(head);
		masPack.setBody(body);
		masPack.setVersion("1.0");
		
		String xml = PackageUtils.getXml(masPack);
		if( StringUtils.isNotBlank(xml)){
			String resp = HttpUtils.sendRequest(nodeServiceUrl, xml, 30000);
			if( StringUtils.isNotBlank(resp)){
				MasPackage respPack = PackageUtils.getMasPackage(resp);
				if( respPack != null){
					logger.info("CreateEntAdmin result is " + respPack.getHead().getReturnCode());
					if( WebServiceConsts.OK_RETURN_CODE.equals(respPack.getHead().getReturnCode())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public UserVO getPortalUser() {
		return portalUser;
	}
	public void setPortalUser(UserVO portalUser) {
		this.portalUser = portalUser;
	}
	public Map<String, Object> getEntityMap() {
		return entityMap;
	}
	public void setEntityMap(Map<String, Object> entityMap) {
		this.entityMap = entityMap;
	}
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getMultiUserRoles() {
		return multiUserRoles;
	}
	public void setMultiUserRoles(String multiUserRoles) {
		this.multiUserRoles = multiUserRoles;
	}
	public int getMessage() {
		return message;
	}
	public void setMessage(int message) {
		this.message = message;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	public PortalUserExtBean getPortalUserExt() {
		return portalUserExt;
	}
	public void setPortalUserExt(PortalUserExtBean portalUserExt) {
		this.portalUserExt = portalUserExt;
	}
	public String getSmsCheckContent() {
		return smsCheckContent;
	}
	public void setSmsCheckContent(String smsCheckContent) {
		this.smsCheckContent = smsCheckContent;
	}
	public String getUserPsw() {
		return userPsw;
	}
	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}
	public String getUserSmsCheckPhone() {
		return userSmsCheckPhone;
	}
	public void setUserSmsCheckPhone(String userSmsCheckPhone) {
		this.userSmsCheckPhone = userSmsCheckPhone;
	}
	public Users getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(Users userInfo) {
		this.userInfo = userInfo;
	}
	
}
