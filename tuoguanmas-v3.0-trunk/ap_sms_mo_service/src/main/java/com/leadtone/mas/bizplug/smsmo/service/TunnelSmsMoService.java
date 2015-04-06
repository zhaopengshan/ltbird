package com.leadtone.mas.bizplug.smsmo.service;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.leadtone.mas.bizplug.common.PinGen;
import com.leadtone.mas.bizplug.sms.bean.SmsMoLogBean;
import com.leadtone.mas.bizplug.sms.dao.SmsMoLogDao;
import com.leadtone.mas.bizplug.sms.dao.TunnelSmsMoLogDao;

public class TunnelSmsMoService implements Runnable {

	private final TunnelSmsMoLogDao tunnelSmsMoLogDao;
	private final SmsMoLogDao smsMoLogDao;
	private final static Logger logger = Logger.getLogger(SmsMoService.class);

	public TunnelSmsMoService(TunnelSmsMoLogDao tunnelSmsMoLogDao, SmsMoLogDao smsMoLogDao) {
		this.tunnelSmsMoLogDao = tunnelSmsMoLogDao;
		this.smsMoLogDao = smsMoLogDao;
	}

	@Override
	public void run() {
		while (true) {
			try {
				List<SmsMoLogBean> listSmsMoLogBean = this.tunnelSmsMoLogDao.queryAll();
				if (listSmsMoLogBean.size() == 0) {
					//没有伤心记录，休眠10秒
					try {
						Thread.sleep(10*1000);
					}catch(InterruptedException e) {
						logger.info(e.getMessage());
					}
				}
				for (Iterator<SmsMoLogBean> it = listSmsMoLogBean.iterator(); it.hasNext();) {
					SmsMoLogBean smsMoLogBean = it.next();
					logger.info(smsMoLogBean.toString());
					this.tunnelSmsMoLogDao.delete(smsMoLogBean);
					smsMoLogBean.setId(PinGen.getSerialPin());
					smsMoLogBean.setStatus(0);
					this.smsMoLogDao.insert(smsMoLogBean);
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
