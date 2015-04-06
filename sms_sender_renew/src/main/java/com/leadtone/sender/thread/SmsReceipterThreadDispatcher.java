package com.leadtone.sender.thread;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.leadtone.sender.task.EmppReceipterTask;
import com.leadtone.sender.task.GatewayReceipterTask;
import com.leadtone.sender.task.ModemReceipterTask;
import com.leadtone.sender.task.QxtNewReceipterTask;
import com.leadtone.sender.task.QxtReceipterTask;
import com.leadtone.sender.task.ZxtReceipterTask;
import com.leadtone.util.ProperUtil;

public class SmsReceipterThreadDispatcher implements Runnable {

	Logger logger = Logger.getLogger(SmsReceipterThreadDispatcher.class);
	private ThreadPoolExecutor smsTaskThreadPool;
	
	private String modemReceipterSwitch;
	private String gatewayReceipterSwitch;
	private String zxtReceipterSwitch;
	private String qxtReceipterSwitch;
	private String qxtNewReceipterSwitch;
	private String emppReceipterSwitch;
	
	public SmsReceipterThreadDispatcher(){
		smsTaskThreadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
		modemReceipterSwitch = ProperUtil.readValue("sms.receipter.modem");
		gatewayReceipterSwitch = ProperUtil.readValue("sms.receipter.gateway");
		zxtReceipterSwitch = ProperUtil.readValue("sms.receipter.zxt");
		qxtReceipterSwitch = ProperUtil.readValue("sms.receipter.qxt");
		qxtNewReceipterSwitch = ProperUtil.readValue("sms.receipter.qxtnew");
		emppReceipterSwitch = ProperUtil.readValue("sms.receipter.empp");
	}
	
	@Override
	public void run() {
		logger.info("回执轮询添加！");
		if ("true".equals(gatewayReceipterSwitch)) {
			logger.info("网关短信回执轮询添加！");
		}
		if ("true".equals(modemReceipterSwitch)) {
			logger.info("猫池短信回执轮询添加！");
		}
		if ("true".equals(zxtReceipterSwitch)) {
			logger.info("ZXT回执轮询添加！");
		}
		if ("true".equals(qxtReceipterSwitch)) {
			logger.info("QXT回执轮询添加！");
		}
		if ("true".equals(qxtNewReceipterSwitch)) {
			logger.info("QXTNEW回执轮询添加！");
		}
		if ("true".equals(emppReceipterSwitch)) {
			logger.info("EMPP回执轮询添加！");
		}
		while(true){
			try{
				ArrayList<Callable<Integer>> callList = new ArrayList<Callable<Integer>>();
				if ("true".equals(gatewayReceipterSwitch)) {
					GatewayReceipterTask gatewayTask = new GatewayReceipterTask();
					callList.add(gatewayTask);
				}
				if ("true".equals(modemReceipterSwitch)) {
					ModemReceipterTask modemTask = new ModemReceipterTask();
					callList.add(modemTask);
				}
				if ("true".equals(zxtReceipterSwitch)) {
					ZxtReceipterTask zxtTask = new ZxtReceipterTask();
					callList.add(zxtTask);
				}
				if ("true".equals(qxtReceipterSwitch)) {
					QxtReceipterTask qxtTask = new QxtReceipterTask();
					callList.add(qxtTask);
				}
				if ("true".equals(qxtNewReceipterSwitch)) {
					QxtNewReceipterTask qxtNewTask = new QxtNewReceipterTask();
					callList.add(qxtNewTask);
				}
				if ("true".equals(emppReceipterSwitch)) {
					EmppReceipterTask emppTask = new EmppReceipterTask();
					callList.add(emppTask);
				}
//				smsTaskThreadPool.invokeAll(callList);
				smsTaskThreadPool.invokeAll(callList, 10, TimeUnit.MINUTES);
				Thread.sleep(1000);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
