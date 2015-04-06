package com.leadtone.mas.bizplug.common;

import java.util.Calendar;
import java.util.Date;

public class PageUtil extends Page {
	
	/**
	 * 模糊查询列1
	 */
	private String column1;
	/**
	 * 模糊查询列2
	 */
	private String column2;
	/**
	 * 模糊查询列3
	 */
	private String column3;
	/**
	 * 模糊查询条件
	 */
	private String keyWord;
	/**
	 * 短信操作，联系人名称
	 */
	private String contactName;
	/**
	 * 短信操作，短信标题
	 */
	private String smsTitle;
	/**
	 * 短信操作，起始时间
	 */
	private Date startDate;
	/**
	 * 短信操作，结束时间
	 */
	private Date endDate;
	/**
	 * 类型
	 */
	private String type;
	/**
	 * 用户名称
	 */
	private String account;
	
	// 手机号
	private String mobile;
	
	// 邮箱
	private String email;
	
	// 用户状态
	private Integer activeFlag;
	
	// 用户所属角色
	private Long roleId;
	
	// 角色名称
	private String roleName;
	
	// 角色描述
	private String roleDesc;
	//批次ID
	private Long batchId;
	
	//发送状态
	private Integer sendResult;
	// pin 码
	private Long merchantPin;
	//通道名称、
	private String name;
	//通道是否可用
	private Long state;
	//直连，第三方标志
	private Long attribute;
	//运营商 分类标识
	private Long classify;
	//省编码
	private String province;
	//通道流水操作类型
	private Long operationType;
	//通道ID对应的流水操作
	private Long tunnelId;
	//业务类型ID
	private Long operationId;
	//创建人的名称
	private String createByName;
	/**
	 * 接收人姓名
	 */
	private String receiverName;
	/**
	 * 接收人手机号
	 */
	private String receiveMoble;
	/**
	 * 失败原因
	 */
	private String failReason;
	/**
	 * 回复人姓名
	 */
	private String replyName;
	/**
	 * 回复人手机号
	 */
	private String replyMobile;
	/**
	 * 其他查询条件，用于扩充,对象亦可
	 */
	private Object otherCondition;
	// 管理员类型
	private Integer userType;
	// 区域组 省或者地市 用于管理员用户列表查询
	private String[] areaRange;
    /**
     * 信息归属查询条件
     */
    private Long createBy;

    private String[] commCondition;
	
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getSmsTitle() {
		return smsTitle;
	}
	public void setSmsTitle(String smsTitle) {
		this.smsTitle = smsTitle;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		if(endDate!=null){
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(endDate);
			cal.add(cal.DAY_OF_MONTH, 1); 
			return cal.getTime();
		}
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Object getOtherCondition() {
		return otherCondition;
	}
	public void setOtherCondition(Object otherCondition) {
		this.otherCondition = otherCondition;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	
	public Integer getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(Integer activeFlag) {
		this.activeFlag = activeFlag;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public Integer getSendResult() {
		return sendResult;
	}
	public void setSendResult(Integer sendResult) {
		this.sendResult = sendResult;
	}
	public String getColumn1() {
		return column1;
	}
	public void setColumn1(String column1) {
		this.column1 = column1;
	}
	public String getColumn2() {
		return column2;
	}
	public void setColumn2(String column2) {
		this.column2 = column2;
	}
	public String getColumn3() {
		return column3;
	}
	public void setColumn3(String column3) {
		this.column3 = column3;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	@Override
	public String toString() {
		return "PageUtil [column1=" + column1 + ", column2=" + column2
				+ ", column3=" + column3 + ", keyWord=" + keyWord
				+ ", contactName=" + contactName + ", smsTitle=" + smsTitle
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", account=" + account + ", mobile=" + mobile + ", email="
				+ email + ", activeFlag=" + activeFlag + ", roleId=" + roleId
				+ ", roleName=" + roleName + ", roleDesc=" + roleDesc
				+ ", batchId=" + batchId + ", sendResult=" + sendResult
				+ ", merchantPin=" + merchantPin + ", otherCondition="
				+ otherCondition + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getState() {
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public Long getAttribute() {
		return attribute;
	}
	public void setAttribute(Long attribute) {
		this.attribute = attribute;
	}
	public Long getClassify() {
		return classify;
	}
	public void setClassify(Long classify) {
		this.classify = classify;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Long getOperationType() {
		return operationType;
	}
	public void setOperationType(Long operationType) {
		this.operationType = operationType;
	}
	public Long getTunnelId() {
		return tunnelId;
	}
	public void setTunnelId(Long tunnelId) {
		this.tunnelId = tunnelId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getOperationId() {
		return operationId;
	}
	public void setOperationId(Long operationId) {
		this.operationId = operationId;
	}
	
	public String getCreateByName() {
		return createByName;
	}
	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getReceiveMoble() {
		return receiveMoble;
	}
	public void setReceiveMoble(String receiveMoble) {
		this.receiveMoble = receiveMoble;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getReplyName() {
		return replyName;
	}
	public void setReplyName(String replyName) {
		this.replyName = replyName;
	}
	public String getReplyMobile() {
		return replyMobile;
	}
	public void setReplyMobile(String replyMobile) {
		this.replyMobile = replyMobile;
	}

	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public String[] getAreaRange() {
		return areaRange;
	}
	public void setAreaRange(String[] areaRange) {
		this.areaRange = areaRange;
	}

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
	public String[] getCommCondition() {
		return commCondition;
	}
	public void setCommCondition(String[] commCondition) {
		this.commCondition = commCondition;
	}
  
}
