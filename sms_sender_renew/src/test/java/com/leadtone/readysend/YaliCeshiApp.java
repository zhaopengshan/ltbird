package com.leadtone.readysend;

import java.util.Date;

import com.leadtone.readysend.bean.MbnSmsReadySend;
import com.leadtone.readysend.service.MbnSmsReadySendService;
import com.leadtone.util.SpringUtils;

public class YaliCeshiApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MbnSmsReadySendService readySendService = (MbnSmsReadySendService) SpringUtils.getBean("mbnSmsReadySendService");
		Long batchId = 1L;
    	for(Long i = 1L; i < 100001; i++){
    		MbnSmsReadySend readySendTemp = new MbnSmsReadySend();
    		readySendTemp.setBatchId(batchId++);
    		readySendTemp.setTaskNumber("01");
    		readySendTemp.setCommitTime(new Date());
//    		readySendTemp.setCompleteTime(completeTime);
    		readySendTemp.setContent("测试短信优化设置"+i);
    		readySendTemp.setCreateBy(8141493389168537740L);
    		readySendTemp.setCutApartNumber(1);
    		readySendTemp.setDescription("压力测试");
//    		readySendTemp.setExpireTime(expireTime);
//    		readySendTemp.setFailReason(failReason);
    		readySendTemp.setId(i);
    		readySendTemp.setMerchantPin(1141493385976423690L);
    		readySendTemp.setOperationId(1L);
    		readySendTemp.setPriorityLevel(3);
    		readySendTemp.setProvince("bj");
    		readySendTemp.setReadySendTime(new Date());
    		readySendTemp.setSelfMobile("10657311");
    		readySendTemp.setSendResult(0);
    		readySendTemp.setSmsAccessNumber("106573111105");
    		readySendTemp.setTitle("测试标题");
    		readySendTemp.setTos("15901533921");
    		readySendTemp.setTosName("孙亚东");
    		readySendTemp.setTunnelType(1);
    		readySendTemp.setWebService(1);
    		readySendService.save(readySendTemp);
    	}
	}

}
