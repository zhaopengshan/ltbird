package com.changyuan.misc.utils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * @author tsw
 *
 */
public class DateUtils {
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat customerFormat = new SimpleDateFormat("yyyyMMdd");
	public static final String Simple_Date_Format = "yyyy-MM-dd";
	public static final int Simple_Date_Format_Length = Simple_Date_Format.length();
	public static final String Simple_DateTime_Format = "yyyy-MM-dd HH:mm:ss";

	public static final String PATTERN_YYYYMMDD = "yyyy-MM-dd";
	public static final String PATTERN_YYYYMMDD2 = "yyyyMMdd";
	public static final String PATTERN_YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_YYYYMMDDHHMMSS2 = "yyyyMMddHHmmss";
	public static final String PATTERN_YYYYMMDDHHMMSSMill = "yyyyMMddHHmmssSSS";

    // 获得当前日期与本周一相差的天数
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    // 获得上周星期一的日期, weeks=-1 表示上一个星期一，-2表示上2个星期
    public static Date getPreviousMonday(int weeks) {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        return monday;
    }

    // 获得本周星期一的日期
    public static Date getCurrentMonday() {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        return monday;
    }

    // 获得下周星期一的日期
    public static Date getNextMonday(int weeks) {
        int mondayPlus = getMondayPlus();
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
        Date monday = currentDate.getTime();
        return monday;
    }
	    
