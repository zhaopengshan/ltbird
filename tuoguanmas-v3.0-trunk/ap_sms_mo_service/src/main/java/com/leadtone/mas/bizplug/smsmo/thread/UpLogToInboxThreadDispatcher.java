package com.leadtone.mas.bizplug.smsmo.thread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.smsmo.service.SmsMoHandleService;
import com.leadtone.mas.bizplug.smsmo.task.EmppUpLogToInboxTask;
import com.leadtone.mas.bizplug.smsmo.task.LtDxUpLogToInboxTask;
import com.leadtone.mas.bizplug.smsmo.task.QxtNewUpLogToInboxTask;
import com.leadtone.mas.bizplug.smsmo.task.QxtUpLogToInboxTask;
import com.leadtone.mas.bizplug.smsmo.task.YdUpLogToInboxTask;
import com.leadtone.mas.util.spring.AppConfig;
import com.leadtone.mas.util.spring.BeanContainer;
public class UpLogToInboxThreadDispatcher implements Runnable {
	
	Logger logger = Logger.getLogger(UpLogToInboxThreadDispatcher.class);
	public ThreadPoolExecutor smsTaskThreadPool;
	
	public UpLogToInboxThreadDispatcher(){
		String poolSize = AppConfig.getValueAsString("sms.thread.poolSize");
		smsTaskThreadPool = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(poolSize == null ? 2 : Integer
						.parseInt(poolSize));
	}

	@Override
	public void run() {
		logger.info("uplog轮询启动");
		// 循环处理上行记录并入库
		SmsMoHandleService service = (SmsMoHandleService)BeanContainer.getBean("smsMoHandleService");
		while(true){
			try{
				ArrayList<Callable<Integer>> callList = new ArrayList<Callable<Integer>>();
				YdUpLogToInboxTask ydUpToInbox = new YdUpLogToInboxTask(service);
				LtDxUpLogToInboxTask ltDxUpToInbox = new LtDxUpLogToInboxTask(service);
				QxtUpLogToInboxTask qxtUpToInbox = new QxtUpLogToInboxTask(service);
				QxtNewUpLogToInboxTask qxtNewUpToInbox = new QxtNewUpLogToInboxTask(service);
				EmppUpLogToInboxTask emppUpToInbox = new EmppUpLogToInboxTask(service);
				callList.add(ydUpToInbox);
				callList.add(ltDxUpToInbox);
				callList.add(qxtUpToInbox);
				callList.add(qxtNewUpToInbox);
				callList.add(emppUpToInbox);
				smsTaskThreadPool.invokeAll(callList, 10, TimeUnit.MINUTES);
			}catch(Exception e){
				logger.error("uplog to inbox 短信处理异常",e);
				e.printStackTrace();
			}
		}
	}
}
