package LeadTone.Exchanger;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Engine;
import LeadTone.Gateway.SGIPGatewayEngine;
import LeadTone.Gateway.ServiceProvider;
import LeadTone.LeadToneDate;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.SGIPPacket.*;
import LeadTone.Port.CMPPXMLExchanger;
import LeadTone.Utility;


/**
 * 用于中国移动CMPP协议包和中国联通SGIP协议包间的转换功能
 */
public class SGIPExchanger extends DataExchanger
{
    SGIPGatewayEngine m_gateway;
    CMPPPacket cmpp_input;
    SGIPPacket sgip_output;

    public SGIPExchanger(SGIPGatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger exchanger)
    {
        super(database, exchanger);
        m_gateway = null;
        cmpp_input = null;
        sgip_output = null;
        m_gateway = gateway;
    }

    public void reencode_message_content(SGIPDeliver deliver)
    {
        String content = Utility.ucs2_to_gb2312(deliver.message_content);
        if(content == null)
            return;
        try
        {
            deliver.message_content = content.getBytes("GB2312");
            deliver.message_coding = 15;
            deliver.message_length = deliver.message_content.length;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000004L);
            Log.log(e);
            Log.log("SGIPExchanger.reencode_message_content : unexpected exit !", 0x2000000000000004L);
        }
    }

    public void run()
    {
        try
        {
            Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread startup !", 4L);
            m_nStatus = 1;
            for(; isRunning(); Engine.nap())
            {
                if(m_gateway.isStopped())
                {
                    Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : gateway is stopped !", 0x2000000000000004L);
                    break;
                }
                if(sgip_output == null)
                    handleOutput();
                toGateway();
                if(m_database.isStopped())
                {
                    Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : database is stopped !", 0x2000000000000004L);
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
            Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : unexpected exit !", 0x2000000000000004L);
        }
        m_nStatus = 3;
        Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread stopped !", 4L);
    }

    public SGIPPacket wrapSubmit(CMPPSubmit submit)
    {
        try
        {
            if(submit.fee_terminal_id != null && !submit.fee_terminal_id.startsWith("86"))
                submit.fee_terminal_id = "86" + submit.fee_terminal_id;
            int length = submit.dest_terminal_id != null ? submit.dest_terminal_id.length : 0;
            for(int i = 0; i < length; i++)
                if(!submit.dest_terminal_id[i].startsWith("86"))
                    submit.dest_terminal_id[i] = "86" + submit.dest_terminal_id[i];

            SGIPSubmit sgip_submit = new SGIPSubmit(submit.sequence_id);
            sgip_submit.gateway_name = submit.gateway_name;
            sgip_submit.session_id = submit.session_id;
            sgip_submit.guid = submit.guid;
            if(m_gateway.m_sp != null && m_gateway.m_sp.enterprise_code != null)
                if(m_gateway.m_sp.enterprise_code.length() <= 5)
                    sgip_submit.node_id = 0x493e0 + Integer.parseInt(m_gateway.m_sp.enterprise_code);
                else
                    sgip_submit.node_id = (int)(0xb2d05e00L + (long)Integer.parseInt(m_gateway.m_sp.enterprise_code));
            sgip_submit.time_stamp = Utility.get_time_stamp(Utility.toTimeString(new LeadToneDate()));
            if(m_gateway.m_sp != null)
            {
                sgip_submit.sp_number = m_gateway.m_sp.service_code;
                if(submit.src_terminal_id != null && submit.src_terminal_id.length() > 0)
                    sgip_submit.sp_number = submit.src_terminal_id;
            }
            if(submit.fee_user_type == 0)
                sgip_submit.charge_number = null;
            else
            if(submit.fee_user_type == 1)
                sgip_submit.charge_number = submit.src_terminal_id;
            else
            if(submit.fee_user_type == 2)
                sgip_submit.charge_number = "000000000000000000000";
            else
            if(submit.fee_user_type == 3)
                sgip_submit.charge_number = submit.fee_terminal_id;
            sgip_submit.user_count = submit.dest_usr_tl;
            sgip_submit.user_number = submit.dest_terminal_id;
            if(m_gateway.m_sp != null && m_gateway.m_sp.enterprise_code != null)
                if(m_gateway.m_sp.enterprise_code.length() <= 5)
                {
                    sgip_submit.corporation_id = m_gateway.m_sp.enterprise_code;
                } else
                {
                    int nLength = m_gateway.m_sp.enterprise_code.length();
                    sgip_submit.corporation_id = m_gateway.m_sp.enterprise_code.substring(nLength - 5);
                }
            sgip_submit.service_type = submit.service_id;
            if(submit.fee_type != null)
                if(submit.fee_type.equals("00"))
                    sgip_submit.fee_type = 0;
                else
                if(submit.fee_type.equals("01"))
                    sgip_submit.fee_type = 1;
                else
                if(submit.fee_type.equals("02"))
                    sgip_submit.fee_type = 2;
                else
                if(submit.fee_type.equals("03"))
                    sgip_submit.fee_type = 3;
                else
                if(submit.fee_type.equals("04"))
                    sgip_submit.fee_type = 3;
                else
                if(submit.fee_type.equals("05"))
                    sgip_submit.fee_type = 4;
            sgip_submit.fee_value = submit.fee_code;
            sgip_submit.given_value = submit.given_value;
            sgip_submit.agent_flag = submit.agent_flag;
            sgip_submit.mo_relate_to_mt_flag = submit.mo_relate_to_mt_flag;
            sgip_submit.priority = submit.msg_level;
            sgip_submit.expire_time = submit.valid_time;
            sgip_submit.schedule_time = submit.at_time;
            if(submit.registered_delivery == 0)
                sgip_submit.report_flag = 2;
            else
            if(submit.registered_delivery == 1)
                sgip_submit.report_flag = 1;
            else
            if(submit.registered_delivery == 2)
            {
                sgip_submit.report_flag = 3;
                sgip_submit.mo_relate_to_mt_flag = 3;
            }
            sgip_submit.tp_pid = submit.tp_pid;
            sgip_submit.tp_udhi = submit.tp_udhi;
            sgip_submit.message_coding = submit.msg_fmt;
            sgip_submit.message_type = 0;
            sgip_submit.message_length = submit.msg_length;
            sgip_submit.message_content = submit.msg_content;
            sgip_submit.wrap();
            return sgip_submit;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapSubmit : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPSubmitResponse unwrapSubmitResponse(SGIPPacket packet)
    {
        try
        {
            SGIPSubmitResponse sgip_response = new SGIPSubmitResponse(packet);
            sgip_response.unwrap();
            if(!sgip_response.isValid())
            {
                return null;
            } else
            {
                CMPPSubmitResponse response = new CMPPSubmitResponse(packet.sequence_id);
                response.gateway_name = sgip_response.gateway_name;
                response.session_id = sgip_response.session_id;
                response.guid = sgip_response.guid;
                response.result = sgip_response.result;
                long msg_id = (long)sgip_response.time_stamp & -1L;
                msg_id <<= 32;
                msg_id |= (long)sgip_response.sequence_id & -1L;
                response.msg_id = msg_id;
                return response;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapSubmitResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPDeliver unwrapUserReport(SGIPPacket packet)
    {
        try
        {
            SGIPUserReport sgip_user_report = new SGIPUserReport(packet);
            sgip_user_report.unwrap();
            if(!sgip_user_report.isValid())
                return null;
            CMPPDeliver deliver = new CMPPDeliver(packet.sequence_id);
            deliver.gateway_name = sgip_user_report.gateway_name;
            deliver.session_id = sgip_user_report.session_id;
            deliver.guid = sgip_user_report.guid;
            long msg_id = (long)sgip_user_report.node_id & 0xfffffffffL;
            msg_id <<= 32;
            msg_id |= (long)sgip_user_report.time_stamp & 0xffffffffL;
            deliver.msg_id = msg_id;
            deliver.registered_delivery = 1;
            deliver.service_id = "USERRPT";
            deliver.destination_id = sgip_user_report.sp_number;
            deliver.src_terminal_id = sgip_user_report.user_number;
            deliver.status_report.done_time = Utility.get_time_stamp(sgip_user_report.time_stamp);
            if(sgip_user_report.user_condition == 0)
                deliver.status_report.status = "ABOLISH";
            else
            if(sgip_user_report.user_condition == 1)
                deliver.status_report.status = "ARREAR";
            else
            if(sgip_user_report.user_condition == 2)
                deliver.status_report.status = "NORMAL";
            else
                deliver.status_report.status = "UNKNOWN";
            return deliver;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapUserReport : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPDeliver unwrapReport(SGIPPacket packet)
    {
        try
        {
            SGIPReport sgip_report = new SGIPReport(packet);
            sgip_report.unwrap();
            SGIPReportResponse response = new SGIPReportResponse(sgip_report.sequence_id);
            response.gateway_name = sgip_report.gateway_name;
            response.sequence_id = sgip_report.sequence_id;
            response.node_id = sgip_report.node_id;
            response.time_stamp = sgip_report.time_stamp;
            response.result = 0;
            response.session_id = sgip_report.session_id;
            response.wrap();
            for(; !m_gateway.send(response); Engine.nap());
            if(!sgip_report.isValid())
                return null;
            CMPPDeliver deliver = new CMPPDeliver(packet.sequence_id);
            deliver.gateway_name = sgip_report.gateway_name;
            deliver.session_id = sgip_report.session_id;
            deliver.guid = sgip_report.guid;
            long msg_id = (long)sgip_report.node_id & 0xfffffffffL;
            msg_id <<= 32;
            msg_id |= (long)sgip_report.time_stamp & 0xffffffffL;
            deliver.msg_id = msg_id;
            deliver.registered_delivery = 1;
            deliver.service_id = "REPORT";
            msg_id = (long)sgip_report.packet_time_stamp & 0xfffffffffL;
            msg_id <<= 32;
            msg_id |= (long)sgip_report.packet_sequence_id & 0xffffffffL;
            deliver.status_report.msg_id = msg_id;
            deliver.status_report.submit_time = Utility.get_time_stamp(sgip_report.time_stamp);
            if(sgip_report.state == 0)
                deliver.status_report.status = "DELIVRD";
            else
            if(sgip_report.state == 1)
                deliver.status_report.status = "WAITING";
            else
            if(sgip_report.state == 2)
                deliver.status_report.status = "FAILED";
            else
                deliver.status_report.status = "UNKNOWN";
            deliver.destination_id = sgip_report.user_number;
            deliver.status_report.smsc_sequence = sgip_report.error_code;
            return deliver;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapReport : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPDeliver unwrapDeliver(SGIPPacket packet)
    {
        try
        {
            SGIPDeliver sgip_deliver = new SGIPDeliver(packet);
            sgip_deliver.unwrap();
            SGIPDeliverResponse response = new SGIPDeliverResponse(sgip_deliver.sequence_id);
            response.gateway_name = sgip_deliver.gateway_name;
            response.sequence_id = sgip_deliver.sequence_id;
            response.node_id = sgip_deliver.node_id;
            response.time_stamp = sgip_deliver.time_stamp;
            response.result = (byte)sgip_deliver.checkValid();
            response.session_id = sgip_deliver.session_id;
            response.wrap();
            for(; !m_gateway.send(response); Engine.nap());
            if(sgip_deliver.message_coding == 8)
                reencode_message_content(sgip_deliver);
            CMPPDeliver deliver = new CMPPDeliver(packet.sequence_id);
            deliver.gateway_name = sgip_deliver.gateway_name;
            deliver.session_id = sgip_deliver.session_id;
            deliver.guid = sgip_deliver.guid;
            long msg_id = (long)sgip_deliver.node_id & 0xfffffffffL;
            msg_id <<= 32;
            msg_id |= (long)sgip_deliver.time_stamp & 0xffffffffL;
            deliver.msg_id = msg_id;
            deliver.registered_delivery = 0;
            deliver.destination_id = sgip_deliver.sp_number;
            deliver.src_terminal_id = sgip_deliver.user_number;
            deliver.tp_pid = sgip_deliver.tp_pid;
            deliver.tp_udhi = sgip_deliver.tp_udhi;
            deliver.msg_fmt = sgip_deliver.message_coding;
            deliver.msg_length = sgip_deliver.message_length;
            deliver.msg_content = sgip_deliver.message_content;
            return deliver;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapDeliver : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public SGIPPacket wrapDeliverResponse(CMPPDeliverResponse response)
    {
        try
        {
            SGIPResponse sgip_response = null;
            if(response.result == 1)
                sgip_response = new SGIPReportResponse(response.sequence_id);
            else
            if(response.result == 2)
                sgip_response = new SGIPUserReportResponse(response.sequence_id);
            else
                sgip_response = new SGIPDeliverResponse(response.sequence_id);
            sgip_response.gateway_name = response.gateway_name;
            sgip_response.session_id = response.session_id;
            sgip_response.guid = response.guid;
            sgip_response.node_id = (int)(response.msg_id >> 32 & -1L);
            sgip_response.time_stamp = (int)(response.msg_id & -1L);
            sgip_response.result = 0;
            sgip_response.wrap();
            return sgip_response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("SGIPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapDeliverResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public void toGateway()
    {
        if(sgip_output != null && m_gateway.send(sgip_output))
            sgip_output = null;
    }

    public void toDatabase()
    {
        if(cmpp_input != null && m_database.m_input.push(cmpp_input))
        {
            if(cmpp_input.command_id == 5)
            {
                CMPPDeliver deliver = (CMPPDeliver)cmpp_input;
                if(deliver.registered_delivery == 0)
                    m_exchanger.m_output.push(cmpp_input);
            }
            cmpp_input = null;
        }
        super.toDatabase();
    }

    public void handleInput()
    {
        SGIPPacket packet = (SGIPPacket)m_gateway.receive();
        if(packet == null)
            return;
        if(packet.command_id == 0x80000003)
            cmpp_input = unwrapSubmitResponse(packet);
        else
        if(packet.command_id == 4)
            cmpp_input = unwrapDeliver(packet);
        else
        if(packet.command_id == 5)
            cmpp_input = unwrapReport(packet);
        else
        if(packet.command_id == 17)
            cmpp_input = unwrapUserReport(packet);
        m_gateway.statistic(cmpp_input);
        packet.empty();
        packet = null;
    }

    public void handleOutput()
    {
        CMPPPacket packet = (CMPPPacket)m_database.m_output.pop(m_gateway.m_strName);
        if(packet == null)
            return;
        m_gateway.statistic(packet);
        if(packet.command_id == 4)
            sgip_output = wrapSubmit((CMPPSubmit)packet);
        else
        if(packet.command_id == 0x80000005)
            sgip_output = wrapDeliverResponse((CMPPDeliverResponse)packet);
        packet.empty();
        packet = null;
    }


}