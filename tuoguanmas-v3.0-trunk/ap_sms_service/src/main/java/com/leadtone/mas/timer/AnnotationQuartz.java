package com.leadtone.mas.timer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.leadtone.mas.bizplug.calendar.service.MasSmsCalendarService;
import com.leadtone.mas.bizplug.dati.service.MasSmsDatiService;
import com.leadtone.mas.bizplug.lottery.service.LotteryService;
import com.leadtone.mas.bizplug.sms.bean.MbnSmsInbox;
import com.leadtone.mas.bizplug.sms.service.MbnSmsInboxService;
import com.leadtone.mas.bizplug.vote.service.MasVoteService;
import com.leadtone.mas.timer.mastask.MasPeriodicTaskService;
import com.leadtone.mas.timer.mastask.MasTaskInterface;

@Component
public class AnnotationQuartz {
	private static final String SMS_OPERATION_CODE_TP="TP";
	// 修改cron，避免与MasDayPeriodicQuartz同时执行产生的锁及值不确定
//	@Scheduled(cron="5,15,25,35,45,55 * * * * ?") //需要注意@Scheduled这个注解，它可配置多个属性：cron\fixedDelay\fixedRate
	public void quartzRun()
	{
		 voteSchedule();
		 new MasPeriodicTaskService().startTaskService(masSmsCalendarService);
	}
	@Autowired
	private MbnSmsInboxService mbnSmsInboxService;
	@Autowired
	private MasSmsDatiService masSmsDatiService;
	@Autowired
	private MasVoteService masVoteService;
	@Autowired
	private MasSmsCalendarService masSmsCalendarService;
	@Autowired
	private LotteryService lotteryService;
	
	private void voteSchedule(){
		/**mbnSmsInboxService.getIndoxBycoding("HD");
		List<MbnSmsInbox> inboxs=mbnSmsInboxService.getIndoxBycoding(SMS_OPERATION_CODE_TP);
		for(int i=0;i<inboxs.size();i++){
			MbnSmsInbox inbox=inboxs.get(i);
		}*/
		try{
		List<MbnSmsInbox> inboxs = mbnSmsInboxService.getInboxAllInfo();
		for(MbnSmsInbox inbox:inboxs){
			if(inbox.getStatus()==1){
				continue;
			}
			if(inbox.getOperationId().equalsIgnoreCase("tp")){
				//投票处理
				masVoteService.handleVoteInbox(inbox);
				inbox.setStatus(1);
				mbnSmsInboxService.update(inbox);
			}else if(inbox.getOperationId().equalsIgnoreCase("dt")){
				//答题
				masSmsDatiService.updateSmsDatiResultInfo(inbox.getSenderMobile(), inbox.getContent(), inbox.getMerchantPin(), inbox.getSenderName(), inbox.getServiceCode(),inbox.getReceiveTime());
				inbox.setStatus(1);
				mbnSmsInboxService.update(inbox);
			}else if(inbox.getOperationId().equalsIgnoreCase("cj")){
				//抽奖
				lotteryService.handleLottery(inbox);
				inbox.setStatus(1);
				mbnSmsInboxService.update(inbox);
			}
		}
		}catch(Exception se){
			
		}
	}
}
