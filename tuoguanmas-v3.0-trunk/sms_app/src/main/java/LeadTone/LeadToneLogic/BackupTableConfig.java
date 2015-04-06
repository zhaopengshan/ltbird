package LeadTone.LeadToneLogic;

import LeadTone.Log;

/**
 * 控制系统CMPPSubmit和CMPPDeliver表备份机制的配置类，可以开启关闭此功能
 */
public class BackupTableConfig {
    /**
     * 实时备份Cmpp和Deliver表机制开关 true 开启, false 关闭
     */
   public static String DYNAMICBACKUPTABLE = "true";

    /**
     * 输出系统机制的开启状况
     */
   public static void dump()
    {
        Log.log("BackupTableConfig parameters :", 0x2000000000000001L);
        Log.log("BackupTableConfig.DYNAMICBACKUPTABLE : " + BackupTableConfig.DYNAMICBACKUPTABLE, 0x2000000000000001L);
    }
}
