package empp2.center;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wondertek.esmp.esms.empp.EMPPConnectResp;
import com.wondertek.esmp.esms.empp.EmppApi;
import com.wondertek.esmp.esms.empp.exception.EMPPObjectException;
import com.wondertek.esmp.esms.empp.exception.HeaderIncompleteException;
import com.wondertek.esmp.esms.empp.exception.InvalidEMPPObjectException;
import com.wondertek.esmp.esms.empp.exception.MessageIncompleteException;
import com.wondertek.esmp.esms.empp.exception.NotEnoughDataInByteBufferException;
import com.wondertek.esmp.esms.empp.exception.UnknownCommandIdException;
import com.wondertek.esmp.esms.empp.exception.ValueNotSetException;

import empp2.Log;
import empp2.cef.spring.MasBeanFactory;
import empp2.config.SubsystemConfig;
import empp2.dao.SmsEmppSubmitDao;
import empp2.recvListener.RecvListener;
import empp2.util.ByteUtil;

public class EMPPCenter extends Center {

	private EmppApi emppApi = null;
	private RecvListener listener = null;
	
	private String host;
	private int port;
	private String accountId;
	private String password;
	private String serviceId;
	private int sleepInterval;
	
	public EMPPCenter() throws IOException {
		super();
		host = SubsystemConfig.getInstances().getProperty("host").trim();
		port = Integer.valueOf( SubsystemConfig.getInstances().getProperty("port").trim() );
		accountId = SubsystemConfig.getInstances().getProperty("accountId").trim();//"10657109084004";
		password = SubsystemConfig.getInstances().getProperty("password").trim();//"Asdf1357";
		serviceId = SubsystemConfig.getInstances().getProperty("serviceId").trim();//"555580001";
		sleepInterval = Integer.parseInt( SubsystemConfig.getInstances().getProperty("sleepInterval").trim() );
		emppApi = new EmppApi();
		listener = new RecvListener(emppApi);
		
	}

	private void connect(){
		try {
			EMPPConnectResp response = emppApi.connect(host, port, accountId, password, listener);
			if (response == null) {
				Log.log("连接超时失败", 0x2000000000000001L);
				return;
			}
			if (!emppApi.isConnected()) {
				Log.log("连接失败:响应包状态位=" + response.getStatus(), 0x2000000000000001L);
				return;
			}
		} catch (Exception e1) {
			Log.log(e1);
		}
	}
	public void setConnect(){
		if( emppApi != null ){
			if( !emppApi.isConnected() ){
				this.connect();
			}
		}else{
			emppApi = new EmppApi();
			listener = new RecvListener(emppApi);
			this.connect();
		}
	}
	
