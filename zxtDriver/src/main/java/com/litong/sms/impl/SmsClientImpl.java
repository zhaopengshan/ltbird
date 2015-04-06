package com.litong.sms.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.litong.sms.ISmsClient;
import com.litong.utils.HttpTool;

public class SmsClientImpl implements ISmsClient {
	public String smsSend(String username, String pwd,String key,String url, String phone,
			String msgcontent, String mttime,String cpoid,String extendCode) {
		String attestation = DigestUtils.md5Hex(username+key+pwd);
		StringBuffer sb = new StringBuffer();
		sb.append("username=").append(username)
		.append("&pwd=").append(pwd)
		.append("&attestation=").append(attestation)
		.append("&phone=").append(phone)
		.append("&msgcontent=").append(msgcontent)
		.append("&mttime=").append(mttime)
		.append("&cpoid=").append(cpoid);
		//.append("&extendCode=").append(extendCode);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());
	}
	public static String smsRpt(String username, String pwd,String key,String url) {
		String attestation = DigestUtils.md5Hex(username+key+pwd);
		StringBuffer sb = new StringBuffer();
		sb.append("username=").append(username)
		.append("&pwd=").append(pwd)
		.append("&attestation=").append(attestation);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());
	}
	public static String smsMo(String username, String pwd,String key,String url) {
		String attestation = DigestUtils.md5Hex(username+key+pwd);
		StringBuffer sb = new StringBuffer();
		sb.append("username=").append(username)
		.append("&pwd=").append(pwd)
		.append("&attestation=").append(attestation);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());
	}
	public static String smsCount(String username, String pwd,String key,String url) {
		String attestation = DigestUtils.md5Hex(username+key+pwd);
		StringBuffer sb = new StringBuffer();
		sb.append("username=").append(username)
		.append("&pwd=").append(pwd)
		.append("&attestation=").append(attestation);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());
	}
	
	public static String sendMsg(String username, String pwd,String key,String url, String phone,
			String msgcontent, String mttime,String cpoid,String extendCode)
	{
		return new SmsClientImpl().smsSend(username,pwd,key,url,phone,msgcontent,mttime,cpoid,extendCode);
	} 
    public static void main(String args[]){
    	
    	try {
    		System.out.println(smsRpt("hbyd02","hbyd5270","key","http://115.28.5.74/notebook/httprpt"));// 调用短信状态报告接口
//    		System.out.println(smsMo("hbyd02","hbyd5270","key","http://115.28.5.74/notebook/httpmo"));//调用上行短信接口
//    		System.out.println(sendMsg("hbyd02","hbyd5270","key","http://115.28.5.74/notebook/httpmt","18600812629",URLEncoder.encode("test", "utf-8"),"20131105092910","111111"/*,"78895"*/));//发送短信接口
    		System.out.println(smsCount("hbyd02","hbyd5270","key","http://115.28.5.74/notebook/httpcount"));//调用短信剩余条数查询接口
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
