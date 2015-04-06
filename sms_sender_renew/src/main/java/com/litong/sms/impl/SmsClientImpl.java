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
	public String smsSend(String username, String pwd, String key, String url,
			/*String extendCode, */String phone, String msgcontent, String mttime,
			String cpoid) {
		String attestation = DigestUtils.md5Hex(username + key + pwd);
		StringBuffer sb = new StringBuffer();
		sb.append("username=").append(username).append("&pwd=").append(pwd)
				.append("&attestation=").append(attestation)
				//.append("&extendCode=").append(extendCode)
				.append("&phone=")
				.append(phone).append("&msgcontent=").append(msgcontent)
				.append("&mttime=").append(mttime).append("&cpoid=")
				.append(cpoid);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());
	}

	public static String sendMsg(String username, String pwd, String key,
			String url,/* String extendCode, */String phone, String msgcontent,
			String mttime, String cpoid) {
		return new SmsClientImpl().smsSend(username, pwd, key, url,// extendCode,
				phone, msgcontent, mttime, cpoid);
	}

	public static String getReport(String username, String pwd, String key,
			String url) {
		String attestation = DigestUtils.md5Hex(username + key + pwd);
		StringBuffer sb = new StringBuffer();
		sb.append("username=").append(username).append("&pwd=").append(pwd)
				.append("&attestation=").append(attestation);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());

	}

	public static String getMOSms(String username, String pwd, String key,
			String url) {
		String attestation = DigestUtils.md5Hex(username + key + pwd);
		StringBuffer sb = new StringBuffer();
		sb.append("username=").append(username).append("&pwd=").append(pwd)
				.append("&attestation=").append(attestation);
		return new HttpTool().sendPostHttpRequest(url, sb.toString());

	}

	public static void processMORet(String rsp)
			throws ParserConfigurationException, UnsupportedEncodingException,
			SAXException, IOException {
		// logger.info("response:"+rsp);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(
				new ByteArrayInputStream(rsp.getBytes("utf-8"))));
		Element root = document.getDocumentElement();
		NodeList nList2 = document.getElementsByTagName("moResult");
		Element node2 = (Element) nList2.item(0);
		if (node2 != null) {
			NodeList results = node2.getElementsByTagName("result");
			for (int i = 0; i < results.getLength(); i++) {
				Element item = (Element) results.item(i);
				if (item != null) {
					try{
						String destno = item.getElementsByTagName("destno").item(0)
								.getFirstChild().getNodeValue();
						
						String phone = item.getElementsByTagName("phone").item(0)
								.getFirstChild().getNodeValue();
						
						String content = item.getElementsByTagName("content")
								.item(0).getFirstChild().getNodeValue();

						String datetime = item.getElementsByTagName("datetime")
								.item(0).getFirstChild().getNodeValue();

						System.out.println(destno+";"+phone + ";" + content + ";" + datetime);
						// todo
					} catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void processRPTRet(String rsp)
			throws ParserConfigurationException, UnsupportedEncodingException,
			SAXException, IOException {
		// logger.info("response:"+rsp);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new InputSource(
				new ByteArrayInputStream(rsp.getBytes("utf-8"))));
		Element root = document.getDocumentElement();
		NodeList nList2 = document.getElementsByTagName("smsResult");
		Element node2 = (Element) nList2.item(0);
		if (node2 != null) {
			NodeList results = node2.getElementsByTagName("result");
			for (int i = 0; i < results.getLength(); i++) {
				Element item = (Element) results.item(i);
				if (item != null) {
					try{
						String spnumber = item.getElementsByTagName("spnumber")
								.item(0).getFirstChild().getNodeValue();

						String phone = item.getElementsByTagName("phone").item(0)
								.getFirstChild().getNodeValue();

						String status = item.getElementsByTagName("status").item(0)
								.getFirstChild().getNodeValue();

						String rptcode = item.getElementsByTagName("rptcode")
								.item(0).getFirstChild().getNodeValue();

						String sendtime = item.getElementsByTagName("sendtime")
								.item(0).getFirstChild().getNodeValue();

						System.out.println(spnumber + ";" + phone + ";" + status
								+ ";" + rptcode + ";" + sendtime);
						// todo
					} catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void main(String args[]) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String id = "08132836477223449999";
		if (id.length() > 8) {
			id = id.substring(id.length() - 8);
		}

		int type = 1;

		try {
			String rsp = "";
			switch (type) {
			case 1:
				rsp = sendMsg("hbyd01", "hbyd11", "key",
						"http://115.28.5.74/notebook/httpmt", 
						"15901533921", URLEncoder.encode("测试接口999【你好啊】", "utf-8"),
						sdf.format(date), id);
				if ("success".equalsIgnoreCase(rsp)) {
					// ok
				}
				break;
			case 2:
				rsp = getReport("hbyd01", "hbyd11", "key",
						"http://115.28.5.74/notebook/httprpt");
//				rsp = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
//						+ "<smsResult>" 
//						+ "<result>"
//						+ "<spnumber>1233</spnumber>"
//						+ "<phone>1311111</phone>"
//						+ "<status>DELIVRD</status>"
//						+ "<rptcode>KB:001</rptcode>"
//						+ "<sendtime>aaa</sendtime>"
//						+ "</result>"
//						+ "</smsResult>";

				processRPTRet(rsp);
				break;
			case 3:
				rsp = getMOSms("hbyd01", "hbyd11", "key",
						"http://115.28.5.74/notebook/httpmo");

//				rsp = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
//						+ "<moResult>" + "<result>" + "<phone>1311111</phone>"
//						+ "<content>111</content>" + "<datetime>aaa</datetime>"
//						+ "</result>" + "<result>" + "<phone>1322222</phone>"
//						+ "<content>2222</content>"
//						+ "<datetime>bbb</datetime>" + "</result>"
//						+ "</moResult>";

				processMORet(rsp);
				break;
			}
			System.out.println("rsp:"+rsp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
