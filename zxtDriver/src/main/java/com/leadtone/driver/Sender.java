package com.leadtone.driver;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

import com.leadtone.driver.thread.SmsReceiveThreadDispatcher;
import com.leadtone.driver.thread.SmsSenderThreadDispatcher;
import com.leadtone.util.ProperUtil;

public class Sender {
	private static ExecutorService execSender = Executors.newSingleThreadExecutor();
	private static ExecutorService execReceive = Executors.newSingleThreadExecutor();
	Logger logger = Logger.getLogger(Sender.class);
	private SmsSenderThreadDispatcher smsSenderDispatcher;
	private SmsReceiveThreadDispatcher smsReceiveThreadDispatcher;
	public void polling() {
		logger.info("启动ZXT！");
		ProperUtil.setLocalPath("config.proterties");
		try {
			//发送启动
			smsSender();
			//上行 状态报告
			smsReceive();
		} catch (Exception e) {
			logger.error("启动短信ZXT失败！原因：" + e.toString());
			e.printStackTrace();
		}catch(Throwable e){
			logger.error("启动短信ZXT失败！原因：" + e.toString());
		}
	}

	// ZXT短信发送线程启动
	private void smsSender() throws InterruptedException,ExecutionException {
		if (smsSenderDispatcher == null) {
			smsSenderDispatcher = new SmsSenderThreadDispatcher();
			execSender.execute(smsSenderDispatcher);
		}
	}
	private void smsReceive() throws InterruptedException,ExecutionException {
		if (smsReceiveThreadDispatcher == null) {
			smsReceiveThreadDispatcher = new SmsReceiveThreadDispatcher();
			execReceive.execute(smsReceiveThreadDispatcher);
		}
	}
	
	public static void main(String[] args) {
		Sender sender = new Sender();
		sender.polling();
	}
}
