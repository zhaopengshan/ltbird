package com.leadtone.mas.bizplug.common;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	private static final Log log = LogFactory.getLog(DateUtil.class);
	/**
	 * 一天内
	 * @param tar
	 * @return
	 */
	public static Date oneDayAgo(Date nowDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-1);
		return calendar.getTime();
	}
	/**
	 * 一周内
	 * @param tar
	 * @return
	 */
	public static Date oneWeekAgo(Date nowDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-7);
		calendar.getTime();
		return calendar.getTime();
	}
	/**
	 * 一月内
	 * @param tar
	 * @return
	 */
	public static Date oneMonthAgo(Date nowDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR)-30);
		calendar.getTime();
		return calendar.getTime();
	}
}
