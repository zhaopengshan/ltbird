package com.leadtone.mas.admin.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leadtone.mas.bizplug.util.WebUtils;

public class ZXTUserTool {
	// URL
	private static String url = "http://127.0.0.1:8080/zxt.user.webservice/deliverMessage";

	private static final Log log = LogFactory.getLog(ZXTUserTool.class);
	private static int HTTP_TIMEOUT = 60 * 1000;

	static {
        if(!org.apache.commons.lang3.StringUtils.isBlank(WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.ZXTURL))){
        	url = WebUtils.getPropertyByName(com.leadtone.mas.admin.common.ApSmsConstants.ZXTURL);                   
        }
	}
	
	// 发送数据
	public static String toGateway(NameValuePair[] pairs) {
		PostMethod filePost = new PostMethod(url);
		filePost.setRequestBody(pairs);
		int status = 0;
		String response = null;
		try {
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
					.setConnectionTimeout(HTTP_TIMEOUT);
			client.getHttpConnectionManager().getParams()
					.setSoTimeout(30 * 1000);
			client.getParams().setContentCharset("UTF-8");
			client.getParams().setHttpElementCharset("UTF-8");
			status = client.executeMethod(filePost);
			InputStream is = filePost.getResponseBodyAsStream();			
			response = IOUtils.toString(is, "UTF-8");
			
			Header userId = filePost.getResponseHeader("id");
			if(userId!=null){
				response = userId.getValue();
			}
		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
			log.info("toGateway exception:"+ e.getMessage());
		} catch (HttpException e) {
//			e.printStackTrace();
			log.info("toGateway exception:"+ e.getMessage());
		} catch (IOException e) {
//			e.printStackTrace();
			log.info("toGateway exception:"+ e.getMessage());
		} finally {
			filePost.releaseConnection();
		}
		log.info("http rsp status: " + status + ";响应报文:\n" + response);
		return (status == HttpStatus.SC_OK) ? response : "";
	}

	/**
	 * 添加用户
	 * @param account
	 * @param password
	 * @param userId
	 * @param extno
	 * @return
	 */
	public static int addUser(String account, String password,
			String parentId, String extno) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new NameValuePair("optType", "AddUser"));
		params.add(new NameValuePair("account", account));
		params.add(new NameValuePair("password", password));
		params.add(new NameValuePair("parentuserid", parentId));
		params.add(new NameValuePair("extno", extno));
		//sunyadong add
		params.add(new NameValuePair("price", "1"));
		params.add(new NameValuePair("createtime", "2013-08-20 17:58:09"));
		params.add(new NameValuePair("iscall", "1"));
		params.add(new NameValuePair("islongsms", "1"));
		params.add(new NameValuePair("charge", "1"));
		params.add(new NameValuePair("isstatus", "2"));
		params.add(new NameValuePair("isrestore", "1"));
		params.add(new NameValuePair("smslong", "70"));
		//pay
		params.add(new NameValuePair("usertype", "5"));
		params.add(new NameValuePair("businessuserid", "10"));
		params.add(new NameValuePair("financialuserid", "10"));
		params.add(new NameValuePair("serviceuserid", "10"));
		params.add(new NameValuePair("isaudit", "0"));
		params.add(new NameValuePair("custgroupid", "1"));
		params.add(new NameValuePair("isreissue", "0"));

		String rsp = toGateway((NameValuePair[]) params
				.toArray(new NameValuePair[params.size()]));
		int userId = 0;
		try{
			userId = Integer.parseInt(rsp);
		} catch(Exception e){
			log.info("addUser:"+ e.getMessage());
		}
		
		return userId;
	}
	
	public static int addCorpUser(String account, String password, String extno) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new NameValuePair("optType", "AddCorpUser"));
		params.add(new NameValuePair("account", account));
		params.add(new NameValuePair("password", password));
		params.add(new NameValuePair("extno", extno));
		//sunyadong add
		params.add(new NameValuePair("price", "1"));
		params.add(new NameValuePair("createtime", "2013-08-20 17:58:09"));
		params.add(new NameValuePair("iscall", "1"));
		params.add(new NameValuePair("islongsms", "1"));
		params.add(new NameValuePair("charge", "1"));
		params.add(new NameValuePair("isstatus", "2"));
		params.add(new NameValuePair("isrestore", "1"));
		params.add(new NameValuePair("smslong", "70"));
		params.add(new NameValuePair("usertype", "4"));
		params.add(new NameValuePair("businessuserid", "10"));
		params.add(new NameValuePair("financialuserid", "10"));
		params.add(new NameValuePair("serviceuserid", "10"));
		params.add(new NameValuePair("isaudit", "0"));
		params.add(new NameValuePair("custgroupid", "1"));
		params.add(new NameValuePair("isreissue", "0"));

		String rsp = toGateway((NameValuePair[]) params
				.toArray(new NameValuePair[params.size()]));
		int userId = 0;
		try{
			userId = Integer.parseInt(rsp);
		} catch(Exception e){
			log.info("addCorpUser:"+ e.getMessage());
		}
		
		return userId;
	}

	/**
	 * 删除用户
	 * @param account
	 * @param password
	 * @return
	 */
	public static String delUser(String account, String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new NameValuePair("optType", "DelUser"));
		params.add(new NameValuePair("account", account));
		params.add(new NameValuePair("password", password));

		return toGateway((NameValuePair[]) params
				.toArray(new NameValuePair[params.size()]));
	}

	

	public static void main(String[] args) {
		String name = "chengl";
		String pwd = "AApwd";
		String userId = "41";
		String extno = "1243";
		int type = 1;
		if (type == 0) {
			addUser(name, pwd, userId, extno);			
		} else if (type == 1) {
			addCorpUser(name, pwd,extno);
		} else if (type == 2) {
			delUser(name, pwd);
		} 
	}

}
