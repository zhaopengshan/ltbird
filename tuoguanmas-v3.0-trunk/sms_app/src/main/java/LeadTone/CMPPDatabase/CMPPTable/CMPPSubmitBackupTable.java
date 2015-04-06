package LeadTone.CMPPDatabase.CMPPTable;

import java.sql.SQLException;

import LeadTone.Utility;
import LeadTone.Database.Table;
import LeadTone.Packet.CMPPPacket.CMPPDeliver;
import LeadTone.Packet.CMPPPacket.CMPPSubmit;
import LeadTone.Packet.CMPPPacket.CMPPSubmitResponse;

/**
 * �̳���Tableӵ������JDBC�Ĺ��ܣ���ɶԱ�CMPPSubmitBackup�Ĳ���
 * ���巽������ο�CMPPDeliverBackupTable��
 */
public class CMPPSubmitBackupTable extends Table {
	private static final String submitBackupTableName = "smw_cmpp_submit";

    public CMPPSubmitBackupTable() {
    	//super("cmpp_submit_backup");
          super("smw_cmpp_submit_result");
    }


    public int insert_by_msg_id(long msg_id)
        throws SQLException
    {
        super.m_rsCurrent = null;
        setSQL(String.valueOf(String.valueOf((new StringBuffer("insert into ")).append(super.m_strName).append(" (select * from "+submitBackupTableName+" where msg_id = '").append(Utility.toHexString(msg_id)).append("')"))));
        return executeUpdate();
    }

    //���յ�����֮�󱸷�Submit����Ϣ
    public int backup(CMPPDeliver deliver)
        throws SQLException
    {
        return insert_by_msg_id(deliver.status_report.msg_id);
    }
    
    //���յ�Response֮�󱸷�Submit����Ϣ
    public int backup(CMPPSubmitResponse response)
        throws SQLException
    {
    return insert_by_id(response.guid);
    }
    
    

    public int insert_by_id(long id)
        throws SQLException
    {

        super.m_rsCurrent = null;
        setSQL(String.valueOf(String.valueOf((new StringBuffer("insert into ")).append(super.m_strName).append(" (select * from  "+submitBackupTableName+"  where id = ").append(id).append(")"))));
        return executeUpdate();
    }

    public int backup(CMPPSubmit submit)
        throws SQLException
    {
        return insert_by_id(submit.guid);
    }

}
