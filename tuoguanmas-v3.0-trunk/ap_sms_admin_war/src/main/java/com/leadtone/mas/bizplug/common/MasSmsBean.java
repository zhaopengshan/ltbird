package com.leadtone.mas.bizplug.common;

import java.io.File;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import com.leadtone.mas.bizplug.addr.bean.Contacts;
import com.leadtone.mas.bizplug.config.service.MbnSevenHCodeService;
import com.leadtone.mas.bizplug.config.service.MbnThreeHCodeService;
import com.leadtone.mas.bizplug.security.service.PortalUserExtService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsOperationClassService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.bizplug.sms.service.MbnSmsTaskNumberService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantConsumeService;
import com.leadtone.mas.bizplug.tunnel.service.MbnMerchantTunnelRelationService;
import com.leadtone.mas.bizplug.tunnel.service.SmsMbnTunnelService;

public class MasSmsBean {
	
	private String title;
	private String receiver;
	private String smsText;
	private String sendType;
	private String flag; 
	private String sendTime;
	private File addrUpload;
	private String addrUploadFileName;
	private Long ydTunnel=0L;
	private Long ltTunnel=0L;
	private Long dxTunnel=0L;
	private Long tdTunnel=0L;
	private String replyText;
	private String codeType;
	private Long merchantPin;
	private int operationType;
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	private long batchId;
	public int getOperationType() {
		return operationType;
	}
	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	private MbnSmsReadySendService mbnSmsReadySendService;
	private MbnThreeHCodeService mbnThreeHCodeService;
	private MbnSevenHCodeService mbnSevenHCodeService;
	private MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService;
	private SmsMbnTunnelService smsMbnTunnelService;
	private MbnMerchantConsumeService mbnMerchantConsumeService;
	private MbnSmsOperationClassService mbnSmsOperationClassService;
	private MbnSmsTaskNumberService mbnSmsTaskNumberService;
	private PortalUserExtService portalUserExtService;
	public MbnSmsReadySendService getMbnSmsReadySendService() {
		return mbnSmsReadySendService;
	}
	public void setMbnSmsReadySendService(
			MbnSmsReadySendService mbnSmsReadySendService) {
		this.mbnSmsReadySendService = mbnSmsReadySendService;
	}
	public MbnThreeHCodeService getMbnThreeHCodeService() {
		return mbnThreeHCodeService;
	}
	public void setMbnThreeHCodeService(MbnThreeHCodeService mbnThreeHCodeService) {
		this.mbnThreeHCodeService = mbnThreeHCodeService;
	}
	public MbnSevenHCodeService getMbnSevenHCodeService() {
		return mbnSevenHCodeService;
	}
	public void setMbnSevenHCodeService(MbnSevenHCodeService mbnSevenHCodeService) {
		this.mbnSevenHCodeService = mbnSevenHCodeService;
	}
	public MbnMerchantTunnelRelationService getMbnMerchantTunnelRelationService() {
		return mbnMerchantTunnelRelationService;
	}
	public void setMbnMerchantTunnelRelationService(
			MbnMerchantTunnelRelationService mbnMerchantTunnelRelationService) {
		this.mbnMerchantTunnelRelationService = mbnMerchantTunnelRelationService;
	}
	public SmsMbnTunnelService getSmsMbnTunnelService() {
		return smsMbnTunnelService;
	}
	public void setSmsMbnTunnelService(SmsMbnTunnelService smsMbnTunnelService) {
		this.smsMbnTunnelService = smsMbnTunnelService;
	}
	public MbnMerchantConsumeService getMbnMerchantConsumeService() {
		return mbnMerchantConsumeService;
	}
	public void setMbnMerchantConsumeService(
			MbnMerchantConsumeService mbnMerchantConsumeService) {
		this.mbnMerchantConsumeService = mbnMerchantConsumeService;
	}
	public MbnSmsOperationClassService getMbnSmsOperationClassService() {
		return mbnSmsOperationClassService;
	}
	public void setMbnSmsOperationClassService(
			MbnSmsOperationClassService mbnSmsOperationClassService) {
		this.mbnSmsOperationClassService = mbnSmsOperationClassService;
	}
	public MbnSmsTaskNumberService getMbnSmsTaskNumberService() {
		return mbnSmsTaskNumberService;
	}
	public void setMbnSmsTaskNumberService(
			MbnSmsTaskNumberService mbnSmsTaskNumberService) {
		this.mbnSmsTaskNumberService = mbnSmsTaskNumberService;
	}
	public String getProv_code() {
		return prov_code;
	}
	public void setProv_code(String provCode) {
		prov_code = provCode;
	}
	private Set<Contacts> userSet;
	private String prov_code;
	
	public Set<Contacts> getUserSet() {
		return userSet;
	}
	public void setUserSet(Set<Contacts> userSet) {
		this.userSet = userSet;
	}
	public Long getMerchantPin() {
		return merchantPin;
	}
	public void setMerchantPin(Long merchantPin) {
		this.merchantPin = merchantPin;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSmsText() {
		return smsText;
	}
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	public String getSendType() {
		return sendType;
	}
	public void setSendType(String sendType) {
		this.sendType = sendType;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	public File getAddrUpload() {
		return addrUpload;
	}
	public void setAddrUpload(File addrUpload) {
		this.addrUpload = addrUpload;
	}
	public String getAddrUploadFileName() {
		return addrUploadFileName;
	}
	public void setAddrUploadFileName(String addrUploadFileName) {
		this.addrUploadFileName = addrUploadFileName;
	}
	public Long getYdTunnel() {
		return ydTunnel;
	}
	public void setYdTunnel(Long ydTunnel) {
		this.ydTunnel = ydTunnel;
	}
	public Long getLtTunnel() {
		return ltTunnel;
	}
	public void setLtTunnel(Long ltTunnel) {
		this.ltTunnel = ltTunnel;
	}
	public Long getDxTunnel() {
		return dxTunnel;
	}
	public void setDxTunnel(Long dxTunnel) {
		this.dxTunnel = dxTunnel;
	}
	public Long getTdTunnel() {
		return tdTunnel;
	}
	public void setTdTunnel(Long tdTunnel) {
		this.tdTunnel = tdTunnel;
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public String getEntSign() {
		return entSign;
	}
	public void setEntSign(String entSign) {
		this.entSign = entSign;
	}
	public String getReplyCode() {
		return replyCode;
	}
	public void setReplyCode(String replyCode) {
		this.replyCode = replyCode;
	}
	public PortalUserExtService getPortalUserExtService() {
		return portalUserExtService;
	}
	public void setPortalUserExtService(PortalUserExtService portalUserExtService) {
		this.portalUserExtService = portalUserExtService;
	}
	private String entSign;
	private String replyCode;

}
