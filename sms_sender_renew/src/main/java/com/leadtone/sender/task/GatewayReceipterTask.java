package com.leadtone.sender.task;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.log4j.Logger;
import com.leadtone.sender.service.ISmsService;
import com.leadtone.util.SpringUtils;

public class GatewayReceipterTask implements Callable<Integer> {
	
	private Logger logger = Logger.getLogger(GatewayReceipterTask.class);
	
	private ISmsService smsService;
	
	public GatewayReceipterTask(){
		if (smsService == null) {
			smsService = (ISmsService) SpringUtils.getBean("smsService");
		}
	}
	
	@Override
	public Integer call(){
		try{
//			logger.info("网关短信回执处理开始");
			List<Map<String, Object>> Gatewaylist = smsService.getGatewaySmsResult();
			if ((Gatewaylist != null) && (Gatewaylist.size() > 0)) {
				smsService.updateSmsSendRestlt(Gatewaylist);
				smsService.updateGatewaySms(Gatewaylist);
			}
			List utcomGatewayList = smsService.getLtdxGatewaySmsResult();
		    if ((utcomGatewayList != null) && (utcomGatewayList.size() > 0)) {
//		      this.logger.info("更新电信，联通网关短信发送结果");
		      smsService.updateSmsSendRestlt(utcomGatewayList);
		      smsService.updateLtdxGatewaySms(utcomGatewayList);
		    }
			
//			String ip = ProperUtil.readValue("sms.http.ip");
//			List userIdList = smsService.getHttpSmsResult();
//			for(int i = 0;i<userIdList.size();i++){
//				Map userIdMap = (Map) userIdList.get(i);
//				String account = ProperUtil.readValue("sms.http.receipter."+userIdMap.get("user_id")+".account");
//				//取该企业的密码
//				String password = ProperUtil.readValue("sms.http.receipter."+userIdMap.get("user_id")+".password");
//				try {
//					String rsp = HttpClientUtil.getSmsReceipterPost(ip, userIdMap.get("user_id").toString(), account, password);
//	        	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
//	                DocumentBuilder builder = factory.newDocumentBuilder();
//	    	        Document document = builder.parse(new InputSource(new ByteArrayInputStream(rsp.getBytes("utf-8"))));
//	    	        Element root = document.getDocumentElement();
//	    	        NodeList boxNodes = root.getElementsByTagName("statusbox");
//	    	        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
//	    	        for(int j=0;j<boxNodes.getLength();j++){
//	    	            Element node = (Element)boxNodes.item(i);
//	    	            Map<String,Object> map = new HashMap();
//	    	        	String taskId = node.getElementsByTagName("taskid").item(0).getFirstChild().getNodeValue();
//	    	        	List smsHttpRspList = smsService.getsmsHttpRsp(Long.parseLong(taskId));
//	    	        	MaprspMap =(Map) smsHttpRspList.get(0);
//	    	        	Long id = (Long)rspMap.get("sms_id");
//	    	            map.put("id", id);
//	    	        	String status =node.getElementsByTagName("status").item(0).getFirstChild().getNodeValue();
//	    	            map.put("send_result", "10".equalsIgnoreCase(status)?2:3);
//	    	            resultList.add(map);
//	    	            smsService.updateSmsHttp(Long.parseLong(taskId), status);
//	    	        }
	//
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}

			
//	    	logger.info("网关短信回执处理结束");
		}catch(Exception e){
			logger.error("网关短信回执处理异常", e);
		}
		return 1;
	}

}