	public boolean checkConnect(){
		if(emppApi == null){
			return false;
		}
		return emppApi.isConnected();
	}
	private List<String> splitDest(String destTerminalId){
		String[] destArray = destTerminalId.split(",");
		List<String> dstId = new ArrayList<String>();
		if(destArray!=null){
			for(int i=0; i < destArray.length; i++){
				dstId.add(destArray[i]);
			}
		}
		return dstId;
	}
	private String[] splitDestArray(String destTerminalId){
		return destTerminalId.split(",");
	}
	private String decodeSmsShort(String encSms) throws UnsupportedEncodingException{
		return new String( ByteUtil.hex2byte(encSms),"UnicodeBigUnmarked");
	}
	private String decodeSmsLong(String encSms, int pkNumber) throws UnsupportedEncodingException{
		return new String( ByteUtil.hex2byte(encSms),"UnicodeBigUnmarked").substring(3);
	}
	/**
     * EMPPCenter模式的工作线程，
     *
     */
    public void run()
    {
        try
        {
        	SmsEmppSubmitDao smsEmppSubmitDao = (SmsEmppSubmitDao) MasBeanFactory.getBean("smsEmppSubmitDao");
        	smsEmppSubmitDao.initStartUp();
        	if(!checkConnect()){
        		setConnect();
        	}
        	m_nStatus = 1;
            for(; isRunning(); sleep(sleepInterval))
            {
            	try{
	            	List<HashMap<String, Object>> list = smsEmppSubmitDao.querySubmitSms();
	            	if(list != null && list.size()>0){
	            		if(!checkConnect()){
	                		setConnect();
	                	}
	        			for (int i = 0; i < list.size(); i++) {
	        				if (emppApi.isSubmitable()) {
	            				HashMap<String, Object> object = list.get(i);
	            				String[] destArray = splitDestArray( object.get("dest_terminal_id").toString() );
	            				//简单方式发送短信,支持长短信
	            				try{
	//            					String smsContent = decodeSmsShort( object.get("msg_content").toString() );
	//            					int[] sequNum = emppApi.submitMsgAsync(smsContent,destArray,serviceId);
	            					String smsContent ="";
									try {
										smsContent = decodeSmsShort( object.get("msg_content").toString() );
									} catch (UnsupportedEncodingException e) {
										object.put("ih_process", "cmpp_submit_failure");
	                					smsEmppSubmitDao.updateSubmitFailure( object );
	                					smsEmppSubmitDao.insertSubmitSmsResult(object);
	                					smsEmppSubmitDao.removeSubmitSms(object.get("id").toString());
										Log.log(e.getMessage(), 0x2000000000000000L);
		            		            Log.log(e);
		            		            Log.log("UnsupportedEncodingException: unepected!", 0x2000000000000000L);
		            		            continue;
									}
									
	            					int[] sequNum = null;
									try {
										sequNum = emppApi.submitMsgAsync(smsContent,destArray,serviceId);
									} catch (Exception e) {
										object.put("ih_process", "cmpp_submit_failure");
	                					smsEmppSubmitDao.updateSubmitFailure( object );
	                					smsEmppSubmitDao.insertSubmitSmsResult(object);
	                					smsEmppSubmitDao.removeSubmitSms(object.get("id").toString());
										Log.log(e.getMessage(), 0x2000000000000000L);
		            		            Log.log(e);
		            		            Log.log("Exception: unepected!", 0x2000000000000000L);
		            		            continue;
									} 
									
	            					for( int a = 0 ; sequNum!=null && a < sequNum.length; a++ ){
	            						object.put("sequence_number", sequNum[0]);
	                					object.put("ih_process", "wait_for_response");
	                					smsEmppSubmitDao.updateSubmitSmsReply( object );
	            					}
	            				}catch (Exception e1) {
	            					Log.log(e1.getMessage(), 0x2000000000000000L);
	            		            Log.log(e1);
	            		            Log.log("submitMsgAsync: unepected!", 0x2000000000000000L);
	            				} 
	            				Log.log("send ok i++", 0x4000000000000000L);
	        				}else{
	        					break;
	        				}
	        			}
	            	}
	            }catch(Exception e)
	            {
	                Log.log(e);
	                Log.log("EMPPCenter.run : unexpected continue !", 0x2000000000000001L);
	            }
        	}
        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("EMPPCenter.run : unexpected exit !", 0x2000000000000001L);
        }
        
//    	if(!checkConnect()){
//            		setConnect();
//            	}else{
//            		if (emppApi.isSubmitable()) {
//            			List<HashMap<String, Object>> list = smsEmppSubmitDao.querySubmitSms();
//            			for (int i = 0; i < list.size(); i++) {
//            				HashMap<String, Object> object = list.get(i);
//            				String[] destArray = splitDestArray( object.get("dest_terminal_id").toString() );
//            				//简单方式发送短信,支持长短信
//            				try{
//            					String smsContent = decodeSmsShort( object.get("msg_content").toString() );
//            					int[] sequNum = emppApi.submitMsgAsync(smsContent,destArray,serviceId);
//            					for( int a = 0 ; a< sequNum.length; a++ ){
//            						object.put("sequence_number", sequNum[0]);
//                					object.put("ih_process", "wait_for_response");
//                					smsEmppSubmitDao.updateSubmitSmsReply( object );
//            					}
//            				}catch (Exception e1) {
//            					e1.printStackTrace();
//            				} 
//            				Log.log("send for() sms list i++", 0x4000000000000000L);
//            			}
//            		}
//            	}
        
//		try{
////	详细设置短信的各个属性,不支持长短信
//	EMPPSubmitSM msg = (EMPPSubmitSM) EMPPObject
//			.createEMPP(EMPPData.EMPP_SUBMIT);
//	msg.setDstTermId(destList);
//	msg.setSrcTermId(accountId);
//	msg.setServiceId(serviceId);
//
//	EMPPShortMsg msgContent = new EMPPShortMsg(
//			EMPPShortMsg.EMPP_MSG_CONTENT_MAXLEN);
////	短信解码 ,处理长短信。
//	Integer pkNumber = Integer.parseInt(object.get("pk_number").toString());
//	Integer pkTotal = Integer.parseInt(object.get("pk_total").toString());
//	String smsContent = "";
//	if( pkTotal > 1){
//		smsContent = decodeSmsLong( object.get("msg_content").toString(), pkNumber );//object[2].toString());
//	}else{
//		smsContent = decodeSmsShort( object.get("msg_content").toString() );//object[2].toString());
//	}
//	
//	//长短信 指定
//	msg.setPkNumber( pkNumber.byteValue() );
//	msg.setPkTotal(pkTotal.byteValue());
//	
//	msgContent.setMessage(smsContent);
//	msg.setShortMessage(msgContent);
//	msg.assignSequenceNumber();
//	
//	object.put("sequence_number", msg.getSequenceNumber());
//	object.put("ih_process", "wait_for_response");
//	smsEmppSubmitDao.updateSubmitSmsReply( object );
//	emppApi.submitMsgAsync(msg);
//}catch (Exception e1) {
//	Log.log(e1);
//}
    }

}
