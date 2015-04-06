package com.leadtone.mas.bizplug.smsmo.service;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.dao.GwSmsMoLogDao;
import com.leadtone.mas.bizplug.sms.dao.SmsMoLogDao;

public class GwSmsMoService implements Runnable {

	private final GwSmsMoLogDao gwSmsMoLogDao;
	private final SmsMoLogDao smsMoLogDao;
	private final static Logger logger = Logger.getLogger(SmsMoService.class);

	public GwSmsMoService(GwSmsMoLogDao gwSmsMoLogDao, SmsMoLogDao smsMoLogDao) {
		this.gwSmsMoLogDao = gwSmsMoLogDao;
		this.smsMoLogDao = smsMoLogDao;
	}

	@Override
	public void run() {
		while (true) {
			try {
				List<SmsMoLogBean> listSmsMoLogBean = this.gwSmsMoLogDao.queryByStatus(0);
				if (listSmsMoLogBean.size() == 0) {
					//没有伤心记录，休眠10秒
					try {
						Thread.sleep(10*1000);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
				for (Iterator<SmsMoLogBean> it = listSmsMoLogBean.iterator(); it.hasNext();) {
					SmsMoLogBean smsMoLogBean = it.next();
					logger.info(smsMoLogBean.toString());
					this.gwSmsMoLogDao.updateStatus(smsMoLogBean);
					smsMoLogBean.setId(PinGen.getSerialPin());
					smsMoLogBean.setStatus(0);
					this.smsMoLogDao.insert(smsMoLogBean);
					logger.info("insert end");
				}
			} catch(Exception e) {
				logger.info(e.getMessage());
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
