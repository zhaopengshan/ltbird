package LeadTone.LeadToneLogic;

import LeadTone.Log;

/**
 * ����ϵͳCMPPSubmit��CMPPDeliver���ݻ��Ƶ������࣬���Կ����رմ˹���
 */
public class BackupTableConfig {
    /**
     * ʵʱ����Cmpp��Deliver����ƿ��� true ����, false �ر�
     */
   public static String DYNAMICBACKUPTABLE = "true";

    /**
     * ���ϵͳ���ƵĿ���״��
     */
   public static void dump()
    {
        Log.log("BackupTableConfig parameters :", 0x2000000000000001L);
        Log.log("BackupTableConfig.DYNAMICBACKUPTABLE : " + BackupTableConfig.DYNAMICBACKUPTABLE, 0x2000000000000001L);
    }
}
