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

import com.leadtone.driver.task.SmsReceiveTask;
import com.leadtone.util.ProperUtil;
public class SmsReceiveThreadDispatcher implements Runnable {
	
	private static HashMap<String,String> zXTAccountMap;
	Logger logger = Logger.getLogger(SmsReceiveThreadDispatcher.class);
	public ThreadPoolExecutor smsTaskThreadPool;
	
	public SmsReceiveThreadDispatcher(){
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
		String moIp = ProperUtil.readValue("sms.http.newzxt.mo.url");
		String rptIp = ProperUtil.readValue("sms.http.newzxt.rpt.url");
		logger.info("短信ZXT上行/状态报告启动");
		while(true){
			try{
				ArrayList<Callable<Integer>> callList = new ArrayList<Callable<Integer>>();
				Iterator<Entry<String, String>> iter = zXTAccountMap.entrySet().iterator();
				while(iter.hasNext()){
					Entry<String, String> entry  =  (Entry<String, String>) iter.next();
					String merchantPin= (String) entry.getKey();
					String accountInfoStr =(String) entry.getValue();
					String[] accountInfo = accountInfoStr.split(",");
					SmsReceiveTask sendTask = new SmsReceiveTask(Long.parseLong(merchantPin), accountInfo[0], accountInfo[1], moIp, rptIp);
					callList.add(sendTask);
				}
//				smsTaskThreadPool.invokeAll(callList);
				if(callList!=null&&callList.size()>0){
					smsTaskThreadPool.invokeAll(callList, 10, TimeUnit.MINUTES);
				}
				//
//				Thread.sleep(10000);
			}catch(Exception e){
				logger.error("ZXT短信处理异常",e);
				e.printStackTrace();
			}
		}
	}
	
	private void getAccountInfo(){
		if(zXTAccountMap==null){
			zXTAccountMap = new HashMap<String,String>();
		}
		if(zXTAccountMap.isEmpty()){
		    String accountInfo = ProperUtil.readValue("sms.http.send.zxt.account.info");
		    String [] array = accountInfo.split(";");
		    for(int i=0;i<array.length;i++){
			    String[] tem = array[i].split(":");
			    zXTAccountMap.put(tem[0], tem[1]);
		    }
		}
	}
}
