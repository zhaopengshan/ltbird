package com.leadtone.mas.bizplug.smsmo.thread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.sms.dao.GwSmsMoLogDao;
import com.leadtone.mas.bizplug.sms.dao.SmsMoLogDao;
import com.leadtone.mas.bizplug.smsmo.task.EmppMoToUpLogTask;
import com.leadtone.mas.bizplug.smsmo.task.LtDxMoToUpLogTask;
import com.leadtone.mas.bizplug.smsmo.task.QxtMoToUpLogTask;
import com.leadtone.mas.bizplug.smsmo.task.QxtNewMoToUpLogTask;
import com.leadtone.mas.bizplug.smsmo.task.YdMoToUpLogTask;
import com.leadtone.mas.util.spring.AppConfig;
import com.leadtone.mas.util.spring.BeanContainer;
public class MoToUpLogThreadDispatcher implements Runnable {
	
	private final Logger logger = Logger.getLogger(MoToUpLogThreadDispatcher.class);
	public ThreadPoolExecutor smsTaskThreadPool;
	
	private GwSmsMoLogDao gwSmsMoLogDao;
	private SmsMoLogDao smsMoLogDao;
	
	public MoToUpLogThreadDispatcher(){
		String poolSize = AppConfig.getValueAsString("sms.thread.poolSize");
		smsTaskThreadPool = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(poolSize == null ? 2 : Integer
						.parseInt(poolSize));
	}

	@Override
	public void run() {
		logger.info("mo to up log轮询启动");
		gwSmsMoLogDao = (GwSmsMoLogDao) BeanContainer.getBean("gwSmsMoLogDao");
		smsMoLogDao = (SmsMoLogDao) BeanContainer.getBean("smsMoLogDao");
		while(true){
			try{
				ArrayList<Callable<Integer>> callList = new ArrayList<Callable<Integer>>();
				YdMoToUpLogTask sendTaskYd = new YdMoToUpLogTask(gwSmsMoLogDao, smsMoLogDao);
				LtDxMoToUpLogTask sendTaskLtDx = new LtDxMoToUpLogTask(gwSmsMoLogDao, smsMoLogDao);
				QxtMoToUpLogTask sendTaskQxt = new QxtMoToUpLogTask(gwSmsMoLogDao, smsMoLogDao);
				QxtNewMoToUpLogTask sendTaskQxtNew = new QxtNewMoToUpLogTask(gwSmsMoLogDao, smsMoLogDao);
				EmppMoToUpLogTask sendTaskEmpp = new EmppMoToUpLogTask(gwSmsMoLogDao, smsMoLogDao);
				callList.add(sendTaskYd);
				callList.add(sendTaskLtDx);
				callList.add(sendTaskQxt);
				callList.add(sendTaskQxtNew);
				callList.add(sendTaskEmpp);
				smsTaskThreadPool.invokeAll(callList, 10, TimeUnit.MINUTES);
				Thread.sleep(100);
			}catch(Exception e){
				logger.error("mo to up log短信处理异常",e);
				e.printStackTrace();
			}
		}
	}
}
