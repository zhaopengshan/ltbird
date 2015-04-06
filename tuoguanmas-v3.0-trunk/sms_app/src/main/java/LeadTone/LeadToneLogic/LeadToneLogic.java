package LeadTone.LeadToneLogic;

import LeadTone.Log;
import LeadTone.LeadToneLogic.MO.SmsServiceMO;

/**
 * �û����д�����߼�ʵ��
 */
public class LeadToneLogic {


    /**
     * �����û����͵����������жϸø��û��·�����Ϣ���ͣ���������Ӧ��
     * �ⲿ����ʵ���·����ⲿ�����ĺ����߼���SmsGatewayWebservice�߼�һ��
     * @param phoneNumber ���к���
     * @param msg_content ������Ϣ����
     * @return ���ط��ͽ��
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
        	//������չSetting
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
     * �ж����к����Ƿ�Ϊ�ֻ����룬��ֹ�������ֻ����û������صĶ��⹥��
     * @param phoneNumber ���к���
     * @return �������к����Ƿ����ֻ�����Ĳ���ֵ
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
     * �����û��ֻ���
     * @param phoneNumber
     * @return  �������û��ֻ���
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
