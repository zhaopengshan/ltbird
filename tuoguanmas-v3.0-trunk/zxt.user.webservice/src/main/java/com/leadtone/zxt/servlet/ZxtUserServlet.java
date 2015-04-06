package com.leadtone.zxt.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.leadtone.zxt.bean.ZXTUser;
import com.leadtone.zxt.constant.AppConst;
import com.leadtone.zxt.constant.OptType;
import com.leadtone.zxt.dao.ZXTUserDao;
import com.leadtone.zxt.util.ProperUtil;
import com.leadtone.zxt.util.SpringUtils;

public class ZxtUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2184410819122081203L;
	public static final String ENCODING_REQUEST = "utf-8";
	private static ExecutorService exec;
	private Logger logger = Logger.getLogger(ZxtUserServlet.class);


	@Autowired
	private ZXTUserDao zxtUserDao;
	public ZXTUserDao getZxtUserDao() {
		return zxtUserDao;
	}

	public void setZxtUserDao(ZXTUserDao zxtUserDao) {
		this.zxtUserDao = zxtUserDao;
	}
	
	public void init(ServletConfig config) throws ServletException {
		if (exec == null) {
			try {
				logger.debug("initialized ThreadPool start. file:"
						+ AppConst.PROPERTY_FILE_NAME);
				exec = Executors.newFixedThreadPool(20);
				zxtUserDao = (ZXTUserDao) SpringUtils.getBean("zxtUserDao");
				ProperUtil.setLocalPath(AppConst.PROPERTY_FILE_NAME);
				
				
			} catch (Exception e) {
				logger.debug("initialized ThreadPool error:" + e.getMessage());
			}
		}
		logger.debug("initialized ThreadPool end.");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		logger.debug("hello,This is UploadServlet");
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			request.setCharacterEncoding("UTF-8");
			String optType = request.getParameter("optType");
			logger.info("OptType is:[" + optType + "]");
			ZXTUser user = new ZXTUser();

			String account = request.getParameter("account");
			String password = request.getParameter("password");
			String parentuserid = request.getParameter("parentuserid");
			String extno = request.getParameter("extno");

			if (OptType.ADD_USER.equalsIgnoreCase(optType)) {
				int parentuseridInt = 10;
				try{
					parentuseridInt = Integer.parseInt(parentuserid);
				} catch(Exception e){
					logger.info(optType + " parent id:[" + parentuserid +"]error");
				}
				if (account == null || password == null ||extno ==null || parentuseridInt==10) {
					out.println(optType + ":params error");
					logger.info(optType + ":params error");
					return;
				}
				user.setAccount(account);
				user.setPassword(password);
				user.setParentuserid(parentuseridInt);
				user.setExtno(extno);
				user.setIssub(0);
				user.setUsertype(5);
				zxtUserDao.addUser(user);
				
				int userid = zxtUserDao.getIdByAccount(account);
				response.setIntHeader("id", userid);
				//out.println("AddCorpUser=ok;id="+userid);
			} else if (OptType.ADD_CORP_USER.equalsIgnoreCase(optType)) {
				if (account == null || password == null ||extno ==null) {
					out.println(optType + ":params error");
					logger.info(optType + ":params error");
					return;
				}
				user.setAccount(account);
				user.setPassword(password);
				user.setExtno(extno);
				user.setParentuserid(10);
				user.setIssub(1);
				zxtUserDao.addUser(user);
				
				int userid = zxtUserDao.getIdByAccount(account);
				response.setIntHeader("id", userid);
				//out.println("AddCorpUser=ok;id="+userid);
			} else if (OptType.DEL_USER.equalsIgnoreCase(optType)) {
				if (account == null || password == null) {
					out.println(optType + ":params error");
					logger.info(optType + ":params error");
					return;
				}
				user.setAccount(account);
				user.setPassword(password);
				zxtUserDao.delUser(user);
			} else {
				out.println("Unkown optType:" + optType);
				logger.info("Unkown optType:" + optType);
				return;
			}

			out.println(optType + ":ok");
			logger.info(optType + ":ok");
		} catch (Exception e) {
			out.println("process error:" + e.getMessage());
			logger.info("process error:" + e.getMessage());
		}
	}
}
