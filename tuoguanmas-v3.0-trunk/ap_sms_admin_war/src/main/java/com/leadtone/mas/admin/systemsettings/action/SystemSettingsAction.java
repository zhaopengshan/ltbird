package com.leadtone.mas.admin.systemsettings.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.leadtone.mas.admin.common.FileUploadUtil;
import com.leadtone.mas.admin.security.action.BaseAction;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.openaccount.bean.MbnConfigMerchant;
import com.leadtone.mas.bizplug.openaccount.service.MbnConfigMerchantIService;
import com.leadtone.mas.bizplug.security.bean.Users;
import com.opensymphony.xwork2.ActionContext;

/**
 * SystemSettingsAction
 * 
 * @author wangyu1
 */
@ParentPackage("json-default")
@Namespace(value = "/systemSettingsAction")
public class SystemSettingsAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory
			.getLog(SystemSettingsAction.class);
	@Resource
	private MbnConfigMerchantIService mbnConfigMerchantIService;
	private List<String> paraList = new ArrayList<String>();

	/**
	 * 上传文件需要用到的文件、文件名称和文件内容类型 三个变量
	 */
	private File upload;
	private String uploadFileName;
	private String uploadContentType;

	/**
	 * 更新系统参数
	 * 
	 * @return
	 */
	@Action(value = "updateParas", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String updateParas() {
		// initSession();
		Users users = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		entityMap = new HashMap<String, Object>();
		initParaList();
		List<MbnConfigMerchant> list = new ArrayList<MbnConfigMerchant>();
		// 判断操作类型是添加还是更新 添加insert 更新update
		String operate = null;// 用的是是否需要状态报告 status_report_is_need判断的
		MbnConfigMerchant tempMcm = this.mbnConfigMerchantIService
				.loadByMerchantPin(users.getMerchantPin(),
						"status_report_is_need");
		if (tempMcm == null) {
			operate = "insert";
		} else {
			operate = "update";
		}
		for (Iterator<String> it = paraList.iterator(); it.hasNext();) {
			String temp = it.next();
			if (temp.endsWith("Connect")) {
				String baseStr = temp.substring(0, temp.lastIndexOf("Connect"));
				MbnConfigMerchant mcm = new MbnConfigMerchant();
				if ("insert".equals(operate)) {
					mcm.setId(PinGen.getSerialPin());
				}
				mcm.setMerchantPin(users.getMerchantPin());
				mcm.setName(baseStr);
				Long connect = 60l;
				try {
					Long prefix = Long.parseLong(this.getRequest()
							.getParameter(baseStr + "Prefix").trim());
					Long suffix = Long.parseLong(this.getRequest()
							.getParameter(baseStr + "Suffix").trim());
					connect = prefix * suffix;
				} catch (NumberFormatException e) {
					connect = 60l;
				}
				if (connect < 10) {
					connect = 10l;
				} else if (connect > 44640) {
					connect = 44640l;
				}
				mcm.setItemValue(String.valueOf(connect));
				list.add(mcm);
			} else if (temp.endsWith("-")) {
				String baseStr = temp.substring(0, temp.lastIndexOf("-"));
				MbnConfigMerchant mcm = new MbnConfigMerchant();
				if ("insert".equals(operate)) {
					mcm.setId(PinGen.getSerialPin());
				}
				mcm.setMerchantPin(users.getMerchantPin());
				mcm.setName(baseStr);
				String connect = 
						this.getRequest().getParameter(baseStr + "Prefix")
						+ "-"
						+ this.getRequest().getParameter(baseStr + "Suffix");
				mcm.setItemValue(connect);
				list.add(mcm);
			} else {
				MbnConfigMerchant mcm = new MbnConfigMerchant();
				if ("insert".equals(operate)) {
					mcm.setId(PinGen.getSerialPin());
				}
				mcm.setMerchantPin(users.getMerchantPin());
				mcm.setName(temp);
				mcm.setItemValue(this.getRequest().getParameter(temp).trim());
				list.add(mcm);
			}
		}
		boolean flag = false;
		if ("insert".equals(operate)) {
			flag = this.mbnConfigMerchantIService.insertBatch(list);
		} else if ("update".equals(operate)) {
			flag = this.mbnConfigMerchantIService.updateBatch(list);
		} else {
			flag = false;
		}
		if (flag) {
			entityMap.put("flag", true);
			entityMap.put("resultMsg", "参数更新成功");
		} else {
			entityMap.put("flag", false);
			entityMap.put("resultMsg", "参数更新失败");
		}
		updateSessionInfo(users.getMerchantPin());
		return SUCCESS;
	}

	/**
	 * 更新首页logo
	 * 
	 * @return
	 */
	@Action(value = "updateLogo", results = { @Result(type = "json", params = {
			"root", "entityMap", "contentType", "text/html" }) })
	public String updateLogo() {
		entityMap = new HashMap<String, Object>();
		if (upload == null && uploadFileName == null) {
			entityMap.put("flag", true);
			entityMap.put("resultMsg", "上传文件为空");
			entityMap.put("oldUploadName", "");
			return SUCCESS;
		}
		// initSession();
		Users users = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		FileOutputStream fos = null;
		try {
			/*
			 * this.fileValidate(entityMap);
			 * if(!(Boolean)entityMap.get("flag")){//判断文件大小 return SUCCESS; }
			 */
			String oldUploadName = this.getRequest().getParameter(
					"oldUploadName");
			File oldFile = new File(oldUploadName);
			if (oldFile.exists()) {
				oldFile.delete();
			}
			String[] fileItem = FileUploadUtil.upload(this.getRequest(),
					upload, uploadFileName);
			if (fileItem == null) {
				entityMap.put("flag", false);
				entityMap.put("resultMsg", "您没有上传首页LOGO，更新LOGO失败");
				return SUCCESS;
			}
			// 修改为相对路径 20130418
			String filePath = "/uploads/" + fileItem[1];
			// 判断首页Logo是否已经存在
			MbnConfigMerchant homeLogo = this.mbnConfigMerchantIService
					.loadByMerchantPin(users.getMerchantPin(), "home_page_logo");
			String operate = null;
			if (homeLogo == null) {
				operate = "insert";
			} else {
				operate = "update";
			}
			Long merchantPin = users.getMerchantPin();
			MbnConfigMerchant mcm = new MbnConfigMerchant();
			if ("insert".equals(operate)) {
				mcm.setId(PinGen.getSerialPin());
			}
			mcm.setMerchantPin(merchantPin);
			mcm.setName("home_page_logo");
			mcm.setItemValue(filePath);
			boolean flag = false;
			if ("insert".equals(operate)) {
				flag = this.mbnConfigMerchantIService.insert(mcm);
			} else if ("update".equals(operate)) {
				flag = this.mbnConfigMerchantIService.update(mcm);
			} else {
				flag = false;
			}
			if (flag) {
				entityMap.put("resultMsg", "首页LOGO上传成功");
				entityMap.put("flag", true);
				entityMap.put("oldUploadName", filePath);
			} else {
				entityMap.put("resultMsg", "首页LOGO上传失败");
				entityMap.put("flag", false);
			}
			updateSessionInfo(users.getMerchantPin());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			log.error("上传首页LOGO失败，失败原因：" + e.getMessage());
			entityMap.put("resultMsg", "首页LOGO上传失败");
			entityMap.put("flag", false);
			return SUCCESS;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException ex) {
					log.info(ex.getMessage());
				}
			}
		}
	}

	/**
	 * 显示页面参数
	 * 
	 * @return
	 */
	@Action(value = "showParas", results = { @Result(name = SUCCESS, location = "/ap/settings/system_settingsUpdate.jsp") })
	public String showParas() {
		Users users = (Users) super.getSession().getAttribute(
				ApSmsConstants.SESSION_USER_INFO);
		entityMap = new HashMap<String, Object>();
		initParaList();
		// 单独发送首页LOGO原始名称
		MbnConfigMerchant homeLogo = this.mbnConfigMerchantIService
				.loadByMerchantPin(users.getMerchantPin(), "home_page_logo");
		if (homeLogo != null && homeLogo.getItemValue() != null
				&& !homeLogo.getItemValue().trim().equals("")) {
			this.getRequest().setAttribute("home_page_logo",
					homeLogo.getItemValue());
		}
		// 循环遍历业务参数
		for (Iterator<String> it = paraList.iterator(); it.hasNext();) {
			String temp = it.next();
			if (temp.endsWith("Connect")) {
				String baseStr = temp.substring(0, temp.lastIndexOf("Connect"));
				MbnConfigMerchant mcm = this.mbnConfigMerchantIService
						.loadByMerchantPin(users.getMerchantPin(), baseStr);
				if (mcm != null && mcm.getItemValue() != null
						&& !mcm.getItemValue().trim().equals("")) {
					String itemValue = mcm.getItemValue();
					Long connect = Long.parseLong(itemValue);
					Long prefix = null;
					Long suffix = null;
					if (connect % 1440 == 0) {
						suffix = 1440L;
					} else if (connect % 60 == 0) {
						suffix = 60L;
					} else {
						suffix = 1L;
					}
					prefix = connect / suffix;
					this.getRequest().setAttribute(baseStr + "Prefix",
							String.valueOf(prefix));
					this.getRequest().setAttribute(baseStr + "Suffix",
							String.valueOf(suffix));
				}
			} else if (temp.endsWith("-")) {
				String baseStr = temp.substring(0, temp.lastIndexOf("-"));
				MbnConfigMerchant mcm = this.mbnConfigMerchantIService
						.loadByMerchantPin(users.getMerchantPin(), baseStr);
				if (mcm != null && mcm.getItemValue() != null
						&& !mcm.getItemValue().trim().equals("")) {
					String[] arr = mcm.getItemValue().split("-");
					this.getRequest().setAttribute(baseStr + "Prefix", arr[0]);
					this.getRequest().setAttribute(baseStr + "Suffix", arr[1]);
				}
			} else {
				MbnConfigMerchant mcm = this.mbnConfigMerchantIService
						.loadByMerchantPin(users.getMerchantPin(), temp);
				if (mcm != null && mcm.getItemValue() != null
						&& !mcm.getItemValue().trim().equals("")) {
					String itemValue = mcm.getItemValue();
					this.getRequest().setAttribute(temp, itemValue);
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 业务参数列表初始化(对应数据库的业务参数)
	 */
	private void initParaList() {
		// 共15项 paraList取14项 首页logo单独取
		// 首页 logo home_page_logo
		// 有效期 validity
		// 短信互动 sms_interaction_validity
		// 会议通知 sms_meeting_notice_validity
		// 日程提醒 sms_schedule_remind_validity
		// 投票调查 sms_vote_research_validity
		// 短信答题 sms_answer_validity
		// 短信抽奖 sms_lucky_draw_validity
		// 优先级 priority 1紧急 2重要 3次重要 4普通
		// 短信互动 sms_interaction_priority
		// 会议通知 sms_meetint_notice_priority
		// 日程提醒 sms_schedule_remind_priority
		// 投票调查 sms_vote_research_priority
		// 短信答题 sms_answer_priority
		// 短信抽奖 sms_lucky_draw_priority
		// 消息长度 message_length
		// 是否需要状态报告 status_report_is_need
		// 连接 Connect
		// 前缀 Prefix
		// 后缀 Suffix
		// 允许发送时间段 sms_send_time_interval
		//
		// 鉴权方式 authentication
		paraList.add("sms_interaction_validityConnect");
		paraList.add("sms_meeting_notice_validityConnect");
		paraList.add("sms_schedule_remind_validityConnect");
		paraList.add("sms_vote_research_validityConnect");
		paraList.add("sms_answer_validityConnect");
		paraList.add("sms_lucky_draw_validityConnect");
		paraList.add("sms_interaction_priority");
		paraList.add("sms_meetint_notice_priority");
		paraList.add("sms_schedule_remind_priority");
		paraList.add("sms_vote_research_priority");
		paraList.add("sms_answer_priority");
		paraList.add("sms_lucky_draw_priority");
		paraList.add("message_length");
		paraList.add("status_report_is_need");
		paraList.add("sms_send_time_interval-");
		paraList.add("authentication");
	}

	/**
	 * 文件验证
	 * 
	 * @return
	 */
	private void fileValidate(Map map) throws IOException {
		boolean flag = true;
		String resultMsg = null;
		FileInputStream fis = new FileInputStream(upload);
		if (fis.available() > 1024 * 1024 * 1024) {// 1G
			resultMsg = "文件太大，上传失败";
			flag = false;
		}
		if (fis.available() == 0) {// 0k
			resultMsg = "文件为空，上传失败";
			flag = false;
		}
		map.put("flag", flag);
		map.put("resultMsg", resultMsg);
	}

	/**
	 * 更新SESSION中的信息 立即生效
	 * @param merchantPin
	 */
	private void updateSessionInfo(Long merchantPin){
		int msgMaxLength = 335;
		MbnConfigMerchant confMerchant = mbnConfigMerchantIService.loadByMerchantPin(merchantPin, "message_length");
		if( confMerchant != null && confMerchant.getItemValue() != null){
			String tmp = confMerchant.getItemValue();
			try{
				msgMaxLength = Integer.parseInt(tmp);
			}catch(Exception e){
				e.printStackTrace();
				msgMaxLength = 335;
			}
		}
		String homePageLogo = "";
		confMerchant = mbnConfigMerchantIService.loadByMerchantPin(merchantPin, "home_page_logo");
		if( confMerchant != null && confMerchant.getItemValue() != null){
			homePageLogo = confMerchant.getItemValue();
		}
		ActionContext.getContext().getSession().put("sms_max_length", msgMaxLength);
		ActionContext.getContext().getSession().put("home_page_logo", homePageLogo);
	}
	
	public MbnConfigMerchantIService getMbnConfigMerchantIService() {
		return mbnConfigMerchantIService;
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

}
