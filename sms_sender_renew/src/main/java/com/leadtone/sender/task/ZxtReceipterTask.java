package com.leadtone.sender.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;

import com.leadtone.sender.bean.MbnSmsInbox;
import com.leadtone.sender.bean.SmsBean;
import com.leadtone.sender.bean.ZxtReceiveBean;
import com.leadtone.sender.service.ISmsService;
import com.leadtone.util.SpringUtils;

public class ZxtReceipterTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(ZxtReceipterTask.class);
	
	private ISmsService smsService;
	
	public ZxtReceipterTask(){
		if (smsService == null) {
			smsService = (ISmsService) SpringUtils.getBean("smsService");
		}
	}
	
	@Override
	public Integer call(){
		try{
			List<SmsBean> list = smsService.getZxtSmsResult();
			if(list!=null&&list.size()>0){
				smsService.updateZxtSmsSendRestlt(list);
				smsService.updateZxtMoRestlt(list);
			}
			List<ZxtReceiveBean> receList = smsService.getZxtMoBean();
			if( receList!=null && receList.size()>0 ){
				List<MbnSmsInbox> inboxList = new ArrayList<MbnSmsInbox>();
				for( int i = 0; i < receList.size(); i++ ){
					ZxtReceiveBean receBean = receList.get(i);
					MbnSmsInbox temp = new MbnSmsInbox();
					temp.setContent(receBean.getContent());
					temp.setId(Long.parseLong( receBean.getId().toString() ));
					temp.setMerchantPin(receBean.getMerchantPin());
					temp.setOperationId(null);
					temp.setReceiverAccessNumber("");
					temp.setReceiveTime(receBean.getDatetime());
					temp.setReplyBatchId(1L);
					temp.setSenderMobile(receBean.getPhone());
					temp.setSenderName("");
					temp.setServiceCode("");
					temp.setStatus(0);
					temp.setWebService(1);
					inboxList.add(temp);
				}
				smsService.saveZxtMoToInbox(inboxList);
				smsService.updateZxtMoBean(receList);
			}
		}catch(Exception e){
			logger.error("zxt短信回执处理异常", e);
		}
		return 1;
	}

}
