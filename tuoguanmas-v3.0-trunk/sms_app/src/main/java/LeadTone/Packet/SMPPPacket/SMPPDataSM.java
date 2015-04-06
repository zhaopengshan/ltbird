package LeadTone.Packet.SMPPPacket;

import LeadTone.*;



public class SMPPDataSM extends SMPPPacket
{
    public String service_type;
    public SMEAddress source;
    public SMEAddress destination;
    public byte esm_class;
    public byte registered_delivery;
    public byte data_coding;

    public SMPPDataSM()
    {
        service_type = null;
        source = new SMEAddress();
        destination = new SMEAddress();
        esm_class = 0;
        registered_delivery = 0;
        data_coding = 0;
    }

    public SMPPDataSM(int sequence_id)
    {
        super(259, sequence_id);
        service_type = null;
        source = new SMEAddress();
        destination = new SMEAddress();
        esm_class = 0;
        registered_delivery = 0;
        data_coding = 0;
    }

    public SMPPDataSM(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
        service_type = null;
        source = new SMEAddress();
        destination = new SMEAddress();
        esm_class = 0;
        registered_delivery = 0;
        data_coding = 0;
    }

    public SMPPDataSM(SMPPPacket packet)
    {
        super(packet);
        service_type = null;
        source = new SMEAddress();
        destination = new SMEAddress();
        esm_class = 0;
        registered_delivery = 0;
        data_coding = 0;
    }

    public boolean isValid()
    {
        if(command_id != 259)
        {
            Log.log("SMPPDataSM.isValid : not a SMPP_DATA_SM command !", 0x6600000000000L);
            return false;
        }
        if(service_type != null && service_type.length() > 5)
        {
            Log.log("SMPPDataSM.isValid : invalid service_type !", 0x6600000000000L);
            return false;
        }
        if(!source.isValid())
        {
            Log.log("SMPPDataSM.isValid : invalid source !", 0x6600000000000L);
            return false;
        }
        if(!destination.isValid())
        {
            Log.log("SMPPDataSM.isValid : invalid destination !", 0x6600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tservice_type = \"" + service_type + "\"", 0x6000000000000L | lMethod);
        source.dump(lMethod);
        destination.dump(lMethod);
        Log.log("\tesm_class = 0x" + Utility.toHexString(esm_class) + " (" + ESMClass.toString(esm_class) + ")", 0x6000000000000L | lMethod);
        Log.log("\tdata_coding = 0x" + Utility.toHexString(data_coding) + " (" + DataCoding.toString(data_coding) + ")", 0x6000000000000L | lMethod);
        m_ops.dump(lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SMPPDataSM.wrap : wrap elements !", 0x6800000000000L);
        dump(0x800000000000L);
        addCString(service_type);
        source.wrap(this);
        destination.wrap(this);
        addByte(esm_class);
        addByte(registered_delivery);
        addByte(data_coding);
        m_ops.wrap(this);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SMPPDataSM.unwrap : unwrap elements !", 0x6800000000000L);
        service_type = getCString();
        source.unwrap(this);
        destination.unwrap(this);
        esm_class = getByte();
        registered_delivery = getByte();
        data_coding = getByte();
        m_ops.unwrap(this);
        dump(0x800000000000L);
    }


}