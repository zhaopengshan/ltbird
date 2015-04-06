package com.leadtone.mas.bizplug.openaccount.bean;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;
import org.apache.struts2.json.annotations.JSONFieldBridge;
import org.apache.struts2.json.bridge.StringBridge;

public class MbnMerchantVip {
	private Long merchantPin;
	private String merchantMobile;
	private String telephone;
	private String groupCode;
	private String ufid;
	private String address;
	private String name;
	private String smsState;
	private String mmsState;
	private String province;
	private String city;
	private String region;
	private String smsAccessNumber;
	private String mmsAccessNumber;
	private String feeCode;
	private String platform;
	private String keyPrimary;
	private String keySub;
	private String keyOther;
	private Integer placeId;
	private Date createTime;
	private String email;
	private Date lastUpdateTime;
	private int corpId;
	private String corpExt;
	public MbnMerchantVip( ){
	}
	public MbnMerchantVip(Long merchantPin, String name, String province, String city, String platform, String corpExt, int corpId){
		this.merchantPin = merchantPin;
		this.province = province;
		this.city = city;
		this.platform = platform;
		this.corpExt = corpExt;
		this.corpId = corpId;
		this.name = name;
	}
	
	@JSONFieldBridge(impl = StringBridge.class)
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	public String getMerchantMobile() {
		return merchantMobile;
	}
	public void setMerchantMobile(String merchantMobile) {
		this.merchantMobile = merchantMobile;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getUfid() {
		return ufid;
	}
	public void setUfid(String ufid) {
		this.ufid = ufid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSmsState() {
		return smsState;
	}
	public void setSmsState(String smsState) {
		this.smsState = smsState;
	}
	public String getMmsState() {
		return mmsState;
	}
	public void setMmsState(String mmsState) {
		this.mmsState = mmsState;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getSmsAccessNumber() {
		return smsAccessNumber;
	}
	public void setSmsAccessNumber(String smsAccessNumber) {
		this.smsAccessNumber = smsAccessNumber;
	}
	public String getMmsAccessNumber() {
		return mmsAccessNumber;
	}
	public void setMmsAccessNumber(String mmsAccessNumber) {
		this.mmsAccessNumber = mmsAccessNumber;
	}
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getKeyPrimary() {
		return keyPrimary;
	}
	public void setKeyPrimary(String keyPrimary) {
		this.keyPrimary = keyPrimary;
	}
	public String getKeySub() {
		return keySub;
	}
	public void setKeySub(String keySub) {
		this.keySub = keySub;
	}
	public String getKeyOther() {
		return keyOther;
	}
	public void setKeyOther(String keyOther) {
		this.keyOther = keyOther;
	}
	public Integer getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Integer placeId) {
		this.placeId = placeId;
	}
	 @JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	 @JSON(format="yyyy-MM-dd HH:mm:ss")
	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public int getCorpId() {
		return corpId;
	}
	public void setCorpId(int corpId) {
		this.corpId = corpId;
	}
	public String getCorpExt() {
		return corpExt;
	}
	public void setCorpExt(String corpExt) {
		this.corpExt = corpExt;
	}
	
}
