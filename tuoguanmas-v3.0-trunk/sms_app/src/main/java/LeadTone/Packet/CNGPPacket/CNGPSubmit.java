package LeadTone.Packet.CNGPPacket;

import LeadTone.BufferException;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.CMPPMessageFormat;
import LeadTone.Packet.CMPPPacket.CMPPRegisteredDelivery;
import LeadTone.Utility;



public class CNGPSubmit extends CNGPPacket
{

    public String sp_id;
    public byte subType;
    public byte needReport;
    public byte priority;
    public String serviceID;
    public String feeType;
    public byte feeUserType;
    public String feeCode;
    public byte msgFormat;
    public String validTime;
    public String atTime;
    public String srcTermID;
    public String chargeTermID;
    public byte destTermIDCount;
    public String destTermID[];
    public int msgLength;
    public byte msgContent[];
    public byte protocolID;
    public byte congestionState;
    
    public CNGPSubmit(int sequence_id)
    {
        super(2, sequence_id);
        sp_id = "";
        subType = 2;
        needReport = 0;
        priority = 0;
        serviceID = null;
        feeType = "00";
        feeUserType = 0;
        feeCode = "000000";
        msgFormat = 0;
        validTime = null;
        atTime = null;
        srcTermID = "";
        chargeTermID = "";
        destTermIDCount = 1;
        msgLength = 0;
        msgContent = null;
        protocolID = 0;
        congestionState = 0;
    }

    public CNGPSubmit(CNGPPacket packet)
    {
        super(packet);
        sp_id = "";
        subType = 2;
        needReport = 0;
        priority = 0;
        serviceID = null;
        feeType = "00";
        feeUserType = 0;
        feeCode = "000000";
        msgFormat = 0;
        validTime = null;
        atTime = null;
        srcTermID = "";
        chargeTermID = "";
        destTermIDCount = 1;
        msgLength = 0;
        msgContent = null;
        protocolID = 0;
        congestionState = 0;
    }

