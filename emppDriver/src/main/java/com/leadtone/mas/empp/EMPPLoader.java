package com.leadtone.mas.empp;

import com.leadtone.mas.StartupTest;
import com.leadtone.mas.empp.servlet.StartServlet;

import empp2.Engine;
import empp2.center.Center;
import empp2.config.SubsystemConfig;

public class EMPPLoader {
	public static void StartUp(){
		// 启动短信服务程序
		Center.main(StartupTest.args);	
	}
	
	public static void Shutdown(){
		if (Center.center != null) {
			Center.center.shutdown();
			int nSleep = 1000*30;
			try{
				nSleep = SubsystemConfig.getInstances().getIntProperty("sync_restart_time");
			} catch(Exception e){
				
			}
			if(nSleep>1000)
				try {
					Thread.sleep(nSleep);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else 
				Engine.wait(Center.center);
		}
		Center.center = null;
	}
	
//	public static boolean isSubmitDynamicBackup(){
//		return BackupTableConfig.DYNAMICBACKUPTABLE.equalsIgnoreCase("true");
//	}
}
