package com.leadtone.mas.timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.calendar.service.MasSmsCalendarService;
import com.leadtone.mas.timer.mastask.MasPeriodicTaskService;

@Component
public class MasDayPeriodicQuartz {
	
	@Autowired
	private MasSmsCalendarService masSmsCalendarService;
	
	//@Scheduled(cron="0 */1 * * *") //需要注意@Scheduled这个注解，它可配置多个属性：cron\fixedDelay\fixedRate
	//@Scheduled(cron="0,10,20,30,40,50 * * * * ?")
//	@Scheduled(cron="0 0 * * * ?")
	public void quartzRun()
	{
		calendarDayPeriodic();
	}
	
	public void calendarDayPeriodic(){
		new MasPeriodicTaskService().startCheckTaskService(masSmsCalendarService);
	}

}
