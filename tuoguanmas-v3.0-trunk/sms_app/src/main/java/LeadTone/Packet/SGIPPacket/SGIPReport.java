package LeadTone.Packet.SGIPPacket;

import LeadTone.BufferException;
import LeadTone.Log;


public class SGIPReport extends SGIPPacket
{

    public int packet_node_id;
    public int packet_time_stamp;
    public int packet_sequence_id;
    public byte report_type;
    public String user_number;
    public byte state;
    public byte error_code;
    public String reserve;

    public SGIPReport(int sequence_id)
    {
        super(5, sequence_id);
        report_type = 0;
        user_number = null;
        state = 0;
        error_code = 0;
        reserve = null;
    }

    public SGIPReport(SGIPPacket packet)
    {
        super(packet);
        report_type = 0;
        user_number = null;
        state = 0;
        error_code = 0;
        reserve = null;
    }

    public boolean isValid()
    {
        if(command_id != 5)
        {
            Log.log("SGIPReport.isValid : not a SGIP_REPORT command !", 0x4600000000000L);
            return false;
        }
        if(report_type < 0 || report_type > 1)
        {
            Log.log("SGIPReport.isValid : invalid report_type !", 0x4600000000000L);
            return false;
        }
        if(user_number != null && (user_number.length() > 21 || !user_number.startsWith("86")))
        {
            Log.log("SGIPReport.isValid : invalid user_number !", 0x4600000000000L);
            return false;
        }
        if(state < 0 || state > 2)
        {
            Log.log("SGIPReport.isValid : invalid state !", 0x4600000000000L);
            return false;
        }
        if(state == 2 && error_code == 0)
            Log.log("SGIPReport.isValid : invalid error_code !", 0x4600000000000L);
        if(state == 0 && error_code != 0)
            Log.log("SGIPReport.isValid : invalid error_code !", 0x4600000000000L);
        return true;
    }

    public void dump(long lMethod)
    {
        Log.log("\tpacket_node_id = " + packet_node_id, 0x4000000000000L | lMethod);
        Log.log("\tpacket_time_stamp = " + packet_time_stamp, 0x4000000000000L | lMethod);
        Log.log("\tpacket_sequence_id = " + packet_sequence_id, 0x4000000000000L | lMethod);
        Log.log("\treport_type = " + report_type + " (" + SGIPReportType.toString(report_type) + ")", 0x4000000000000L | lMethod);
        Log.log("\tuser_number = \"" + user_number + "\"", 0x4000000000000L | lMethod);
        Log.log("\tstate = " + state + " (" + SGIPReportState.toString(state) + ")", 0x4000000000000L | lMethod);
        Log.log("\terror_code = " + error_code + " (" + SGIPResult.toString(error_code) + ")", 0x4000000000000L | lMethod);
        Log.log("\treserve = \"" + reserve + "\"", 0x4000000000000L | lMethod);
    }

    public void wrap()
        throws BufferException
    {
        Log.log("SGIPReport.wrap : wrap elements !", 0x4800000000000L);
        dump(0x800000000000L);
        addInteger(packet_node_id);
        addInteger(packet_time_stamp);
        addInteger(packet_sequence_id);
        addByte(report_type);
        addString(user_number, 21);
        addByte(state);
        addByte(error_code);
        addString(reserve, 8);
    }

    public void unwrap()
        throws BufferException
    {
        Log.log("SGIPReport.unwrap : unwrap elements !", 0x4800000000000L);
        packet_node_id = getInteger();
        packet_time_stamp = getInteger();
        packet_sequence_id = getInteger();
        report_type = getByte();
        user_number = getString(21);
        state = getByte();
        error_code = getByte();
        reserve = getString(8);
        dump(0x800000000000L);
    }


}