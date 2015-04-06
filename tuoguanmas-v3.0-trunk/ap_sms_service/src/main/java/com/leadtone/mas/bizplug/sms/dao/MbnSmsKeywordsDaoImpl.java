package com.leadtone.mas.bizplug.sms.dao;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsKeywords;
import com.leadtone.mas.bizplug.sms.dao.base.SmsBaseDaoImpl;

@Repository
public class MbnSmsKeywordsDaoImpl extends
		SmsBaseDaoImpl<MbnSmsKeywords, Serializable> implements
		MbnSmsKeywordsDao {

}
