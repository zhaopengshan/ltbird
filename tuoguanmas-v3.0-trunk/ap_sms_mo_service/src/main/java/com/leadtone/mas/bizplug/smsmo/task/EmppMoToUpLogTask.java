package com.leadtone.mas.bizplug.smsmo.task;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.dao.GwSmsMoLogDao;
import com.leadtone.mas.bizplug.sms.dao.SmsMoLogDao;

public class EmppMoToUpLogTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(EmppMoToUpLogTask.class);
	
	private GwSmsMoLogDao gwSmsMoLogDao;
	private SmsMoLogDao smsMoLogDao;
	
	public EmppMoToUpLogTask(GwSmsMoLogDao gwSmsMoLogDaoTemp, SmsMoLogDao smsMoLogDaoTemp){
		gwSmsMoLogDao = gwSmsMoLogDaoTemp;
		smsMoLogDao = smsMoLogDaoTemp;
	}
	
	@Override
	public Integer call(){
		try {
			List<SmsMoLogBean> listSmsMoLogBean = this.gwSmsMoLogDao.queryByEmppStatus(0);
//			if (listSmsMoLogBean.size() == 0) {
//				//没有伤心记录，休眠10秒
//				try {
//					Thread.sleep(10*1000);
//				}catch(InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
			for (Iterator<SmsMoLogBean> it = listSmsMoLogBean.iterator(); it.hasNext();) {
				SmsMoLogBean smsMoLogBean = it.next();
				logger.info(smsMoLogBean.toString());
				this.gwSmsMoLogDao.updateEmppStatus(smsMoLogBean);
				smsMoLogBean.setId(PinGen.getSerialPin());
				smsMoLogBean.setStatus(0);
				smsMoLogBean.setType("EMPP");
				smsMoLogBean.setClassify(15);
				this.smsMoLogDao.insert(smsMoLogBean);
				logger.info("YD insert end"+ smsMoLogBean.toString());
			}
		} catch(Exception e) {
			logger.error("Yd mo to up log 短信处理异常",e);
		}
		return 1;
	}
}
