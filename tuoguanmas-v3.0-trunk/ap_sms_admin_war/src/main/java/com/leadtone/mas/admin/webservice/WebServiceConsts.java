package com.leadtone.mas.admin.webservice;

public class WebServiceConsts {
	// 注册
	public static final String REGISTER_METHOD = "register";
	// 心跳
	public static final String HEARTBEAT_METHOD = "heartBeat";
	// 同步企业信息
	public static final String SYNCENTINFO_METHOD = "syncEntInfo";
	// 同步企业配置
	public static final String SYNCENTCONFIG_METHOD = "syncEntConfig";
	// 经分数据上报
	public static final String BIZREPORT_METHOD = "bizReport";
	// 统计数据上报
	public static final String COUNTREPORT_METHOD = "countReport";
	// 创建企业
	public static final String CREATEENT_METHOD = "createEnt";
	// 创建企业管理员
	public static final String CREATEENTADMIN_METHOD = "createEntAdmin";
	// 更新企业管理员Ext
	public static final String UPDATEENTADMINEXT_METHOD = "updateEntAdminExt";
	// 控制指令
	public static final String CTRL_METHOD = "ctrl";
	
	public static final String OK_RETURN_CODE = "0";
	public static final String OK_RETURN_MESSAGE = "ok";
	
	public static final String BAD_REQUEST_RETURN_CODE = "1001";
	public static final String BAD_REQUEST_RETURN_MESSAGE = "请求错误";
	
	public static final String XML_PARSE_FAIL_RETURN_CODE = "1002";
	public static final String XML_PARSE_FAIL_RETURN_MESSAGE = "XML解析错误";
	
	public static final String NODE_VALID_FAIL_RETURN_CODE = "1003";
	public static final String NODE_VALID_FAIL_RETURN_MESSAGE = "鉴权错误";
	
	public static final String UNSUPPORT_METHOD_RETURN_CODE = "1004";
	public static final String UNSUPPORT_METHOD_RETURN_MESSAGE = "指令错误";
}