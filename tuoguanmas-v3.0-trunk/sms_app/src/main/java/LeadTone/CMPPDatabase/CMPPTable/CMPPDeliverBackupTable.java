package LeadTone.CMPPDatabase.CMPPTable;

import LeadTone.Utility;
import LeadTone.CMPPDatabase.CMPPDeliverDatabase;
import LeadTone.Database.Table;
import LeadTone.Packet.CMPPPacket.CMPPDeliver;
import LeadTone.Packet.CMPPPacket.CMPPDeliverResponse;

import java.sql.SQLException;

/**
 * �̳���Tableӵ������JDBC�Ĺ��ܣ���ɶԱ�CMPPDeliverBackup�Ĳ���
 */
public class CMPPDeliverBackupTable extends Table {
	
	private static final String deliverBackupTableName = "smw_cmpp_deliver";

    public CMPPDeliverBackupTable() {
        //super("cmpp_deliver_backup");
        super("smw_cmpp_deliver_result");
    }

    
    /**
     * ��Deliver��Ϣ�л�ȡ�������ƣ����ȡ����ֵ��������CMPPDeliverBackup��Insert��Deliver��¼�Ĳ���
     * @param deliver
     * @return ���ز�������֯��SQL��䲿��
     */
    public String getParameters(CMPPDeliver deliver)
    {
        String strParameters = "";
        strParameters = strParameters + "msg_id,";
        if(deliver.destination_id != null && deliver.destination_id.length() > 0)
            strParameters = strParameters + "destination_id,";
        if(deliver.service_id != null && deliver.service_id.length() > 0)
            strParameters = strParameters + "service_id,";
        strParameters = strParameters + "tp_pid,tp_udhi,msg_fmt,";
        if(deliver.src_terminal_id != null && deliver.src_terminal_id.length() > 0)
            strParameters = strParameters + "src_terminal_id,";
        strParameters = strParameters + "registered_delivery,msg_length,";
        if(deliver.msg_content != null && deliver.msg_content.length > 0)
            strParameters = strParameters + "msg_content,";
        strParameters = strParameters + "msg_mr,";
        if(deliver.status_report.status != null && deliver.status_report.status.length() > 0)
            strParameters = strParameters + "status,";
        if(deliver.status_report.submit_time != null && deliver.status_report.submit_time.length() > 0)
            strParameters = strParameters + "submit_time,";
        if(deliver.status_report.done_time != null && deliver.status_report.done_time.length() > 0)
            strParameters = strParameters + "done_time,";
        if(deliver.status_report.dest_terminal_id != null && deliver.status_report.dest_terminal_id.length() > 0)
            strParameters = strParameters + "dest_terminal_id,";
        strParameters = strParameters + "smsc_sequence,ih_gateway,ih_session,ih_sequence";
        
        strParameters = strParameters + ",ih_process";
        
        return new String(strParameters);
    }
    
    
    /**
     * ��Deliver��Ϣ�л�ȡ����ֵ
     * @param deliver
     * @return ���ز�����ֵ��SQL��䲿��
     */
    public String getValues(CMPPDeliver deliver)
    {
        String strValues = "";
        strValues = strValues + "'" + Utility.toHexString(deliver.msg_id) + "',";
        if(deliver.destination_id != null && deliver.destination_id.length() > 0)
            strValues = strValues + "'" + deliver.destination_id + "',";
        if(deliver.service_id != null && deliver.service_id.length() > 0)
            strValues = strValues + "'" + deliver.service_id + "',";
        strValues = strValues + deliver.tp_pid + "," + deliver.tp_udhi + "," + deliver.msg_fmt + ",";
        if(deliver.src_terminal_id != null && deliver.src_terminal_id.length() > 0)
            strValues = strValues + "'" + deliver.src_terminal_id + "',";
        strValues = strValues + deliver.registered_delivery + "," + deliver.msg_length + ",";
        if(deliver.msg_content != null && deliver.msg_content.length > 0)
            if(m_pool.m_dc.isMySQL() || m_pool.m_dc.isMSSQLServer())
            {
                String strHex = Utility.toHexString(deliver.msg_content);
                strValues = strValues + "'" + strHex + "',";
            } else
            if(m_pool.m_dc.isOracle())
                strValues = strValues + "HEXTORAW('" + Utility.toHexString(deliver.msg_content) + "'),";
            else
            if(m_pool.m_dc.isODBC())
            {
                String strHex = Utility.toHexString(deliver.msg_content);
                strValues = strValues + "0x" + strHex + ",";
            } else
            if(m_pool.m_dc.isDB2())
            {
                String strHex = Utility.toHexString(deliver.msg_content);
                strValues = strValues + "X'" + strHex + "',";
            } else
            {
                strValues = strValues + "'" + deliver.msg_content + "',";
            }
        strValues = strValues + "'" + Utility.toHexString(deliver.status_report.msg_id) + "',";
        if(deliver.status_report.status != null && deliver.status_report.status.length() > 0)
            strValues = strValues + "'" + deliver.status_report.status + "',";
        if(deliver.status_report.submit_time != null && deliver.status_report.submit_time.length() > 0)
            strValues = strValues + "'" + deliver.status_report.submit_time + "',";
        if(deliver.status_report.done_time != null && deliver.status_report.done_time.length() > 0)
            strValues = strValues + "'" + deliver.status_report.done_time + "',";
        if(deliver.status_report.dest_terminal_id != null && deliver.status_report.dest_terminal_id.length() > 0)
            strValues = strValues + "'" + deliver.status_report.dest_terminal_id + "',";
        strValues = strValues + deliver.status_report.smsc_sequence + "," + "'" + deliver.gateway_name + "'," + deliver.session_id + "," + deliver.sequence_id;
        
        if (CMPPDeliverDatabase.m_output_switch){
            strValues = strValues + ",'insert_cmpp_deliver'";
        }
        else
        {
        	strValues = strValues + ",'cmpp_deliver_responsed'";	
        }

          return new String(strValues);
    } 
    
    
    
    /**
     * ��CMPPDeliverBackup��������յ���Deliver��Ϣ
     * @param deliver
     * @throws SQLException
     */
    public void insert(CMPPDeliver deliver)
        throws SQLException
    {
        String strParameters = getParameters(deliver);
        String strValues = getValues(deliver);
        insert(strParameters, strValues);
    }
    
    
    /**
     * ����CMPPDeliver�е����ݿ�������CMPPDeliverBackup��
     * @param id Ҫ��������������
     * @return ���ظ��¼�¼������
     * @throws SQLException �׳�SQLException
     */
    public int insert(long id)
        throws SQLException
    {
        super.m_rsCurrent = null;
        setSQL(String.valueOf(String.valueOf((new StringBuffer("insert into ")).append(super.m_strName).append(" (select * from "+deliverBackupTableName+" where id = ").append(id).append(")"))));
        return executeUpdate();
    }

    /**
     * ����DeliverResponse֮�󱸷�CMPPDeliver������
     * @param response DeliverResponse��Ϣ
     * @return  ���ظ��¼�¼������
     * @throws SQLException  �׳�SQLException
     */
    public int backup(CMPPDeliverResponse response)
        throws SQLException
    {
        return insert(response.guid);
    }
    
    
    /**
     * �յ�deliver��Ϣ�󣬱���CMPPDeliver������
     * @param deliver deliver��Ϣ
     * @return  ���ظ��¼�¼������
     * @throws SQLException  �׳�SQLException
     */
    public int backup(CMPPDeliver deliver)
        throws SQLException
    {
        return insert(deliver.guid);
    }
}
