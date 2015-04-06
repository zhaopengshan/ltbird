package LeadTone.Packet.SGIPPacket;

import LeadTone.Log;



public class SGIPReportResponse extends SGIPResponse
{

    public SGIPReportResponse(SGIPPacket packet)
    {
        super(packet);
    }

    public SGIPReportResponse(int sequence_id)
    {
        super(0x80000005, sequence_id);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000005)
        {
            Log.log("SGIPReportResponse.isValid : not a SGIP_REPORT_RESPONSE command !", 0x4600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}