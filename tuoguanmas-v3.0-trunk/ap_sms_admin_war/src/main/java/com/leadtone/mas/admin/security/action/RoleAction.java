package com.leadtone.mas.admin.security.action;

import com.leadtone.mas.bizplug.security.bean.UserVO;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.leadtone.mas.admin.common.ApSmsConstants;
import com.leadtone.mas.admin.util.ConvertUtil;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.security.bean.Resources;
import com.leadtone.mas.bizplug.security.bean.Role;
import com.leadtone.mas.bizplug.security.bean.RoleVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.IRoleService;
import com.leadtone.mas.bizplug.security.service.MenuService;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.opensymphony.xwork2.ActionContext;

@ParentPackage("json-default")
@Namespace(value = "/roleAction")
public class RoleAction extends BaseAction {

	// 角色实体类
	private RoleVO portalRole;
	// 新增页面选择多个授权资源
	private String multiResources;
	// 新增页面为角色分配多个用户
	private String[] multiUsers;
	private String flag ;
	private Map<String, Object> entityMap = new HashMap<String, Object>();
	
	private static  Logger logger = Logger.getLogger(RoleAction.class.getName());
	private HttpServletRequest request  = ServletActionContext.getRequest();
	@Resource
	private IRoleService roleService; 
	@Resource
	private MenuService menuService; 
	@Resource
	private UserService userService; 
	
	private static final long serialVersionUID = 1L;

