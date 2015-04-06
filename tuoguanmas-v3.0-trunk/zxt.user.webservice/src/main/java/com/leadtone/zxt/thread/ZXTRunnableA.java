package com.leadtone.zxt.thread;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import com.leadtone.zxt.bean.ZXTUser;
import com.leadtone.zxt.constant.OptType;
import com.leadtone.zxt.dao.ZXTUserDao;
import com.leadtone.zxt.util.SpringUtils;

public class ZXTRunnableA implements Runnable {
	private Logger logger = Logger.getLogger(ZXTRunnableA.class);
	
	private String opt;
	private ZXTUser user;
	
	@Autowired
	private ZXTUserDao zxtUserDao;
	
	public ZXTRunnableA(String opt,ZXTUser user) {
		this.opt = opt;
		this.user = user;
		try{
			this.zxtUserDao = (ZXTUserDao) SpringUtils.getBean("zxtUserDao");
		} catch(Exception e){
			logger.info("load zxtUserDao error :"+ e.getMessage());
		}
	}

	public void run() {
		try{
			if (OptType.ADD_USER.equalsIgnoreCase(opt)) {
				logger.info("Add user start");
				user.setCreatetime(new Date());
				zxtUserDao.addUser(user);
				logger.info("Add user end");
			} else if (OptType.DEL_USER.equalsIgnoreCase(opt)) {
				logger.info("Del user start");
				zxtUserDao.delUser(user);
				logger.info("Del user end");
			} else {
				logger.info("Unkown opt:" + opt);
			}
		} catch(Exception e){
			logger.info("process exception :"+ e.getMessage());
		}
	}

	public ZXTUserDao getZxtUserDao() {
		return zxtUserDao;
	}

	public void setZxtUserDao(ZXTUserDao zxtUserDao) {
		this.zxtUserDao = zxtUserDao;
	}
}
