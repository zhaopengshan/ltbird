/**
 * 
 */
package com.leadtone.mas.bizplug.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author PAN-Z-G
 * 
 */
public class SmsNumberArithmetic {
	/**
	 * 允许任务号最大值为999
	 */
	private static final String TAXNUMBER_MAX = "999";
	/**
	 * 允许任务号最小值为001
	 */
	private static final String TAXNUMBER_MIN = "001";

	/**
	 * 生成任务号，并时刻都为三位
	 * 
	 * @param taxNum
	 * @return
	 */
	public static String reNumber(String taxNum) {
		Integer num = 0;
		String reNumber = null;
		try {
			if (null == taxNum) {
				reNumber = TAXNUMBER_MIN;
			} else if (taxNum.charAt(0) > '0') {
				if (!taxNum.equals(TAXNUMBER_MAX)) {
					num = Integer.parseInt(taxNum) + 1;
					reNumber = num.toString();
				} else
					reNumber = TAXNUMBER_MAX;
			} else if (taxNum.charAt(0) == '0' && taxNum.charAt(1) == '0') {
				num = Integer.parseInt(taxNum.substring(2, taxNum.length())) + 1;
				reNumber = num < 10 ? "00" + num : "0" + num;
			} else {
				num = Integer.parseInt(taxNum.substring(1, taxNum.length())) + 1;
				reNumber = num < 100 ? "0" + num : num.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reNumber;
	}
	
	public static Date reEndTime(Date date,Integer minute){
		Calendar canlendarCalendar = Calendar.getInstance();
		canlendarCalendar.setTime(date); 
		canlendarCalendar.set(Calendar.MINUTE, canlendarCalendar.get(Calendar.MINUTE)+minute); 
		return  canlendarCalendar.getTime();
	}

	public static Date timeType(String date){
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		Date date2=null;
		try {
			date2= simpleDateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date2;
	}
	
	public static String random(){
		String str="";
		Random random=new Random();
		for (int i = 0; i < 4; i++) {
			str+=(int)(random.nextFloat()*10) ;
		}
		return str.trim();
	}
	
	public static void main(String arge[]){
		System.out.println(reNumber("009")); 
	}
}
