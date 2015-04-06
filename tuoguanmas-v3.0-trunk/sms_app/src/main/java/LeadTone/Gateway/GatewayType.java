package LeadTone.Gateway;


public class GatewayType
{
    /**
     * 未知网关类型
     */
    public static final int GATEWAY_UNKNOWN = 0;
    /**
     * 短信中心网关类型
     */
    public static final int GATEWAY_SMSC = 0x10000;
    /**
     * 短信中心服务器网关类型
     */
    public static final int GATEWAY_SMSC_SERVER = 0x10010;
    /**
     * 中国移动短信中心网关类型
     */
    public static final int GATEWAY_SMSC_CMCC = 0x10100;
    /**
     * 中国联通短信中心网关类型
     */
    public static final int GATEWAY_SMSC_UNICOM = 0x10200;
    /**
     * CMPP协议网关类型
     */
    public static final int GATEWAY_ISMG = 0x20000;
    /**
     * CMPP协议服务器网关类型
     */
    public static final int GATEWAY_ISMG_SERVER = 0x20010;
    /**
     * CMPP协议清华深讯实现网关类型
     */
    public static final int GATEWAY_ISMG_TSSX = 0x20100;
    /**
     * CMPP协议亚信实现网关类型
     */
    public static final int GATEWAY_ISMG_ASIAINFO = 0x20200;
    /**
     * CMPP协议英斯克实现网关类型
     */
    public static final int GATEWAY_ISMG_INTRINSIC = 0x20300;
    /**
     * CMPP协议北纬通讯实现网关类型
     */
    public static final int GATEWAY_ISMG_BISP = 0x20400;
    /**
     * CMPP协议北京斯特奇实现网关类型
     */
    public static final int GATEWAY_ISMG_SITECH = 0x20500;
    /**
     * CMPP协议东大诺基亚实现网关类型
     */
    public static final int GATEWAY_ISMG_NOKIA_1 = 0x20601;
    /**
     * CMPP协议东大诺基亚实现网关类型
     */
    public static final int GATEWAY_ISMG_NOKIA_2 = 0x20602;
    /**
     * CMPP协议湖南拓维实现网关类型
     */
    public static final int GATEWAY_ISMG_TUOWEI = 0x20700;
    /**
     * CMPP协议卓望实现网关类型
     */
    public static final int GATEWAY_ISMG_MISC = 0x20800;
    /**
     * CMPP协议华为实现网关类型
     */
    public static final int GATEWAY_ISMG_HUAWEI = 0x20900;
    /**
     * SGIP协议网关类型
     */
    public static final int GATEWAY_SGIP = 0x30000;
    /**
     * SGIP协议服务器网关类型
     */
    public static final int GATEWAY_SGIP_SERVER = 0x30010;
    /**
     * CNGP协议网关类型
     */
    public static final int GATEWAY_CNGP = 0x40000;
    /**
     * CNGP协议第一种实现网关类型
     */
    public static final int GATEWAY_CNGP1 = 0x40001;
    /**
     * CNGP协议第二种实现网关类型
     */
    public static final int GATEWAY_CNGP2 = 0x40002;


    public GatewayType()
    {
    }

    /**
     * 判断网关类型是否属于短信中心类型网关
     * @param nGatewayType 网关类型代码
     * @return 网关类型是否属于短信中心类型网关的布尔值
     */
    public static boolean isSMSC(int nGatewayType)
    {
        return (nGatewayType & 0xffff0000) == 0x10000;
    }

    /**
     * 判断网关类型是否属于CMPP协议网关
     * @param nGatewayType 网关类型代码
     * @return 网关类型是否属于CMPP协议网关的布尔值
     */
    public static boolean isISMG(int nGatewayType)
    {
        return (nGatewayType & 0xffff0000) == 0x20000;
    }

    /**
     * 判断网关类型是否属于SGIP协议网关
     * @param nGatewayType 网关类型代码
     * @return 网关类型是否属于SGIP协议网关的布尔值
     */
    public static boolean isSGIP(int nGatewayType)
    {
        return (nGatewayType & 0xffff0000) == 0x30000;
    }

