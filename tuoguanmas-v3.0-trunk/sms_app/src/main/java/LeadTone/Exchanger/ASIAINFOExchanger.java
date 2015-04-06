package LeadTone.Exchanger;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Gateway.*;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Port.CMPPXMLExchanger;


/**
 * 亚信对CMPP协议的实现，具体差异参考亚信对CMPP协议实现的特殊化
 */
public class ASIAINFOExchanger extends CMPPExchanger
{

    public ASIAINFOExchanger(ASIAINFOGateway gateway, CMPPDatabase database, CMPPXMLExchanger exchanger)
    {
        super(gateway, database, exchanger);
    }

    public CMPPPacket wrapSubmit(CMPPSubmit submit)
    {
        try
        {
            if(m_gateway.m_sp != null && m_gateway.m_sp.enterprise_code != null)
                submit.msg_src = m_gateway.m_sp.enterprise_code;
            int length = submit.dest_terminal_id != null ? submit.dest_terminal_id.length : 0;
            for(int i = 0; i < length; i++)
                if(submit.dest_terminal_id[i].startsWith("86"))
                    submit.dest_terminal_id[i] = submit.dest_terminal_id[i].substring(2);

            ASIAINFOSubmit asiainfo_submit = new ASIAINFOSubmit(submit.sequence_id);
            asiainfo_submit.gateway_name = submit.gateway_name;
            asiainfo_submit.session_id = submit.session_id;
            asiainfo_submit.guid = submit.guid;
            asiainfo_submit.msg_id = submit.msg_id;
            asiainfo_submit.pk_total = submit.pk_total;
            asiainfo_submit.pk_number = submit.pk_number;
            asiainfo_submit.registered_delivery = submit.registered_delivery;
            asiainfo_submit.msg_level = submit.msg_level;
            asiainfo_submit.service_id = submit.service_id;
            asiainfo_submit.fee_user_type = submit.fee_user_type;
            asiainfo_submit.fee_terminal_id = submit.fee_terminal_id;
            asiainfo_submit.tp_pid = submit.tp_pid;
            asiainfo_submit.tp_udhi = submit.tp_udhi;
            asiainfo_submit.msg_fmt = submit.msg_fmt;
            asiainfo_submit.msg_src = submit.msg_src;
            asiainfo_submit.fee_type = submit.fee_type;
            asiainfo_submit.fee_code = submit.fee_code;
            asiainfo_submit.valid_time = submit.valid_time;
            asiainfo_submit.at_time = submit.at_time;
            asiainfo_submit.src_terminal_id = submit.src_terminal_id;
            asiainfo_submit.dest_usr_tl = submit.dest_usr_tl;
            asiainfo_submit.dest_terminal_id = submit.dest_terminal_id;
            asiainfo_submit.msg_length = submit.msg_length;
            asiainfo_submit.msg_content = submit.msg_content;
            asiainfo_submit.wrap(m_gateway.m_sp.service_code);  //for sms submit return ErrorCode 10 (src_terminal_id is wrong)
            submit.empty();
            return asiainfo_submit;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("ASIAINFOExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapSubmit : unexpected exit !", 0x2000000000000004L);
        return null;
    }
}
