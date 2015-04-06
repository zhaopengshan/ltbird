package LeadTone.Packet.CNGPPacket;

import LeadTone.*;



public class CNGPLoginResponse extends CNGPPacket
{

    public byte authenticator_ismg[];
    public byte version;
    
    public CNGPLoginResponse()
    {
        authenticator_ismg = null;
        version = 18;
    }

    public CNGPLoginResponse(int sequence_id)
    {
        super(0x80000001, sequence_id);
        authenticator_ismg = null;
        version = 18;
    }

    public CNGPLoginResponse(CNGPPacket packet)
    {
        super(packet);
        authenticator_ismg = null;
        version = 18;
    }

    public boolean isValid()
    {
        if(command_id != 0x80000001)
        {
            Log.log("CNGPLoginResponse.isValid : not a CNGP_LOGIN_RESPONSE command !", 0x500600000000000L);
            return false;
        }
        if(authenticator_ismg == null || authenticator_ismg.length != 16)
        {
            Log.log("CNGPLoginResponse.isValid : invalid ismg_auth length!", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tauthenticator_ismg = " + Utility.toHexString(authenticator_ismg), 0x500000000000000L | lMethod);
        Log.log("\tversion = 0x" + Utility.toHexString(version), 0x500000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CNGPLoginResponse.wrap : wrap elements !", 0x500800000000000L);
        dump(0x800000000000L);
        addBytes(authenticator_ismg);
        addByte(version);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CNGPLoginResponse.unwrap : unwrap elements !", 0x500800000000000L);
        authenticator_ismg = getBytes(16);
        version = getByte();
        dump(0x800000000000L);
    }


}