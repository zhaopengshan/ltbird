package LeadTone.LeadToneLogic;

import LeadTone.Log;

/**
 * 控制系统更新最终发送状态机制的配置类，可以开启关闭此功能
 */
public class UpdateFinalResultConfig {

    /**
     * 由状态报告触发更新cmpp_submit表final_result机制开关 true 开启, false 关闭
     */
    public static String UPDATEFINALRESULTSWITCH = "true";

   /**
     * 输出系统机制的开启状况
     */ 
   public static void dump()
    {
        Log.log("UpdateFinalResultConfig parameters :", 0x2000000000000001L);
        Log.log("UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH : " + UpdateFinalResultConfig.UPDATEFINALRESULTSWITCH, 0x2000000000000001L);
    }
}
