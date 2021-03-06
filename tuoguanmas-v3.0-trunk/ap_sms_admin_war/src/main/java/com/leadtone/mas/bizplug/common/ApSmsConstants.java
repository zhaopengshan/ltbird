package com.leadtone.mas.bizplug.common;

public class ApSmsConstants {
	public static final String AP_SMS_SPRING_CONFIG = "E:\\Workspace\\MAS3.0\\ap_sms\\src\\main\\resources\\spring-ap-sms.xml";
	
	public static final String STATUS_SUCCESS = "1";
	public static final String STATUS_ERROR = "0"; 
	
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
	
	public static final int SMS_TUNNEL_TYPE_WG=2;
	public static final int SMS_TUNNEL_TYPE_HJ=1;
	public static final int SMS_TUNNEL_TYPE_MC=3;
	
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
	
	// 短信验证码 PAN-Z-G
	public static final String SESSION_SMS_CHECKING_NUMBER="SESSION_SMS_CHECKING_NUMBER";
	
	
	/**
	 *SESSION密码找回验证码
	 */
	public static final String SESSION_FIND_SMS_NULBER="SESSION_FIND_SMS_NULBER";
	
	public static final String SESSION_USER_INFO = "SESSION_USER_INFO";
	
	// 操作类型
	public static final int SMS_SEND_OP_ID = 1;
	public static final int MEET_OP_ID = 2;
	public static final int MINDER_OP_ID = 3;
	public static final int VOTE_OP_ID = 4;
	public static final int ANSWER_OP_ID = 5;
	public static final int AWARD_OP_ID = 6;	

	//通道类型
	public static final int TUNNEL_TYPE_TD = 1;
	public static final int TUNNEL_TYPE_GW = 2;
	public static final int TUNNEL_TYPE_MODEM = 3;
	
	// 短信发送状态
	public static final int SMS_CANCEL_STATE = -1;
	public static final int SMS_READY_STATE = 0;
	public static final int SMS_SENDING_STATE = 1;
	public static final int SMS_SEND_OK_STATE = 2;
	public static final int SMS_SEND_FAIL_STATE = 3;
	//收件箱短信状态
	public static final int SMS_INBOX_READ = 1;
	public static final int SMS_INBOX_NOREAD = 0;
	// 定义通用短信发送类型
	public static final int LOW_PRIORITY = 10;
	public static final int COMMON_PRIORITY = 20;
	public static final int HIGH_PRIORITY = 30;
	public static final int URGENT_PRIORITY = 40;
	public static final int VIP_PRIORITY = 50;
	
	//短信互动
	public static final String SMS_OPERATION_CODE_HD="HD";
	//会议通知
	public static final String SMS_OPERATION_CODE_HY="HY";
	//日程提醒
	public static final String SMS_OPERATION_CODE_TX="TX";
	//投票调查
	public static final String SMS_OPERATION_CODE_TP="TP";
	
	// 彩信发送状态
		public static final int MMS_CANCEL_STATE = -1;
		public static final int MMS_READY_STATE = 0;
		public static final int MMS_SENDING_STATE = 1;
		public static final int MMS_SEND_OK_STATE = 2;
		public static final int MMS_SEND_FAIL_STATE = 3;

//通道 管理
	//通道属性
	public static final int TUNNEL_ATTRIBUTE_OTHER = 2;//第三方通道
	public static final int TUNNEL_ATTRIBUTE_SELF = 1;//直连网关
	//通道删除状态
	public static final int TUNNEL_DEL_STATUS_Y = 1;//删除
	public static final int TUNNEL_DEL_STATUS_N = 0;//显示
	//通道type
	public static final int TUNNEL_TYPE_SMS = 1;//短信通道
	public static final int TUNNEL_TYPE_MMS = 2;//彩信通道
	//通道类型
	public static final int TUNNEL_CLASSIFY_SELF_YD = 1;//本省移动
	public static final int TUNNEL_CLASSIFY_YD = 2;		//移动
	public static final int TUNNEL_CLASSIFY_SELF_LT =3;	//本省联通
	public static final int TUNNEL_CLASSIFY_LT = 4;		//联通
	public static final int TUNNEL_CLASSIFY_SELF_DX =5;	//本省电信
	public static final int TUNNEL_CLASSIFY_DX = 6;		//电信
	public static final int TUNNEL_CLASSIFY_GLOBAL = 7;	//全网
	public static final int TUNNEL_CLASSIFY_MODEM = 8;	//猫池
	public static final int TUNNEL_CLASSIFY_TD = 9;		//TD话机
	public static final int TUNNEL_CLASSIFY_ZXT = 10;	//资信通
	public static final int TUNNEL_CLASSIFY_QXT = 11;	//企信通
	
