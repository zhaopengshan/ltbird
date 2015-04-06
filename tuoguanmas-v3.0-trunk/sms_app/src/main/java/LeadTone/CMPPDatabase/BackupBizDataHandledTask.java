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
 *处理因程序异常退出或被秒杀后，db中还有已处理完的（成功或失败的）但未转移到备份表的数据
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
		    		/*************************************修改成只备份移表与当前省有关的数据******************************************************/
		    		for (int i = 0; i < Center.m_gateways.size(); i++) {
		    			GatewayEngine gateway = (GatewayEngine) Center.m_gateways.elementAt(i);
		    			backdata= m_submit.select2BackData(gateway.m_strName);
		    		}
					m_submit.close();
					for(CMPPSubmit submit:backdata){
						// 如果动态备份机制开启则执行备份并删除当前省网关相关的数据
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
						 //程序睡眠指定时间
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
