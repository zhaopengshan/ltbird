package LeadTone.longMsg;

import java.io.UnsupportedEncodingException;

import LeadTone.Log;
import LeadTone.Utility;
import LeadTone.Packet.CMPPPacket.CMPPDeliver;

public class MasCmpp2SmsReceive
{
  private static MasCmpp2SmsReceive instance;
  private static CMPPDeliverCache dmc = CMPPDeliverCache.getInstance();

  public static MasCmpp2SmsReceive getInstance()    
  {
    if (instance == null) {
      instance = new MasCmpp2SmsReceive();
    }
    return instance;
  }

  public static final String toHex(byte b) {
	  int str="0123456789ABCDEF".charAt(0xF & b >> 4) + "0123456789ABCDEF".charAt(b & 0xF);
    return String.valueOf(str);
  }

  public static boolean isLongSms(CMPPDeliver cmppdm)
  {
    return (Integer.parseInt(toHex(cmppdm.msg_content[0]), 16)== 5) && 
      (Integer.valueOf(toHex(cmppdm.msg_content[1]), 16).intValue() == 0);
  }

  public static boolean longSmsHandler(CMPPDeliver cmppdm)
  {
    Log.log("MasCmpp2SmsReceive---onDeliver--start--------",0x4000000000000L);
    if (cmppdm == null) {
    	Log.log("接收的消息cmppdm is null ----why? error? ----",0x4000000000000L);
      return false;
    }

    Log.log("短信接收上行类型：1，状态报告消息；0，普通短信。目前接收的消息为：" + cmppdm.registered_delivery,0x4000000000000L);
    Log.log("短信MSGID：" + cmppdm.msg_id,0x4000000000000L);
    Log.log("短信内容：" + cmppdm.msg_content,0x4000000000000L);
    Log.log("短信格式：" + cmppdm.msg_fmt,0x4000000000000L);

        LongSmsBean smsBean = new LongSmsBean();
        
        Log.log("===========================2: result " + cmppdm.msg_content[2],0x4000000000000L);
        Log.log("===========================3: result " + cmppdm.msg_content[3],0x4000000000000L);
        Log.log("===========================4: result " + cmppdm.msg_content[4],0x4000000000000L);
        Log.log("===========================5: result " + cmppdm.msg_content[5],0x4000000000000L);
        if (cmppdm.msg_content[2] == 3)
        {
          int index = cmppdm.msg_content[3];
          int total = cmppdm.msg_content[4];
          int cur = cmppdm.msg_content[5];

          smsBean.setReferNum(index);
          smsBean.setTotal(total);
          smsBean.setCurrent(cur);
        }

        try {
          if (cmppdm.msg_fmt == 8)
          {
            String strContent = new String(cmppdm.msg_content, "UTF-16BE");
            Log.log("UTF16==="+strContent,0x4000000000000L);
            strContent = strContent.substring(3);//转成UTF16或GBK时，长短信的占位是3个汉字长度即6个字符。
            smsBean.setMsgcontent(strContent);
          }
          else
          {
            String strContent = new String(cmppdm.msg_content, "GBK");
            Log.log("GBK==="+strContent,0x4000000000000L);
            strContent = strContent.substring(3);
            smsBean.setMsgcontent(strContent);
          }
        }
        catch (UnsupportedEncodingException e)
        {
          e.printStackTrace();
        }

        Log.log("短信当前号：" + smsBean.getCurrent(),0x4000000000000L);
        Log.log("短信索引：" + smsBean.getReferNum(),0x4000000000000L);
        Log.log("短信总编号：" + smsBean.getTotal(),0x4000000000000L);

        LongSmsBean recvBean = UnFinishRecvBeanMap.contentHandler(smsBean.getReferNum(), smsBean);

        if (recvBean != null)
        {
        	Log.log("长短信接收成功：" + recvBean.getMsgcontent(),0x4000000000000L);
          cmppdm.msg_content=recvBean.getMsgcontent().getBytes();
          dmc.add(cmppdm);
          return true;
        }

    return false;
  }
}