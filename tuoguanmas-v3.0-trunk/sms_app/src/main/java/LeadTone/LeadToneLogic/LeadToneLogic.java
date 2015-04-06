package LeadTone.LeadToneLogic;

import LeadTone.Log;
import LeadTone.LeadToneLogic.MO.SmsServiceMO;

/**
 * 用户上行处理的逻辑实现
 */
public class LeadToneLogic {


    /**
     * 根据用户发送的上行内容判断该给用户下发的消息类型，并调用相应的
     * 外部方法实现下发，外部方法的核心逻辑与SmsGatewayWebservice逻辑一致
     * @param phoneNumber 上行号码
     * @param msg_content 上行消息内容
     * @return 返回发送结果
     */
    public String callSend(String phoneNumber, String msg_content) {
    	String errorcode;
        try{
        	SmsServiceMO sms = new SmsServiceMO();

        if (msg_content == null) {
            Log.log("LeadToneLogic.callSend : msg_content is null !", 0x4000000000000L);
            return new String("LeadToneLogic.callSend msg_content is null");
        } else if (msg_content.trim().equalsIgnoreCase("RJ") || (msg_content.length() >= 2 && msg_content.substring(0, 2).equalsIgnoreCase("RJ"))) {
            Log.log("LeadToneLogic.callSend : SoftWare", 0x4000000000000L);
            errorcode = sms.sendSoftWare(phoneNumber);
            Log.log("LeadToneLogic.callSend : " + errorcode + " !", 0x4000000000000L);
            return new String("ErrorCode:" + errorcode);
        }  else if (msg_content.trim().equalsIgnoreCase("CA") || (msg_content.length() >= 2 && msg_content.substring(0, 2).equalsIgnoreCase("CA"))) {
            Log.log("LeadToneLogic.callSend : CA", 0x4000000000000L);
            errorcode = sms.sendCA(phoneNumber);
            Log.log("LeadToneLogic.callSend : " + errorcode + " !", 0x4000000000000L);
            return new String("ErrorCode:" + errorcode);
        } else if (msg_content.trim().equalsIgnoreCase("SZ") || (msg_content.length() >= 2 && msg_content.substring(0, 2).equalsIgnoreCase("SZ"))) {
            Log.log("LeadToneLogic.callSend : Setting", 0x4000000000000L);
            errorcode = sms.sendSetting(phoneNumber);
            Log.log("LeadToneLogic.callSend : " + errorcode + " !", 0x4000000000000L);
            return new String("ErrorCode:" + errorcode);
        }else if((msg_content.toUpperCase()).indexOf("JCZH")!=-1){
        	//增加扩展Setting
        	Log.log("LeadToneLogic.callSend : ExSetting", 0x4000000000000L);
            errorcode =sms.sendExSendSetting(msg_content,phoneNumber);
            Log.log("LeadToneLogic.callSend : " + errorcode + " !", 0x4000000000000L);
            return new String("ErrorCode:" + errorcode);	
        } 
        else {
            Log.log("LeadToneLogic.callSend : can't find msg_content handler !", 0x4000000000000L);
            return new String("LeadToneLogic.callSend can't find msg_content handler");
        }
        }catch(Exception e){
          Log.log("LeadToneLogic.callSend Exception : ", 0x2000000000000001L);
          Log.log(e.getMessage(), 0x2000000000000001L);
          Log.log(e);
          return "LeadToneLogic.callSend Exception";
        }
    }


    /**
     * 判断上行号码是否为手机号码，防止其他非手机的用户的网关的恶意攻击
     * @param phoneNumber 上行号码
     * @return 返回上行号码是否是手机号码的布尔值
     */
    public static boolean isPhoneNumber(String phoneNumber) {
        try{
        if (phoneNumber == null) {     // check phoneNumber logic
            return false;
        }
        /*
        if (!phoneNumber.matches("^13\\d{9}$")) {
            return false;
        }
        */
        return true;
        }catch(Exception e){
          Log.log("LeadToneLogic.isPhoneNumber Exception : ", 0x2000000000000001L);
          Log.log(e.getMessage(), 0x2000000000000001L);
          Log.log(e);
          return false;
        }
    }

    /**
     * 整理用户手机号
     * @param phoneNumber
     * @return  整理后的用户手机号
     */
    public static String dealPhoneNumber(String phoneNumber) {
        try{
        if (phoneNumber.substring(0, 3).equalsIgnoreCase("+86")) {
            return phoneNumber.substring(3).trim();
        }
        if (phoneNumber.substring(0, 2).equalsIgnoreCase("86")) {
            return phoneNumber.substring(2).trim();
        }
        return phoneNumber.trim();
       }catch(Exception e){
          Log.log("LeadToneLogic.dealPhoneNumber Exception : ", 0x2000000000000001L);
          Log.log(e.getMessage(), 0x2000000000000001L);
          Log.log(e);
          return null;
        }

    }

}
