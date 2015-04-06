package com.leadtone.mas.bizplug.smsmo.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.leadtone.mas.bizplug.smsmo.thread.MoToUpLogThreadDispatcher;
import com.leadtone.mas.bizplug.smsmo.thread.UpLogToInboxThreadDispatcher;
import com.leadtone.mas.util.spring.AppConfig;
import com.leadtone.mas.util.spring.BeanContainer;
import com.leadtone.mas.util.spring.JarTool;
import com.leadtone.mas.util.spring.MasBeanFactory;

public class SmsMoService {

	private static final Logger logger = Logger.getLogger(SmsMoService.class);
	private static ExecutorService execMoToUpLog = Executors.newSingleThreadExecutor();
	private static ExecutorService execUpLogToInbox = Executors.newSingleThreadExecutor();
	private MoToUpLogThreadDispatcher moToUpLogThreadDispatcher;
	private UpLogToInboxThreadDispatcher upLogToInboxThreadDispatcher;
	private void moToUpLog(){
		logger.info("启动mo To UpLog");
		if (moToUpLogThreadDispatcher == null) {
			moToUpLogThreadDispatcher = new MoToUpLogThreadDispatcher();
			execMoToUpLog.execute(moToUpLogThreadDispatcher);
		}
	}
	private void upLogToInbox(){
		logger.info("启动upLog To Inbox");
		if (upLogToInboxThreadDispatcher == null) {
			upLogToInboxThreadDispatcher = new UpLogToInboxThreadDispatcher();
			execUpLogToInbox.execute(upLogToInboxThreadDispatcher);
		}
	}
	private void process(){
		logger.info("启动MO service");
//		ClassPathResource resource = new ClassPathResource("spring-ap-sms-mo.xml");
//		MasBeanFactory.init(resource);
//		System.out.println(tools.getJarDir());
		JarTool tools = new JarTool(SmsMoService.class);
		PropertyConfigurator.configure(tools.getJarDir()+"/conf/log4j.properties");
	
		AppConfig.readConfig(tools.getJarDir()+"/conf/config.proterties");
		BeanContainer.initBeans();
//		MasBeanFactory.init("D:\\Source\\3.0\\trunk\\tuoguanmas-v3.0-trunk\\ap_sms_mo_service\\target\\conf\\spring-ap-sms-mo.xml");
//		SmsMoTaskService smsMoTaskService = (SmsMoTaskService)BeanContainer.getBean("smsMoTaskService");
		try {
			//启动mo to up_comm_log
			moToUpLog();
			//上行inbox
			upLogToInbox();
		} catch (Exception e) {
			logger.error("启动MO service 失败！原因：" + e.toString());
			e.printStackTrace();
		}
	}
	/**
	 * 短信上行服务类入口函数
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		SmsMoService smsMoToUpLogToInbox = new SmsMoService();
		smsMoToUpLogToInbox.process();
//		JarTool tools = new JarTool(SmsMoService.class);
//		//ClassPathResource resource = new ClassPathResource("spring-ap-sms-mo.xml");
//		//MasBeanFactory.init(resource);
//		PropertyConfigurator.configure(tools.getJarDir()+"/conf/log4j.properties");
//	
//		AppConfig.readConfig(tools.getJarDir()+"/conf/config.proterties");
//		BeanContainer.initBeans();
//		//MasBeanFactory.init("C:\\workspace\\ap_sms\\ap_sms_mo_service\\src\\main\\resources\\spring-ap-sms-mo.xml");
//
//		SmsMoTaskService smsMoTaskService = (SmsMoTaskService)BeanContainer.getBean("smsMoTaskService");
//
//		//启动短信猫上行数据轮训线程
//		smsMoTaskService.startTunnelSmsMoService();
//
//		//启动网关上行数据轮训线程
//		smsMoTaskService.startGwSmsMoService();
//
//		// 从上行记录表中获取数据
//		// 循环处理上行记录并入库
//		孙 service = (SmsMoHandleService)BeanContainer.getBean("smsMoHandleService");
//		logger.info("SMS MO Service start....");
//		while(true) {
//
//			List<SmsMoLogBean> listSmsMoLogBean = service.getSmsMoLogs(0);
//			if (listSmsMoLogBean.size() == 0) {
//				//没有伤心记录，休眠10秒
//				try {
//					Thread.sleep(10*1000);
//				}catch(InterruptedException e) {
//					logger.info(e.getMessage());
//				}
//			}
//			for (Iterator<SmsMoLogBean> it = listSmsMoLogBean.iterator(); it.hasNext();) {
//				SmsMoLogBean smsMoLogBean = it.next();
//				logger.info(smsMoLogBean.toString());
//				try {
//					MbnSmsInbox mbnSmsInbox = service.parseContent(smsMoLogBean);
//					if (mbnSmsInbox != null) {
//						service.addSmsInbox(mbnSmsInbox);
//					}
//					smsMoLogBean.setStatus(1);
//					service.updateSmsMoLogStatus(smsMoLogBean);
//				} catch(Exception e) {
//					logger.info(e.getMessage());
//				}
//			}
//		}
	}
}
