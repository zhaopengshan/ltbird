package LeadTone.Packet.CNGPPacket;

import LeadTone.BufferException;
import LeadTone.Log;



public class CNGPDeliverResponse extends CNGPPacket
{
    public String msgID;
    public byte protocolID;
    public byte congestionState;

    public CNGPDeliverResponse(int sequence_id)
    {
        super(0x80000003, sequence_id);
        msgID = "";
        protocolID = 0;
        congestionState = 0;
    }

    public CNGPDeliverResponse(CNGPPacket packet)
    {
        super(packet);
        msgID = "";
        protocolID = 0;
        congestionState = 0;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000003)
        {
            Log.log("CNGPDeliverResponse.isValid : not a CMPP_Deliver_RESPONSE command !", 0x500600000000000L);
            return false;
        }
        if(msgID != null && msgID.length() > 10)
        {
            Log.log("CNGPDeliverResponse.isValid : invalid message_id !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tMsgID = \"" + msgID + "\" (SMGW:" + msgID.substring(0, 4) + " Time:" + msgID.substring(4, 8) + " SequenceID:" + msgID.substring(8, 10), 0x500000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CNGPDeliverResponse.wrap : wrap elements !", 0x500800000000000L);
        dump(0x800000000000L);
        addString(msgID, 10);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CNGPDeliverResponse.unwrap : unwrap elements !", 0x500800000000000L);
        msgID = getString(10);
        if(isEOB());
        dump(0x800000000000L);
    }


}