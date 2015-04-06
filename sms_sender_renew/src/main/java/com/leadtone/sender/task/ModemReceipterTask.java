package com.leadtone.sender.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import com.leadtone.sender.service.ISmsService;
import com.leadtone.util.SpringUtils;

public class ModemReceipterTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(ModemReceipterTask.class);
	
	private ISmsService smsService;
	
	public ModemReceipterTask(){
		if (smsService == null) {
			smsService = (ISmsService) SpringUtils.getBean("smsService");
		}
	}
	
	@Override
	public Integer call(){
		try{
//			logger.info("猫池短信回执处理开始");
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = smsService.getModemSmsResult();
			if(list!=null&&list.size()>0){
				smsService.updateSmsSendRestlt(list);
				smsService.deleteSendedSms(list);
			}
//	    	logger.info("猫池短信回执处理结束");
		}catch(Exception e){
			logger.error("猫池短信回执处理异常", e);
		}
		return 1;
	}

}
