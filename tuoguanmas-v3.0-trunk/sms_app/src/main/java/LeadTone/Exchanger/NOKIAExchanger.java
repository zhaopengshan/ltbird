package LeadTone.Exchanger;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Engine;
import LeadTone.Gateway.NOKIAGatewayEngine;
import LeadTone.Gateway.ServiceProvider;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.NOKIAPacket.*;
import LeadTone.Port.CMPPXMLExchanger;
import LeadTone.Utility;


/**
 * NOKIA也是对CMPP协议的实现，但是由于和CMPP标准协议差异较大，故单独处理，不做继承，
 * 具体差异参考NOKIA对CMPP协议实现的特殊化
 */
public class NOKIAExchanger extends DataExchanger
{
    NOKIAGatewayEngine m_gateway;
    CMPPPacket cmpp_input;
    NOKIAPacket nokia_output;

    public NOKIAExchanger(NOKIAGatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger exchanger)
    {
        super(database, exchanger);
        m_gateway = null;
        cmpp_input = null;
        nokia_output = null;
        m_gateway = gateway;
    }

    public void reencode_short_message(NOKIADeliver deliver)
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
            Log.log("NOKIAExchanger.reencode_msg_content : unexpected exit !", 0x2000000000000004L);
        }
    }

    public void run()
    {
        try
        {
            Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread startup !", 4L);
            m_nStatus = 1;
            for(; isRunning(); Engine.nap())
            {
                if(m_gateway.isStopped())
                {
                    Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : gateway is stopped !", 0x2000000000000004L);
                    break;
                }
                if(nokia_output == null)
                    handleOutput();
                toGateway();
                if(m_database.isStopped())
                {
                    Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : database is stopped !", 0x2000000000000004L);
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
            Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : unexpected exit !", 0x2000000000000004L);
        }
        m_nStatus = 3;
        Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread stopped !", 4L);
    }

    public NOKIAPacket wrapSubmit(CMPPSubmit submit)
    {
        try
        {
            if(m_gateway.m_sp != null && m_gateway.m_sp.enterprise_code != null)
                submit.msg_src = m_gateway.m_sp.enterprise_code;
            int length = submit.dest_terminal_id != null ? submit.dest_terminal_id.length : 0;
            for(int i = 0; i < length; i++)
                if(submit.dest_terminal_id[i].startsWith("86"))
                    submit.dest_terminal_id[i] = submit.dest_terminal_id[i].substring(2);

            NOKIASubmit nokia_submit = new NOKIASubmit(submit.sequence_id);
            nokia_submit.gateway_name = submit.gateway_name;
            nokia_submit.session_id = submit.session_id;
            nokia_submit.guid = submit.guid;
            nokia_submit.command_status = 0;
            if(submit.registered_delivery == 1)
                nokia_submit.message_mode |= 1;
            else
            if(submit.registered_delivery == 2)
                nokia_submit.message_mode |= 2;
            nokia_submit.priority = submit.msg_level;
            nokia_submit.service_type = submit.service_id;
            nokia_submit.fee_user_type = submit.fee_user_type;
            nokia_submit.fee_user = submit.fee_terminal_id;
            nokia_submit.protocol_id = submit.tp_pid;
            if(submit.tp_udhi != 0)
                nokia_submit.message_mode |= 8;
            nokia_submit.data_coding = submit.msg_fmt;
            nokia_submit.sp_id = submit.msg_src;
            nokia_submit.fee_type = Byte.parseByte(submit.fee_type);
            nokia_submit.info_fee = Integer.parseInt(submit.fee_code);
            nokia_submit.validity_period = submit.valid_time;
            nokia_submit.schedule = submit.at_time;
            nokia_submit.source_address = submit.src_terminal_id;
            nokia_submit.count_of_destination = submit.dest_usr_tl;
            nokia_submit.destination_address = submit.dest_terminal_id;
            nokia_submit.sm_length = submit.msg_length;
            nokia_submit.short_message = submit.msg_content;
            nokia_submit.wrap();
            return nokia_submit;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapSubmit : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public NOKIAPacket wrapQuery(CMPPQuery query)
    {
        try
        {
            NOKIAQuery nokia_query = new NOKIAQuery(query.sequence_id);
            nokia_query.gateway_name = query.gateway_name;
            nokia_query.session_id = query.session_id;
            nokia_query.guid = query.guid;
            nokia_query.query_time = query.query_time;
            nokia_query.query_type = query.query_type;
            nokia_query.query_code = query.query_code;
            nokia_query.wrap();
            return nokia_query;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapQuery : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public NOKIAPacket wrapDeliverResponse(CMPPDeliverResponse response)
    {
        try
        {
            NOKIADeliverResponse nokia_response = new NOKIADeliverResponse(response.sequence_id);
            nokia_response.gateway_name = response.gateway_name;
            nokia_response.session_id = response.session_id;
            nokia_response.guid = response.guid;
            nokia_response.command_status = 0;
            return nokia_response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapDeliverResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPSubmitResponse unwrapSubmitResponse(NOKIAPacket packet)
    {
        try
        {
            NOKIASubmitResponse nokia_response = new NOKIASubmitResponse(packet);
            nokia_response.unwrap();
            if(!nokia_response.isValid())
                return null;
            CMPPSubmitResponse response = new CMPPSubmitResponse(nokia_response.sequence_id);
            response.gateway_name = nokia_response.gateway_name;
            response.session_id = nokia_response.session_id;
            response.guid = nokia_response.guid;
            response.sequence_id = nokia_response.sequence_id;
            if(nokia_response.command_status != 0)
                response.result = (byte)(nokia_response.command_status & 0xff);
            else
                response.result = (byte)nokia_response.failed_users_count;
            response.msg_id = Utility.toDigitLong(nokia_response.message_id);
            return response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapSubmitResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPQueryResponse unwrapQueryResponse(NOKIAPacket packet)
    {
        try
        {
            NOKIAQueryResponse nokia_response = new NOKIAQueryResponse(packet);
            nokia_response.unwrap();
            if(!nokia_response.isValid())
            {
                return null;
            } else
            {
                CMPPQueryResponse response = new CMPPQueryResponse(nokia_response.sequence_id);
                response.gateway_name = nokia_response.gateway_name;
                response.session_id = nokia_response.session_id;
                response.guid = nokia_response.guid;
                response.query_time = nokia_response.query_time;
                response.query_type = nokia_response.query_type;
                response.query_code = nokia_response.query_code;
                response.MT_TLMsg = nokia_response.MT_TLMsg;
                response.MT_TLUsr = nokia_response.MT_TLUsr;
                response.MT_Scs = nokia_response.MT_Scs;
                response.MT_WT = nokia_response.MT_WT;
                response.MT_FL = nokia_response.MT_FL;
                response.MO_Scs = nokia_response.MT_Scs;
                response.MO_WT = nokia_response.MO_WT;
                response.MO_FL = nokia_response.MO_FL;
                return response;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("NOKIAExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapQueryResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPDeliver unwrapDeliver(NOKIAPacket packet)
    {
        try
        {
            NOKIADeliver nokia_deliver = new NOKIADeliver(packet);
            nokia_deliver.unwrap();
            if(!nokia_deliver.isValid())
                return null;
            NOKIADeliverResponse response = new NOKIADeliverResponse(nokia_deliver.sequence_id);
            response.gateway_name = nokia_deliver.gateway_name;
            response.sequence_id = nokia_deliver.sequence_id;
            response.command_status = 0;
            response.session_id = nokia_deliver.session_id;
            response.wrapNOKIAPacket();
            for(; !m_gateway.send(response); Engine.nap());
            if(nokia_deliver.data_coding == 8)
                reencode_short_message(nokia_deliver);
            CMPPDeliver deliver = new CMPPDeliver(nokia_deliver.sequence_id);
            deliver.gateway_name = nokia_deliver.gateway_name;
            deliver.session_id = nokia_deliver.session_id;
            deliver.guid = nokia_deliver.guid;
            deliver.src_terminal_id = nokia_deliver.source_address;
            deliver.destination_id = nokia_deliver.destination_address;
            deliver.service_id = nokia_deliver.service_type;
            deliver.tp_pid = nokia_deliver.protocol_id;
            if((nokia_deliver.message_mode & 8) == 8)
                deliver.tp_udhi = 1;
            deliver.msg_fmt = nokia_deliver.data_coding;
            deliver.msg_length = nokia_deliver.sm_length;
            deliver.msg_content = nokia_deliver.short_message;
            if((nokia_deliver.message_mode & 1) == 1)
            {
                deliver.registered_delivery = 1;
                deliver.status_report.msg_id = Utility.toDigitLong(nokia_deliver.status_report.message_id);
                String result = nokia_deliver.status_report.status;
                if(result.equalsIgnoreCase("000"))
                    result = "DELIVRD";
                else
                if(result.equalsIgnoreCase("001"))
                    result = "TEMPERR";
                else
                if(result.equalsIgnoreCase("002"))
                    result = "NOROUTE";
                else
                if(result.equalsIgnoreCase("003"))
                    result = "DELETED";
                else
                if(result.equalsIgnoreCase("004"))
                    result = "FAILED ";
                else
                if(result.equalsIgnoreCase("005"))
                    result = "EXPIRED";
                else
                if(result.equalsIgnoreCase("006"))
                    result = "SFAILED";
                else
                if(result.equalsIgnoreCase("007"))
                    result = "CANCELD";
                else
                if(result.equalsIgnoreCase("008"))
                    result = "SYS ERR";
                else
                    result = "UNKOWN";
                deliver.status_report.status = result;
                deliver.status_report.submit_time = nokia_deliver.status_report.submit_time.substring(0, 10);
                deliver.status_report.done_time = nokia_deliver.status_report.done_time.substring(0, 10);
            }
            return deliver;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("NOKIAGatewayEngine(" + m_nID + "," + m_gateway.m_strName + ").unwrapDeliver : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public void toGateway()
    {
        if(nokia_output != null && m_gateway.send(nokia_output))
            nokia_output = null;
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

    public void handleSubmit()
    {
        CMPPSubmit submit = (CMPPSubmit)m_database.m_output.pop(4, m_gateway.m_strName);
        if(submit == null)
        {
            return;
        } else
        {
            nokia_output = wrapSubmit(submit);
            submit.empty();
            return;
        }
    }

    public void handleOutput()
    {
        CMPPPacket packet = (CMPPPacket)m_database.m_output.pop(m_gateway.m_strName);
        if(packet == null)
            return;
        m_gateway.statistic(packet);
        if(packet.command_id == 0x80000005)
            nokia_output = wrapDeliverResponse((CMPPDeliverResponse)packet);
        else
        if(packet.command_id == 0x80000006)
            nokia_output = wrapQuery((CMPPQuery)packet);
        else
        if(packet.command_id == 4)
            nokia_output = wrapSubmit((CMPPSubmit)packet);
        packet.empty();
        packet = null;
    }

    public void handleInput()
    {
        NOKIAPacket packet = (NOKIAPacket)m_gateway.receive();
        if(packet == null)
            return;
        if(packet.command_id == 5)
            cmpp_input = unwrapDeliver(packet);
        else
        if(packet.command_id == 0x80000006)
            cmpp_input = unwrapQueryResponse(packet);
        else
        if(packet.command_id == 0x80000004)
            cmpp_input = unwrapSubmitResponse(packet);
        m_gateway.statistic(cmpp_input);
        packet.empty();
        packet = null;
    }


}