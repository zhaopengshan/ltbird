package LeadTone.Exchanger;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Engine;
import LeadTone.Gateway.CMPPGatewayEngine;
import LeadTone.Gateway.MISCGateway;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Port.CMPPXMLExchanger;


/**
 * 卓望对CMPP协议的实现，具体差异参考卓望对CMPP协议实现的特殊化
 */
public class MISCExchanger extends CMPPExchanger
{

    public MISCExchanger(MISCGateway gateway, CMPPDatabase database, CMPPXMLExchanger exchanger)
    {
        super(gateway, database, exchanger);
    }

    public CMPPDeliver unwrapDeliver(CMPPPacket packet)
    {
        try
        {
            MISCDeliver deliver = new MISCDeliver(packet);
            deliver.unwrap();
            if(deliver.msg_fmt == 8)
                reencode_msg_content(deliver);
            CMPPDeliverResponse response = new CMPPDeliverResponse(deliver.sequence_id);
            response.gateway_name = deliver.gateway_name;
            response.sequence_id = deliver.sequence_id;
            response.result = 0;
            response.session_id = deliver.session_id;
            response.wrap();
            for(; !m_gateway.send(response); nap());
            return deliver;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("MISCExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapDeliver : unexpected exit !", 0x2000000000000004L);
        return null;
    }
}
