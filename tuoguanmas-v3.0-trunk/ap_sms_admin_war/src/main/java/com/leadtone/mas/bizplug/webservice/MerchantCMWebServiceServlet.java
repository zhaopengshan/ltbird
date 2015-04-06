package com.leadtone.mas.bizplug.webservice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.util.Xml_Bean;
import com.leadtone.mas.bizplug.webservice.bean.MasPackage;

public class MerchantCMWebServiceServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5867079550670382888L;
	private static Logger logger = Logger
			.getLogger(MerchantCMWebServiceServlet.class);
	
	@Override
	public void init() {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	/**
	 * 对方默认都是成功的 
	 * @param response
	 */
	private void writeRsp(HttpServletResponse response){
		try {
			IOUtils.write("1", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//请求 判断： id!=null 状态报告; mobile!=null 上行短信。
		String id = request.getParameter("id");
		String mobile = request.getParameter("Mobile");
		if( id != null ){
			//状态报告出来
			doStatusReply(request);
			writeRsp(response);
			return ;
		}
		if( mobile != null ){
			doMoSms(request);
			writeRsp(response);
			return ;
		}
	}
	
	/**
	 * 状态报告处理方法
	 * @param request
	 */
	private void doStatusReply(HttpServletRequest request){
		String id = request.getParameter("id");
		String status = request.getParameter("Status");
		String recvTime = request.getParameter("RecvTime");
		String sendTime = request.getParameter("SendTime");
	}
	
	/**
	 * 上行短信处理方法
	 * @param request
	 */
	private void doMoSms(HttpServletRequest request){
		String mobile = request.getParameter("Mobile");
		String appendId = request.getParameter("appendId");
		String content = request.getParameter("Content");
		String recvTime = request.getParameter("RecvTime");
	}
}
