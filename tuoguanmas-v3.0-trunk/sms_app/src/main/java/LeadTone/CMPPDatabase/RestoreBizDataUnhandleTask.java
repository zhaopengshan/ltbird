package LeadTone.CMPPDatabase;

import java.io.PrintStream;
import java.util.List;

import LeadTone.Engine;
import LeadTone.Log;
import LeadTone.CMPPDatabase.CMPPTable.CMPPSubmitTable;
import LeadTone.Center.Center;
import LeadTone.Database.DatabasePool;
import LeadTone.Gateway.GatewayEngine;
import LeadTone.Packet.CMPPPacket.CMPPSubmit;
/**
* 处理因程序被秒杀而中断，使当前省正在处理的数据状态置为未处理状态
* @author gongyuwei-leadtone
* @datetime 2011-09-16
*/
public class RestoreBizDataUnhandleTask extends Engine {
	private CMPPSubmitTable m_submit;
	private int m_nID;
	private boolean isRunning = true;
	public RestoreBizDataUnhandleTask(int nID,DatabasePool pool) {
		super("RestoreBizDataUnhandleTask"+nID);
		this.m_nID=nID;
		m_submit = new CMPPSubmitTable();
		m_submit.m_pool = pool;
	}
	public void dump(PrintStream ps) {
		ps.print("\tsubmitdb(" + m_nID + "," + (isRunning() ? "+" : "-")
				+ ") : isRunning" + "\r\n");
	}

	public void empty() {
		m_submit.empty();
		m_submit=null;

	}

	public void run(){
		while(isRunning){
	    	try {
	    		 Log.log("RestoreBizDataUnhandleTask.run : administrator will start stopped the system !", 1L);
	    		 //m_submit对象会有null情况，所以此处增加判空处理，并实例。于骞，20120628.
	    		 if(m_submit == null ){
	    			 Log.log("The RestoreBizDataUnhandleTask Thread is null! Now will init m_submit Thread!",1L);
	    			 m_submit = new CMPPSubmitTable();
	    		 }
	    			 
	    		m_submit.open();
	    		/****************************************修改成只重置与当前省有关的数据************************************************/
	    		if(Center.m_gateways.size() > 0){
	    			
		    		for (int i = 0; i < Center.m_gateways.size(); i++) {
		    			if(Center.m_gateways.elementAt(i) != null ){
		    				GatewayEngine gateway = (GatewayEngine) Center.m_gateways.elementAt(i);
				    		m_submit.update2RestorData(gateway.m_strName);
		    			}
		    		}
	    		}
	    		/****************************************修改成只重置与当前省有关的数据************************************************/
	    		m_submit.close();
	    		/*m_submit.update2RestorData();
				m_submit.close();*/
	    		int BACKUP_INTERVAL_TIME=30;
				 try {
					 //程序睡眠指定时间
					 Thread.sleep(1000 * BACKUP_INTERVAL_TIME * 60L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	    	}catch (Exception e) {
	    		Log.log(e);
	    		e.printStackTrace();
	    		isRunning = false;
				}
			}
		}
}
