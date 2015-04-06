package com.leadtone.mas.bizplug.addr.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.addr.service.ContactsService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.leadtone.mas.bizplug.smssend.action.BaseAction;

@ParentPackage("json-default")
@Namespace(value = "/addr")
public class AddrAction extends BaseAction {
	private static Logger log = Logger.getLogger(AddrAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ContactsService contactsService;

	private List<Contacts> contactsList = new ArrayList<Contacts>();

	/**
	 * 退出系统，销毁SESSION，退回到登录界面
	 * 
	 * @return
	 */
	@Action(value = "getAddr", results = { @Result(name = "success", type = "json", params = {
			"root", "contactsList", "contentType", "text/html" }) })
	public String findAddrByParentId() {
		Users loginUser = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		String s = this.getRequest().getParameter("id");
		Long id = -100L;
		if (s != null) {
			id = Long.parseLong(s);
		}
		this.contactsList = contactsService.getContactsByParentId(loginUser.getMerchantPin(), id);
		return SUCCESS;
	}

	/**
	 * 退出系统，销毁SESSION，退回到登录界面
	 * 
	 * @return
	 */
	@Action(value = "getAddrByKey", results = { @Result(name = "success", type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String findAddrByKey() {
		Users loginUser = (Users) super.getSession().getAttribute(ApSmsConstants.SESSION_USER_INFO);
		String term = this.getRequest().getParameter("term");
		this.contactsList = contactsService.getContactsByUserName(loginUser.getMerchantPin(), term);
		entityMap = new HashMap<String, Object>();
		entityMap.put("totalResultsCount", this.contactsList.size());
		entityMap.put("addrs", contactsList);
		return SUCCESS;
	}

	public List<Contacts> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<Contacts> contactsList) {
		this.contactsList = contactsList;
	}

}
