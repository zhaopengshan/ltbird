package com.leadtone.mas.timer.mastask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.leadtone.mas.bizplug.calendar.bean.MbnPeriodcTask;
import com.leadtone.mas.bizplug.calendar.service.MasSmsCalendarService;
import com.leadtone.mas.bizplug.common.ApSmsConstants;
import com.leadtone.mas.bizplug.common.DateLocUtils;

/**
 * 
 * @author xiazhy
 * 
 * 周期任务表处理
 *
 */
public class MasPeriodicTaskService implements MasTaskInterface {
	private static Log LOG = LogFactory.getLog(MasPeriodicTaskService.class);
	
	private static final int  START_PAGE=0;
	private static final int  PAGE_SIZE=100;
	/**
	 * 处理待执行的周期任务(每10秒执行一次)
	 */
	@Override
	public void startTaskService(MasSmsCalendarService masSmsCalendarService) {
		try{
			List<MbnPeriodcTask> mbnPeriodcTaskList=null;
			Map<String,Object> paraMap=new HashMap<String,Object>();
			paraMap.put("startPage", START_PAGE);
			paraMap.put("pageSize", PAGE_SIZE);
			paraMap.put("status", ApSmsConstants.PERIODIC_STATUS_EXE);
			paraMap.put("operationId", ApSmsConstants.OPERATION_CODING_TYPE_TX);
			mbnPeriodcTaskList=masSmsCalendarService.getPeriodcTaskList(paraMap);
			for(MbnPeriodcTask periodicTask:mbnPeriodcTaskList){
				//获取规则信息
				if(periodicTask.getOperationId()==ApSmsConstants.OPERATION_CODING_TYPE_TX){
					if( periodicTask.getTimes() !=null 
							&& periodicTask.getTimes() > 0 
							&& periodicTask.getSendTimes() >= periodicTask.getTimes()){
						// 完成指定执行次数，更新任务状态为任务结束
						periodicTask.setStatus(ApSmsConstants.PERIODIC_STATUS_TASK_DONE);
						masSmsCalendarService.updatePeriodcTask(periodicTask);
					}
					// DateLocUtils.isEqualsDate(periodicTask.getAwakeTime(), new Date()
					calendarPeriodicService(periodicTask);
					// 调用短信发送接口发送短信
					periodicTask.setStatus(ApSmsConstants.PERIODIC_STATUS_OVER);
					masSmsCalendarService.taskSendMsg(periodicTask);
				}
			}
			
			//LOG.info(mbnPeriodcTaskList.size());
		}catch(Exception se){
			LOG.error(se.getMessage());
		}
	}
	/**
	 * 检查周期任务(1小时执行一次)
	 * @param masSmsCalendarService
	 */
	public void startCheckTaskService(MasSmsCalendarService masSmsCalendarService){
		try{
			List<MbnPeriodcTask> mbnPeriodcTaskList=null;
			Map<String,Object> paraMap=new HashMap<String,Object>();
			// paraMap.put("startPage", START_PAGE);
			// paraMap.put("pageSize", PAGE_SIZE);
			paraMap.put("status", ApSmsConstants.PERIODIC_STATUS_OVER);
			paraMap.put("operationId", ApSmsConstants.OPERATION_CODING_TYPE_TX);
			paraMap.put("awakeTime", DateLocUtils.getNowTime("yyyy-MM-dd HH"));
			mbnPeriodcTaskList = masSmsCalendarService.getPeriodcTaskList(paraMap);
			for(MbnPeriodcTask periodcTask:mbnPeriodcTaskList){
				//获取规则信息,并检查开始时间，如果开始时间为当前天的则修改信息
				if(periodcTask.getOperationId()==ApSmsConstants.OPERATION_CODING_TYPE_TX){
					if(DateLocUtils.isEqualsDate(periodcTask.getAwakeTime(), Calendar.getInstance().getTime())){
						calendarPeriodicService(periodcTask);
						periodcTask.setStatus(ApSmsConstants.PERIODIC_STATUS_EXE);
						try{
							masSmsCalendarService.updatePeriodcTask(periodcTask);
						}catch(Exception se){
							LOG.error(se.getMessage());
						}
					}
				}
			}
			LOG.info(mbnPeriodcTaskList.size());
		}catch(Exception se){
			LOG.error(se.getMessage());
		}
		
		
	}

	/*
	 * 设置下次执行时间
	 */
	private void calendarPeriodicService(MbnPeriodcTask mbnPeriodcTask){
		int awokeMode=mbnPeriodcTask.getAwokeMode();//唤醒模式  1天，2周，3月
		// int number=mbnPeriodcTask.getNumber();//周几或几号
		// Date awakeDateTime=mbnPeriodcTask.getAwakeTime();//唤醒时间 201212231223
		Calendar calOri=Calendar.getInstance();
		calOri.setTime(mbnPeriodcTask.getAwakeTime());
		//1,获取周期执行时间
		if(awokeMode==ApSmsConstants.PERIODIC_AWOKE_MODE_DAY){
			calOri.add(Calendar.DAY_OF_MONTH, 1);
		}else if(awokeMode==ApSmsConstants.PERIODIC_AWOKE_MODE_WEEK){
			calOri.add(Calendar.WEEK_OF_MONTH, 1);
		}else{//月
			calOri.add(Calendar.MONTH, 1);
		}
		if(mbnPeriodcTask.getStatus().equals(ApSmsConstants.PERIODIC_STATUS_OVER)){
			mbnPeriodcTask.setLastTime(mbnPeriodcTask.getAwakeTime());//上次执行时间
			mbnPeriodcTask.setAwakeTime(calOri.getTime());//下次执行时间
		}
		mbnPeriodcTask.setStatus(ApSmsConstants.PERIODIC_STATUS_INIT);//执行完成,转变为待执行
	}
	
}
