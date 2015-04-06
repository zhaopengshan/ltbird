package LeadTone.Packet.CNGPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPMessageFormat;
import LeadTone.Utility;



public class CNGPDeliver extends CNGPPacket
{
    public String msgID;
    public byte isReport;
    public byte msgFormat;
    public String recvTime;
    public String srcTermID;
    public String destTermID;
    public byte msgLength;
    public byte msgContent[];
    public byte protocolID;
    public byte congestionState;
    public CNGPStatusReport status_report;
    
    public CNGPDeliver(int sequence_id)
    {
        super(3, sequence_id);
        msgID = "";
        isReport = 0;
        msgFormat = 0;
        recvTime = "yyyymmddhhmmss";
        srcTermID = "";
        destTermID = "";
        msgLength = 0;
        protocolID = 0;
        congestionState = 0;
        status_report = new CNGPStatusReport();
    }

    public CNGPDeliver(CNGPPacket packet)
    {
        super(packet);
        msgID = "";
        isReport = 0;
        msgFormat = 0;
        recvTime = "yyyymmddhhmmss";
        srcTermID = "";
        destTermID = "";
        msgLength = 0;
        protocolID = 0;
        congestionState = 0;
        status_report = new CNGPStatusReport();
    }

    public boolean isValid()
    {
        if(command_id != 3)
        {
            Log.log("CNGPDeliver.isValid : not a CMPP_DELIVER command !", 0x500600000000000L);
            return false;
        }
        if(destTermID != null && destTermID.length() >= 21)
        {
            Log.log("CNGPDeliver.isValid : invalid DestTermID !", 0x500600000000000L);
            return false;
        }
        if(msgFormat != 0 && msgFormat != 3 && msgFormat != 4 && msgFormat != 8 && msgFormat != 15)
            Log.log("CNGPDeliver.isValid : msgFormat is not defined in CNGP !", 0x500600000000000L);
        if(srcTermID != null && srcTermID.length() >= 21)
        {
            Log.log("CNGPDeliver.isValid : invalid srcTerminalID !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tMsgID = \"" + msgID + "\"", 0x500000000000000L | lMethod);
        Log.log("\tIsReport = " + isReport + "(" + (isReport != 0 ? "\u72B6\u6001\u62A5\u544A" : "MO") + ")", 0x500000000000000L | lMethod);
        Log.log("\tMsgFormat = " + msgFormat + "(" + CMPPMessageFormat.toString(msgFormat) + ")", 0x500000000000000L | lMethod);
        Log.log("\tRecvTime = \"" + recvTime + "\"", 0x500000000000000L | lMethod);
        Log.log("\tSrcTermID = \"" + srcTermID + "\"", 0x500000000000000L | lMethod);
        Log.log("\tDestTermID = \"" + destTermID + "\"", 0x500000000000000L | lMethod);
        Log.log("\tMsgLength = " + msgLength, 0x500000000000000L | lMethod);
        Log.log("\tbinary_content = 0x" + Utility.toHexString(msgContent), 0x500000000000000L | lMethod);
        if((isReport & 0) == 0)
        {
            if(msgFormat == 0 || msgFormat == 15)
                Log.log("\ttext_content = \"" + Utility.get_msg_content(msgContent) + "\"", 0x500000000000000L | lMethod);
        } else
        if((isReport & 1) == 1)
        {
            Log.log("\ttext_content = \"" + Utility.get_msg_content(msgContent) + "\"", 0x500000000000000L | lMethod);
            status_report.dump(lMethod);
        }
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CNGPDeliver.unwrap : unwrap elements !", 0x500800000000000L);
        msgID = getString(10);
        isReport = getByte();
        msgFormat = getByte();
        recvTime = getString(14);
        srcTermID = getString(21);
        destTermID = getString(21);
        msgLength = getByte();
        int len = msgLength;
        msgContent = getBytes(len);
        if((isReport & 1) == 1)
            status_report.unwrap(Utility.get_msg_content(msgContent));
        dump(0x500800000000000L);
    }


}