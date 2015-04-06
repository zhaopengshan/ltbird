package LeadTone.Packet.SGIPPacket;


public class SGIPResult
{

    public SGIPResult()
    {
    }

    public static String toString(int result)
    {
        switch(result)
        {
        case 0:
            return "\u6B63\u786E";

        case 1:
            return "\u975E\u6CD5\u767B\u5F55";

        case 2:
            return "\u91CD\u590D\u767B\u5F55";

        case 3:
            return "\u8FDE\u63A5\u8FC7\u591A";

        case 4:
            return "\u767B\u5F55\u7C7B\u578B\u9519";

        case 5:
            return "\u53C2\u6570\u683C\u5F0F\u9519";

        case 6:
            return "\u975E\u6CD5\u624B\u673A\u53F7\u7801";

        case 7:
            return "\u6D88\u606FID\u9519";

        case 8:
            return "\u6D88\u606F\u957F\u5EA6\u9519";

        case 9:
            return "\u975E\u6CD5\u5E8F\u5217\u53F7";

        case 10:
            return "\u975E\u6CD5\u64CD\u4F5CGNS";

        case 21:
            return "\u76EE\u7684\u5730\u5740\u4E0D\u53EF\u8FBE";

        case 22:
            return "\u8DEF\u7531\u9519";

        case 23:
            return "\u8DEF\u7531\u4E0D\u5B58\u5728";

        case 24:
            return "\u8BA1\u8D39\u53F7\u7801\u65E0\u6548";

        case 25:
            return "\u7528\u6237\u4E0D\u80FD\u901A\u8BAF";

        case 26:
            return "\u624B\u673A\u5185\u5B58\u4E0D\u8DB3";

        case 27:
            return "\u624B\u673A\u4E0D\u652F\u6301\u77ED\u6D88\u606F";

        case 28:
            return "\u624B\u673A\u63A5\u6536\u77ED\u6D88\u606F\u51FA\u9519";

        case 29:
            return "\u4E0D\u77E5\u9053\u7684\u7528\u6237";

        case 30:
            return "\u4E0D\u63D0\u4F9B\u6B64\u529F\u80FD";

        case 31:
            return "\u975E\u6CD5\u8BBE\u5907";

        case 32:
            return "\u7CFB\u7EDF\u5931\u8D25";

        case 11:
        case 12:
        case 13:
        case 14:
        case 15:
        case 16:
        case 17:
        case 18:
        case 19:
        case 20: 
        default:
            return "\u4FDD\u7559";
        }
    }
}