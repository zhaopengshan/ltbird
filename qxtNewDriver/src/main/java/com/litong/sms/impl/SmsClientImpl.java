package com.litong.sms.impl;

//import org.apache.commons.codec.digest.DigestUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.litong.sms.ISmsClient;
import com.litong.utils.HttpTool;

public class SmsClientImpl implements ISmsClient {
	public String smsSend( String url, String username, String pwd, String phone,
			String msgcontent, String ext, String mttime ) {
		StringBuffer sb = new StringBuffer();
		sb.append("ua=").append(username)
		.append("&pw=").append(pwd)
		.append("&mb=").append(phone)
		.append("&ms=").append(msgcontent)
		.append("&tm=").append(mttime);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());
	}
	
	public static String smsCount(String username, String pwd, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("ua=").append(username)
		.append("&pw=").append(pwd);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());
	}
	public static String sendMsg(String url, String username, String pwd, String phone,
			String msgcontent, String ext, String mttime){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mttime = df.format(new Date());
		return new SmsClientImpl().smsSend(url, username, pwd, phone, msgcontent, ext, mttime);
	} 
	public static String smsRpt(String username, String pwd, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("ac=").append("rpt")
		.append("&ua=").append(username)
		.append("&pw=").append(pwd);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());
	}
	public static String smsMo(String username, String pwd, String url) {
		StringBuffer sb = new StringBuffer();
		sb.append("ua=").append(username)
		.append("&pw=").append(pwd)
		.append("&ac=").append("mo");
		return new HttpTool().sendPostHttpRequest(url, sb.toString());
	}
    public static void main(String args[]){
    	
    	try {
//    		System.out.println(smsCount("tj_ltdx","243352","http://115.29.249.207:18005/balance.do"));//调用短信剩余条数查询接口
//    		System.out.println(smsRpt("tj_ltdx","243352","http://115.29.249.207:18008/getrpt"));// 调用短信状态报告接口
//    		System.out.println(smsMo("tj_ltdx","243352","http://115.29.249.207:18008/getrpt"));//调用上行短信接口
//    		System.out.println(sendMsg("http://115.29.249.207:18002/send.do","tj_ltdx","243352","15901533921","ceshidua11nxin【先特网络】",null,null));//调用短信剩余条数查询接口
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
}
