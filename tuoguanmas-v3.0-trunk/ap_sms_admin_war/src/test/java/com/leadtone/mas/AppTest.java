package com.leadtone.mas;

import java.util.Date;

import com.leadtone.mas.bizplug.sms.bean.MbnSmsReadySend;
import com.leadtone.mas.bizplug.sms.service.MbnSmsReadySendService;
import com.leadtone.mas.connector.utils.SpringUtils;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	MbnSmsReadySendService readySendService = (MbnSmsReadySendService) SpringUtils.getBean("mbnSmsReadySendService");
    	Long batchId = 1L;
    	for(Long i = 0L; i < 1; i++){
    		MbnSmsReadySend readySendTemp = new MbnSmsReadySend();
    		if(i%10 == 0){
    			readySendTemp.setBatchId(batchId++);
    		}
    		readySendTemp.setCommitTime(new Date());
//    		readySendTemp.setCompleteTime(completeTime);
    		readySendTemp.setContent("测试短信优化设置"+i);
    		readySendTemp.setCreateBy(8132327733239089975L);
    		readySendTemp.setCutApartNumber(0);
    		readySendTemp.setDescription("压力测试");
//    		readySendTemp.setExpireTime(expireTime);
//    		readySendTemp.setFailReason(failReason);
    		readySendTemp.setId(i);
    		readySendTemp.setMerchantPin(1132327713346566034L);
    		readySendTemp.setOperationId(1L);
    		readySendTemp.setPriorityLevel(3);
    		readySendTemp.setProvince("he");
    		readySendTemp.setReadySendTime(new Date());
    		readySendTemp.setSelfMobile("15901533921");
    		readySendTemp.setSendResult(0);
    		readySendTemp.setSmsAccessNumber("1065731152391137");
//    		readySendTemp.setTaskNumber(taskNumber);
    		readySendTemp.setTitle("测试标题");
    		readySendTemp.setTos("15901533921");
    		readySendTemp.setTosName("孙亚东");
    		readySendTemp.setTunnelType(2);
    		readySendTemp.setWebService(1);
    		readySendService.insert(readySendTemp);
    	}
        assertTrue( true );
    }
    
//  readySendTemp.setBatchId(batchId);
//	readySendTemp.setCommitTime(commitTime);
//	readySendTemp.setCompleteTime(completeTime);
//	readySendTemp.setContent(content);
//	readySendTemp.setCreateBy(createBy);
//	readySendTemp.setCutApartNumber(cutApartNumber);
//	readySendTemp.setDescription(description);
//	readySendTemp.setExpireTime(expireTime);
//	readySendTemp.setFailReason(failReason);
//	readySendTemp.setId(id);
//	readySendTemp.setMerchantPin(merchantPin);
//	readySendTemp.setOperationId(operationId);
//	readySendTemp.setPriorityLevel(priorityLevel);
//	readySendTemp.setProvince(province);
//	readySendTemp.setReadySendTime(readySendTime);
//	readySendTemp.setSelfMobile(selfMobile);
//	readySendTemp.setSendResult(sendResult);
//	readySendTemp.setSmsAccessNumber(smsAccessNumber);
//	readySendTemp.setTaskNumber(taskNumber);
//	readySendTemp.setTitle(title);
//	readySendTemp.setTos(tos);
//	readySendTemp.setTosName(tosName);
//	readySendTemp.setTunnelType(tunnelType);
//	readySendTemp.setWebService(webService);
    
}
