package com.leadtone.mas.bizplug.smsmo.task;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBeanVO;
import com.leadtone.mas.bizplug.smsmo.service.SmsMoHandleService;

public class QxtNewUpLogToInboxTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(QxtNewUpLogToInboxTask.class);
	private SmsMoHandleService service;
	
	public QxtNewUpLogToInboxTask(SmsMoHandleService serviceTemp){
		service = serviceTemp;
	}
	
	@Override
	public Integer call(){
		try{
			List<SmsMoLogBeanVO> listSmsMoLogBean = service.queryByStatusVo(0, "QXTNEW");
//			if (listSmsMoLogBean.size() == 0) {
//				//没有伤心记录，休眠10秒
//				try {
//					Thread.sleep(10*1000);
//				}catch(InterruptedException e) {
//					logger.info(e.getMessage());
//				}
//			}
			for (Iterator<SmsMoLogBeanVO> it = listSmsMoLogBean.iterator(); it.hasNext();) {
				SmsMoLogBeanVO smsMoLogBean = it.next();
				logger.info("qxtnew uplog to inbox "+smsMoLogBean.toString());
				try {
					MbnSmsInbox mbnSmsInbox = service.parseContent(smsMoLogBean);
					if (mbnSmsInbox != null) {
						service.addSmsInbox(mbnSmsInbox);
					}
					smsMoLogBean.setStatus(1);
					service.updateSmsMoLogStatus(smsMoLogBean);
				} catch(Exception e) {
					logger.error("qxtnew up log to inbox 短信处理异常smsMoLogBean "+smsMoLogBean.toString(), e);
				}
			}
		}catch(Exception e){
			logger.error("qxtnew up log to inbox 短信处理异常",e);
		}
		return 1;
	}

	public SmsMoHandleService getService() {
		return service;
	}
}
