package LeadTone.LeadToneLogic;

import LeadTone.Log;

/**
 * ����ϵͳ�������շ���״̬���Ƶ������࣬���Կ����رմ˹���
 */
public class UpdateFinalResultConfig {

    /**
     * ��״̬���津������cmpp_submit��final_result���ƿ��� true ����, false �ر�
     */
    public static String UPDATEFINALRESULTSWITCH = "true";

   /**
     * ���ϵͳ���ƵĿ���״��
     */ 
   public static void dump()
    {
        Log.log("UpdateFinalResultConfig parameters :", 0x2000000000000001L);
        Log.log("UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH : " + UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH, 0x2000000000000001L);
    }
}
