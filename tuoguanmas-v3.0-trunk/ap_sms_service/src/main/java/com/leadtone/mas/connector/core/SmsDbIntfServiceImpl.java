package com.leadtone.mas.connector.core;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.leadtone.mas.connector.dao.SmsInboxDao;
import com.leadtone.mas.connector.dao.SmsOutboxDao;
import com.leadtone.mas.connector.dao.SmsSentDao;
import com.leadtone.mas.connector.domain.SmsInboxDbIntf;
import com.leadtone.mas.connector.domain.SmsOutboxDbIntf;
import com.leadtone.mas.connector.domain.SmsSentDbIntf;

@Service("smsDbIntfService")
public class SmsDbIntfServiceImpl implements SmsDbIntfService {
	@Resource
	private SmsInboxDao smsInboxDao;
	@Resource
	private SmsOutboxDao smsOutboxDao;
	@Resource
	private SmsSentDao smsSentDao;

	@Override
	public List<SmsOutboxDbIntf> getSmsOutbox() {
		return smsOutboxDao.getAll();
	}

	@Override
	public void deleteSmsOutbox(String masSmsId) {
		smsOutboxDao.delete(masSmsId);
	}

	@Override
	public void insertSmsSent(SmsSentDbIntf smsSent) {
		smsSentDao.insert(smsSent);
	}

	@Override
	public List<SmsSentDbIntf> getWaitRptList() {
		return smsSentDao.getWaitRptList();
	}

	@Override
	public void updateSmsSent(SmsSentDbIntf smsSent) {
		smsSentDao.updateStatus(smsSent);
	}

	@Override
	public void deleteSmsSentByTime(Date date) {
		smsSentDao.deleteByTime(date);
	}

	@Override
	public void insertSmsInbox(SmsInboxDbIntf smsInbox) {
		smsInboxDao.insert(smsInbox);
	}

	@Override
	public void deleteSmsInboxByTime(Date date) {
		smsInboxDao.deleteByTime(date);
	}

}
