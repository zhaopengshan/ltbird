package com.leadtone.sender;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import com.leadtone.sender.thread.SmsReceipterThreadDispatcher;
import com.leadtone.sender.thread.SmsSenderThreadDispatcher;
import com.leadtone.util.ProperUtil;

public class Sender {
	private static ExecutorService execSender = Executors.newSingleThreadExecutor();
	private static ExecutorService smsReceipter = Executors.newSingleThreadExecutor();
	Logger logger = Logger.getLogger(Sender.class);
	private SmsSenderThreadDispatcher smsSenderDispatcher;
	private SmsReceipterThreadDispatcher SmsReceipterDispatcher;
	
	public void polling() {
		logger.info("短信调度轮询启动！");
		ProperUtil.setLocalPath("config.proterties");
		
		try {
			//发送启动
			smsSender();
			//接收启动
			smsReceipter();
		} catch (Exception e) {
			logger.error("启动短信调度失败！原因：" + e.toString());
			e.printStackTrace();
		}
	}

	// 网关短信发送线程启动
	@SuppressWarnings({ "unchecked" })
	private void smsSender() throws InterruptedException,ExecutionException {
		//网关 猫 
		if (smsSenderDispatcher == null) {
			smsSenderDispatcher = new SmsSenderThreadDispatcher();
			execSender.execute(smsSenderDispatcher);
		}
	}
	
	private void smsReceipter(){
		if (SmsReceipterDispatcher == null) {
			SmsReceipterDispatcher = new SmsReceipterThreadDispatcher();
			smsReceipter.execute(SmsReceipterDispatcher);
		}
	}
	
	public static void main(String[] args) {
		Sender sender = new Sender();
		sender.polling();
	}
}
