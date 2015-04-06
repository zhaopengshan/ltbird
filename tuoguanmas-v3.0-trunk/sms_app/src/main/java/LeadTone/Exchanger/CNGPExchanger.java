package LeadTone.Exchanger;

import LeadTone.CMPPDatabase.CMPPDatabase;
import LeadTone.Engine;
import LeadTone.Gateway.CNGPGatewayEngine;
import LeadTone.Gateway.ServiceProvider;
import LeadTone.Log;
import LeadTone.Packet.CMPPPacket.*;
import LeadTone.Packet.CNGPPacket.*;
import LeadTone.Port.CMPPXMLExchanger;
import LeadTone.Utility;


/**
 * 用于中国移动CMPP协议包和中国网通CNGP协议包间的转换功能
 */
public class CNGPExchanger extends DataExchanger
{
    CNGPGatewayEngine m_gateway;
    CMPPPacket cmpp_input;
    CNGPPacket cngp_output;

    public CNGPExchanger(CNGPGatewayEngine gateway, CMPPDatabase database, CMPPXMLExchanger exchanger)
    {
        super(database, exchanger);
        m_gateway = null;
        cmpp_input = null;
        cngp_output = null;
        m_gateway = gateway;
    }

    public void reencode_short_message(CNGPDeliver deliver)
    {
        String content = Utility.ucs2_to_gb2312(deliver.msgContent);
        if(content == null)
            return;
        try
        {
            deliver.msgContent = content.getBytes("GB2312");
            deliver.msgFormat = 15;
            deliver.msgLength = (byte)deliver.msgContent.length;
        }
        catch(Exception e)
        {
            Log.log(e.getMessage(), 0x2000000000000004L);
            Log.log(e);
            Log.log("CNGPExchanger.reencode_msg_content : unexpected exit !", 0x2000000000000004L);
        }
    }

