/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.leadtone.mas.bizplug.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author blueskybluesea
 */
public class DateUtils {

    public static Date getToday() {
        return Calendar.getInstance().getTime();
    }

    public static String getWhatDayToday() {
        Calendar calendar = Calendar.getInstance();
        String whatDay = null;
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                whatDay = "星期日";
                break;
            case Calendar.MONDAY:
                whatDay = "星期一";
                break;
            case Calendar.TUESDAY:
                whatDay = "星期二";
                break;
            case Calendar.WEDNESDAY:
                whatDay = "星期三";
                break;
            case Calendar.THURSDAY:
                whatDay = "星期四";
                break;
            case Calendar.FRIDAY:
                whatDay = "星期五";
                break;
            case Calendar.SATURDAY:
                whatDay = "星期六";
                break;
        }
        return whatDay;
    }
    public static Date getYesterday(Date today){
    	Calendar c = Calendar.getInstance();  
    	 c.setTime(today);  
    	 int day=c.get(Calendar.DATE);  
    	 c.set(Calendar.DATE,day-1);  
    	return c.getTime();
    }
    public static Date getTomorrow(Date today){
    	Calendar c = Calendar.getInstance();  
	   	c.setTime(today);  
	   	int day=c.get(Calendar.DATE);  
	   	c.set(Calendar.DATE,day+1);  
	   	return c.getTime();
    }
}
