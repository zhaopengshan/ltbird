/**
 * 
 */
package com.leadtone.delegatemas.admin.security.common;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.http.HttpSession;

import com.leadtone.mas.bizplug.common.ApSmsConstants;

/**
 * @author PAN-Z-G
 *
 */
public class FindPwdTimeTask extends TimerTask{
	private HttpSession session;
	public  Integer timeTaskSecond=0;
	private int timeNumber = 0;

	@Override
	public void run() {
		mobileCheckingTask();
	}

	public void executeTime(TimerTask task, long delay, long period,String timeTaskSecond,
			HttpSession session) {
		try {
			this.session = session;
			this.timeTaskSecond=Integer.parseInt(timeTaskSecond);
			Timer timer = new Timer();
			timer.schedule(task, delay, period);
			//System.out.println(this.timeTaskSecond*60);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mobileCheckingTask() {
		System.out.println(timeNumber);
		try {
			++timeNumber;
			if (timeNumber > timeTaskSecond*60) {
				if(session.getAttribute(ApSmsConstants.SESSION_FIND_SMS_NULBER)!=null)
					session.setAttribute(ApSmsConstants.SESSION_FIND_SMS_NULBER, "");
 				this.cancel();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 	
	}
	
}
