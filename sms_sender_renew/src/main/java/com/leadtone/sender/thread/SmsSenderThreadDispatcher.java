package com.leadtone.sender.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import com.leadtone.sender.service.ISmsService;
import com.leadtone.sender.task.SmsSendTask;
import com.leadtone.util.DateUtil;
import com.leadtone.util.ProperUtil;
import com.leadtone.util.SpringUtils;

public class SmsSenderThreadDispatcher implements Runnable {
	
	Logger logger = Logger.getLogger(SmsSenderThreadDispatcher.class);
	public ThreadPoolExecutor smsTaskThreadPool;
	private ISmsService smsService;
//	private long timeCC = System.currentTimeMillis();
	
	public SmsSenderThreadDispatcher(){
		String poolSize = ProperUtil.readValue("sms.thread.poolSize");
		smsTaskThreadPool = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(poolSize == null ? 4 : Integer
						.parseInt(poolSize));
		smsService = (ISmsService) SpringUtils.getBean("smsService");
	}

	@Override
	public void run() {
		//启动加载参数，参数改动需要重启噢
		while(true){
//			logger.info("短信调度轮询新一轮启动");
//			logger.info("start time ****************" + (System.currentTimeMillis() - timeCC) );
			try{
				List merchantList = smsService.getMerchantPinList(0, DateUtil.getNowFormatTime("yyyy-MM-dd HH:mm:ss"));
				ArrayList<Callable<Integer>> callList = new ArrayList<Callable<Integer>>();
//				logger.info("runing time1 ****************" + (System.currentTimeMillis() - timeCC) );
				for(int i=0; merchantList != null && i < merchantList.size(); i++){
					Map map = (Map) merchantList.get(i);
					Long merchantPin = (Long)map.get("merchant_pin");
					String provinceStr = (String)map.get("province");
					SmsSendTask sendTask = new SmsSendTask(merchantPin, provinceStr);
					callList.add(sendTask);
				}
//				logger.info("runing time2 ****************" + (System.currentTimeMillis() - timeCC) );
				if( callList!=null && callList.size() > 0 ){
//					smsTaskThreadPool.invokeAll(callList);
					smsTaskThreadPool.invokeAll(callList, 10, TimeUnit.MINUTES);
				}else{
					Thread.sleep(1000);
				}
//				logger.info("end time ****************" + (System.currentTimeMillis() - timeCC) );
			}catch(Exception e){
				e.printStackTrace();
			}
//			logger.info("短信调度轮询新一轮循环结束");
		}
	}
}
