/*
 * 创建日期 2005-12-14
 *
 * TODO 要更改此生成的文件的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
package empp2.recvListener;

import java.math.BigInteger;

import com.wondertek.esmp.esms.empp.EMPPAnswer;
import com.wondertek.esmp.esms.empp.EMPPChangePassResp;
import com.wondertek.esmp.esms.empp.EMPPDeliver;
import com.wondertek.esmp.esms.empp.EMPPDeliverReport;
import com.wondertek.esmp.esms.empp.EMPPObject;
import com.wondertek.esmp.esms.empp.EMPPRecvListener;
import com.wondertek.esmp.esms.empp.EMPPReqNoticeResp;
import com.wondertek.esmp.esms.empp.EMPPSubmitSM;
import com.wondertek.esmp.esms.empp.EMPPSubmitSMResp;
import com.wondertek.esmp.esms.empp.EMPPSyncAddrBookResp;
import com.wondertek.esmp.esms.empp.EMPPTerminate;
import com.wondertek.esmp.esms.empp.EMPPUnAuthorization;
import com.wondertek.esmp.esms.empp.EmppApi;

import empp2.Log;
import empp2.cef.spring.MasBeanFactory;
import empp2.dao.SmsEmppSubmitDao;

/**
 * @author chensheng
 *
 * TODO 要更改此生成的类型注释的模板，请转至
 * 窗口 － 首选项 － Java － 代码样式 － 代码模板
 */
public class RecvListener implements EMPPRecvListener {

	private static final long RECONNECT_TIME = 10 * 1000;
    
    private EmppApi emppApi = null;
    
    private int closedCount = 0;
    
    protected RecvListener(){
    }
    
    public RecvListener(EmppApi emppApi){
        this.emppApi = emppApi;
    }
    
    private void setReplyMsgId(EMPPSubmitSMResp resp, BigInteger msgId){//int sequenceNumber, BigInteger msgId, int result
    	SmsEmppSubmitDao smsEmppSubmitDao = (SmsEmppSubmitDao) MasBeanFactory.getBean("smsEmppSubmitDao");
    	smsEmppSubmitDao.updateSubmitReply(resp, msgId);
    }
    private void setReportMsgId(BigInteger msgId, String finalResult){
    	SmsEmppSubmitDao smsEmppSubmitDao = (SmsEmppSubmitDao) MasBeanFactory.getBean("smsEmppSubmitDao");
    	smsEmppSubmitDao.updateSubmitReport(msgId, finalResult);
    }
    
    private void deliverSave(EMPPDeliver deliver){
    	SmsEmppSubmitDao smsEmppSubmitDao = (SmsEmppSubmitDao) MasBeanFactory.getBean("smsEmppSubmitDao");
    	smsEmppSubmitDao.deliverSave(deliver);
    }
  
