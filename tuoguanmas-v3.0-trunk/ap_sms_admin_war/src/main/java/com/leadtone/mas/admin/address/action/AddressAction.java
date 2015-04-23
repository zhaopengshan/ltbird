package com.leadtone.mas.admin.address.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.leadtone.mas.admin.common.ApSmsConstants;
import com.leadtone.mas.admin.common.FileUploadUtil;
import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.admin.util.ConvertUtil;
import com.leadtone.mas.bizplug.addr.bean.Contact;
import com.leadtone.mas.bizplug.addr.bean.ContactOrderVO;
import com.leadtone.mas.bizplug.addr.bean.ContactVO;
import com.leadtone.mas.bizplug.addr.bean.Group;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.Page;
import com.leadtone.mas.bizplug.common.PageUtil;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.common.StringUtil;
import com.leadtone.mas.bizplug.common.ex.export.DataExport;
import com.leadtone.mas.bizplug.common.im.DataImport;
import com.leadtone.mas.bizplug.security.bean.UserVO;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.security.service.UserService;
import com.leadtone.mas.bizplug.util.WebUtils;

@ParentPackage("json-default")
@Namespace(value = "/address")
public class AddressAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource
	private ContactsService contactsService;
	@Resource
	private UserService userService; 
	// 用于文件上传
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
	// 导入导出列排序
	private ContactOrderVO contactOrderVO;
	private Group group;
	// checkbox接收批量删除的id
	private Long[] ids;
	// 判断联系人的操作动作：按搜索结果：searchResult, 按选中联系人： selected, 按全部联系人： all
	private String flag;

	// 页面传来的选中的联系人的id，以逗号分隔
	private String contactIds;
	private String groupIds;
	private Contact contact;
	private String originalName;// 原名称
    private String setGroupName;
    private String setGroupId;
	
	// 用于批量导入结果显示
	private String messageTotal;
	private String messageContent;
	private boolean resultflag;
	private List<String> resultContent;
	private HttpServletRequest request = ServletActionContext.getRequest();
	// 取出session
	private Users users = (Users)super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
	//要导入通讯录的表头
	private String[] captions;
	//是否 区分  私有组
	private boolean privateGroupOpen = true;
	private boolean superUser = false;
	/**
	 * 取得通讯录表头
	 * @return
	 */
	@Action(value = "importContactsSteps", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
    public String importContactsSteps() {
		entityMap = new HashMap<String, Object>();
        long start = System.currentTimeMillis();
        try {
            if (uploadFileName == null
                    || (!uploadFileName.endsWith(".xls") && !uploadFileName.endsWith(".xlsx") && !uploadFileName.endsWith(".csv"))) {
                entityMap.put("resultMsg", "上传文件格式错误，请下载客户资料模版 ！");
            } else {
                // 先要将文件上传至服务器，然后才能读取。因为input输入框对服务器是隐藏的
            	String[] fileItem = FileUploadUtil.upload(this.getRequest(),
    					upload, uploadFileName);
    			if (fileItem == null || fileItem.length != 3) {
    				entityMap.put("flag", false);
    				entityMap.put("resultMsg", "您没通讯组上传");
    				long end = System.currentTimeMillis();
    				logger.info("parse contact file,totally use " + (end - start)
    						+ " millseconds.");
    				return SUCCESS;
    			}
                String filePath = fileItem[0] + fileItem[1];
                //session.setAttribute("filePath", filePath);
                logger.info("==========================filePath====================================" + filePath);
                File file = new File(filePath);
                DataImport di = new DataImport(file);
                String[][] content = di.getFileContent(false);
                captions = content[0];
                entityMap.put("captions", captions);
                entityMap.put("flag", true);
                if (file.exists()) {
        			if (!file.delete()) {
        				logger.warn("删除临时文件 " + file.getAbsolutePath() + file.getName()
        						+ " 失败，请手动删除！");
        			}
        		}
            }
        } catch (Exception e) {
            e.printStackTrace();
            long end = System.currentTimeMillis();
            logger.info("error when parse contact file,totally use " + (end - start) + " millseconds.");
            return SUCCESS;
        }
        return SUCCESS;
    }
	/**
	 * 通过Excel批量导入通讯成员
	 * 
	 * @return
	 */
	@Action(value = "importContacts", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String importContacts() {
		Long merchantPin = users.getMerchantPin();
		entityMap = new HashMap<String, Object>();
		long start = System.currentTimeMillis();
		try {
			logger.info("导入文件开始");
			String[] fileItem = FileUploadUtil.upload(this.getRequest(),
					upload, uploadFileName);
			if (fileItem == null || fileItem.length != 3) {
				entityMap.put("flag", false);
				entityMap.put("resultMsg", "您没通讯组上传");
				long end = System.currentTimeMillis();
				logger.info("parse contact file,totally use " + (end - start)
						+ " millseconds.");
				return SUCCESS;
			}
			if(contactOrderVO.getGroup()==-1){
				entityMap.put("flag", false);
				entityMap.put("resultMsg", "unselectgroup");
				long end = System.currentTimeMillis();
				logger.info("parse contact file,totally use " + (end - start)
						+ " millseconds.");
				return SUCCESS;	
			}
			String filePath = fileItem[0] + fileItem[1];
			File file = new File(filePath);
			// Excel导出对象
			DataImport di = new DataImport(file);
			String[][] content = di.getFileContent(true);
			if (file.exists()) {
				if (!file.delete()) {
					logger.warn("删除临时文件 " + file.getAbsolutePath() + file.getName()
							+ " 失败，请手动删除！");
				}
			}
			
			boolean fillGroupFlag = true;
			for (int i = 0; i < content.length; i++) {
				String[] contactArray = content[i];
				String group = contactArray[contactOrderVO.getGroup()];
				if(group==null || "".equals(group.trim())){
					fillGroupFlag = false;
					break;
				}
			}
			if(!fillGroupFlag){
			    if(setGroupName!=null && !"".equals(setGroupName.trim())){
			    	//TODO
			    }else if(setGroupId!=null && !"".equals(setGroupId.trim())){
				    Group g = contactsService.loadGroupById(Long.parseLong(setGroupId));
				    setGroupName = g.getGroupName();
			    }else {
    				entityMap.put("flag", false);
    				entityMap.put("resultMsg", "groupnull");
    				long end = System.currentTimeMillis();
    				logger.info("parse contact file,totally use " + (end - start)
    						+ " millseconds.");
    				return SUCCESS;	
			    }
			}

			//通讯录 不区分 私有公共
			Long createBy = users.getId();
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN)) ){
				//createBy = null;
				privateGroupOpen = false;
			}else{
				if( users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
					superUser = true;
				}
			}

			this.contactsService.importContactFromExcel(content,
					contactOrderVO, merchantPin, entityMap, createBy, privateGroupOpen, superUser, setGroupName);

			long end = System.currentTimeMillis();
			logger.info("parse contact file,totally use " + (end - start)
					+ " millseconds.");
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			long end = System.currentTimeMillis();
			logger.info("批量导入失败：", e);
			logger.info("error when parse contact file,totally use "
					+ (end - start) + " millseconds.");
			messageTotal = "通讯录批量导入失败";
			entityMap.put("messageTotal", messageTotal);
			entityMap.put("flag", false);
			entityMap.put("messageContent", "");
			return SUCCESS;
		}
	}

	/**
	 * 跳转到批量导入结果显示页
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Action(value = "importContactsResult", results = { @Result(name = SUCCESS, location = "/ap/address/importContactsResult.jsp") })
	public String importContactsResult() {
		try {
			messageTotal = URLDecoder.decode(messageTotal, "UTF-8");
			messageContent = URLDecoder.decode(messageContent, "UTF-8");
			resultContent=Arrays.asList(messageContent.split(";"));
		} catch (UnsupportedEncodingException e) {
			logger.info("url转码异常:",e);
		}
		return SUCCESS;
	}

	/*
	 * 下载Excel模板
	 * 
	 * @return
	 * 
	 * public String downloadExcelTemplate(){
	 * 
	 * return SUCCESS; }
	 */
	/**
	 * 添加通讯录组
	 * 
	 * @return
	 */
	@Action(value = "insertGroup", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String insertGroup() {
		Long merchantPin = users.getMerchantPin();
		entityMap = new HashMap<String, Object>();
		boolean flag = false;
		group.setId(PinGen.getSerialPin());
		group.setBookId(merchantPin);
		group.setPid(-1L);
		Long createBy = users.getId();
		if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
				|| users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
			createBy = null;
		}
		Group g = this.contactsService.findGroupByNameAndPin(
				group.getGroupName(), merchantPin, null);
		if (g != null) {
			entityMap.put("flag", false);
			if(createBy!=null && !g.getCreateBy().toString().equals(createBy.toString())){
			    entityMap.put("resultMsg", "创建失败，该通讯录组在企业下已存在，请修改名称后重新创建！");
			}else{
				entityMap.put("resultMsg", "创建失败，该通讯录组已存在");
			}
			return SUCCESS;
		}
		//分组创建人，用于私有分组的设计2012.01.24
		group.setCreateBy(users.getId());
		
		flag = this.contactsService.addGroup(group);
		if (flag) {
			entityMap.put("flag", true);
			entityMap.put("resultMsg", "通讯录组创建成功");
		} else {
			entityMap.put("flag", false);
			entityMap.put("resultMsg", "通讯录组创建失败");
		}
		return SUCCESS;
	}

	/**
	 * 修改通讯录组
	 * 
	 * @return
	 */
	@Action(value = "updateGroup", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String updateGroup() {
		Long merchantPin = users.getMerchantPin();
		entityMap = new HashMap<String, Object>();
		boolean flag = false;
		group.setBookId(merchantPin);
		group.setPid(-1L);
		Long createBy = users.getId();
		//通讯录 不区分 私有公共
		if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
				|| users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
			createBy = group.getCreateBy();
		}
		Group g = this.contactsService.findGroupByNameAndPin(
				group.getGroupName(), merchantPin, createBy);
		if (g != null && !g.getGroupName().equals(originalName)) {
			entityMap.put("flag", false);
			entityMap.put("resultMsg", "通讯录组修改失败，该通讯录组已存在");
			return SUCCESS;
		}
		flag = this.contactsService.updateGroup(group);
		if (flag) {
			entityMap.put("flag", true);
			entityMap.put("resultMsg", "通讯录组修改成功");
		} else {
			entityMap.put("flag", false);
			entityMap.put("resultMsg", "通讯录组修改失败");
		}
		return SUCCESS;
	}

	/**
	 * 删除通讯录组
	 * 
	 * @return
	 */
	@Action(value = "deleteGroup", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String deleteGroup() {
		Long merchantPin = users.getMerchantPin();
		entityMap = new HashMap<String, Object>();
		List<Long> list = new ArrayList<Long>();
		Long temp = null;

		String[] tempIds = groupIds.split(",");
		ids = new Long[tempIds.length];
		for (int i = 0; i < tempIds.length; i++) {
			ids[i] = Long.parseLong(tempIds[i]);
		}
		int delCount = 0,remain=0;
		for (int i = 0; i < ids.length; i++) {
			temp = ids[i];
			List l = this.contactsService.checkGroup(temp, merchantPin);
			if (l.size() > 0) {
				// entityMap.put("flag", false);//应该有个提示
				// entityMap.put("resultMsg", "该通讯录组下有联系人，不能删除");
				remain++;
				continue;
			} else {
				delCount++;
				list.add(temp);
			}
		}
		boolean flag = false;
		if (list.size() > 0) {
			flag = this.contactsService.removeBatchGroup(list);
			if (flag) {
				entityMap.put("flag", true);
				entityMap.put("resultMsg", delCount+"个组删除成功,"+remain+"个组存在联系人不能删除！");
			} else {
				entityMap.put("flag", false);
				entityMap.put("resultMsg", "通讯录组删除失败");
			}
		} else {
			entityMap.put("flag", false);
			entityMap.put("resultMsg", delCount+"个组删除成功,"+remain+"个组存在联系人不能删除！");
		}
		return SUCCESS;
	}

	/**
	 * 显示通讯录组列表
	 * 
	 * @return
	 */
	@Action(value = "showGroupList", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String showGroupList() {
		try {
			Long pinId = users.getMerchantPin();
			PageUtil pageUtil = new PageUtil();
			pageUtil.setStart(page);
			pageUtil.setPageSize(rows);
			pageUtil.setMerchantPin(pinId);
			//#是否支持私有组属性
			if( WebUtils.getPropertyByName(ApSmsConstants.PRIVATEGROUPOPEN).equals(ApSmsConstants.LOCK_FLAG_TRUE)
					&& users.getUserType() != ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				pageUtil.setCreateBy(users.getId());
			}
			logger.info("query group pageUtil:" + pageUtil);
			Page page = contactsService.pageGroup(pageUtil);
			if (page != null) {
				@SuppressWarnings("unchecked")
				List<Group> datas = (List<Group>) page.getData();
				entityMap = new HashMap<String, Object>();
				entityMap.put("total", page.getRecords());
				if (datas == null) {
					datas = new ArrayList<Group>();
				}
				entityMap.put("rows", datas);
				entityMap.put("totalrecords", page.getTotal());
				entityMap.put("currpage", page.getStart());
			}

			logger.info("query group page: " + entityMap);
		} catch (Exception e) {
			logger.error("query group error ", e);
			return ERROR;
		}
		return SUCCESS;
	}

	@Action(value = "queryGroup", results = {
			@Result(name = SUCCESS, location = "/ap/address/addressDialog.jsp"),
			@Result(name = ERROR, location = "/error.jsp") })
	public String queryGroup() {

		try {
			Long pinId = users.getMerchantPin();
			Long createBy = users.getId();
			PageUtil pageUtil = new PageUtil();
			pageUtil.setMerchantPin(pinId);
			//#是否支持私有组属性
			if( Boolean.valueOf( WebUtils.getPropertyByName(ApSmsConstants.PRIVATEGROUPOPEN))){
				pageUtil.setCreateBy( createBy );
				privateGroupOpen = true;
			}
			Page page = contactsService.pageGroup(pageUtil);
			if (page != null) {
				@SuppressWarnings("unchecked")
				List<Group> groupsList = (List<Group>) page.getData();
				if (groupsList == null) {
					groupsList = new ArrayList<Group>();
				}
				logger.debug("qeury groupsList: " + groupsList);
				request.setAttribute("groupsList", groupsList);
			}
		} catch (Exception e) {
			logger.error("query AllGroups error, ", e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	@Action(value = "getGroupDate", results = {@Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" })})
	public String getGroupDate() {
		try {
			Long pinId = users.getMerchantPin();
			Long createBy = users.getId();
			PageUtil pageUtil = new PageUtil();
			pageUtil.setMerchantPin(pinId);
			//#是否支持私有组属性
			if( Boolean.valueOf( WebUtils.getPropertyByName(ApSmsConstants.PRIVATEGROUPOPEN))
					&& users.getUserType() != ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				pageUtil.setCreateBy( createBy );
				privateGroupOpen = false;
			}
			Page page = contactsService.pageGroup(pageUtil);
			if (page != null) {
				@SuppressWarnings("unchecked")
				List<Group> groupsList = (List<Group>) page.getData();
				if (groupsList == null) {
					groupsList = new ArrayList<Group>();
				}
				logger.debug("qeury groupsList: " + groupsList);
				entityMap=new HashMap<String,Object>();
				entityMap.put("groupsList", groupsList);
			}
		} catch (Exception e) {
			logger.error("query AllGroups error, ", e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	@Action(value = "queryForward", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String queryForward() {
		try {
			Long pinId = users.getMerchantPin();
			Long createBy = users.getId();
			//通讯录 不区分 私有公共
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))){
				createBy = null;
			}
            List<Group> groupsList = null;
            if(Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))) {
                PageUtil pageUtil = new PageUtil();
                pageUtil.setMerchantPin(pinId);
                pageUtil.setCreateBy( createBy );
                Page page = contactsService.pageGroup(pageUtil);
                if (page != null) {
					groupsList = (List<Group>) page.getData();
                }
            }else{
            	groupsList = contactsService.getAllGroup(pinId, createBy);
            }
            if (groupsList == null) {
				groupsList = new ArrayList<Group>();
			}	
            List<ContactVO> contactVOs = null;
            if(users.getUserType() == ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN) {
            	contactVOs = contactsService.countGroupContacts(pinId,null);
            }else{
            	contactVOs = contactsService.countGroupContacts(pinId,createBy);
            }
			logger.debug("qeury groupsList: " + groupsList);
			entityMap=new HashMap<String,Object>();
			entityMap.put("countGroupContacts", contactVOs);
			entityMap.put("groupsList", groupsList);
		} catch (Exception e) {
			logger.error("query AllGroups error, ", e);
			return ERROR;
		}
		return SUCCESS;
	}

	@Action(value = "query", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String query() {
		try {
			Long pinId = users.getMerchantPin();
			PageUtil pageUtil = new PageUtil();
			pageUtil.setStart(page);
			pageUtil.setPageSize(rows);
			pageUtil.setMerchantPin(pinId);
			Long createBy = users.getId();
			//通讯录 不区分 私有公共
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
					|| users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				createBy = null;
			}
			pageUtil.setCreateBy(createBy);
			if (contact != null) {
				if (null != contact.getName())
					pageUtil.setAccount(URLDecoder.decode(contact.getName()));
				pageUtil.setMobile(contact.getMobile());
				//添加全部联系人情况0
				pageUtil.setBatchId( (contact.getBookGroupId() == null || contact.getBookGroupId() == 0L ) ? null : contact.getBookGroupId() );
			}
			logger.info("concat query concat:" + contact);
			logger.debug("concat query pageUtil:" + pageUtil);

			Page page = contactsService.page(pageUtil);
			if (page != null) {
				@SuppressWarnings("unchecked")
				List<ContactVO> datas = (List<ContactVO>) page.getData();
				entityMap = new HashMap<String, Object>();
				entityMap.put("total", page.getRecords());
				if (datas == null) {
					datas = new ArrayList<ContactVO>();
				}
				entityMap.put("rows", datas);
				entityMap.put("totalrecords", page.getTotal());
				entityMap.put("currpage", page.getStart());
				entityMap.put("dbTotal", contactsService.countContact(pinId,createBy));
			}

			logger.info("query concat page: " + entityMap);
		} catch (Exception e) {
			logger.error("query concat error ", e);
			return ERROR;
		}
		return SUCCESS;
	}

	@Action(value = "addContact", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String addContact() {
		try {
			Long pinId = users.getMerchantPin();
			logger.info("receive add concat param:" + contact);
			contact.setBookId(pinId); // 测试用，此值为商户pin码，从session里取得
			contact.setCreateBy(users.getId());
			if (null == contact.getMerchantBlackFlag())
				contact.setMerchantBlackFlag(1); // 默认为1：商户白名单(默认1)
			if( contact.getBookGroupId() == null ){
				entityMap = new HashMap<String, Object>();
				entityMap.put("flag", ERROR);
				entityMap.put("message", "没有指定联系人分组，新增联系人失败，请重试!");
				return SUCCESS;
			}
			contactsService.addContact(contact);
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", SUCCESS);
			Long createBy = users.getId();
			//通讯录 不区分 私有公共
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
					|| users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				createBy = null;
			}
			List<ContactVO> contactVOs = contactsService
					.countGroupContacts(pinId,createBy);
			entityMap.put("countGroupContacts", contactVOs);
		} catch (Exception e) {
			logger.error("add contact error", e);
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", ERROR);
			entityMap.put("message", "新增联系人失败，请稍后重试");
			// return ERROR;
		}
		return SUCCESS;
	}

	@Action(value = "deleteContact", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String deleteContact() {
		try {
			Long pinId = users.getMerchantPin();
			Long createBy = users.getId();
			//通讯录 不区分 私有公共
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
					|| users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				createBy = null;
			}
			if ("searchResult".equals(flag)) {
				// 删除搜索结果的联系人
				logger.info("delete contact by searched results: " + contact);
				contact.setBookId(pinId);
				contact.setCreateBy(createBy);
				contactsService.deleteContact(contact);

			} else if ("selected".equals(flag)) {
				// 删除选中的联系人 多个id以逗号分隔
				// String Ids = request.getParameter("contactIds");
				logger.info("delete contact by seleted contacts: " + contactIds);
				String[] deleteIds = contactIds.split(",");
				Long[] deleteIdLongs = ConvertUtil.arrStringToLong(deleteIds);
				contactsService.deleteContact(deleteIdLongs, pinId);

			} else if ("all".equals(flag)) {
				// 删除全部联系人

				// 因为页面传来的值可能含用户输入的条件，这里把条件置空
				contact = new Contact();
				contact.setName("");
				contact.setMobile("");
				contact.setBookGroupId(null);
				contact.setBookId(pinId);
				contact.setCreateBy(createBy);
				logger.info("delete all contacts: " + contact);
				contactsService.deleteContact(contact);
			}
			
			List<ContactVO> contactVOs = contactsService
					.countGroupContacts(pinId,createBy);

			entityMap = new HashMap<String, Object>();
			entityMap.put("countGroupContacts", contactVOs);
			entityMap.put("flag", SUCCESS);
		} catch (Exception e) {
			logger.error("delete contact error", e);
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", "删除用户失败，请稍后重试");
		}
		return SUCCESS;
	}

	@Action(value = "updateContact", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String updateContact() {
		try {

			logger.debug("update contact param: " + contact);
			Long pinId = users.getMerchantPin();

			contact.setBookId(pinId);
			entityMap = new HashMap<String, Object>();
			Long createBy = users.getId();
			Long groupId = contact.getBookGroupId();
			//通讯录 不区分 私有公共
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
					|| users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				createBy = null;
				groupId = null;
			}else{
				Long contactCreateBy = contact.getCreateBy();
				if( Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN)) && !createBy.equals(contactCreateBy) ){
					entityMap.put("flag", ERROR);
					entityMap.put("message", "您无权进行此操作，更新联系人失败！");
					return SUCCESS;
				}
			}
			List<Contact> tContact = contactsService.checkContactByMobile(
					contact.getMobile(), pinId, createBy, groupId);
			if ( tContact!=null && tContact.size()>0 ) {
				Contact tempContact = tContact.get(0);
				long dbContactId = tempContact.getId();
				long updateContactId = contact.getId();
				if(dbContactId != updateContactId){
					entityMap.put("flag", ERROR);
					entityMap.put("message", "手机号已存在，更新联系人失败！");
					return SUCCESS;
				}
			}
			
			if (null == contact.getMerchantBlackFlag())
				contact.setMerchantBlackFlag(1); // 默认为1：商户白名单(默认1)
			contactsService.updateContact(contact);
			List<ContactVO> contactVOs = contactsService
					.countGroupContacts(pinId,createBy);

			
			entityMap.put("countGroupContacts", contactVOs);
			entityMap.put("flag", SUCCESS);
		} catch (Exception e) {
			logger.error("update contact error", e);
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", ERROR);
			entityMap.put("message", "更新联系人失败，请稍后重试");
		}
		return SUCCESS;
	}

	@Action(value = "moveContact", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String moveContact() {
		try {
			Long pinId = users.getMerchantPin();
			// String Ids = request.getParameter("contactIds");
			Long groupId = Long.parseLong(request.getParameter("groupId"));
			Long createBy = users.getId();
			Group groupDetail = contactsService.loadGroupById(groupId);
			entityMap = new HashMap<String, Object>();
			if( groupDetail != null ){
				Long groupCreateBy = groupDetail.getCreateBy();
				//通讯录 不区分 私有公共
				if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN)) ){
					privateGroupOpen = false;
					createBy = null;
				}else{
					if( users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
						superUser = true;
					}
				}
				if( users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
					createBy = null;
				}
				logger.debug("move contact param: contactIds->" + contactIds
						+ "; groupId:" + groupId);
				String[] deleteIds = contactIds.split(",");
	
				Long[] deleteIdLongs = ConvertUtil.arrStringToLong(deleteIds);
				contactsService.batchUpdateContactGroup(entityMap, deleteIdLongs, groupId,
						pinId, groupCreateBy, privateGroupOpen, superUser, users.getId());
				List<ContactVO> contactVOs = contactsService
						.countGroupContacts(pinId,createBy);
				entityMap.put("countGroupContacts", contactVOs);
				entityMap.put("flag", SUCCESS);
			}else{
				entityMap.put("flag", ERROR);
			}
		} catch (Exception e) {
			logger.error("move contact error", e);
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", ERROR);
		}
		return SUCCESS;

	}

	@Action(value = "exportContact", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String exportContact() {
		try {
			Long pinId = users.getMerchantPin();
			Long createBy = users.getId();
			List<Long> createBys = new ArrayList<Long>();
			//通讯录 不区分 私有公共
			if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))
					|| users.getUserType() == com.leadtone.mas.admin.common.ApSmsConstants.USER_TYPE_ENTERPRISE_ADMIN ){
				createBys = null;
			}else{
				createBys.add(createBy);
				UserVO userVO = new UserVO();
				userVO.setMerchantPin(pinId);
				userVO.setUserType(3);
				Users userTemp = userService.validateUser(userVO);
				if(userTemp!=null){
					createBys.add(userTemp.getId());
				}
			}
			List<ContactVO> contactVOs = new ArrayList<ContactVO>();
			if(contact!=null&&!StringUtil.isEmpty(contact.getName())){
				contact.setName(URLDecoder.decode(contact.getName(), "UTF-8"));
			}
			if ("searchResult".equals(flag)) {
				// 导出搜索结果的联系人
				logger.info("export contact by searched results: " + contact);
				contact.setBookId(pinId);
				contactVOs = contactsService.exportContact(contact,createBys);
			} else if ("selected".equals(flag)) {
				// 导出选中的联系人 多个id以逗号分隔

				// String Ids = request.getParameter("contactIds");
				logger.info("export contact by seleted contacts: " + contactIds);
				String[] exportIds = contactIds.split(",");
				Long[] exportIdLongs = ConvertUtil.arrStringToLong(exportIds);
				contactVOs = contactsService
						.exportContact(exportIdLongs, pinId);
			} else if ("all".equals(flag)) {
				// 导出全部联系人
				logger.info("export all contacts: " + contact);
				// 因为页面传来的值可能含用户输入的条件，这里把条件置空
				contact = new Contact();
				contact.setName("");
				contact.setMobile("");
				contact.setBookGroupId(null);
				contact.setBookId(pinId);
				contactVOs = contactsService.exportContact(contact,createBys);
			}

			entityMap = new HashMap<String, Object>();
			String downLoadPath = exportToExcel(contactVOs);
			logger.debug("--------------------" + downLoadPath);
			entityMap.put("fileName", downLoadPath);
			entityMap.put("flag", SUCCESS);
		} catch (Exception e) {
			logger.error("export contact error", e);
			entityMap = new HashMap<String, Object>();
			entityMap.put("flag", ERROR);
		}
		return SUCCESS;
	}

	/**
	 * 查询在某个组里是否已经存在 此手机号
	 * 
	 * @return
	 */
	@Action(value = "validateContact", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String validateContact() {
		Long pinId = users.getMerchantPin();
		logger.debug("receive validateMobile: " + contact.getMobile());
		Long createBy = users.getId();
		Long groupId = contact.getBookGroupId();
		if( !Boolean.valueOf( WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.PRIVATEGROUPOPEN))){
			createBy = null;
			groupId = null;
		}
		List<Contact> tContact = contactsService.checkContactByMobile(
				contact.getMobile(), pinId, createBy, groupId);
		entityMap = new HashMap<String, Object>();
		if ( tContact!=null&&tContact.size()>0 ) {
			entityMap.put("flag", 1);
		} else {
			entityMap.put("flag", 0);
		}
		return SUCCESS;
	}

	/**
	 * 导出excel
	 * 
	 * 返回下载文件名
	 */
	public String exportToExcel(List<ContactVO> contactVOs) {
		String strFileFlag = DataExport.EXCEL_XLS;
		long start = System.currentTimeMillis();
		String strFileName = null;
		if (contactVOs != null && !contactVOs.isEmpty()) {
			String exportFile = "";

			String[] cols = { "手机号码", "联系人姓名", "性别", "身份证号", "生日", "分组",
					"集团短号", "座机号码", "单位", "地址", "MSN", "QQ", "电子邮件", "微博", "备注" };
			String[][] contents = this.listToArr(contactVOs);

			// 获取应用的绝对路径
			String appPath = this.request.getRequestURL().toString();
			appPath = appPath.substring(0, appPath.lastIndexOf("/"));

			// 获取导出文件所在的目录的本地路径,以用户生成新的文件
			String filePath = File.separator + "downloads" + File.separator
					+ "contacts";
			String localDir = request.getSession().getServletContext()
					.getRealPath(filePath)
					+ File.separator;
			// 获取导出文件所在的网络路径
			String downLoadPath = appPath + filePath.replaceAll("\\\\", "/");
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				strFileName = sdf.format(new Date()) + strFileFlag;

				exportFile = new DataExport().exportToFile(localDir,
						strFileName, contents, cols, strFileFlag);// 默认导出.xls格式的文件
				downLoadPath = filePath
						+ File.separator
						+ exportFile.substring(exportFile
								.lastIndexOf(File.separator) + 1);
				// this.request.setAttribute("exportFile", downLoadPath);
				// / this.request.setAttribute("message", "客户资料导出成功！");
			} catch (Exception e) {
				logger.error("export contact error,", e);
				long end = System.currentTimeMillis();
				logger.info(" export contact,totally use " + (end - start)
						+ " millseconds ");
			}
		}
		long end = System.currentTimeMillis();
		logger.info("export contact,totally use " + (end - start)
				+ " millseconds ");

		return strFileName;
	}

	/**
	 * 将客户集合转化为可以到处二维数组
	 * 
	 * @param contacts
	 * @return
	 */
	private String[][] listToArr(List<ContactVO> contactVOs) {
		String[][] billsInArr = new String[contactVOs.size()][15];

		for (int j = 0; j < contactVOs.size(); j++) {
			ContactVO contactVO = contactVOs.get(j);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			billsInArr[j][0] = contactVO.getMobile(); // 手机号码
			billsInArr[j][1] = contactVO.getName(); // 联系人姓名
			billsInArr[j][2] = (null == contactVO.getGender() ? "" : (contactVO
					.getGender() == 1 ? "男" : "女")); // 性别
			billsInArr[j][3] = (null == contactVO.getIdentificationNumber() ? ""
					: contactVO.getIdentificationNumber()); // 身份证号
			billsInArr[j][4] = (null == contactVO.getBirthday() ? "" : sdf
					.format(contactVO.getBirthday())); // 生日
			billsInArr[j][5] = (-1 == contactVO.getBookGroupId() ? "未分组"
					: contactVO.getGroupName()); // 分组
			billsInArr[j][6] = (null == contactVO.getVpmn() ? "" : contactVO
					.getVpmn()); // 集团短号
			billsInArr[j][7] = (null == contactVO.getTelephone() ? ""
					: contactVO.getTelephone()); // 座机号码
			billsInArr[j][8] = (null == contactVO.getVpmn() ? "" : contactVO
					.getVpmn()); // 单位
			billsInArr[j][9] = (null == contactVO.getAddress() ? "" : contactVO
					.getAddress()); // 地址
			billsInArr[j][10] = (null == contactVO.getMsn() ? "" : contactVO
					.getMsn()); // MSN
			billsInArr[j][11] = (null == contactVO.getQqNumber() ? ""
					: contactVO.getQqNumber()); // QQ
			billsInArr[j][12] = (null == contactVO.getEmail() ? "" : contactVO
					.getEmail()); // 电子邮件
			billsInArr[j][13] = (null == contactVO.getMicroBlog() ? ""
					: contactVO.getMicroBlog()); // 微博
			billsInArr[j][14] = (null == contactVO.getDescription() ? ""
					: contactVO.getDescription()); // 备注
		}

		return billsInArr;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public String getContactIds() {
		return contactIds;
	}

	public void setContactIds(String contactIds) {
		this.contactIds = contactIds;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public ContactOrderVO getContactOrderVO() {
		return contactOrderVO;
	}

	public void setContactOrderVO(ContactOrderVO contactOrderVO) {
		this.contactOrderVO = contactOrderVO;
	}

	public ContactsService getContactsService() {
		return contactsService;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setContactsService(ContactsService contactsService) {
		this.contactsService = contactsService;
	}

	public String getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(String groupIds) {
		this.groupIds = groupIds;
	}

	public String getOriginalName() {
		return originalName;
	}

	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}

	public String getMessageTotal() {
		return messageTotal;
	}

	public void setMessageTotal(String messageTotal) {
		this.messageTotal = messageTotal;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	
	public boolean isResultflag() {
		return resultflag;
	}

	public void setResultflag(boolean resultflag) {
		this.resultflag = resultflag;
	}

	public List<String> getResultContent() {
		return resultContent;
	}

	public void setResultContent(List<String> resultContent) {
		this.resultContent = resultContent;
	}
	public String[] getCaptions() {
		return captions;
	}
	public void setCaptions(String[] captions) {
		this.captions = captions;
	}
	public Users getUsers() {
		return users;
	}
	public boolean isPrivateGroupOpen() {
		return privateGroupOpen;
	}
	public void setPrivateGroupOpen(boolean privateGroupOpen) {
		this.privateGroupOpen = privateGroupOpen;
	}
	public String getSetGroupName() {
		return setGroupName;
	}
	public void setSetGroupName(String setGroupName) {
		this.setGroupName = setGroupName;
	}
	public String getSetGroupId() {
		return setGroupId;
	}
	public void setSetGroupId(String setGroupId) {
		this.setGroupId = setGroupId;
	}

}
