package LeadTone.CMPPDatabase;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.CMPPDatabase.CMPPTable.CMPPSubmitBackupTable;
import LeadTone.CMPPDatabase.CMPPTable.CMPPSubmitTable;
import LeadTone.Center.Center;
import LeadTone.Database.DatabasePool;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.LeadToneLogic.BackupTableConfig;
import LeadTone.Packet.CMPPPacket.CMPPSubmit;
/**
 *����������쳣�˳�����ɱ��db�л����Ѵ�����ģ��ɹ���ʧ�ܵģ���δת�Ƶ����ݱ������
 * @author gongyuwei-leadtone
 * @datetime 2011-09-16
 */
public class BackupBizDataHandledTask extends Engine {

    private CMPPSubmitTable m_submit;
	private CMPPSubmitBackupTable m_submit_backup;
	private int m_nID;

	public BackupBizDataHandledTask(int nID,DatabasePool pool) {
		super("BackupBizDataHandledTask(" + nID + ")");
		m_nID=nID;
		m_submit = new CMPPSubmitTable();
		m_submit.m_pool = pool;
		m_submit_backup = new CMPPSubmitBackupTable();
		m_submit_backup.m_pool = pool;
	}
	public void dump(PrintStream ps) {
		ps.print("\tsubmitdb(" + m_nID + "," + (isRunning() ? "+" : "-")
				+ ") : isRunning" + "\r\n");
	}

	public void empty() {
		m_submit.empty();
		m_submit=null;
		m_submit_backup.empty();
		m_submit_backup = null;
	}

    public void run(){
    	while(!Thread.interrupted()){
		    	List<CMPPSubmit> backdata=null;
		    	try {
		    		m_submit.open();
		    		/*************************************�޸ĳ�ֻ�����Ʊ��뵱ǰʡ�йص�����******************************************************/
		    		for (int i = 0; i < Center.m_gateways.size(); i++) {
		    			GatewayEngine gateway = (GatewayEngine) Center.m_gateways.elementAt(i);
		    			backdata= m_submit.select2BackData(gateway.m_strName);
		    		}
					m_submit.close();
					for(CMPPSubmit submit:backdata){
						// �����̬���ݻ��ƿ�����ִ�б��ݲ�ɾ����ǰʡ������ص�����
						if (BackupTableConfig.DYNAMICBACKUPTABLE
								.equalsIgnoreCase("true")) {
							Log.log("BackupBizDataHandledTask.run : backup submit record !",
									0x4000000000000L);
							m_submit_backup.open();
							m_submit_backup.backup(submit);
							m_submit_backup.close();
							Log.log("BackupBizDataHandledTask.run : delete submit record !",
									0x4000000000000L);
							m_submit.open();
							m_submit.deletenow(submit);
							m_submit.close();
						}
					}
					 int BACKUP_INTERVAL_TIME=30;
					 try {
						 //����˯��ָ��ʱ��
						Thread.sleep(1000 * BACKUP_INTERVAL_TIME * 60L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					 Log.log(e);
				}
		    }
    	}
}