	private Users loginUser = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
	/**
	 * 初始化新增角色，修改角色，授权页面
	 * 查询所有的用户和所有的资源
	 */
	@Action(value = "forward", results = { @Result(name = "forward", location = "/ap/role/roleadd.jsp"),
			@Result(name = "powerForward", location = "/ap/role/rolepower.jsp"),
			@Result(name = ERROR, location = "/error.jsp"),
			@Result(name = INPUT, location = "/ap/role/roleadd.jsp")})
	public String forward(){
		List<Resources> resources = menuService.findTopMenus();
		List<Users> userList = userService.getAllUser(loginUser.getMerchantPin());
		if("addForward".equals(flag)){
			// 初次进入新增页面，得初始化授权资源
			request.setAttribute("resourceList", resources);
			// 初始化用户列表
			request.setAttribute("userList", userList);
		}else if("updateForward".equals(flag)){
			// 修改角色前，查询角色信息，然后显示在修改(即新增)页面
			RoleVO roleVO = roleService.viewRoleInfo(portalRole.getId(), loginUser.getMerchantPin());
			Set<Users> roleUsers = roleVO.getUsers();
			if(roleUsers != null){
				List<Users> delList = new ArrayList<Users>();
				// 从所有的用户里删除此角色拥有的用户，待选 用户里显示
				for(Iterator<Users> uIterator = roleUsers.iterator(); uIterator.hasNext();){
					Users _roleUser = uIterator.next();
					for(Users _user : userList){
						if(_roleUser.getId().equals(_user.getId())){
							delList.add(_user);
						}
					}	
				}
				userList.removeAll(delList);
			}
			// 查询角色信息
			request.setAttribute("roleVO", roleVO);
			// 初次进入新增页面，得初始化授权资源
			request.setAttribute("resourceList", resources);
			// 初始化用户列表
			request.setAttribute("userList", userList);
			logger.debug("roleVO: " + roleVO);
		}else if("powerForward".equals(flag)){
			// 初次进入授权页面，得初始化授权资源
			request.setAttribute("resourceList", menuService.findTopMenus());
			RoleVO roleVO = roleService.viewRoleInfo(portalRole.getId(), loginUser.getMerchantPin());
			if(null == roleVO){
				roleVO = new RoleVO();
				roleVO.setId(portalRole.getId());
				roleVO.setName(portalRole.getName());
			}
			logger.debug("givePower: roleVO->"+roleVO);
			// 查询角色信息
			request.setAttribute("roleVO", roleVO);
			return "powerForward";
		}
		logger.debug("resourcesList-->"+ resources+"; userList->"+ userList);
		return "forward";
	}
	/**
	 * 解析新增/修改页面的参数 资源及用户相关的参数
	 */
	public void parseAddParam(String flag){
		logger.debug(" roleResources: 	"+ multiResources);
		logger.debug(" roleUsers: 	"+ multiUsers);
		logger.debug(" role: 	"+ portalRole);
		Set<Users> uSet = new HashSet<Users>()	;
		Set<Resources> rSet = new HashSet<Resources>()	;
		Users user = new Users();
                if(null == multiUsers) {
                    request.getParameterValues("multiUsers");
                }
		if(null != multiUsers){
			String[] users = multiUsers;
			for(String _user : users){
				user = new Users();
				// 拼接页面传来的以逗号分隔的用户列表
				user.setId(Long.parseLong(_user));
				//if("add".equals(flag))
					user.setCreateBy(loginUser.getId());
				//else
				//	user.setUpdateBy(loginUser.getId());	
				uSet.add(user);
			}
		}
		String[] resources = multiResources.split(", ");
		Resources resource = null;
		// 页面传来的授权资源
		for(String _resource : resources){
			resource = new Resources();
			resource.setId(Long.parseLong(_resource));
			//if("add".equals(flag))
				resource.setCreateBy(loginUser.getId());	// 当前登录用户的id;   ------- 当前登录用户，通过参数传到dao？？？？
			//else
			//	resource.set(loginUser.getId());	
			rSet.add(resource);
		}
		portalRole.setUsers(uSet);
		portalRole.setResources(rSet);
		logger.debug("uset: "+uSet);
		logger.debug("rset: "+rSet);
	}
	/**
	 * 新增用户		
	 * flag: 有三个取值    forward: 初次进入页面       update: 修改用户         save : 新增页面保存
	 * @return
	 * @throws Exception
	 */
	@Action(value = "addrole", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String addrole() {
		try{
			//String[] rolesStrings = request.getParameterValues("multiRoles");
			logger.info("add role's flag:"+ flag);
			/*if("forward".equals(flag)){
				addForward("add");
				return "forward";
			}*/
			portalRole.setCreateBy(loginUser.getId());
                        portalRole.setMerchantPin(loginUser.getMerchantPin());
			parseAddParam("add");
			roleService.createRoleInfo(portalRole);
			entityMap = new HashMap<String, Object>()	;
			entityMap.put("flag", "success");
			logger.info("add role success");
		}catch(Exception exception){
			logger.error("add role error: "+exception);
			entityMap = new HashMap<String, Object>()	;
			entityMap.put("flag", ERROR);
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * Ajax 验证用户是否存在，页面右侧通讯录的查询用户
	 * @throws Exception
	 */
	@Action(value = "queryRoleExist", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String queryRoleExist(){
		try{
			String roleName = request.getParameter("roleName");
			List<Role> roleList = roleService.queryRoleByNameMerchantPin(roleName,loginUser.getMerchantPin());	// cnt >0表示此用户已经存在，否则不存在
			entityMap.put("flag", roleList.size());
			// 新增页面验证用户是否存在
			logger.info("queryRole: "+ entityMap);
			
		}catch(Exception e){
			logger.error("validate user erorr: "+ e);
		}
		return SUCCESS;
	}
	/**
	 * 查询角色信息  分页查询
	 * @return
	 * @throws Exception
	 */
	@Action(value = "query", results = {@Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" })})
	public String query(){
		PageUtil pageUtil = new PageUtil();
		pageUtil.setStart(page);
		pageUtil.setPageSize(rows);
                pageUtil.setMerchantPin(loginUser.getMerchantPin());
		if(portalRole != null){
			pageUtil.setRoleName(URLDecoder.decode(portalRole.getName()));
			pageUtil.setRoleDesc(URLDecoder.decode(portalRole.getDescription()));
		}
		try{
			logger.info("role query portalRole:" + portalRole);
			logger.info("role query PageUtil:" + pageUtil);
			Page page = roleService.page(pageUtil);
			if( page != null ){
				@SuppressWarnings("unchecked")
				List<Role> datas = (List<Role>) page.getData();
				entityMap = new HashMap<String, Object>();
	            entityMap.put("total", page.getRecords());
	            if( datas == null ){
	            	datas = new ArrayList<Role>();
	            }
	            entityMap.put("rows", datas);
	            entityMap.put("totalrecords", page.getTotal());
	            entityMap.put("currpage", page.getStart());
			}
        }catch(Exception e){
            e.printStackTrace();
            return ERROR;
        }
		return SUCCESS;
	}
	
	/**
	 * 修改角色信息
	 * @return
	 */
	@Action(value = "updateRole", results = {@Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" })})
	public String updateRole(){
		try{
			logger.debug("begin update role "+ portalRole);
			parseAddParam("update");
			portalRole.setUpdateBy(loginUser.getId());//这里是登录用户的ID;
			//portalUser.setUpdateTime(new Date());  --- 在dao里赋值了
			roleService.updateRoleInfo(portalRole);
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", SUCCESS);
		}catch(Exception exception){
			logger.error("update role error", exception);
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", ERROR);
			return ERROR;
		}
		return SUCCESS;
	}
//	
	/**
	 * 删除用户(含删除多个用户情况)
	 * @return
	 */
	@Action(value="deleteRoles" , results = {@Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" })})
	public String deleteRoles(){
		try{
                        entityMap = new HashMap<String, Object>();
			String roleIds = request.getParameter("roleIds");
			logger.info("remove role roleIds: "+ roleIds);
			String[] deleteIds = roleIds.split(","); 
			Long[] dlLongs = ConvertUtil.arrStringToLong(deleteIds);
                        for(Long roleId : dlLongs) {
                            RoleVO tempRoleVO = roleService.viewRoleInfo(roleId, null);
                            if(tempRoleVO.getUsers() != null && tempRoleVO.getUsers().size() > 0) {
                                entityMap.put("resultInfo", "角色"+tempRoleVO.getName()+"下存在用户,不能删除角色!");
                                return SUCCESS;
                            }
                        }
			roleService.removeRoleInfo(dlLongs);
			
		}catch (Exception e) {
			logger.error("delete role error", e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 给角色授权
	 * @return
	 */
	@Action(value = "givePower", results = {@Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" })})
	public String givePower(){
		try{
			// 角色赋权保存
			String[] resources = multiResources.split(", ");
			Set<Resources> rSet = new HashSet<Resources>();
			Resources resource = new Resources();
			// 页面传来的授权资源
			for(String _resource : resources){
				resource = new Resources();
				resource.setId(Long.parseLong(_resource));
				resource.setCreateBy(loginUser.getId());	// 当前登录用户的id;   ------- 当前登录用户，通过参数传到dao？？？？
				rSet.add(resource);
			}
			portalRole.setResources(rSet);
			roleService.assignRolePrevilege(portalRole);	
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", SUCCESS);
		}catch (Exception e) {
			logger.error("assign role error, ", e); // TODO: handle exception
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", ERROR);
			return ERROR;
		}
		return SUCCESS;
	}
	public RoleVO getPortalRole() {
		return portalRole;
	}

	public void setPortalRole(RoleVO portalRole) {
		this.portalRole = portalRole;
	}

	public Map<String, Object> getEntityMap() {
		return entityMap;
	}

	public void setEntityMap(Map<String, Object> entityMap) {
		this.entityMap = entityMap;
	}
	
	public String[] getMultiUsers() {
		return multiUsers;
	}

	public void setMultiUsers(String[] multiUsers) {
		this.multiUsers = multiUsers;
	}
	public String getMultiResources() {
		return multiResources;
	}

	public void setMultiResources(String multiResources) {
		this.multiResources = multiResources;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
}