    public void run()
    {
        try
        {
            Log.log("CNGPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread startup !", 4L);
            m_nStatus = 1;
            for(; isRunning(); Engine.nap())
            {
                if(m_gateway.isStopped())
                {
                    Log.log("CNGPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : gateway is stopped !", 0x2000000000000004L);
                    break;
                }
                if(cngp_output == null)
                    handleOutput();
                toGateway();
                if(m_database.isStopped())
                {
                    Log.log("CNGPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : database is stopped !", 0x2000000000000004L);
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
            Log.log("CNGPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : unexpected exit !", 0x2000000000000004L);
        }
        m_nStatus = 3;
        Log.log("CNGPExchanger(" + m_nID + "," + m_gateway.m_strName + ").run : thread stopped !", 4L);
    }

    public CNGPPacket wrapSubmit(CMPPSubmit submit)
    {
        try
        {
            if(m_gateway.m_sp != null && m_gateway.m_sp.enterprise_code != null)
                submit.msg_src = m_gateway.m_sp.enterprise_code;
            int length = submit.dest_terminal_id != null ? submit.dest_terminal_id.length : 0;
            for(int i = 0; i < length; i++)
                if(submit.dest_terminal_id[i].startsWith("86"))
                    submit.dest_terminal_id[i] = submit.dest_terminal_id[i].substring(2);

            CNGPSubmit cngp_submit = new CNGPSubmit(submit.sequence_id);
            cngp_submit.gateway_name = submit.gateway_name;
            cngp_submit.session_id = submit.session_id;
            cngp_submit.guid = submit.guid;
            cngp_submit.command_status = 0;
            cngp_submit.priority = submit.msg_level;
            cngp_submit.serviceID = submit.service_id;
            cngp_submit.feeUserType = submit.fee_user_type;
            cngp_submit.chargeTermID = submit.fee_terminal_id;
            cngp_submit.msgFormat = submit.msg_fmt;
            cngp_submit.sp_id = submit.msg_src;
            cngp_submit.feeType = submit.fee_type;
            if(submit.registered_delivery == 1)
                cngp_submit.needReport |= 1;
            else
            if(submit.registered_delivery == 2)
            {
                cngp_submit.needReport |= 1;
                cngp_submit.feeType = "05";
            } else
            {
                cngp_submit.needReport = 0;
            }
            cngp_submit.feeCode = submit.fee_code;
            cngp_submit.validTime = submit.valid_time;
            cngp_submit.atTime = submit.at_time;
            String zone_id = m_gateway.m_sp.service_code.substring(0, 4);
            cngp_submit.srcTermID = submit.src_terminal_id;
            cngp_submit.destTermIDCount = submit.dest_usr_tl;
            cngp_submit.destTermID = submit.dest_terminal_id;
            cngp_submit.msgLength = submit.msg_length;
            cngp_submit.msgContent = submit.msg_content;
            cngp_submit.wrap();
            return cngp_submit;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CNGPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapSubmit : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CNGPPacket wrapDeliverResponse(CMPPDeliverResponse response)
    {
        try
        {
            CNGPDeliverResponse cngp_response = new CNGPDeliverResponse(response.sequence_id);
            cngp_response.gateway_name = response.gateway_name;
            cngp_response.session_id = response.session_id;
            cngp_response.guid = response.guid;
            cngp_response.command_status = 0;
            return cngp_response;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CNGPExchanger(" + m_nID + "," + m_gateway.m_strName + ").wrapDeliverResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPSubmitResponse unwrapSubmitResponse(CNGPPacket packet)
    {
        try
        {
            CNGPSubmitResponse cngp_response = new CNGPSubmitResponse(packet);
            cngp_response.unwrap();
            if(!cngp_response.isValid())
            {
                return null;
            } else
            {
                CMPPSubmitResponse response = new CMPPSubmitResponse(cngp_response.sequence_id);
                response.gateway_name = cngp_response.gateway_name;
                response.session_id = cngp_response.session_id;
                response.guid = cngp_response.guid;
                response.sequence_id = cngp_response.sequence_id;
                response.result = (byte)(cngp_response.command_status & 0xff);
                response.msg_id = Utility.toDigitLong(cngp_response.msgID);
                return response;
            }
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CNGPExchanger(" + m_nID + "," + m_gateway.m_strName + ").unwrapSubmitResponse : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public CMPPDeliver unwrapDeliver(CNGPPacket packet)
    {
        try
        {
            CNGPDeliver cngp_deliver = new CNGPDeliver(packet);
            cngp_deliver.unwrap();
            if(!cngp_deliver.isValid())
                return null;
            CNGPDeliverResponse response = new CNGPDeliverResponse(cngp_deliver.sequence_id);
            response.gateway_name = cngp_deliver.gateway_name;
            response.sequence_id = cngp_deliver.sequence_id;
            response.command_status = 0;
            response.session_id = cngp_deliver.session_id;
            response.wrapCNGPPacket();
            for(; !m_gateway.send(response); Engine.nap());
            if(cngp_deliver.msgFormat == 8)
                reencode_short_message(cngp_deliver);
            CMPPDeliver deliver = new CMPPDeliver(cngp_deliver.sequence_id);
            deliver.gateway_name = cngp_deliver.gateway_name;
            deliver.session_id = cngp_deliver.session_id;
            deliver.guid = cngp_deliver.guid;
            deliver.src_terminal_id = cngp_deliver.srcTermID;
            deliver.destination_id = cngp_deliver.destTermID.substring(4);
            deliver.service_id = "";
            deliver.tp_pid = 0;
            deliver.tp_udhi = 0;
            deliver.msg_fmt = cngp_deliver.msgFormat;
            deliver.msg_length = cngp_deliver.msgLength;
            deliver.msg_content = cngp_deliver.msgContent;
            if((cngp_deliver.isReport & 1) == 1)
            {
                deliver.registered_delivery = 1;
                deliver.status_report.msg_id = Utility.toDigitLong(cngp_deliver.status_report.message_id);
                deliver.status_report.status = cngp_deliver.status_report.status;
                deliver.status_report.submit_time = cngp_deliver.status_report.submit_time.substring(0, 10);
                deliver.status_report.done_time = cngp_deliver.status_report.done_time.substring(0, 10);
            }
            return deliver;
        }
        catch(Exception e)
        {
            Log.log(e);
        }
        Log.log("CNGPGatewayEngine(" + m_nID + "," + m_gateway.m_strName + ").unwrapDeliver : unexpected exit !", 0x2000000000000004L);
        return null;
    }

    public void toGateway()
    {
        if(cngp_output != null && m_gateway.send(cngp_output))
            cngp_output = null;
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
            cngp_output = wrapSubmit(submit);
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
            cngp_output = wrapDeliverResponse((CMPPDeliverResponse)packet);
        else
        if(packet.command_id == 4)
            cngp_output = wrapSubmit((CMPPSubmit)packet);
        packet.empty();
        packet = null;
    }

    public void handleInput()
    {
        CNGPPacket packet = (CNGPPacket)m_gateway.receive();
        if(packet == null)
            return;
        if(packet.command_id == 3)
            cmpp_input = unwrapDeliver(packet);
        else
        if(packet.command_id == 0x80000002)
            cmpp_input = unwrapSubmitResponse(packet);
        m_gateway.statistic(cmpp_input);
        packet.empty();
        packet = null;
    }


}