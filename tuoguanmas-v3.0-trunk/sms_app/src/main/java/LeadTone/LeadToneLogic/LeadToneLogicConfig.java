package LeadTone.LeadToneLogic;

import LeadTone.Log;

/**
 * ����ϵͳ�����û����У�MO�����Ƶ������࣬���Կ����رմ˹���
 */
public class LeadToneLogicConfig {
    /**
     * �����û�������Ϣ���� true ����, false �ر�
     */
    public static String LOGICSWITCH = "true";

    /**
     * ���ϵͳ���ƵĿ���״��
     */
    public static void dump()
    {
        Log.log("LeadToneLogicConfig parameters :", 0x2000000000000001L);
        Log.log("LeadToneLogicConfig.LOGICSWITCH : " + LeadToneLogicConfig.LOGICSWITCH, 0x2000000000000001L);
     }
}
