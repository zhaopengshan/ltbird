package LeadTone.Packet.SMPPPacket;




public class SMPPCommandSMResponse extends SMPPPacket
{

    public SMPPCommandSMResponse()
    {
    }

    public SMPPCommandSMResponse(int command_id, int sequence_id)
    {
        super(command_id, sequence_id);
    }

    public SMPPCommandSMResponse(SMPPPacket packet)
    {
        super(packet);
    }
}