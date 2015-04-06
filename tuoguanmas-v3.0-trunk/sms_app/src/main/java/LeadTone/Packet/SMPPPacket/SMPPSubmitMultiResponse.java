package LeadTone.Packet.SMPPPacket;

import LeadTone.BufferException;
import LeadTone.Log;


public class SMPPSubmitMultiResponse extends SMPPSubmitSMResponse
{
    public int no_unsuccess;
    public UnsuccessSMEAddressList unsuccesses;

    public SMPPSubmitMultiResponse()
    {
        no_unsuccess = 0;
        unsuccesses = new UnsuccessSMEAddressList();
    }

    public SMPPSubmitMultiResponse(int sequence_id)
    {
        super(0x80000021, sequence_id);
        no_unsuccess = 0;
        unsuccesses = new UnsuccessSMEAddressList();
    }

    public SMPPSubmitMultiResponse(SMPPPacket packet)
    {
        super(packet);
        no_unsuccess = 0;
        unsuccesses = new UnsuccessSMEAddressList();
    }

    public boolean isValid()
    {
        if(command_id != 0x80000021)
        {
            Log.log("SMPPSubmitMultiResponse.isValid : not a SMPP_SUBMIT_MULTI_RESPONSE command !", 0x6600000000000L);
            return false;
        }
        if(message_id != null && message_id.length() > 65)
        {
            Log.log("SMPPSubmitMultiResponse.isValid : not a SMPP_SUBMIT_MULTI_RESPONSE command !", 0x6600000000000L);
            return false;
        }
        if(no_unsuccess < 0 || no_unsuccess > 254)
        {
            Log.log("SMPPSubmitMultiResponse.isValid : invalid no_unsuccess !", 0x6600000000000L);
            return false;
        }
        if(!unsuccesses.isValid())
        {
            Log.log("SMPPSubmitMultiResponse.isValid : invalid unsuccesses !", 0x6600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tmessage_id = \"" + message_id + "\"", 0x6000000000000L | lMethod);
        Log.log("\tno_unsuccess = \"" + no_unsuccess + "\"", 0x6000000000000L | lMethod);
        unsuccesses.dump(lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPSubmitMultiResponse.wrap : wrap elements !", 0x6800000000000L);
        dump(0x800000000000L);
        addCString(message_id);
        addByte((byte)(no_unsuccess & 0xff));
        unsuccesses.wrap(this);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPSubmitMultiResponse.unwrap : unwrap elements !", 0x6800000000000L);
        message_id = getCString();
        no_unsuccess = getByte();
        unsuccesses.setSize(no_unsuccess);
        unsuccesses.unwrap(this);
        dump(0x800000000000L);
    }


}