	public static final int TUNNEL_CLASSIFY_YDYW = 13;	//移动异网
	public static final int TUNNEL_CLASSIFY_QXT_NEW = 14;	//企信通
	public static final int TUNNEL_CLASSIFY_EMPP = 15;	//移动 EMPP
	
	//通道使用状态
	public static final int TUNNEL_STATE_N = 0;//不可用
	public static final int TUNNEL_STATE_Y = 1;//可用
	
	public static final String USER_GROUP_SUFFIX = "<用户组>";
	// 移动 联通 电信服务商代码
	public static final String YD_CODE = "yd";
	public static final String LT_CODE = "lt";
	public static final String DX_CODE = "dx";
//mbn_config_sys_dictionary type:1省份,2性别,3大区,4市,5区县,6商圈,7通道类型
	public static final int CONFIG_DICTIONARY_PROVINCE = 1;
	public static final int CONFIG_DICTIONARY_GENDER = 2;
	public static final int CONFIG_DICTIONARY_REGION = 3;
	public static final int CONFIG_DICTIONARY_CITY = 4;
	public static final int CONFIG_DICTIONARY_COUNTY = 5;
	public static final int CONFIG_DICTIONARY_CBD = 6;
	public static final int CONFIG_DICTIONARY_TUNNEL_TYPE = 7;
	
	// 定义操作类型
	public static final String OPERATION_CODING_HD = "HD";//互动
	public static final String OPERATION_CODING_HY = "HY";//会议
	public static final String OPERATION_CODING_TX = "TX";//提醒
	public static final String OPERATION_CODING_DC = "DC";//调查
	public static final String OPERATION_CODING_DT = "DT";//答题
	public static final String OPERATION_CODING_CJ = "CJ";//抽奖
	public static final String OPERATION_CODING_TP = "TP";//投票
	public static final String OPERATION_CODING_YZ = "YZ";//短信验证码
	
	public static final int OPERATION_CODING_TYPE_HD=1;//会议
	public static final int OPERATION_CODING_TYPE_HY=2;//会议
	public static final int OPERATION_CODING_TYPE_TX=3;//提醒
	public static final int OPERATION_CODING_TYPE_TP=4;//投票
	public static final int OPERATION_CODING_TYPE_DT=5;//答题
	public static final int OPERATION_CODING_TYPE_CJ=6;//抽奖
	public static final int OPERATION_CODING_TYPE_YZ = 7;//短信验证码
	
	
	//属性文件中配置属性名
    public static final String QUERYSELFINFO = "queryselfinfo";
    public static final String PRIVATEGROUPOPEN = "privategroupopen";
    public static final String USERADDRESSPRIVILEGE = "useraddressprivilege";
    
	// 用户类型
    public static final int USER_TYPE_SUPER_ADMIN = 0;	// 0：超级管理员
	public static final int USER_TYPE_PROVINCE_ADMIN = 1;	// 1：省系统管理员
	public static final int USER_TYPE_CITY_ADMIN = 2;		// 2：地市管理员
	public static final int USER_TYPE_ENTERPRISE_ADMIN = 3;	// 3：企业管理员
	public static final int USER_TYPE_ENTERPRISE_NORMAL = 4;// 4：企业用户
	
	public static final int PERIODIC_AWOKE_MODE_DAY=1;//唤醒模式天
	public static final int PERIODIC_AWOKE_MODE_WEEK=2;
	public static final int PERIODIC_AWOKE_MODE_MONTH=3;
	
	/**
	 * 修改密码周期属性
	 */
	public static final String PWD_MODIFY_PERIOD="pwd_modify_period";
	public static final String SMS_SIGN_CONTENT = "sms_sign_content";
	/**
	 * 首次登录是否修改密码标识
	 */
	public static final String FIRST_LOGIN_MODIFY_PWD_FLAG = "first_login_modify_pwd_flag";
	/*
	 * 挂机短信设置常量定义
	 */
	public static final String CALL_ONHOOK_SMS_ABILITY = "call_onhook_sms_ability";
	public static final String CALL_ONHOOK_NORMAL_SMS = "call_onhook_normal_sms";
	public static final String CALL_BUSY_SMS = "call_busy_sms";
	public static final String CALL_TIMEOUT_SMS = "call_timeout_sms";
	public static final String CALL_OTHER_SMS = "call_other_sms";
}
