package com.leadtone.mas;

import org.apache.log4j.Logger;

import com.leadtone.mas.empp.EMPPLoader;
import com.leadtone.mas.empp.servlet.StartServlet;

import empp2.cef.spring.MasBeanFactory;

public class StartupTest {
	
	private static Logger logger = Logger.getLogger(StartupTest.class);
	public static String[] args = null;
	/**
	 * @param args
	 */
	public static void main(String[] arg) {
		try {
			String path = "D:\\temp\\sms_empp\\src\\main\\resources\\";
			args = new String[3];
			args[0] = path + "applicationContext.xml";
			args[1] = path + "log4j.properties";
			args[2] = path + "subsystem.properties";

			logger.error("Begin Initialize MAS InitMasDBServlet!");
			MasBeanFactory.init(args[0]);
			logger.info("Initialize MAS InitMasDBServlet Done!");

			StartServlet.startSms();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}

	public static void startSms() throws Exception {
		EMPPLoader.StartUp();
	}
}