 	 // 处理接收到的消息
    public void onMessage(EMPPObject message) {
        if(message instanceof EMPPUnAuthorization){
            EMPPUnAuthorization unAuth=(EMPPUnAuthorization)message;
            System.out.println("客户端无权执行此操作 commandId="+unAuth.getUnAuthCommandId());
            return;
         }
        if(message instanceof EMPPSubmitSMResp){
        	EMPPSubmitSMResp resp=(EMPPSubmitSMResp)message;
        	Log.log("收到sumbitResp:", 0x4000000000000000L);
        	byte[] msgId=fiterBinaryZero(resp.getMsgId());
        	int result = resp.getResult();
        	
        	Log.log("msgId="+new BigInteger(msgId), 0x4000000000000000L);
        	Log.log("result="+result, 0x4000000000000000L);
        	//提交应答
        	//短信提交 后应答 此时传回MSGID，以便 状态报告上来是对应上
        	setReplyMsgId(resp, new BigInteger(msgId));//resp.getSequenceNumber(), new BigInteger(msgId), resp.getResult());
        	return;
        }
  		if(message instanceof EMPPDeliver){
  			EMPPDeliver deliver = (EMPPDeliver)message;
  			if(deliver.getRegister()==EMPPSubmitSM.EMPP_STATUSREPORT_TRUE){
  				//收到状态报告
  				EMPPDeliverReport report=deliver.getDeliverReport();
  				Log.log("收到状态报告:", 0x4000000000000000L);
  				byte[] msgId=fiterBinaryZero(report.getMsgId());
  			    Log.log("msgId="+new BigInteger(msgId), 0x4000000000000000L);
  			    String stat = report.getStat();
  			    Log.log("status="+stat, 0x4000000000000000L);
  			    //收到状态报告，更新状态
  			    setReportMsgId(new BigInteger(msgId), stat);
  			}else{
  				//收到手机回复
//	  			System.out.println("收到"+deliver.getSrcTermId()+"发送的短信");
//	  			System.out.println("短信内容为："+deliver.getMsgContent().getMessage());
  				Log.log("收到"+deliver.getSrcTermId()+"发送的短信", 0x4000000000000000L);
	  			deliverSave(deliver);
  			}
  		    return;
  		}
  		 if(message instanceof EMPPSyncAddrBookResp){
  		      EMPPSyncAddrBookResp resp=(EMPPSyncAddrBookResp)message;
  		      if(resp.getResult()!=EMPPSyncAddrBookResp.RESULT_OK)
  		          System.out.println("同步通讯录失败");
  		      else{
  		          System.out.println("收到服务器发送的通讯录信息");
  		          System.out.println("通讯录类型为："+resp.getAddrBookType());
  		          System.out.println(resp.getAddrBook());
  		      }
          }
  		 if(message instanceof EMPPChangePassResp){
                EMPPChangePassResp resp=(EMPPChangePassResp)message;
                if(resp.getResult()==EMPPChangePassResp.RESULT_VALIDATE_ERROR)
                    System.out.println("更改密码：验证失败");
                if(resp.getResult()==EMPPChangePassResp.RESULT_OK)
                {
                    System.out.println("更改密码成功,新密码为："+resp.getPassword());
                    emppApi.setPassword(resp.getPassword());
                }
                return;
                
            } 
  		 if(message instanceof EMPPReqNoticeResp){
                EMPPReqNoticeResp response=(EMPPReqNoticeResp)message;
                if(response.getResult()!=EMPPReqNoticeResp.RESULT_OK)
                   System.out.println("查询运营商发布信息失败");
                else{
                   System.out.println("收到运营商发布的信息");
                   System.out.println(response.getNotice());
                }
                return;
           }
  		if(message instanceof EMPPAnswer){
  		    System.out.println("收到企业疑问解答");
             EMPPAnswer  answer=(EMPPAnswer)message;
             System.out.println(answer.getAnswer());
             
         }
  		    System.out.println(message);
  	    
       }
 	 //处理连接断掉事件
     public void OnClosed(Object object) {
        // 该连接是被服务器主动断掉，不需要重连
        if(object instanceof EMPPTerminate){
            System.out.println("收到服务器发送的Terminate消息，连接终止");
            return;
        }
        //这里注意要将emppApi做为参数传入构造函数
        RecvListener listener = new RecvListener(emppApi)
		;
        System.out.println("连接断掉次数："+(++closedCount));
        for(int i = 1;!emppApi.isConnected();i++){
            try {
                System.out.println("重连次数:"+i);
                Thread.sleep(RECONNECT_TIME);
                emppApi.reConnect(listener);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("重连成功");
    }
 
 	//处理错误事件
    public void OnError(Exception e) {
        e.printStackTrace();
    }
    
    private static byte[] fiterBinaryZero(byte[] bytes) {
        byte[] returnBytes = new byte[8];
        for (int i = 0; i < 8; i++) {
            returnBytes[i] = bytes[i];
        }
        return returnBytes;
    }
}
