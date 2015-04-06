package com.leadtone.sender.task;

import java.util.List;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.service.ISmsService;
import com.leadtone.util.SpringUtils;

public class QxtReceipterTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(QxtReceipterTask.class);
	
	private ISmsService smsService;
	
	public QxtReceipterTask(){
		if (smsService == null) {
			smsService = (ISmsService) SpringUtils.getBean("smsService");
		}
	}
	
	@Override
	public Integer call(){
		try{
			List<SmsBean> list = smsService.getQxtSmsResult();
			if(list!=null&&list.size()>0){
				smsService.updateQxtSmsSendRestlt(list);
				smsService.updateQxtMoRestlt(list);
			}
		}catch(Exception e){
			logger.error("Qxt短信回执处理异常", e);
		}
		return 1;
	}

}
