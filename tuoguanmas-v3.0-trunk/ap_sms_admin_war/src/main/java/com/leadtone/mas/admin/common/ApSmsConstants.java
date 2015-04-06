package com.leadtone.mas.admin.common;

public class ApSmsConstants {
	
	public static final int LIMIT_TRY_COUNT=3;	// 失败可重试次数
	// 性别
	public static final int GENDER_FEMALE = 0; // 0：女
	public static final int GENDER_MALE = 1;   // 1: 男
	
	// 用户类型
        public static final int USER_TYPE_SUPER_ADMIN = 0;	// 0：超级管理员
	public static final int USER_TYPE_PROVINCE_ADMIN = 1;	// 1：省系统管理员
	public static final int USER_TYPE_CITY_ADMIN = 2;		// 2：地市管理员
	public static final int USER_TYPE_ENTERPRISE_ADMIN = 3;	// 3：企业管理员
	public static final int USER_TYPE_ENTERPRISE_NORMAL = 4;// 4：企业用户
	//webService登录标识
	public static final int USER_WEBSERVICE_YES=2;
	public static final int USER_WEBSERVICE_NO=1;
	// 锁定标志
	public static final int LOCK_FLAG_LOCKED = 1;  // 1: 锁定
	public static final int LOCK_FLAG_UNLOCKED = 0;// 0: 非锁定 
	
	//开关锁定标识
	public static final String LOCK_FLAG_TRUE="true";
	public static final String LOCK_FLAG_FALSE="flase";
	
	// 短信验证码 PAN-Z-G
	public static final String SESSION_SMS_CHECKING_NUMBER="SESSION_SMS_CHECKING_NUMBER";
	
	// 有效标志 
	public static final int ACTIVE_FLAG_YES = 1;// 1: 有效；
	public static final int ACTIVE_FLAG_NO = 0;	// 0: 无效
	
	public static final int REPLY_YES=0; //支持回复
	public static final int REPLY_NO=1; //不支持回复
	
	public static final int DELIVERY_YES=0; //支持回执
	public static final int DELIVERY_NO=1; 	// 不支持回执
	
	// 发送类型
	public static final int IMMEDIATE_SEND=0;	// 立即发送
	public static final int TIMEING_SEND=1;		// 定时发送

	// 消息发送状态(短信插件到调度程序)
	public static final int STATUS_WAIT_SEND=0;
	public static final int STATUS_SENDING=1;
	public static final int STATUS_SENDED_SUCCESS=2;
	public static final int STATUS_SENDED_CANCEL=3;
	public static final int STATUS_SENDED_ERROR=4;
	
	
	// 消息类型
	public static final String MSG_TYPE_SMS="SMS";
	public static final String MSG_TYPE_MMS="MMS";
	public static final String MSG_TYPE_LBS="LBS";
	public static final String MSG_TYPE_CTD="CTD";
	public static final String MSG_TYPE_CCS="CCS";
	public static final String MSG_TYPE_IOT="IOT";
	public static final String MSG_TYPE_INTERNET="INTERNET";
	
	// 消息编码类型
	public static final int MSG_FORMAT_ASCII=0;
	public static final int MSG_FORMAT_SMS_WRITE=3;
	public static final int MSG_FORMAT_BINARY=4;
	public static final int MSG_FORMAT_UCS2=8;
	public static final int MSG_FORMAT_GBK=15;
	
	public static final String SESSION_USER_INFO = "SESSION_USER_INFO";
    //是否为移动管理权限
    public static final int IS_MOBILE_PRIVILEGE = 1;
    public static final int IS_MOBILE_CITY = 2;
    public static final int NOT_IS_MOBILE_PRIVILEGE = 0;

    //虚拟企业类型
    public static final String MERCHANT_PROVINCE_VIRTUAL_TYPE = "0";
    public static final String MERCHANT_CITY_VIRTUAL_TYPE = "1";
    public static final String MERCHANT_REAL_TYPE = "2";
        
        public static final int SM_TUNNEL_TYPE=1;
        public static final int MM_TUNNEL_TYPE=2;
        public static final int SIM_MODEM_CLASSIFY=8;
        public static final int MOBILE_WHOLE_NETWORK=2;
        public static final int UNICOM_WHOLE_NETWORK=4;
        public static final int TELECOM_WHOLE_NETWORK=6;
        public static final int ZXT_TUNNEL_TYPE=10;//资信通
        
        public static final int TUNNEL_CLASSIFY_YDYW = 13;	//移动异网
        public static final int NEW_QXT_TUNNEL_TYPE=14;//新企信通
        public static final int EMPP_TUNNEL_TYPE=15;//上海移动empp
        
        public static final int SELF_PROVINCE_CLASSIFY=1;
        public static final Long TUNNEL_STATE_VALID=1L;
        public static final Long TUNNEL_STATE_INVALID=0L;
        public static final String NORMAL_CORP="2";
        
        public static final Long UNION_CORP_ADMIN_ROLEID=2L;
        //属性文件中配置属性名
        public static final String QUERYSELFINFO = "queryselfinfo";
        public static final String PRIVATEGROUPOPEN = "privategroupopen";
        public static final String USERADDRESSPRIVILEGE = "useraddressprivilege";
        public static final String LOGINPORTLOCK="loginportlock";
        public static final String MOBILELOCK="mobilelock";
        
        public static final String DELEGATEMAS = "delegatemas";
        public static final String ONLINE = "tuoguan";
        public static final String WSURL = "wsUrl";
        public static final String ZXTURL = "zxtUrl";
        public static final String TUNNELALL = "tunnelAll";      
        
        // 属性文件中扩展码类型 值为user/operation
        public static final String EXT_CODE_TYPE = "extcodetype";
        public static final String USER_EXT_CODE_TYPE_VALUE = "user";
        public static final String OPERATION_EXT_CODE_TYPE_VALUE = "operation";
        public static final String SMS_PRIORITY_FROM_USER = "smsPriorityFromUser";
        
        // 扩展码类型 
        // 按用户扩展 xxyy xx用户代码 yy任务代码
        public static final int USER_EXT_CODE_TYPE = 1;
        // 按操作类型扩展 XXyyy XX操作类型，如HD,ZX等 yyy任务代码
        public static final int OPERATION_EXT_CODE_TYPE = 2;
}
