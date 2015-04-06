package com.leadtone.driver.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import com.leadtone.driver.task.SmsSendTask;
import com.leadtone.util.ProperUtil;
public class SmsSenderThreadDispatcher implements Runnable {
	
	private static HashMap<String,String> qXTAccountMap;
	Logger logger = Logger.getLogger(SmsSenderThreadDispatcher.class);
	public ThreadPoolExecutor smsTaskThreadPool;
	
	public SmsSenderThreadDispatcher(){
		String poolSize = ProperUtil.readValue("sms.thread.poolSize");
		smsTaskThreadPool = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(poolSize == null ? 2 : Integer
						.parseInt(poolSize));
	}

	@Override
	public void run() {
		//启动加载参数，参数改动需要重启噢
		//获取资信通企业配置初始化 zXTAccountMap
		getAccountInfo();
		String ip = ProperUtil.readValue("sms.http.newqxt.send.url");
		String rptIp = ProperUtil.readValue("sms.http.newqxt.rpt.url");
		logger.info("短信QXT轮询启动"+ip);
		while(true){
//			logger.info("短信ZXT轮询启动 ");
			try{
				ArrayList<Callable<Integer>> callList = new ArrayList<Callable<Integer>>();
				Iterator<Entry<String, String>> iter = qXTAccountMap.entrySet().iterator();
				while(iter.hasNext()){
					Entry<String, String> entry  =  (Entry<String, String>) iter.next();
					String uid= (String) entry.getKey();
					String accountInfoStr =(String) entry.getValue();
//					logger.info("短信ZXT轮询启动 子系统account"+accountInfoStr);
					String[] accountInfo = accountInfoStr.split(",");
					SmsSendTask sendTask = new SmsSendTask(Long.parseLong(accountInfo[2]), accountInfo[0], accountInfo[1], uid, ip, rptIp);
					callList.add(sendTask);
				}
//				smsTaskThreadPool.invokeAll(callList);
				if(callList!=null&&callList.size()>0){
					smsTaskThreadPool.invokeAll(callList, 10, TimeUnit.MINUTES);
				}
			}catch(Exception e){
				logger.error("QXT短信处理异常",e);
				e.printStackTrace();
			}
		}
	}
	
	private void getAccountInfo(){
		if(qXTAccountMap==null){
			qXTAccountMap = new HashMap<String,String>();
		}
		if(qXTAccountMap.isEmpty()){
		    String accountInfo = ProperUtil.readValue("sms.http.send.qxt.account.info");
		    String [] array = accountInfo.split(";");
		    for(int i=0;i<array.length;i++){
			    String[] tem = array[i].split(":");
			    qXTAccountMap.put(tem[0], tem[1]);
		    }
		}
	}
}
