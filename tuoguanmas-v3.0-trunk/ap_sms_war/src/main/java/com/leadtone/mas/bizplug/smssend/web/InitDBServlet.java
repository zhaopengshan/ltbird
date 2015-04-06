package com.leadtone.mas.bizplug.smssend.web;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class InitDBServlet extends HttpServlet {
	private static final long serialVersionUID = -8440577732227095658L;
	private static Logger log = Logger.getLogger(InitDBServlet.class);
	private static Logger runLog = Logger.getLogger("run");

	@Override
	public void init() {
		innerInit();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
	}

	void innerInit() {
		log.info("Begin Initialize MAS InitMasDBServlet!");

		//MasBeanFactory.init("E:\\Workspace\\MAS3.0\\ap_sms\\conf\\spring-ap-sms.xml");
		//MasBeanFactory.init("E:\\Workspace\\MAS3.0\\conf\\masService\\conf\\spring-msgschedule.xml");
		log.info("Initialize MAS InitMasDBServlet Done!");
	}
}