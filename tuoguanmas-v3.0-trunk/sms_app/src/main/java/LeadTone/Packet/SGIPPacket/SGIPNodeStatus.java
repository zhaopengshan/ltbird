package LeadTone.Packet.SGIPPacket;

import LeadTone.BufferException;
import LeadTone.Log;


public class SGIPNodeStatus
{
    public String node_id;
    public String receive_time;
    public String send_time;
    public byte result;
    public String reserve;

    public SGIPNodeStatus()
    {
        node_id = null;
        receive_time = null;
        send_time = null;
        result = 0;
        reserve = null;
    }

    public boolean isValid()
    {
        if(node_id != null && node_id.length() > 6)
        {
            Log.log("SGIPNodeStatus.isValid : invalid node_id !", 0x8600000000000L);
            return false;
        }
        if(receive_time != null && receive_time.length() > 16)
        {
            Log.log("SGIPNodeStatus.isValid : invalid receive_time !", 0x8600000000000L);
            return false;
        }
        if(send_time != null && send_time.length() > 16)
        {
            Log.log("SGIPNodeStatus.isValid : invalid send_time !", 0x8600000000000L);
            return false;
        } else
        {
            return true;
        }
    }

    public void dump(long lMethod)
    {
        Log.log("\tnode_id = \"" + node_id + "\"", 0x8000000000000L | lMethod);
        Log.log("\treceive_time = \"" + receive_time + "\"", 0x8000000000000L | lMethod);
        Log.log("\tsend_time = \"" + send_time + "\"", 0x8000000000000L | lMethod);
        Log.log("\tresult = " + result + " (" + SGIPNodeStatusResult.toString(result) + ")", 0x8000000000000L | lMethod);
    }

    public void wrap(SGIPPacket packet)
        throws BufferException
    {
        Log.log("SGIPNodeStatus.wrap : wrap elements !", 0x8800000000000L);
        dump(0x800000000000L);
        packet.addByte(result);
        packet.addString(node_id, 6);
        packet.addString(receive_time, 16);
        packet.addString(send_time, 16);
        packet.addString(reserve, 8);
    }

    public void unwrap(SGIPPacket packet)
        throws BufferException
    {
        Log.log("SGIPNodeStatus.unwrap : unwrap elements !", 0x8800000000000L);
        result = packet.getByte();
        node_id = packet.getString(6);
        receive_time = packet.getString(16);
        send_time = packet.getString(16);
        reserve = packet.getString(8);
        dump(0x800000000000L);
    }


}