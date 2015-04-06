package com.leadtone.mas.empp.servlet;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.leadtone.mas.empp.EMPPLoader;

import empp2.cef.spring.MasBeanFactory;

/**
 * Servlet implementation class StartServlet
 */
public class StartServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(StartServlet.class);
	
	public static Logger runLogger = Logger.getLogger("run");

	public static String[] args = null;

	/**
	 * Default constructor.
	 */
	public StartServlet() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();

		try {
//			String path = config.getServletContext().getRealPath("/")
//					+ config.getInitParameter("path");
			String path = "D:\\temp\\sms_empp\\src\\main\\resources\\";
			args = new String[3];
			args[0] = path + "applicationContext.xml";
			args[1] = path + "log4j.properties";
			args[2] = path + "config.properties";

			logger.info("Begin Initialize MAS InitMasDBServlet!");
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