    /**
     * 判断网关类型是否属于CNGP协议网关
     * @param nGatewayType 网关类型代码
     * @return 网关类型是否属于CNGP协议网关的布尔值
     */
    public static boolean isCNGP(int nGatewayType)
    {
        return (nGatewayType & 0xffff0000) == 0x40000;
    }

    /**
     * 判断网关类型是否属于服务器类网关
     * @param nGatewayType
     * @return 网关类型是否属于服务器类网关的布尔值
     */
    public static boolean isServer(int nGatewayType)
    {
        return nGatewayType == 0x20010 || nGatewayType == 0x10010 || nGatewayType == 0x30010;
    }

    /**
     * 网关类型描述到网关类型代码的转换函数
     * @param strGateway 网关类型描述
     * @return  网关类型代码
     */
    public static int toType(String strGateway)
    {
        if(strGateway == null || strGateway.length() <= 0)
            return 0;
        if(strGateway.equals("smsc"))
            return 0x10000;
        if(strGateway.equals("smsc_server"))
            return 0x10010;
        if(strGateway.equals("smsc_cmcc"))
            return 0x10100;
        if(strGateway.equals("smsc_unicom"))
            return 0x10200;
        if(strGateway.equals("ismg") || strGateway.equals("standard"))
            return 0x20000;
        if(strGateway.equals("ismg_server"))
            return 0x20010;
        if(strGateway.equals("ismg_tssx") || strGateway.equals("tssx"))
            return 0x20100;
        if(strGateway.equals("ismg_asiainfo") || strGateway.equals("asiainfo"))
            return 0x20200;
        if(strGateway.equals("ismg_intrinsic") || strGateway.equals("intrinsic"))
            return 0x20300;
        if(strGateway.equals("ismg_bisp") || strGateway.equals("bisp"))
            return 0x20400;
        if(strGateway.equals("ismg_sitech") || strGateway.equals("sitech"))
            return 0x20500;
        if(strGateway.equals("ismg_nokia") || strGateway.equals("nokia") || strGateway.equals("nokia_1"))
            return 0x20601;
        if(strGateway.equals("nokia_2"))
            return 0x20602;
        if(strGateway.equals("ismg_tuowei") || strGateway.equals("tuowei"))
            return 0x20700;
        if(strGateway.equals("ismg_huawei") || strGateway.equals("huawei"))
            return 0x20900;
        if(strGateway.equals("ismg_misc") || strGateway.equals("misc"))
            return 0x20800;
        if(strGateway.equals("sgip"))
            return 0x30000;
        if(strGateway.equals("sgip_server"))
            return 0x30010;
        if(strGateway.equalsIgnoreCase("cngp"))
            return 0x40000;
        if(strGateway.equalsIgnoreCase("cngp1"))
            return 0x40001;
        return !strGateway.equalsIgnoreCase("cngp2") ? 0 : 0x40002;
    }

    /**
     * 网关类型代码到网关类型描述的转换函数
     * @param nGatewayType  网关类型代码
     * @return 网关类型描述
     */
    public static String toString(int nGatewayType)
    {
        switch(nGatewayType)
        {
        case 65536: 
            return "smsc";

        case 65792: 
            return "smsc_cmcc";

        case 66048: 
            return "smsc_unicom";

        case 131072: 
            return "ismg";

        case 131328: 
            return "ismg_shenxun";

        case 131584: 
            return "ismg_asiainfo";

        case 131840: 
            return "ismg_intrinsic";

        case 132096: 
            return "ismg_bisp";

        case 132352: 
            return "ismg_sitech";

        case 132609: 
            return "ismg_nokia_1";

        case 132610: 
            return "ismg_nokia_2";

        case 132864: 
            return "ismg_tuowei";

        case 133120: 
            return "ismg_misc";

        case 196608: 
            return "sgip";

        case 133376: 
            return "huawei";

        case 262144: 
            return "cngp";

        case 262145: 
            return "cngp1";

        case 262146: 
            return "cngp2";
        }
        return "unknown";
    }



}
