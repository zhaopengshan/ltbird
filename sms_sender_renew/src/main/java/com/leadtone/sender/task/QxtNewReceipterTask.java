package com.leadtone.sender.task;

import java.util.List;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.service.ISmsService;
import com.leadtone.util.SpringUtils;

public class QxtNewReceipterTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(QxtNewReceipterTask.class);
	
	private ISmsService smsService;
	
	public QxtNewReceipterTask(){
		if (smsService == null) {
			smsService = (ISmsService) SpringUtils.getBean("smsService");
		}
	}
	
	@Override
	public Integer call(){
		try{
			List<SmsBean> list = smsService.getQxtNewSmsResult();
			if(list!=null&&list.size()>0){
				smsService.updateQxtNewSmsSendRestlt(list);
				smsService.updateQxtNewProcRestlt(list);
			}
		}catch(Exception e){
			logger.error("Qxt new 短信回执处理异常", e);
		}
		return 1;
	}

}
