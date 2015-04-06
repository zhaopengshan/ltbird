package com.leadtone.mas.connector.core;

import java.util.Date;
import java.util.List;

import com.leadtone.mas.connector.domain.SmsInboxDbIntf;
import com.leadtone.mas.connector.domain.SmsOutboxDbIntf;
import com.leadtone.mas.connector.domain.SmsSentDbIntf;


public interface SmsDbIntfService {

	public List<SmsOutboxDbIntf> getSmsOutbox();
	
	public void deleteSmsOutbox(String masSmsId);
	
	public void insertSmsSent(SmsSentDbIntf smsSent);
	
	public List<SmsSentDbIntf> getWaitRptList();
	
	public void updateSmsSent(SmsSentDbIntf smsSent);
	
	public void deleteSmsSentByTime(Date date);
	
	public void insertSmsInbox(SmsInboxDbIntf smsInbox);
	
	public void deleteSmsInboxByTime(Date date);
}
