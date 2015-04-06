package com.leadtone.mas.bizplug.smsmo.task;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBeanVO;
import com.leadtone.mas.bizplug.sms.dao.GwSmsMoLogDao;
import com.leadtone.mas.bizplug.sms.dao.SmsMoLogDao;
import com.leadtone.mas.util.spring.BeanContainer;

public class QxtMoToUpLogTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(QxtMoToUpLogTask.class);
	
	private GwSmsMoLogDao gwSmsMoLogDao;
	private SmsMoLogDao smsMoLogDao;
	
	public QxtMoToUpLogTask(GwSmsMoLogDao gwSmsMoLogDaoTemp, SmsMoLogDao smsMoLogDaoTemp){
		gwSmsMoLogDao = gwSmsMoLogDaoTemp;
		smsMoLogDao = smsMoLogDaoTemp;
	}
	
	@Override
	public Integer call(){
		try {
			List<SmsMoLogBeanVO> listSmsMoLogBean = this.gwSmsMoLogDao.queryByQxtStatusVo(0);
//			if (listSmsMoLogBean.size() == 0) {
//				//没有伤心记录，休眠10秒
//				try {
//					Thread.sleep(10*1000);
//				}catch(InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
			for (Iterator<SmsMoLogBeanVO> it = listSmsMoLogBean.iterator(); it.hasNext();) {
				SmsMoLogBeanVO smsMoLogBean = it.next();
				logger.info(smsMoLogBean.toString());
				this.gwSmsMoLogDao.updateQxtStatus(smsMoLogBean);
				smsMoLogBean.setId(PinGen.getSerialPin());
				smsMoLogBean.setStatus(0);
				smsMoLogBean.setType("QXT");
				smsMoLogBean.setClassify(11);
				this.smsMoLogDao.insertVo(smsMoLogBean);
				logger.info("QXT insert end"+ smsMoLogBean.toString());
			}
		} catch(Exception e) {
			logger.error("QXT mo to up log 上行短信处理异常",e);
		}
		return 1;
	}

}