	// 获得当前日期的字符串形式.
	public static final String getCurrentStringDate(String pattern) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(currentTime);
	}

	public static void main(String[] args) {
		// System.out.println(getTimeInMillis());
		//String date = getCurrentStringDate(PATTERN_YYYYMMDDHHMMSSMill);
		// long now=System.currentTimeMillis();
		// System.out.println("毫秒数："+now);
	    //String date = DateUtils.getNextDay(new Date(),"-7", DateUtils.PATTERN_YYYYMMDD2);
		//System.out.println(date);
		// System.out.println(isDate(date,PATTERN_YYYYMMDDHHMM));
	    String date = getStringDate();
//	    System.out.println(date);
	}

	@Deprecated
	public static Date getBetweenDate(Date queryDate) {
		Calendar date = Calendar.getInstance();
		date.setTime(queryDate);
		date.add(Calendar.DAY_OF_YEAR, 1);
		date.add(Calendar.SECOND, -1);
		return date.getTime();
	}

	public static Date getLastTimeInDay(Date day) {
		Calendar date = Calendar.getInstance();
		date.setTime(getFirstTimeInDay(day));
		date.add(Calendar.DAY_OF_YEAR, 1);
		date.add(Calendar.SECOND, -1);
		return date.getTime();
	}

	public static Date getFirstTimeInDay(Date day) {
		Calendar date = Calendar.getInstance();
		date.setTime(day);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);
		date.set(Calendar.MILLISECOND, 0);
		return date.getTime();
	}

	public static final String getCurrentStringDateYMDHMS() {
		return getCurrentStringDate(PATTERN_YYYYMMDDHHMMSS);
	}

	public static final String getCurrentStringDateYMDHM() {
		return getCurrentStringDate(PATTERN_YYYYMMDDHHMM);
	}

	public static final String getCurrentStringDateYMD() {
		return getCurrentStringDate(PATTERN_YYYYMMDD);
	}

	public static final String getCurrentStringDateYYYYMMDD() {
        return getCurrentStringDate(PATTERN_YYYYMMDD2);
    }
	
	public static final String getCurrentStringDateYYMDHMSS(){
		return getCurrentStringDate(PATTERN_YYYYMMDDHHMMSS2);
	}
	
	public static final boolean isDate(String strDate, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		formatter.setLenient(false);
		ParsePosition pos = new ParsePosition(0);
		try {
			Date d = formatter.parse(strDate, pos);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String date2Str(Date date) {
		try {
			return dateFormat.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	public static String date2Str(Date date, String pattern) {
		try {
			customerFormat.applyPattern(pattern);
			return customerFormat.format(date);
		} catch (Exception ex) {
			return "";
		}
	}

	public static Date str2Date(String str) {
		try {
			return dateFormat.parse(str);
		} catch (Exception ex) {
			return null;
		}
	}

	public static Date str2Date(String str, String pattern) {
		try {
			SimpleDateFormat dateFormat1 = new SimpleDateFormat(pattern);
			return dateFormat1.parse(str);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static Date getLastMonthLastDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, max);
		return calendar.getTime();
	}

	/**
	 * 字符串转换为普通的日期
	 * 
	 * @param str
	 *            日期格式Simple_Date_Format，Simple_DateTime_Format
	 * @return java.util.Date
	 */
	public static java.util.Date strToSysDate(String str) {
		if (null != str && str.length() > 0) {
			try {
				if (str.length() <= Simple_Date_Format_Length) { // 只包含日期。
					return (new SimpleDateFormat(Simple_Date_Format)).parse(str);
				} else { // 包含日期时间
					return (new SimpleDateFormat(Simple_DateTime_Format)).parse(str);
				}
			} catch (ParseException error) {
				return null;
			}
		} else
			return null;
	}

	/**
	 * 字符串转换为sql的日期
	 * 
	 * @param str
	 *            String
	 * @return java.sql.Date
	 */
	public static java.sql.Date strToSqlDate(String str) {
		if (strToSysDate(str) == null || str.length() < 1)
			return null;
		else
			return new java.sql.Date(strToSysDate(str).getTime());
	}

	/**
	 * sql日期型转换为带时间的字符串
	 * 
	 * @param dDate
	 *            　java.sql.Date
	 * @return String "yyyy-MM-dd HH:mm:ss";
	 */
	public static String toDateTimeStr(java.sql.Date dDate) {
		if (dDate == null) {
			return null;
		} else {
			return (new SimpleDateFormat(Simple_DateTime_Format)).format(dDate);
		}
	}
	
	/**
	 * 普通日期型转换为带时间的字符串
	 * 
	 * @param dDate
	 *            java.util.Date
	 * @return String "yyyy-MM-dd HH:mm:ss";
	 */
	public static String toDateTimeStr(java.util.Date dDate) {
		if (dDate == null) {
			return null;
		} else {
			return (new SimpleDateFormat(Simple_DateTime_Format)).format(dDate);
		}
	}

	/**
	 * sql日期型转换为不带时间的字符串
	 * 
	 * @param d
	 *            java.sql.Date
	 * @return String "yyyy-MM-dd"
	 */
	public static String toDateStr(java.sql.Date d) {
		if (d == null) {
			return null;
		} else {
			return (new SimpleDateFormat(Simple_Date_Format)).format(d);
		}
	}

	/**
	 * 普通日期型转换为不带时间的字符串
	 * 
	 * @param d
	 *            java.util.Date
	 * @return　String String "yyyy-MM-dd"
	 */
	public static String toDateStr(java.util.Date d) {
		if (d == null) {
			return null;
		} else {
			return (new SimpleDateFormat(Simple_Date_Format)).format(d);
		}
	}

	/**
	 * 获得当时的时间
	 * 
	 * @return java.sql.Date
	 */
	public static java.sql.Date getCurrentDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}

	/**
	 * 获得当前的日期和时间（日历）
	 * 
	 * @return java.util.Date
	 */
	public static java.util.Date getCurrentDateTime() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 将util型的日期型的数据转换为sql型的日期数据
	 * 
	 * @param date
	 *            java.util.Date
	 * @return java.sql.Date
	 */
	public static java.sql.Date utilToSql(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将sql型的日期型的数据转换为util型的日期数据
	 * 
	 * @param date
	 *            java.sql.Date
	 * @return java.util.Date
	 */
	public static java.util.Date sqlToUtil(java.sql.Date date) {
		return new java.util.Date(date.getTime());
	}

	/**
	 * 将日期和时间复合（组合）起来。
	 * 
	 * @param date
	 *            java.sql.Date
	 * @param time
	 *            java.sql.Time
	 * @return java.sql.Date
	 */
	public static java.sql.Date compositeDateTime(java.sql.Date date, java.sql.Time time) {
		if (null == date || null == time)
			return null;
		Calendar calDate = new GregorianCalendar();
		calDate.setTimeInMillis(date.getTime());
		Calendar calTime = new GregorianCalendar();
		calTime.setTimeInMillis(time.getTime());
		Calendar calCompositeDateTime = new GregorianCalendar();
		int iYear = calDate.get(Calendar.YEAR);
		int iMonth = calDate.get(Calendar.MONTH);
		int iDay = calDate.get(Calendar.DATE);
		int iHour = calTime.get(Calendar.HOUR_OF_DAY);
		int iMin = calTime.get(Calendar.MINUTE);
		int iSec = calTime.get(Calendar.SECOND);
		int iMSec = calTime.get(Calendar.MILLISECOND);
		calCompositeDateTime.set(iYear, iMonth, iDay, iHour, iMin, iSec);
		calCompositeDateTime.set(Calendar.MILLISECOND, iMSec);
		return utilToSql(calCompositeDateTime.getTime());
	}

	/**
	 * 解析字符串型的日期型数据
	 * 
	 * @param strDate
	 *            类似于：
	 * @return 解析后的日期对象
	 */
	public static java.util.Date parseDate(String strDate) {
		long r = 0;
		try {
			StringTokenizer token = new StringTokenizer(strDate, " ");
			String date = token.nextToken();
			Date now = java.sql.Date.valueOf(date);
			r = now.getTime();
			try {
				String time = token.nextToken();
				StringTokenizer tkTime = new StringTokenizer(time, ":");
				r += Integer.parseInt(tkTime.nextToken()) * 60 * 60 * 1000;
				r += Integer.parseInt(tkTime.nextToken()) * 60 * 1000;
				r += Integer.parseInt(tkTime.nextToken()) * 1000;
			} catch (Exception ex) {
				r = now.getTime();
			}
		} catch (Exception ex) {
			return new Date();
		}
		return new Date(r);
	}

	public static String fullTimeNoFormat() {
		return fullTimeNoFormat(new Date());
	}

	public static String fullTimeNoFormat(long date) {
		return fullTimeNoFormat(new Date(date));
	}

	public static String shortDateForChina(long date) {
		return shortDateForChina(new Date(date));
	}

	/**
	 * 日期数据格式化
	 * 
	 * @param date
	 * @return yyyy 年 MM 月 dd 日
	 */
	public static String shortDateForChina(Date date) {
		String r = "";
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			r = formater.format(date);
		} catch (Exception ex) {
			r = formater.format(new Date());
		}
		return r;
	}

	/**
	 * 将日期数据对象格式化为字符串。
	 * 
	 * @param date
	 *            java.util.Date
	 * @return 格式类似于：yyyyMMddHHmmss。
	 */
	public static String fullTimeNoFormat(java.util.Date date) {
		String r = "";
		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			r = formater.format(date);
		} catch (Exception ex) {
			r = formater.format(new Date());
		}
		return r;
	}

	/**
	 * 将日期数据对象格式化为固定字符串格式。
	 * 
	 * @param date
	 * @return 格式类似于：yyyy-MM-dd HH:mm:ss
	 */
	public static String fullTime(java.util.Date date) {
		String r = "";
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			r = formater.format(date);
		} catch (Exception ex) {
			r = formater.format(new Date());
		}
		return r;
	}

	/**
	 * 将当前日期数据对象格式化为固定字符串格式。
	 * 
	 * @return 格式类似于：yyyy-MM-dd HH:mm:ss
	 */
	public static String fullTime() {
		return fullTime(new Date());
	}

	/**
	 * 将long日期数据对象格式化为固定字符串格式。
	 * 
	 * @param date
	 * @return 格式类似于：yyyy-MM-dd HH:mm:ss
	 */
	public static String fullTime(long date) {
		return fullTime(new Date(date));
	}

	/**
	 * 将日期数据对象格式化为固定字符串格式。
	 * 
	 * @param date
	 * @return 格式类似于：yyyy-MM-dd
	 */
	public static String shortDate(Date date) {
		String r = "";
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		try {
			r = formater.format(date);
		} catch (Exception ex) {
			r = formater.format(new Date());
		}
		return r;
	}

	/**
	 * 将当前日期数据对象格式化为固定字符串格式。
	 * 
	 * @return 格式类似于：yyyy-MM-dd
	 */
	public static String shortDate() {
		return shortDate(new Date());
	}

	/**
	 * 将long数据对象格式化为固定字符串格式。
	 * 
	 * @param date
	 * @return 格式类似于：yyyy-MM-dd
	 */
	public static String shortDate(long date) {
		return shortDate(new Date(date));
	}

	/**
	 * 将日期数据对象格式化为固定字符串格式。
	 * 
	 * @param date
	 * @return 格式类似于：HH:mm:ss
	 */
	public static String shortTime(Date date) {
		String r = "";
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm:ss");
		try {
			r = formater.format(date);
		} catch (Exception ex) {
			r = formater.format(new Date());
		}
		return r;
	}

	/**
	 * 将当前日期数据对象格式化为固定字符串格式。
	 * 
	 * @return 格式类似于：HH:mm:ss
	 */
	public static String shortTime() {
		return shortTime(new Date());
	}

	/**
	 * 将long日期数据对象格式化为固定字符串格式。
	 * 
	 * @param date
	 * @return 格式类似于：HH:mm:ss
	 */
	public static String shortTime(long date) {
		return shortTime(new Date(date));
	}

	/**
	 * 获得某个月的第一天0时0分0秒的时间
	 */
	public static java.util.Date getFirstDateOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal.getTime();
	}

	/**
	 * 获取某个月的某一天的0时0分0秒的时间
	 */
	public static java.util.Calendar getFirstTimeOfDay(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1);
		cal.set(Calendar.DAY_OF_MONTH, day);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return cal;
	}

	/**
	 * 将java.util.Date 转换为java.util.Calendar
	 * 
	 * @param date
	 *            java.util.Date date
	 * @return java.util.Calendar
	 */
	public static java.util.Calendar DateToCalendar(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}


	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static String getStringDate(String format) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 */
	public static String getTwoHour(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1]) / 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1]) / 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getNextDay(Date nowdate, String delay, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            String mdate = "";
            long myTime = (nowdate.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
            nowdate.setTime(myTime * 1000);
            mdate = format.format(nowdate);
            return mdate;
        } catch (Exception e) {
            return "";
        }
    }

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 返回美国时间格式 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8 || mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(dat)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	/**
	 * 获得一个日期所在的周的星期几的日期，如要找出2002年2月3日所在周的星期一是几号
	 * 
	 * @param sdate
	 * @param num
	 * @return
	 */
	public static String getWeek(String sdate, String num) {
		// 再转换为时间
		Date dd = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(dd);
		if (num.equals("1")) // 返回星期一所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		else if (num.equals("2")) // 返回星期二所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		else if (num.equals("3")) // 返回星期三所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		else if (num.equals("4")) // 返回星期四所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		else if (num.equals("5")) // 返回星期五所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		else if (num.equals("6")) // 返回星期六所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		else if (num.equals("0")) // 返回星期日所在的日期
			c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	public static String getWeekStr(String sdate) {
		String str = "";
		str = getWeek(sdate);
		if ("1".equals(str)) {
			//str = "星期日";
		} else if ("2".equals(str)) {
			//str = "星期一";
		} else if ("3".equals(str)) {
			//str = "星期二";
		} else if ("4".equals(str)) {
			//str = "星期三";
		} else if ("5".equals(str)) {
			//str = "星期四";
		} else if ("6".equals(str)) {
			//str = "星期五";
		} else if ("7".equals(str)) {
			//str = "星期六";
		}
		return str;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 形成如下的日历 ， 根据传入的一个时间返回一个结构 星期日 星期一 星期二 星期三 星期四 星期五 星期六 下面是当月的各个时间
	 * 此函数返回该日历第一行星期日所在的日期
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getNowMonth(String sdate) {
		// 取该时间所在月的一号
		sdate = sdate.substring(0, 8) + "01";
		// 得到这个月的1号是星期几
		Date date = strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int u = c.get(Calendar.DAY_OF_WEEK);
		String newday = getNextDay(sdate, (1 - u) + "");
		return newday;
	}

	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * @param k
	 *            表示是取几位随机数，可以自己定
	 */

	public static String getNo(int k) {
		return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	}

	/**
	 * 返回一个随机数
	 * 
	 * @param i
	 * @return
	 */
	public static String getRandom(int i) {
		Random jjj = new Random();
		if (i == 0)
			return "";
		String jj = "";
		for (int k = 0; k < i; k++) {
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	public static boolean RightDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		if (date == null)
			return false;
		if (date.length() > 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			sdf.parse(date);
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 根据输入的时间的字符串 自动匹配格式  转换时间对象
	 * @param send_time  add by  xiaolei1.wang
	 * @return
	 */
	public  Date getDate(String send_time)   {
	  	try {
	  		SimpleDateFormat sdf = new SimpleDateFormat( getDateFormat( send_time) );
			return  sdf.parse(send_time);
		} catch (ParseException e) {
			return null;
		}
	}
	
	 // 验证符合的时间格式  返回对应的时间  
    public static final String REGX_YYYY_MM_DD_HH_MM_SS = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))($|\\s([0-1]\\d|[2][0-3])\\:[0-5]\\d\\:[0-5]\\d)";
    public static final String REGX_YYYY_MM_DD_H_MM_SS = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))($|\\s[0-9]\\:[0-5]\\d\\:[0-5]\\d)";
    public static final String REGX_YYYY_MM_DD_HH_M_SS = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))($|\\s([0-1]\\d|[2][0-3])\\:[0-9]\\:[0-5]\\d)";
    public static final String REGX_YYYY_MM_DD_H_M_SS = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))($|\\s[0-9]\\:[0-9]\\:[0-5]\\d)";
    public static final String REGX_YYYY_MM_DD_H_M_S = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))($|\\s[0-9]\\:[0-9]\\:[0-9])";
    public static final String REGX_YYYY_MM_DD_HH_MM_S = "^[0-9]{4}-(((0[13578]|(10|12))-(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)-(0[1-9]|[1-2][0-9]|30)))($|\\s([0-1]\\d|[2][0-3])\\:[0-5]\\d\\:[0-9])";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_H_MM_SS = "yyyy-MM-dd H:mm:ss";
    public static final String YYYY_MM_DD_HH_M_SS = "yyyy-MM-dd HH:m:ss";
    public static final String YYYY_MM_DD_HH_MM_S = "yyyy-MM-dd HH:mm:s";
    public static final String YYYY_MM_DD_H_M_SS = "yyyy-MM-dd H:m:ss";
    public static final String YYYY_MM_DD_H_M_S = "yyyy-MM-dd H:m:s";
	/**
	 * 输入 时间字符串  返回对应的  时间格式  提供 SimpleDateFormat 使用
	 * @param time	add by  xiaolei1.wang
	 * @return
	 */
	public String getDateFormat(String time){
		String[] formats = {REGX_YYYY_MM_DD_H_MM_SS,REGX_YYYY_MM_DD_HH_M_SS,
				REGX_YYYY_MM_DD_HH_MM_S,REGX_YYYY_MM_DD_HH_MM_SS
				,REGX_YYYY_MM_DD_H_M_S,REGX_YYYY_MM_DD_H_M_SS};
		Map<String,String> hash = new HashMap<String,String>(){
			private static final long serialVersionUID = 1L;
			{
				put(REGX_YYYY_MM_DD_HH_MM_SS,YYYY_MM_DD_HH_MM_SS);
				put(REGX_YYYY_MM_DD_H_MM_SS,YYYY_MM_DD_H_MM_SS);
				put(REGX_YYYY_MM_DD_HH_M_SS,YYYY_MM_DD_HH_M_SS);
				put(REGX_YYYY_MM_DD_HH_MM_S,YYYY_MM_DD_HH_MM_S);
				put(REGX_YYYY_MM_DD_H_M_S,YYYY_MM_DD_H_M_S);
				put(REGX_YYYY_MM_DD_H_M_SS,YYYY_MM_DD_H_M_SS);
			}
		};
		for(String reg :formats ){
			if(Pattern.matches(reg, time)){
				return hash.get(reg);
			}
		}
		return null;
	}
}
