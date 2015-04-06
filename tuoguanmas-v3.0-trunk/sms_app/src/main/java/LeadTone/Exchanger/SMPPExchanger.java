package LeadTone.Exchanger;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Engine;
import LeadTone.Gateway.SMPPGatewayEngine;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.SMPPPacket.*;
import LeadTone.Port.CMPPXMLExchanger;
import LeadTone.Utility;


/**
 * 用于中国移动CMPP协议包和短消息点对点SMPP协议包间的转换功能
 */
public class SMPPExchanger extends DataExchanger
{
    SMPPGatewayEngine m_gateway;
    CMPPPacket cmpp_input;
    SMPPPacket smpp_output;

    public SMPPExchanger(SMPPGatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger exchanger)
    {
        super(database, exchanger);
        m_gateway = null;
        cmpp_input = null;
        smpp_output = null;
        m_gateway = gateway;
    }

    public void reencode_msg_content(SMPPDeliverSM deliver)
    {
        String content = Utility.ucs2_to_gb2312(deliver.short_message);
        if(content == null)
            return;
        try
        {
            deliver.short_message = content.getBytes("GB2312");
            deliver.data_coding = 15;
            deliver.sm_length = deliver.short_message.length;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000004L);
            Log.log(e);
            Log.log("SMPPExchange.reencode_msg_content : unexpected exit !", 0x2000000000000004L);
        }
    }

    public void run()
    {
        try
        {
            Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread startup !", 4L);
            m_nStatus = 1;
            for(; isRunning(); Engine.nap())
            {
                if(m_gateway.isStopped())
                {
                    Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : gateway is stopped !", 0x2000000000000004L);
                    break;
                }
                if(smpp_output == null)
                    handleOutput();
                toGateway();
                if(m_database.isStopped())
                {
                    Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : database is stopped !", 0x2000000000000004L);
                    break;
                }
                if(cmpp_input == null)
                    handleInput();
                toDatabase();
            }

        }
        catch(Exception e)
        {
            Log.log(e);
            Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : unexpected exit !", 0x2000000000000004L);
        }
        m_nStatus = 3;
        Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread stopped !", 4L);
    }

    public SMPPPacket wrapSubmitMulti(CMPPSubmit submit)
    {
        try
        {
            SMPPSubmitMulti smpp_submit = new SMPPSubmitMulti(submit.sequence_id);
            smpp_submit.gateway_name = submit.gateway_name;
            smpp_submit.session_id = submit.session_id;
            smpp_submit.guid = submit.guid;
            smpp_submit.service_type = submit.service_id;
            smpp_submit.source.TON = 0;
            smpp_submit.source.NPI = 1;
            smpp_submit.source.address = submit.src_terminal_id;
            if(submit.src_terminal_id != null && submit.src_terminal_id.length() > 0 && submit.src_terminal_id.startsWith("86"))
                smpp_submit.source.TON = 1;
            smpp_submit.number_of_dests = submit.dest_usr_tl;
            for(int i = 0; i < submit.dest_usr_tl; i++)
            {
                DestinationAddress da = new DestinationAddress(submit.dest_terminal_id[i].startsWith("86") ? 1 : 0, 1, submit.dest_terminal_id[i]);
                smpp_submit.destinations.add(da);
            }

            smpp_submit.esm_class = 0;
            if(submit.tp_udhi != 0)
                smpp_submit.esm_class |= 0x40;
            smpp_submit.protocol_id = submit.tp_pid;
            smpp_submit.priority_flag = submit.msg_level;
            smpp_submit.schedule_delivery_time = submit.at_time;
            smpp_submit.validity_period = submit.valid_time;
            smpp_submit.registered_delivery = submit.registered_delivery;
            smpp_submit.replace_if_present_flag = 0;
            smpp_submit.data_coding = submit.msg_fmt;
            smpp_submit.sm_default_msg_id = 0;
            smpp_submit.sm_length = submit.msg_length;
            smpp_submit.short_message = submit.msg_content;
            if(smpp_submit.data_coding == 15 && smpp_submit.short_message != null && smpp_submit.short_message.length > 0)
            {
                smpp_submit.data_coding = 8;
                smpp_submit.short_message = Utility.gb2312_to_ucs2(new String(smpp_submit.short_message, "GB2312"));
                smpp_submit.sm_length = smpp_submit.short_message != null ? smpp_submit.short_message.length : 0;
            }
            smpp_submit.wrap();
            return smpp_submit;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapSubmitMulti : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public SMPPPacket wrapSubmitSM(CMPPSubmit submit)
    {
        try
        {
            SMPPSubmitSM smpp_submit = new SMPPSubmitSM(submit.sequence_id);
            smpp_submit.gateway_name = submit.gateway_name;
            smpp_submit.session_id = submit.session_id;
            smpp_submit.guid = submit.guid;
            smpp_submit.service_type = submit.service_id;
            smpp_submit.source.TON = 0;
            smpp_submit.source.NPI = 1;
            smpp_submit.source.address = submit.src_terminal_id;
            if(submit.src_terminal_id != null && submit.src_terminal_id.length() > 0 && submit.src_terminal_id.startsWith("86"))
                smpp_submit.source.TON = 1;
            smpp_submit.destination.TON = 0;
            smpp_submit.destination.NPI = 1;
            if(submit.dest_terminal_id != null && submit.dest_terminal_id.length > 0)
            {
                smpp_submit.destination.address = submit.dest_terminal_id[0];
                if(submit.dest_terminal_id[0] != null && submit.dest_terminal_id[0].length() > 0 && submit.dest_terminal_id[0].startsWith("86"))
                    smpp_submit.destination.TON = 1;
            }
            smpp_submit.esm_class = 0;
            if(submit.tp_udhi != 0)
                smpp_submit.esm_class |= 0x40;
            smpp_submit.protocol_id = submit.tp_pid;
            smpp_submit.priority_flag = submit.msg_level;
            smpp_submit.schedule_delivery_time = submit.at_time;
            smpp_submit.validity_period = submit.valid_time;
            smpp_submit.registered_delivery = submit.registered_delivery;
            smpp_submit.replace_if_present_flag = 0;
            smpp_submit.data_coding = submit.msg_fmt;
            smpp_submit.sm_default_msg_id = 0;
            smpp_submit.sm_length = submit.msg_length;
            smpp_submit.short_message = submit.msg_content;
            if(smpp_submit.data_coding == 15 && smpp_submit.short_message != null && smpp_submit.short_message.length > 0)
            {
                smpp_submit.data_coding = 8;
                smpp_submit.short_message = Utility.gb2312_to_ucs2(new String(smpp_submit.short_message, "GB2312"));
                smpp_submit.sm_length = smpp_submit.short_message != null ? smpp_submit.short_message.length : 0;
            }
            smpp_submit.wrap();
            return smpp_submit;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapSubmit : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public SMPPPacket wrapSubmit(CMPPSubmit submit)
    {
        if(submit.dest_usr_tl > 1)
            return wrapSubmitMulti(submit);
        if(submit.dest_usr_tl == 1)
            return wrapSubmitSM(submit);
        else
            return null;
    }

    public SMPPPacket wrapDeliverResponse(CMPPDeliverResponse response)
    {
        try
        {
            SMPPDeliverSMResponse smpp_response = new SMPPDeliverSMResponse(response.sequence_id);
            smpp_response.gateway_name = response.gateway_name;
            smpp_response.session_id = response.session_id;
            smpp_response.guid = response.guid;
            smpp_response.command_status = 0;
            smpp_response.message_id = Utility.toHexString(response.msg_id);
            return smpp_response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapDeliverResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPSubmitResponse unwrapSubmitMultiResponse(SMPPPacket packet)
    {
        try
        {
            SMPPSubmitMultiResponse smpp_response = new SMPPSubmitMultiResponse(packet);
            smpp_response.unwrap();
            if(!smpp_response.isValid())
            {
                return null;
            } else
            {
                CMPPSubmitResponse response = new CMPPSubmitResponse(packet.sequence_id);
                response.gateway_name = smpp_response.gateway_name;
                response.session_id = smpp_response.session_id;
                response.guid = smpp_response.guid;
                response.result = (byte)(smpp_response.command_status & 0xff);
                response.msg_id = Utility.toHexLong(smpp_response.message_id);
                return response;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapSubmitMultiResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPSubmitResponse unwrapSubmitSMResponse(SMPPPacket packet)
    {
        try
        {
            SMPPSubmitSMResponse smpp_response = new SMPPSubmitSMResponse(packet);
            smpp_response.unwrap();
            if(!smpp_response.isValid())
            {
                return null;
            } else
            {
                CMPPSubmitResponse response = new CMPPSubmitResponse(packet.sequence_id);
                response.gateway_name = smpp_response.gateway_name;
                response.session_id = smpp_response.session_id;
                response.guid = smpp_response.guid;
                response.result = (byte)(smpp_response.command_status & 0xff);
                response.msg_id = Utility.toHexLong(smpp_response.message_id);
                return response;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapSubmitSMResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPDeliver unwrapDeliver(SMPPPacket packet)
    {
        try
        {
            SMPPDeliverSM smpp_deliver = new SMPPDeliverSM(packet);
            smpp_deliver.unwrap();
            if(!smpp_deliver.isValid())
                return null;

            //以下这段代码实现在收到Deliver消息后立即组织DeliverResponse消息并放入发送队列，
            //并不交给数据库伦循CMPPDeliver表中ih_process字段为insert_cmpp_deliver状态的记录
            /*
            SMPPDeliverSMResponse response = new SMPPDeliverSMResponse(smpp_deliver.sequence_id);
            response.gateway_name = smpp_deliver.gateway_name;
            response.sequence_id = smpp_deliver.sequence_id;
            response.command_status = 0;
            response.guid = smpp_deliver.guid;
            response.session_id = smpp_deliver.session_id;
            response.message_id = Utility.toHexString(smpp_deliver.sequence_id);
            response.wrap();
            for(; !m_gateway.send(response); Engine.nap());
            */

            if(smpp_deliver.data_coding == 8)
                reencode_msg_content(smpp_deliver);
            CMPPDeliver deliver = new CMPPDeliver(packet.sequence_id);
            deliver.gateway_name = smpp_deliver.gateway_name;
            deliver.session_id = smpp_deliver.session_id;
            deliver.guid = smpp_deliver.guid;
            deliver.msg_id = smpp_deliver.sequence_id;
            deliver.service_id = smpp_deliver.service_type;
            deliver.destination_id = smpp_deliver.destination.address;
            deliver.tp_pid = smpp_deliver.protocol_id;
            deliver.tp_udhi = (byte)(smpp_deliver.esm_class & 0x40);
            deliver.msg_fmt = smpp_deliver.data_coding;
            deliver.src_terminal_id = smpp_deliver.source.address;
            if((smpp_deliver.esm_class & 4) != 0)
                deliver.registered_delivery = 1;
            else
                deliver.registered_delivery = 0;
            deliver.msg_length = smpp_deliver.sm_length;
            deliver.msg_content = smpp_deliver.short_message;
            if(deliver.registered_delivery != 0 && smpp_deliver.short_message != null)
                deliver.status_report.unwrapSMSC(Utility.get_msg_content(deliver.msg_content));
            return deliver;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SMPPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapDeliver : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public void toGateway()
    {
        if(smpp_output != null && m_gateway.send(smpp_output))
            smpp_output = null;
    }

    public void toDatabase()
    {
        if(cmpp_input != null && m_database.m_input.push(cmpp_input))
        {
            if(cmpp_input.command_id == 5)
                m_exchanger.m_output.push(cmpp_input);
            cmpp_input = null;
        }
        super.toDatabase();
    }

    public void handleOutput()
    {
        CMPPPacket packet = (CMPPPacket)m_database.m_output.pop(m_gateway.m_strName);
        if(packet == null)
            return;
        m_gateway.statistic(packet);
        if(packet.command_id == 0x80000005)
            smpp_output = wrapDeliverResponse((CMPPDeliverResponse)packet);
        else
        if(packet.command_id == 4)
            smpp_output = wrapSubmit((CMPPSubmit)packet);
        packet.empty();
        packet = null;
    }

    public void handleInput()
    {
        SMPPPacket packet = (SMPPPacket)m_gateway.receive();
        if(packet == null)
            return;
        if(packet.command_id == 5)
            cmpp_input = unwrapDeliver(packet);
        else
        if(packet.command_id == 0x80000004)
            cmpp_input = unwrapSubmitSMResponse(packet);
        else
        if(packet.command_id == 0x80000021)
            cmpp_input = unwrapSubmitMultiResponse(packet);
        m_gateway.statistic(cmpp_input);
        packet.empty();
        packet = null;
    }


}