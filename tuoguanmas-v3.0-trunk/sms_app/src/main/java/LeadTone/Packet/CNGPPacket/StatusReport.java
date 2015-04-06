package LeadTone.Packet.CNGPPacket;


public class StatusReport
{

    public StatusReport()
    {
    }

    public static String toString(String status)
    {
        if(status.equals("DELIVRD"))
            return "success";
        if(status.equals("EXPIRED"))
            return "\u8D85\u8FC7\u6709\u6548\u671F";
        if(status.equals("DELETED"))
            return "\u77ED\u6D88\u606F\u5DF2\u88AB\u5220\u9664";
        if(status.equals("UNDELIV"))
            return "\u77ED\u6D88\u606F\u4E0D\u53EF\u8F6C\u53D1";
        if(status.equals("ACCEPTD"))
            return "\u77ED\u6D88\u606F\u5DF2\u88AB\u7528\u6237\u63A5\u6536";
        if(status.equals("UNKNOWN"))
            return "\u672A\u77E5\u77ED\u6D88\u606F\u72B6\u6001";
        if(status.equals("REJECTD"))
            return "\u77ED\u6D88\u606F\u88AB\u62D2\u7EDD";
        if(status.startsWith("CA:") || status.startsWith("MA:"))
        {
            try
            {
                int result = Integer.parseInt(status.substring(3));
                switch(result)
                {
                case 51:
                    return "\u5C1A\u672A\u5EFA\u7ACB\u8FDE\u63A5";

                case 52:
                    return "\u5C1A\u672A\u6210\u529F\u767B\u5F55";

                case 53:
                    return "\u53D1\u9001\u6D88\u606F\u5931\u8D25";

                case 54:
                    return "\u8D85\u65F6\u672A\u63A5\u6536\u5230\u5E94\u7B54\u6D88\u606F";
                }
                if(result >= 100 && result <= 499)
                    return "\u5382\u5BB6\u81EA\u5B9A\u4E49\u9519\u8BEF\u7801";
                else
                    return "\u4FDD\u7559";
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return status;
        }
        if(status.startsWith("CB:"))
        {
            try
            {
                int result = Integer.parseInt(status.substring(3));
                switch(result)
                {
                case 1:
                    return "\u975E\u9884\u4ED8\u8D39\u7528\u6237";

                case 2:
                    return "\u6570\u636E\u5E93\u64CD\u4F5C\u5931\u8D25";

                case 3:
                    return "\u9274\u6743\u5931\u8D25";

                case 4:
                    return "\u8D85\u8FC7\u6700\u5927\u9519\u8BEF\u6B21\u6570";

                case 5:
                    return "PHS\u7528\u6237\u5E10\u6237\u6570\u636E\u5F02\u5E38";

                case 6:
                    return "\u670D\u52A1\u6CA1\u6709\u6FC0\u6D3B";

                case 7:
                    return "\u7528\u6237\u4F59\u989D\u4E0D\u8DB3";

                case 8:
                    return "\u8D85\u8FC7\u5305\u6708\u8D39\u7528\u4E0A\u9650";

                case 9:
                    return "\u8D85\u8FC7\u6700\u9AD8\u6B20\u8D39\u989D";

                case 10:
                    return "\u7528\u6237\u5DF2\u6CE8\u518C\u8BE5\u670D\u52A1";

                case 11:
                    return "\u7528\u6237\u6CA1\u6709\u6CE8\u518C\u8BE5\u670D\u52A1";

                case 14:
                    return "\u672A\u767B\u8BB0\u7684SMGW";

                case 15:
                    return "SMGW\u767B\u5F55\u6458\u8981\u9519\u8BEF";

                case 16:
                    return "\u53C2\u6570\u9519\u8BEF";

                case 17:
                    return "\u670D\u52A1\u5668\u7AEF\u6570\u636E\u672A\u4F20\u5B8C";

                case 18:
                    return "\u91CD\u590D\u53D1\u9001\u6D88\u606F\u5E8F\u5217\u53F7MsgID\u76F8\u540C\u7684\u8BA1\u8D39\u8BF7\u6C42\u4FE1\u606F";

                case 20:
                    return "\u672A\u77E5\u9519\u8BEF";

                case 21:
                    return "\u6570\u636E\u5E93\u9519\u8BEF";

                case 22:
                    return "SP\u4E92\u8054\u5931\u8D25";

                case 23:
                    return "\u6570\u503C\u8D8A\u754C";

                case 24:
                    return "\u5B57\u6BB5\u8D85\u957F";

                case 25:
                    return "\u65E0\u76F8\u5173\u6570\u636E";

                case 26:
                    return "\u6570\u636E\u91CD\u590D";

                case 40:
                    return "\u672A\u767B\u8BB0\u7684SP";

                case 41:
                    return "SP\u5E10\u53F7\u5F02\u5E38";

                case 42:
                    return "SP\u65E0\u6743\u9650";

                case 43:
                    return "SP\u5E10\u53F7\u5DF2\u5B58\u5728";

                case 44:
                    return "\u672A\u767B\u8BB0\u7684SP\u4E1A\u52A1\u7C7B\u578B";

                case 45:
                    return "SP\u4E1A\u52A1\u7C7B\u578B\u6570\u636E\u5F02\u5E38";

                case 46:
                    return "SP\u4E1A\u52A1\u7C7B\u578B\u5DF2\u5B58\u5728";

                case 52:
                    return "\u4FDD\u7559";

                case 53:
                    return "\u4FDD\u7559";

                case 54:
                    return "\u4FDD\u7559";

                case 55:
                    return "\u7B7E\u7EA6\u4FE1\u606F\u5DF2\u5B58\u5728";

                case 56:
                    return "\u7B7E\u7EA6\u4FE1\u606F\u4E0D\u6625\u5728";

                case 57:
                    return "\u7B7E\u7EA6\u6570\u636E\u5F02\u5E38";

                case 61:
                    return "\u6708\u6D88\u8D39\u8D85\u989D";

                case 62:
                    return "\u5355\u7B14\u6D88\u8D39\u8D85\u989D";

                case 63:
                    return "\u7528\u6237\u62D2\u7EDD";

                case 64:
                    return "\u77ED\u6D88\u606F\u7F16\u53F7\u5DF2\u5B58\u5728";

                case 65:
                    return "\u5BF9\u5E94\u6263\u8D39\u8BF7\u6C42\u4E0D\u5B58\u5728";

                case 66:
                    return "\u6263\u8D39\u8BF7\u6C42\u5DF2\u88AB\u786E\u8BA4";

                case 67:
                    return "\u672A\u5B9A\u4E49\u7684\u8BA1\u8D39\u7C7B\u578B";

                case 68:
                    return "\u672A\u5B9A\u4E49\u7684\u7F16\u7801\u65B9\u5F0F";
                }
                if(result >= 100 && result <= 499)
                    return "\u5382\u5BB6\u81EA\u5B9A\u4E49\u9519\u8BEF\u7801";
                else
                    return "\u4FDD\u7559";
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return status;
        }
        if(status.startsWith("MB:"))
        {
            try
            {
                int result = Integer.parseInt(status.substring(3));
                switch(result)
                {
                case 16:
                    return "\u6D88\u606F\u957F\u5EA6\u9519\u8BEF";

                case 17:
                    return "\u547D\u4EE4\u957F\u5EA6\u9519\u8BEF";

                case 18:
                    return "\u6D88\u606FID\u65E0\u6548";

                case 19:
                    return "\u6CA1\u6709\u6267\u884C\u6B64\u547D\u4EE4\u7684\u6743\u9650";

                case 32:
                    return "\u65E0\u6548\u7684SYSTEMID";

                case 33:
                    return "\u65E0\u6548\u7684\u5BC6\u7801";

                case 34:
                    return "\u65E0\u6548\u7684SYSTEMTYPE";

                case 64:
                    return "\u5730\u5740\u9519\u8BEF";

                case 65:
                    return "\u8D85\u8FC7\u6700\u5927\u63D0\u4EA4\u6570";

                case 69:
                    return "\u521B\u5EFA\u6D88\u606F\u5931\u8D25";

                case 70:
                    return "\u65E0\u6548\u7684MsgId";

                case 71:
                    return "\u6570\u636E\u5E93\u5931\u8D25";

                case 66:
                    return "\u8D85\u8FC7\u6700\u5927\u4E0B\u53D1\u6570";

                case 67:
                    return "\u65E0\u6548\u7684\u7528\u6237";

                case 68:
                    return "\u65E0\u6548\u7684\u6570\u636E\u683C\u5F0F";

                case 72:
                    return "\u53D6\u6D88\u6D88\u606F\u5931\u8D25";

                case 73:
                    return "\u77ED\u6D88\u606F\u72B6\u6001\u9519\u8BEF";

                case 74:
                    return "\u66FF\u6362\u6D88\u606F\u5931\u8D25";

                case 75:
                    return "\u66FF\u6362\u6D88\u606F\u6E90\u5730\u5740\u9519\u8BEF";

                case 98:
                    return "\u6E90\u5730\u5740\u9519\u8BEF";

                case 101:
                    return "\u76EE\u7684\u5730\u5740\u9519\u8BEF";

                case 102:
                    return "\u65E0\u6548\u7684\u5B9A\u65F6\u65F6\u95F4";

                case 103:
                    return "\u65E0\u6548\u7684\u8D85\u65F6\u65F6\u95F4";

                case 104:
                    return "\u65E0\u6548\u7684IsReport";

                case 106:
                    return "\u65E0\u6548\u7684PRI";

                case 107:
                    return "\u65E0\u6548\u7684NeedReport";

                case 128: 
                    return "\u6307\u5B9A\u7528\u6237\u5DF2\u5B58\u5728";

                case 129: 
                    return "\u521B\u5EFA\u7528\u6237\u5931\u8D25";

                case 130: 
                    return "\u7528\u6237ID\u9519\u8BEF";

                case 131: 
                    return "\u6307\u5B9A\u7528\u6237\u4E0D\u5B58\u5728";
                }
                if(result >= 144 && result <= 4095)
                    return "\u5382\u5BB6\u81EA\u5B9A\u4E49\u9519\u8BEF\u7801";
                else
                    return "\u4FDD\u7559";
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return status;
        } else
        {
            return "unknown";
        }
    }
}