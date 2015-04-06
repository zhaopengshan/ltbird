package com.leadtone.mas.bizplug.smsmo.task;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.leadtone.mas.bizplug.sms.dao.GwSmsMoLogDao;
import com.leadtone.mas.bizplug.sms.dao.SmsMoLogDao;
import com.leadtone.mas.bizplug.sms.dao.TunnelSmsMoLogDao;
import com.leadtone.mas.bizplug.smsmo.service.GwSmsMoService;
import com.leadtone.mas.bizplug.smsmo.service.SmsMoService;
import com.leadtone.mas.bizplug.smsmo.service.TunnelSmsMoService;

//@Service("smsMoTaskService")
public class SmsMoTaskService {
	@Resource
	private TaskExecutor taskExecutor;
	@Resource
	private SmsMoLogDao smsMoLogDao;
	@Resource
	private TunnelSmsMoLogDao tunnelSmsMoLogDao;
	@Resource
	private GwSmsMoLogDao gwSmsMoLogDao;

	private final static Logger logger = Logger.getLogger(SmsMoService.class);

	public TaskExecutor getTaskExecutor() {
		return taskExecutor;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public SmsMoLogDao getSmsMoLogDao() {
		return smsMoLogDao;
	}

	public void setSmsMoLogDao(SmsMoLogDao smsMoLogDao) {
		this.smsMoLogDao = smsMoLogDao;
	}

	public TunnelSmsMoLogDao getTunnelSmsMoLogDao() {
		return tunnelSmsMoLogDao;
	}

	public void setTunnelSmsMoLogDao(TunnelSmsMoLogDao tunnelSmsMoLogDao) {
		this.tunnelSmsMoLogDao = tunnelSmsMoLogDao;
	}

	public GwSmsMoLogDao getGwSmsMoLogDao() {
		return gwSmsMoLogDao;
	}

	public void setGwSmsMoLogDao(GwSmsMoLogDao gwSmsMoLogDao) {
		this.gwSmsMoLogDao = gwSmsMoLogDao;
	}

	public SmsMoTaskService() {
	}

	public void startTunnelSmsMoService() {
		logger.info("Tunnel Mo Sms Service start....");
		taskExecutor.execute(new TunnelSmsMoService(tunnelSmsMoLogDao, smsMoLogDao));
	}

	public void startGwSmsMoService() {
		logger.info("Gateway Mo Sms Service start....");
		taskExecutor.execute(new GwSmsMoService(gwSmsMoLogDao, smsMoLogDao));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
