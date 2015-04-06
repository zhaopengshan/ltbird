package com.leadtone.mas.admin.webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.util.Xml_Bean;
import com.leadtone.mas.bizplug.webservice.bean.MasHeadPackage;
import com.leadtone.mas.bizplug.webservice.bean.MasPackage;

public class PackageUtils {
	private static Logger logger = Logger.getLogger(PackageUtils.class);
	
	public static String getRequestXml(HttpServletRequest request) {
		byte[] tmp = new byte[0];
		try {
			tmp = IOUtils.toByteArray(request.getInputStream());
		} catch (IOException e) {
			logger.error("Wrapper request fail", e);
		}
		return new String(tmp);
	}
	public static void writeRsp(HttpServletResponse response, MasPackage masPackage){
		try {
			String xml = Xml_Bean.java2xml(masPackage);
			IOUtils.write(xml, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static MasPackage getMasPackage(String reqXml){
		try {
			MasPackage masPack = (MasPackage) Xml_Bean.xml2java(MasPackage.class, reqXml);
			return masPack;
		} catch (Exception e) {
			logger.error("Xml parse fail", e);
		}
		return null;
	}
	
	public static String getXml(MasPackage masPack){
		try {
			return Xml_Bean.java2xml(masPack);
		} catch (Exception e) {
			logger.error("Xml parse fail", e);
		}
		return "";
	}
	
	
	public static MasPackage buildSuccessPackage(String nodeId,String methodName){
		MasHeadPackage head = new MasHeadPackage();
		head.setNodeId(nodeId);
		head.setMethodName(methodName);
		head.setReturnCode("0");
		head.setReturnMessage("ok");
		MasPackage masPack = new MasPackage();
		masPack.setHead(head);
		return masPack;
	}
	public static MasPackage buildPackage(String nodeId, String methodName,
			String entId, String returnCode, String returnMsg){
		MasHeadPackage head = new MasHeadPackage();
		head.setNodeId(nodeId);
		head.setMethodName(methodName);
		head.setReturnCode(returnCode);
		head.setReturnMessage(returnMsg);
		MasPackage masPack = new MasPackage();
		masPack.setHead(head);
		return masPack;
	}
}

