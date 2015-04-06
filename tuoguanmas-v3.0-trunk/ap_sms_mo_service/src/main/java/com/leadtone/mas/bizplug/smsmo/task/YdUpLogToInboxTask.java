package com.leadtone.mas.bizplug.smsmo.task;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.smsmo.service.SmsMoHandleService;

public class YdUpLogToInboxTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(YdUpLogToInboxTask.class);
	private SmsMoHandleService service;
	
	public YdUpLogToInboxTask(SmsMoHandleService serviceTemp){
		service = serviceTemp;
	}
	
	@Override
	public Integer call(){
		try{
			List<SmsMoLogBean> listSmsMoLogBean = service.getSmsMoLogs(0, "CMCC");
//			if (listSmsMoLogBean.size() == 0) {
//				//没有伤心记录，休眠10秒
//				try {
//					Thread.sleep(10*1000);
//				}catch(InterruptedException e) {
//					logger.info(e.getMessage());
//				}
//			}
			for (Iterator<SmsMoLogBean> it = listSmsMoLogBean.iterator(); it.hasNext();) {
				SmsMoLogBean smsMoLogBean = it.next();
				logger.info("cmcc uplog to inbox "+smsMoLogBean.toString());
				try {
					MbnSmsInbox mbnSmsInbox = service.parseContent(smsMoLogBean);
					if (mbnSmsInbox != null) {
						service.addSmsInbox(mbnSmsInbox);
					}
					smsMoLogBean.setStatus(1);
					service.updateSmsMoLogStatus(smsMoLogBean);
				} catch(Exception e) {
					logger.error("YD up log to inbox 短信处理异常smsMoLogBean "+smsMoLogBean.toString(), e);
				}
			}
		}catch(Exception e){
			logger.error("YD up log to inbox 短信处理异常",e);
		}
		return 1;
	}

	public SmsMoHandleService getService() {
		return service;
	}
}