    public boolean isValid()
    {
        if(command_id != 2)
        {
            Log.log("CNGPSubmit.isValid : not a CNGP_SUBMIT command !", 0x500600000000000L);
            return false;
        }
        if(sp_id == null || sp_id.equals(""))
        {
            Log.log("CNGPSubmit.isValid : invalid sp_id !", 0x500600000000000L);
            return false;
        }
        if(subType < 0 || subType > 3)
        {
            Log.log("CNGPSubmit.isValid : invalid subType !", 0x500600000000000L);
            return false;
        }
        if(needReport != 0 || needReport != 1)
        {
            Log.log("CNGPSubmit.isValid : invalid needReport !", 0x500600000000000L);
            return false;
        }
        if(priority < 0 && priority > 3)
        {
            Log.log("CNGPSubmit.isValid : priority length exceed !", 0x500600000000000L);
            priority = 3;
        }
        if((feeUserType == 1 || feeUserType == 2 || feeUserType == 0) && chargeTermID != null && chargeTermID.length() > 0)
        {
            Log.log("CNGPSubmit.isValid : chargeTermID is not null !", 0x500600000000000L);
            return false;
        }
        if(feeUserType == 3 && (chargeTermID == null || chargeTermID.length() == 0))
        {
            Log.log("CNGPSubmit.isValid : null chargeTermID !", 0x500600000000000L);
            return false;
        }
        if(chargeTermID != null && chargeTermID.length() > 21)
        {
            Log.log("CNGPSubmit.isValid : chargeTermID length exceed !", 0x500600000000000L);
            return false;
        }
        if(msgFormat != 0 && msgFormat != 3 && msgFormat != 4 && msgFormat != 8 && msgFormat != 15 && msgFormat != 64)
            Log.log("CNGPSubmit.isValid : msgFormat not defined in CMPP !", 0x500600000000000L);
        if(sp_id != null && sp_id.length() > 10)
            Log.log("CNGPSubmit.isValid : sp_id length exceed !", 0x500600000000000L);
        if(feeType == null || feeType.length() != 2)
        {
            Log.log("CNGPSubmit.isValid : invalid fee_type !", 0x500600000000000L);
            return false;
        }
        int num;
        try
        {
            num = Integer.parseInt(feeType);
            if(num < 0 || num > 5)
                return false;
        }
        catch(Exception e)
        {
            return false;
        }
        if(feeCode != null && feeCode.length() > 6)
        {
            Log.log("CNGPSubmit.isValid : feeCode exceed !", 0x500600000000000L);
            return false;
        }
        try
        {
            int n_freeType = Integer.parseInt(feeType);
        }
        catch(Exception e)
        {
            return false;
        }
        if(validTime != null && validTime.length() != 16 && validTime.length() != 0)
        {
            Log.log("CNGPSubmit.isValid : invalid validTime !", 0x500600000000000L);
            return false;
        }
        if(atTime != null && atTime.length() != 16 && atTime.length() != 0)
        {
            Log.log("CNGPSubmit.isValid : invalid atTime !", 0x500600000000000L);
            return false;
        }
        if(srcTermID != null && srcTermID.length() > 21)
        {
            Log.log("CNGPSubmit.isValid : srcTerminalID length exceed !", 0x500600000000000L);
            return false;
        }
        if(destTermIDCount <= 0 || destTermIDCount >= 100)
            Log.log("CNGPSubmit.isValid : invalid destTermIDCount or too much destTermIDCount !", 0x500600000000000L);
        if(destTermID == null || destTermID.length != destTermIDCount)
        {
            Log.log("CNGPSubmit.isValid : null destTerminalID or not according to destTermIDCount !", 0x500600000000000L);
            return false;
        }
        for(int i = 0; i < destTermIDCount; i++)
            if(destTermID[i] != null && destTermID[i].length() > 21)
            {
                Log.log("CNGPSubmit.isValid : destTerminalID length exceed !", 0x500600000000000L);
                return false;
            }

        if(msgLength <= 0 || msgFormat == 0 && msgLength > 160 || (msgFormat == 8 || msgFormat == 15) && msgLength > 140)
        {
            Log.log("CNGPSubmit.isValid : invalid msgLength !", 0x500600000000000L);
            return false;
        }
        if(msgContent == null || msgContent.length != msgLength)
        {
            Log.log("CNGPSubmit.isValid : invalid msgContent !", 0x500600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tSPID = " + sp_id, 0x500000000000000L | lMethod);
        Log.log("\tSubType = " + subType + " (" + CNGPSubType.toString(subType) + ")", 0x500000000000000L | lMethod);
        Log.log("\tNeedReport = " + needReport + " (" + CMPPRegisteredDelivery.toString(needReport) + ")", 0x500000000000000L | lMethod);
        Log.log("\tPriority = " + priority, 0x500000000000000L | lMethod);
        Log.log("\tServiceID = \"" + serviceID + "\"", 0x500000000000000L | lMethod);
        Log.log("\tfeeType = \"" + feeType + "\"" + " (" + CNGPFeeType.toString(feeType) + ")", 0x500000000000000L | lMethod);
        Log.log("\tfeeUserType = \"" + feeUserType + "\" " + " (" + CNGPFeeUserType.toString(feeUserType) + ")", 0x500000000000000L | lMethod);
        Log.log("\tfeeCode = \"" + feeCode + "\"", 0x500000000000000L | lMethod);
        Log.log("\tmsgFormt = " + msgFormat + " (" + CMPPMessageFormat.toString(msgFormat) + ")", 0x500000000000000L | lMethod);
        Log.log("\tvalidTime = \"" + validTime + "\"", 0x500000000000000L | lMethod);
        Log.log("\tatTime = \"" + atTime + "\"", 0x500000000000000L | lMethod);
        Log.log("\tsrcTermID= \"" + srcTermID + "\"", 0x500000000000000L | lMethod);
        Log.log("\tChargeTermID = \"" + chargeTermID + "\"", 0x500000000000000L | lMethod);
        Log.log("\tDestTermIDCount = " + destTermIDCount, 0x500000000000000L | lMethod);
        for(int i = 0; i < destTermIDCount; i++)
            Log.log("\tDestTermID[" + i + "] = \"" + destTermID[i] + "\"", 0x500000000000000L | lMethod);

        Log.log("\tMsgLength = " + msgLength, 0x500000000000000L | lMethod);
        Log.log("\tMsgContent = 0x" + Utility.toHexString(msgContent), 0x500000000000000L | lMethod);
        if(msgFormat == 0 || msgFormat == 15)
            Log.log("\t            = \"" + Utility.get_msg_content(msgContent) + "\"", 0x500000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("CNGPSubmit.wrap : wrap elements !", 0x500800000000000L);
        dump(0x500800000000000L);
        addString(sp_id, 10);
        addByte(subType);
        addByte(needReport);
        addByte(priority);
        addString(serviceID, 10);
        addString("" + Integer.parseInt(feeType), 2);
        addByte(feeUserType);
        addString("" + Integer.parseInt(feeCode), 6);
        addByte(msgFormat);
        addString(validTime, 17);
        addString(atTime, 17);
        addString(srcTermID, 21);
        addString(chargeTermID, 21);
        addByte(destTermIDCount);
        for(int i = destTermIDCount; i > 0; i--)
            addString(destTermID[i - 1], 21);

        addByte((byte)(msgLength & 0xff));
        addBytes(msgContent);
        byte restContent[] = new byte[254 - msgContent.length];
        addBytes(restContent);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("CNGPSubmit.unwrap : unwrap elements !", 0x500800000000000L);
        sp_id = getString(10);
        subType = getByte();
        needReport = getByte();
        priority = getByte();
        serviceID = getString(10);
        feeType = getString(2);
        feeUserType = getByte();
        feeCode = getString(6);
        msgFormat = getByte();
        validTime = getString(17);
        atTime = getString(17);
        srcTermID = getString(21);
        chargeTermID = getString(21);
        destTermIDCount = getByte();
        destTermID = new String[destTermIDCount];
        for(int i = 0; i < destTermIDCount; i++)
            destTermID[i] = getString(21);

        msgLength = getByte();
        msgContent = getBytes(msgLength);
        dump(0x500800000000000L);
    }


}