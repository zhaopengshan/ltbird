package LeadTone.Packet.SGIPPacket;

import LeadTone.Log;



public class SGIPUserReportResponse extends SGIPResponse
{

    public SGIPUserReportResponse(int sequence_id)
    {
        super(0x80000011, sequence_id);
    }

    public SGIPUserReportResponse(SGIPPacket packet)
    {
        super(packet);
    }

    public boolean isValid()
    {
        if(command_id != 0x80000011)
        {
            Log.log("SGIPUserReportResponse.isValid : not a SGIP_USERRPT_RESPONSE command !", 0x1600000000000L);
            return false;
        } else
        {
            return true;
        }
    }
}