package com.leadtone.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * 用于发送HTTP请求的工具类
 * 
 * @author zy
 */
public class HttpClientUtil {
	/**
	 * 向指定的 IP, 端口, 路径发送 HTTP POST 请求, HTTP BODY 为
	 * content,并且在URL路径后加入一个method=method的字符串
	 * 
	 * @param ip
	 * @param port
	 * @param url
	 * @param content
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String smsSendPost(String ip, String userId, String account,
			String password, String mobile, String content)
			throws HttpException, IOException {
		PostMethod post = null;
		content = URLEncoder.encode(content,"utf-8");
		try {
			HttpClient client = new HttpClient();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	Date date = new Date();
	    	String time = URLEncoder.encode(sdf.format(date),"utf-8");
			
			post = new PostMethod("http://" + ip
					+ "/sms.aspx?action=send&userid=" + userId + "&account="
					+ account + "&password=" + password + "&mobile=" + mobile
					+ "&content=" + content + "&sendTime="+time+"&checkcontent=1");
			StringRequestEntity entity = new StringRequestEntity(content,
					"text/xml", "utf-8");
			// post.setRequestBody(content);
			post.setRequestEntity(entity);
			// post.setRequestContentLength(content.length());
			post.setContentChunked(false);
			int result = client.executeMethod(post);
			if (result == HttpStatus.SC_OK) {
				String resp = post.getResponseBodyAsString();
				return resp;
			} else {
				throw new HttpException("HTTP 访问失败。");
			}
		} finally {
			if (post != null)
				post.releaseConnection();
		}
	}

	public static String smsSendPost2(String ip, String userId, String account,
			String password, String mobile, String content)
			throws HttpException, IOException {
		PostMethod post = null;
		String contentType ="15";
		if(content.length()>67){
			contentType="8";
		}
		content = URLEncoder.encode(content,"GBK");
		try {
			HttpClient client = new HttpClient();
			String str = "http://" + ip
					+ "/QxtSms/QxtFirewall?OperID="
					+ account + "&OperPass=" + password + "&SendTime=&ValidTime=&AppendID="+userId+"&DesMobile=" + mobile
					+ "&Content=" + content + "&ContentType=" +contentType;
			post = new PostMethod(str);
			StringRequestEntity entity = new StringRequestEntity(content,
					"text/xml", "utf-8");
			// post.setRequestBody(content);
			post.setRequestEntity(entity);
			// post.setRequestContentLength(content.length());
			post.setContentChunked(false);
			int result = client.executeMethod(post);
			if (result == HttpStatus.SC_OK) {
				String resp = post.getResponseBodyAsString();
				return resp;
			} else {
				throw new HttpException("HTTP 访问失败。");
			}
		} finally {
			if (post != null)
				post.releaseConnection();
		}
	}
	
	public static String getSmsReceipterPost(String ip, String userId,
			String account, String password)
			throws HttpException, IOException {
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();
			post = new PostMethod("http://" + ip
					+ "/statusApi.aspx?action=query&userid=" + userId + "&account="
					+ account + "&password=" + password);
			post.setContentChunked(false);
			int result = client.executeMethod(post);
			if (result == HttpStatus.SC_OK) {
				String resp = post.getResponseBodyAsString();
				return resp;
			} else {
				throw new HttpException("HTTP 访问失败。");
			}
		} finally {
			if (post != null)
				post.releaseConnection();
		}
	}
	
	/**
	 * 
	 * @param ip
	 * @param userId
	 * @param account
	 * @param password
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String getUpSms(String ip, String userId,
			String account, String password)
			throws HttpException, IOException {
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();
			post = new PostMethod("http://" + ip
					+ "/callApi.aspx?action=query&userid=" + userId + "&account="
					+ account + "&password=" + password);
			post.setContentChunked(false);
			int result = client.executeMethod(post);
			if (result == HttpStatus.SC_OK) {
				String resp = post.getResponseBodyAsString();
				return resp;
			} else {
				throw new HttpException("HTTP 访问失败。");
			}
		} finally {
			if (post != null)
				post.releaseConnection();
		}
	}

	/**
	 * 向url路径发送 HTTP POST 请求, HTTP BODY 内容为 content
	 * 
	 * @param url
	 * @param content
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public static String post(String url, String content) throws HttpException,
			IOException {
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();
			post = new PostMethod(url);
			StringRequestEntity entity = new StringRequestEntity(content,
					"text/xml", "utf-8");
			// post.setRequestBody(content);
			post.setRequestEntity(entity);
			// post.setRequestContentLength(content.length());
			post.setContentChunked(false);
			int result = client.executeMethod(post);
			if (result == HttpStatus.SC_OK) {
				String resp = post.getResponseBodyAsString();
				return resp;
			} else {
				throw new HttpException("HTTP 访问失败。");
			}
		} finally {
			if (post != null)
				post.releaseConnection();
		}
	}

	/**
	 * 请求指定URL,把返回结果转成指定字符集返回
	 */
	public static String post2(String url, String charset)
			throws HttpException, IOException {
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();
			post = new PostMethod(url);
			post.setRequestHeader("Content-Type", "text/html;charset="
					+ charset);
			post.setContentChunked(false);
			int result = client.executeMethod(post);
			if (result == HttpStatus.SC_OK) {
				String resp = new String(post.getResponseBody(), charset);
				return resp;
			} else {
				throw new HttpException("HTTP 访问失败。");
			}
		} finally {
			if (post != null)
				post.releaseConnection();
		}
	}

	public static String sendEMail(String url, String uid, String timestamp,
			String pwd, String xmlContent) throws HttpException, IOException {
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();
			post = new PostMethod(url);
			post.setRequestHeader("uid", uid);
			post.setRequestHeader("timestamp", timestamp);
			post.setRequestHeader("pwd", pwd);

			StringRequestEntity entity = new StringRequestEntity(xmlContent,
					"text/xml", "utf-8");
			post.setRequestEntity(entity);
			post.setContentChunked(false);

			int result = client.executeMethod(post);
			if (result == HttpStatus.SC_OK) {
				String resp = new String(post.getResponseBody(), "UTF-8");

				System.out.println(xmlContent);
				System.out.println("\n");
				System.out.println(resp);
				return resp;
			} else {
				throw new HttpException("HTTP 访问失败。");
			}
		} finally {
			if (post != null)
				post.releaseConnection();
		}
	}

	
	 public static void main(String[] args){
		 try {
			String rsp = smsSendPost("210.51.1.89:5888","41","hbtest","hb123456","13671382401","abc");
			
			System.out.println(rsp);
			
			rsp = getUpSms("210.51.1.89:5888","41","hbtest","hb123456");
			System.out.println(rsp);
//    	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
//            DocumentBuilder builder = factory.newDocumentBuilder();
//	        Document document = builder.parse(new InputSource(new ByteArrayInputStream(rsp.getBytes("utf-8"))));
//	        Element root = document.getDocumentElement();
//	        NodeList nList1 = document.getElementsByTagName("message");
//	        Element node1 = (Element)nList1.item(0);
//			String taskID= node1.getElementsByTagName("msgid").item(0).getFirstChild().getNodeValue();
//	        NodeList nList2 = document.getElementsByTagName("response");
//	        Element node2 = (Element)nList2.item(0);
//			String returnStatus= node2.getElementsByTagName("code").item(0).getFirstChild().getNodeValue();
//			System.out.println(taskID+"||"+returnStatus);
//			String rsp = getSmsReceipterPost("221.179.180.158:9000","41","hbyd00","hbyd00!");
//			System.out.print(rsp);
//		int [] unSorted = {19,8,6,12,5,10,0,2};
//		byte [] bitMap = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
//		for(int i=0;i<unSorted.length;i++){
//			bitMap[unSorted[i]]=1;
//		}
//		int [] sorted = new int[8];
//		int j =0;
//		for(int i=0;i<bitMap.length;i++){
//			if(bitMap[i]==1){
//				sorted[j]=i;
//				j++;
//			}
//		}
//		System.out.print(sorted);
//		int[] array= new int[100];
//		for(int i=0;i<100;i++){
//			array[i]=i+1;
//		}
//		for(int i=0;i<array.length;i++){
//		    if(array[i] / 5 % 3==1){
//
//		    }else if(6-10 21-25 ){
//			
//		    }else if(){}
//		}
//		System.out.println(3|4); 

		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
}