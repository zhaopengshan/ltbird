package LeadTone.LeadToneLogic;

import LeadTone.Log;

/**
 * 控制系统处理用户上行（MO）机制的配置类，可以开启关闭此功能
 */
public class LeadToneLogicConfig {
    /**
     * 处理用户上行信息开关 true 开启, false 关闭
     */
    public static String LOGICSWITCH = "true";

    /**
     * 输出系统机制的开启状况
     */
    public static void dump()
    {
        Log.log("LeadToneLogicConfig parameters :", 0x2000000000000001L);
        Log.log("LeadToneLogicConfig.LOGICSWITCH : " + LeadToneLogicConfig.LOGICSWITCH, 0x2000000000000001L);
     }
}
