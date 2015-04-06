package LeadTone.Packet.SGIPPacket;


public class SGIPMORelateToMTFlag
{

    public SGIPMORelateToMTFlag()
    {
    }

    public static String toString(byte mo_relate_to_mt_flag)
    {
        switch(mo_relate_to_mt_flag)
        {
        case 0:
            return "MO\u70B9\u64AD\u5F15\u8D77\u7684\u7B2C\u4E00\u6761MT\u6D88\u606F";

        case 1:
            return "MO\u70B9\u64AD\u5F15\u8D77\u7684\u975E\u7B2C\u4E00\u6761\u6D88\u606F";

        case 2:
            return "\u975EMO\u70B9\u64AD\u5F15\u8D77\u7684MT\u6D88\u606F";

        case 3: 
            return "\u7CFB\u7EDF\u53CD\u9988\u5F15\u8D77\u7684MT\u6D88\u606F";
        }
        return "\u4FDD\u7559";
    }
}