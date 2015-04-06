package com.leadtone.mas.connector.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.crypto.Cipher;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.j2eebestpractice.ssiframework.util.DateUtil;

import com.leadtone.mas.connector.dao.PortalUserDao;
import com.leadtone.mas.connector.dao.SmsGetReportDao;
import com.leadtone.mas.connector.dao.SmsReceiveDao;
import com.leadtone.mas.connector.dao.SmsSendDao;
import com.leadtone.mas.connector.domain.PortalUser;
import com.leadtone.mas.connector.domain.SmsGetReport;
import com.leadtone.mas.connector.domain.SmsReceive;
import com.leadtone.mas.connector.domain.SmsSend;
import com.leadtone.mas.connector.utils.EncryptUtils;
import com.leadtone.mas.connector.utils.Nodelet;
import com.leadtone.mas.connector.utils.PinGen;
import com.leadtone.mas.connector.utils.StringUtil;
import com.leadtone.mas.connector.utils.XmlUtils;

/**
 * 
 * @author hejiyong
 * date:2013-1-21
 * 
 */


public interface CoreInterface {
	public Map<String,String> parseXmlHead(String xmlDoc);
	public String processAPRequest(String requestType,String message,Map<String,String> xmlHead);
}
