package LeadTone.Gateway;


public class GatewayType
{
    /**
     * δ֪��������
     */
    public static final int GATEWAY_UNKNOWN = 0;
    /**
     * ����������������
     */
    public static final int GATEWAY_SMSC = 0x10000;
    /**
     * �������ķ�������������
     */
    public static final int GATEWAY_SMSC_SERVER = 0x10010;
    /**
     * �й��ƶ�����������������
     */
    public static final int GATEWAY_SMSC_CMCC = 0x10100;
    /**
     * �й���ͨ����������������
     */
    public static final int GATEWAY_SMSC_UNICOM = 0x10200;
    /**
     * CMPPЭ����������
     */
    public static final int GATEWAY_ISMG = 0x20000;
    /**
     * CMPPЭ���������������
     */
    public static final int GATEWAY_ISMG_SERVER = 0x20010;
    /**
     * CMPPЭ���廪��Ѷʵ����������
     */
    public static final int GATEWAY_ISMG_TSSX = 0x20100;
    /**
     * CMPPЭ������ʵ����������
     */
    public static final int GATEWAY_ISMG_ASIAINFO = 0x20200;
    /**
     * CMPPЭ��Ӣ˹��ʵ����������
     */
    public static final int GATEWAY_ISMG_INTRINSIC = 0x20300;
    /**
     * CMPPЭ�鱱γͨѶʵ����������
     */
    public static final int GATEWAY_ISMG_BISP = 0x20400;
    /**
     * CMPPЭ�鱱��˹����ʵ����������
     */
    public static final int GATEWAY_ISMG_SITECH = 0x20500;
    /**
     * CMPPЭ�鶫��ŵ����ʵ����������
     */
    public static final int GATEWAY_ISMG_NOKIA_1 = 0x20601;
    /**
     * CMPPЭ�鶫��ŵ����ʵ����������
     */
    public static final int GATEWAY_ISMG_NOKIA_2 = 0x20602;
    /**
     * CMPPЭ�������άʵ����������
     */
    public static final int GATEWAY_ISMG_TUOWEI = 0x20700;
    /**
     * CMPPЭ��׿��ʵ����������
     */
    public static final int GATEWAY_ISMG_MISC = 0x20800;
    /**
     * CMPPЭ�黪Ϊʵ����������
     */
    public static final int GATEWAY_ISMG_HUAWEI = 0x20900;
    /**
     * SGIPЭ����������
     */
    public static final int GATEWAY_SGIP = 0x30000;
    /**
     * SGIPЭ���������������
     */
    public static final int GATEWAY_SGIP_SERVER = 0x30010;
    /**
     * CNGPЭ����������
     */
    public static final int GATEWAY_CNGP = 0x40000;
    /**
     * CNGPЭ���һ��ʵ����������
     */
    public static final int GATEWAY_CNGP1 = 0x40001;
    /**
     * CNGPЭ��ڶ���ʵ����������
     */
    public static final int GATEWAY_CNGP2 = 0x40002;


    public GatewayType()
    {
    }

    /**
     * �ж����������Ƿ����ڶ���������������
     * @param nGatewayType �������ʹ���
     * @return ���������Ƿ����ڶ��������������صĲ���ֵ
     */
    public static boolean isSMSC(int nGatewayType)
    {
        return (nGatewayType & 0xffff0000) == 0x10000;
    }

    /**
     * �ж����������Ƿ�����CMPPЭ������
     * @param nGatewayType �������ʹ���
     * @return ���������Ƿ�����CMPPЭ�����صĲ���ֵ
     */
    public static boolean isISMG(int nGatewayType)
    {
        return (nGatewayType & 0xffff0000) == 0x20000;
    }

    /**
     * �ж����������Ƿ�����SGIPЭ������
     * @param nGatewayType �������ʹ���
     * @return ���������Ƿ�����SGIPЭ�����صĲ���ֵ
     */
    public static boolean isSGIP(int nGatewayType)
    {
        return (nGatewayType & 0xffff0000) == 0x30000;
    }

    /**
     * �ж����������Ƿ�����CNGPЭ������
     * @param nGatewayType �������ʹ���
     * @return ���������Ƿ�����CNGPЭ�����صĲ���ֵ
     */
    public static boolean isCNGP(int nGatewayType)
    {
        return (nGatewayType & 0xffff0000) == 0x40000;
    }

    /**
     * �ж����������Ƿ����ڷ�����������
     * @param nGatewayType
     * @return ���������Ƿ����ڷ����������صĲ���ֵ
     */
    public static boolean isServer(int nGatewayType)
    {
        return nGatewayType == 0x20010 || nGatewayType == 0x10010 || nGatewayType == 0x30010;
    }

    /**
     * ���������������������ʹ����ת������
     * @param strGateway ������������
     * @return  �������ʹ���
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
     * �������ʹ��뵽��������������ת������
     * @param nGatewayType  �������ʹ���
     * @return ������������
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
