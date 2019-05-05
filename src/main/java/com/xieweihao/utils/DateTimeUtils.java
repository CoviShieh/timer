package com.xieweihao.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
/**
 * 日期时间工具类
 * @author Abe
 *
 */
public class DateTimeUtils {
	
	/**
	 * 年月日缺省分隔符
	 */
	private static char DAY_DELIMITER = '-';

	/**
	 * 指示年的数值,和JDK的Calendar.YEAR的值相同
	 */
	public static int YEAR = Calendar.YEAR;

	/**
	 * 指示月份的数值,和JDK的Calendar.MONTH的值相同
	 */
	public static int MONTH = Calendar.MONTH;

	/**
	 * 指示一月中的某天的数值,和JDK的Calendar.DAY_OF_MONTH的值相同
	 */
	public static int DAY = Calendar.DAY_OF_MONTH;

	/**
	 * 指示一天中的小时的数值,和JDK的Calendar.DAY_OF_DAY的值相同
	 */
	public static int HOUR = Calendar.HOUR_OF_DAY;

	/**
	 * 指示一小时中的分钟的数值,和JDK的Calendar.MINUTE的值相同
	 */
	public static int MINUTE = Calendar.MINUTE;

	/**
	 * 指示一分钟中的秒钟的数值,和JDK的Calendar.SECOND的值相同
	 */
	public static int SECOND = Calendar.SECOND;

	/**
	 * 指示 Sunday 的 DAY_OF_WEEK 字段值,和JDK的Calendar.SUNDAY的值相同
	 */
	public static int SUNDAY = Calendar.SUNDAY;

	/**
	 * 指示 Monday 的 DAY_OF_WEEK 字段值,和JDK的Calendar.MONDAY的值相同
	 */
	public static int MONDAY = Calendar.MONDAY;

	/**
	 * 指示 Tuesday 的 DAY_OF_WEEK 字段值,和JDK的Calendar.TUESDAY的值相同
	 */
	public static int TUESDAY = Calendar.TUESDAY;

	/**
	 * 指示 WEDNESDAY 的 DAY_OF_WEEK 字段值,和JDK的Calendar.WEDNESDAY的值相同
	 */
	public static int WEDNESDAY = Calendar.WEDNESDAY;

	/**
	 * 指示 THURSDAY 的 DAY_OF_WEEK 字段值,和JDK的Calendar.THURSDAY的值相同
	 */
	public static int THURSDAY = Calendar.THURSDAY;

	/**
	 * 指示 FRIDAY 的 DAY_OF_WEEK 字段值,和JDK的Calendar.FRIDAY的值相同
	 */
	public static int FRIDAY = Calendar.FRIDAY;

	/**
	 * 指示 SATURDAY 的 DAY_OF_WEEK 字段值,和JDK的Calendar.SATURDAY的值相同
	 */
	public static int SATURDAY = Calendar.SATURDAY;
	
	/**
	 * 根据时间格式返回当前时间的字符串
	 * @param date_format 例如:yyyy-MM-dd HH:mm:ss
	 * @return 返回当前时间的字符串
	 */
	public static String getTime(String date_format){// "yyyy-MM-dd HH:mm:ss"
		java.util.Date date = new java.util.Date();// 得到当前系统时间
		return getTime(date_format,date);
	}
	
	/**
	 * 返回当前时间yyyy-MM-dd HH:mm:ss的字符串
	 * @return  返回当前时间
	 */
	public static String now() {
		return getTime("yyyy-MM-dd HH:mm:ss");// "yyyy-MM-dd HH:mm:ss"
	}
	
	/**
	 * 取得系统默认时区的日期时间
	 * 
	 * @return String YYYY-MM-DD HH:MM:DD
	 */
	public static String getDateTime() {
		return getDateTime(new GregorianCalendar());
	}
	
	/**
	 * 取得当前时间
	 * 
	 * @return String HH:MM:SS
	 */
	public static String getTime() {
		return getDateTime().substring(11, 19);
	}

	/**
	 * 取得当前日期
	 * 
	 * @return String YYYY-MM-DD
	 */
	public static String getDate() {
		return getDateTime().substring(0, 10);
	}
	
	/**
	 * 根据时间与时间格式返回时间字符串
	 * @param  date_format 时间格式 例如:yyyy-MM-dd HH:mm:ss
	 * @param  date 时间对象 
	 * @return 返回时间字符串
	 */
	public static String getTime(String date_format,Date date){
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(date_format);
		String current_time = formatter.format(date);
		return current_time;
	}
	
	/**
	 * 转换时间对象为字符串格式为yyyy-MM-dd HH:mm:ss
	 * @param date 日期
	 * @return 返回时间字符串
	 */
	public static String date2String(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 根据时间格式格式化时间对象
	 * @param date 时间对象
	 * @param format 格式化格式
	 * @return 返回时间字符串
	 */
	public static String convertDateToStringByFormat(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 * 根据天数得到当前日期的前几天的日期
	 * @param days 天数
	 * @return 返回日期
	 */
	public static Date getDateByByDaysInt(int days){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	/**
	 * 根据天数得到指定日期的前几天的日期
	 * @param currentDate 当前日期
	 * @param days 天数
	 * @return 返回日期
	 */
	public static Date getDateByByDaysInt(Date currentDate,int days){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	
	/**
	 * 根据分钟数得到当前日期的前几分钟的日期
	 * @param minutes 分钟
	 * @return 返回日期
	 */
	public static Date getDateByByMinutesInt(int minutes){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}
	/**
	 * 根据分钟数得到指定日期的前几分钟的日期
	 * @param currentDate 当前日期
	 * @param minutes 分钟
	 * @return 返回日期
	 */
	public static Date getDateByByMinutesInt(Date currentDate,int minutes){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}
	
	/**
	 * 格式化不带秒数的日期字符串
	 * @param dateStr 日期字符串
	 * @return 返回yyyy-MM-dd格式的日期字符
	 */
	public static String convertDateWithoutSecondsByFormat(String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.format(sdf.parse(dateStr));
	      }catch (Exception e) {
			return null;
		}
		
	}
	/**
	 * 根据日期字符串转化为日期充对象，默认格式为yyyy-MM-dd
	 * @param dateString 时间字符串
	 * @return 返回日期
	 */
	public static Date string2Date(String dateString){
		return convertDateStringToDate(dateString,"yyyy-MM-dd");
	}
	/**
	 * 根据日期字符串与格式转化为日期对象
	 * @param dateString 日期字符串
	 * @param format 转化格式
	 * @return 返回日期对象
	 */
	public static Date convertDateStringToDate(String dateString,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
		
	}
	
	/**
	 * 根据时间格式把时间字符串转化为格式化后的时间字符串
	 * @param dateString 日期字符串
	 * @param format 转化格式
	 * @return 返回转化后的日期字符串
	 */
	public static String  convertDateStringToString(String dateString,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		String dateStr="";
		try {
			date = sdf.parse(dateString);
			//dateStr=convertDateToStringByFormat(date);
			dateStr=convertDateToStringByFormat(date,format);
				} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return dateStr;
		
	}
	
	/**
	 * 比较两时间的差异
	 * 
	 * @param startTime
	 *            String 开始时间 YYYY-MM-DD HH:MM:SS
	 * @param endTime
	 *            String 结束时间 YYYY-MM-DD HH:MM:SS
	 * @return long[] 长度为4，long[0]:相差的天数、long[1]:相差的小时、long[2]:相差的分、long[3]:相差的秒
	 */
	public static long[] diff(String startTime, String endTime) {
		long startTimeMillis = getCalendar(startTime).getTimeInMillis();
		long endTimeMillis = getCalendar(endTime).getTimeInMillis();
		long diff = endTimeMillis - startTimeMillis;
		long[] diffPart = new long[4];
		diffPart[0] = diff / (24 * 60 * 60 * 1000l);
		diffPart[1] = (diff % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000l);
		diffPart[2] = (diff % (60 * 60 * 1000)) / (60 * 1000l);
		diffPart[3] = (diff % (60 * 1000)) / 1000l;
		return diffPart;
	}
	
	/**
	 * 在指定的日期中按某个时间类型添加指定步长
	 * 
	 * @param datetime
	 *            String YYYY-MM-DD HH:MM:SS
	 * @param type
	 *            int YEAR,MONTH,DAY,HOUR,MINUTE,SECOND
	 * @param step
	 *            int 步长 可以是整数或负数
	 * @return String 改变后的日期时间 YYYY-MM-DD HH:MM:SS
	 */
	public static String getPreDateTime(String datetime, int type, int step) {
		Calendar calendar = new GregorianCalendar(Integer.parseInt(datetime
				.substring(0, 4)),
				Integer.parseInt(datetime.substring(5, 7)) - 1, Integer
						.parseInt(datetime.substring(8, 10)), Integer
						.parseInt(datetime.substring(11, 13)), Integer
						.parseInt(datetime.substring(14, 16)), Integer
						.parseInt(datetime.substring(17, 19)));
		calendar.add(type, step);
		return getDateTime(calendar);
	}

	/**
	 * 根据日历返回日期时间
	 * 
	 * @param Calendar
	 *            日历
	 * @return String YYYY-MM-DD HH:MM:DD
	 */
	private static String getDateTime(Calendar calendar) {
		StringBuffer buf = new StringBuffer("");

		buf.append(calendar.get(Calendar.YEAR));
		buf.append(DAY_DELIMITER);
		buf.append(calendar.get(Calendar.MONTH) + 1 > 9 ? calendar
				.get(Calendar.MONTH)
				+ 1 + "" : "0" + (calendar.get(Calendar.MONTH) + 1));
		buf.append(DAY_DELIMITER);
		buf.append(calendar.get(Calendar.DAY_OF_MONTH) > 9 ? calendar
				.get(Calendar.DAY_OF_MONTH)
				+ "" : "0" + calendar.get(Calendar.DAY_OF_MONTH));
		buf.append(" ");
		buf.append(calendar.get(Calendar.HOUR_OF_DAY) > 9 ? calendar
				.get(Calendar.HOUR_OF_DAY)
				+ "" : "0" + calendar.get(Calendar.HOUR_OF_DAY));
		buf.append(":");
		buf.append(calendar.get(Calendar.MINUTE) > 9 ? calendar
				.get(Calendar.MINUTE)
				+ "" : "0" + calendar.get(Calendar.MINUTE));
		buf.append(":");
		buf.append(calendar.get(Calendar.SECOND) > 9 ? calendar
				.get(Calendar.SECOND)
				+ "" : "0" + calendar.get(Calendar.SECOND));
		return buf.toString();
	}
	
	/**
	 * 根据字符串类型取得日期类型
	 * 
	 * @param datetime
	 *            String YYYY-MM-DD HH:MM:SS
	 * @return Calendar
	 */
	public static Calendar getCalendar(String datetime) {
		return new GregorianCalendar(
				Integer.parseInt(datetime.substring(0, 4)), Integer
						.parseInt(datetime.substring(5, 7)) - 1, Integer
						.parseInt(datetime.substring(8, 10)), Integer
						.parseInt(datetime.substring(11, 13)), Integer
						.parseInt(datetime.substring(14, 16)), Integer
						.parseInt(datetime.substring(17, 19)));
	}

	/**
	 * 获得当前日历处理类
	 * 
	 * @return Calendar
	 */
	public static Calendar getCalendar() {
		return getCalendar(getDateTime());
	}

	/**
	 * 在指定的日期中按某个时间类型添加指定步长
	 * 
	 * @param datetime
	 *            YYYY-MM-DD HH:MM:SS
	 * @param type
	 *            YEAR,MONTH,DAY,HOUR,MINUTE,SECOND
	 * @param step
	 *            步长 可以是整数或负数
	 * @return 改变后的日期时间 YYYY-MM-DD
	 */
	public static String getPreDate(String date, int type, int step) {
		Calendar calendar = new GregorianCalendar(Integer.parseInt(date
				.substring(0, 4)), Integer.parseInt(date.substring(5, 7)) - 1,
				Integer.parseInt(date.substring(8, 10)), 0, 0, 0);
		calendar.add(type, step);
		return getDateTime(calendar).substring(0, 10);
	}

	/**
	 * 取得当前日期的整数,将四位的年份、两位的月份、两位的日期合在一起返回
	 * 
	 * @return int yyyyMMdd
	 */
	public static int getDateInt() {
		String date = getDate();
		return Integer.parseInt(date.substring(0, 4) + date.substring(5, 7)
				+ date.substring(8, 10));
	}

	/**
	 * 取得当前时间的正数,将两位小时、两位分钟、两位秒钟合在一起返回
	 * 
	 * @return int HHmmss
	 */
	public static int getTimeInt() {
		String date = getDateTime();
		return Integer.parseInt(date.substring(11, 13) + date.substring(14, 16)
				+ date.substring(17, 19));
	}
	
	/**
	 * 取得指定时间的时间戳
	 * 
	 * @param datetime
	 *            String YYYY-MM-DD HH:MM:SS
	 * @return long 时间戳
	 */
	public static long getTimeStamp(String datetime) {
		return getCalendar(datetime).getTime().getTime();
	}

	/**
	 * 取得当前的时间戳
	 * 
	 * @return long 时间戳
	 */
	public static long getTimeStamp() {
		return System.currentTimeMillis();
	}
	
	/**
	 * 获得指定日期的某一部分
	 * 
	 * @param datetime
	 *            String yyyy-MM-dd HH:mm:ss的格式
	 * @param part
	 *            int 日期部分 例如：DateUtil.YEAR,DateUtil.MONTH等
	 * @return int
	 */
	public static int getdDatePart(String datetime, int part) {
		Calendar calendar = getCalendar(datetime);
		return calendar.get(part);
	}

	/**
	 * 获得当前日期的某一部分
	 * 
	 * @param datetime
	 *            String yyyy-MM-dd HH:mm:ss的格式
	 * @param part
	 *            int 日期部分 例如：DateUtil.YEAR,DateUtil.MONTH等
	 * @return int
	 */
	public static int getdDatePart(int part) {
		Calendar calendar = getCalendar(getDateTime());
		return calendar.get(part);
	}

	public static String getDayOfMonth(String datetime) {
		Calendar cal = getCalendar(datetime);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		return cal.get(Calendar.DAY_OF_MONTH) + "";
	}

	public String getDaysByDateTime(String dateTime) {
		Calendar cal = getCalendar(dateTime);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		return cal.get(Calendar.DAY_OF_MONTH) + "";
	}
	
    public static Date addDays(String date,int days){    	
    	Date d1=getCalendar(date).getTime();
		Calendar   calendar   =   new   GregorianCalendar(); 
	    calendar.setTime(d1); 
	    calendar.add(Calendar.DATE,days);//把日期往后增加一天.整数往后推,负数往前移动 
	    Date d2=calendar.getTime();   //这个时间就是日期往后推一天的结果 
	    return d2;
    }
    
	private static Integer index = 0;
	
	public static String createSeqCode() {
		return formatDate(new Date(), "yyyyMMddhhmmssSSS")
				+ String.valueOf(index == 9 ? index = 0 : ++index);
	}
	
	   public static String formatDate(Date date, String format)
	    {
	        return formatDateByFormat(date, format);
	    }
	    
	    public static String formatDateByFormat(Date date, String format)
	    {
	        String result = "";
	        if(date != null)
	            try
	            {
	                SimpleDateFormat sdf = new SimpleDateFormat(format);
	                result = sdf.format(date);
	            }
	            catch(Exception ex)
	            {
	               return "";
	            }
	        return result;
	    }
	    
	    public static String[] getWeekDay(String datetime){
	    	
	    	Date date = string2Date(datetime);
	    	Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);
	        calendar.setFirstDayOfWeek(Calendar.MONDAY);	// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
	        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
	        if(dayWeek==1){
	            dayWeek = 8;
	        }
	        
	        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，星期一与当前是一周的第几天的差值  
//	        java.util.Date mondayDate = calendar.getTime();
//	        String weekBegin = DateTimeUtils.date2String(mondayDate);
	        
	        String[] dates = new String[7];
	        for (int i = 0; i < 7; i++) {
//	            dates[i] = calendar.getTime();
//	            calendar.add(Calendar.DATE, 1);
	        	Date weekDay = calendar.getTime();
	        	dates[i] = date2String(weekDay);
	        	calendar.add(Calendar.DATE, 1);
	        }
	    	return dates;
	    }
	    
	    public static String getWeekOfMon(String datetime){
//	    	Map<String,Date> map = new HashMap();
	    	Date date = string2Date(datetime);
	    	Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);
	        calendar.setFirstDayOfWeek(Calendar.MONDAY);	// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
	        int dayWeek = calendar.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天  
	        if(dayWeek==1){
	            dayWeek = 8;
	        }
	        //拿到周一的值
	        calendar.add(Calendar.DATE, calendar.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，星期一与当前是一周的第几天的差值  
//	        map.put("mondayDate", calendar.getTime());
//	        //拿到周日的值
//	        calendar.add(Calendar.DATE, 4 +calendar.getFirstDayOfWeek());
//	        map.put("sundayDate", calendar.getTime());
	    	
	    	return date2String(calendar.getTime());
	    }
	    
	    /** 
	    6      * 时间戳转换成日期格式字符串 
	    7      * @param seconds 精确到毫秒的字符串 
	    8      * @param formatStr 
	    9      * @return 
	   10      */  
	        public static String timeStamp2Date(String seconds,String format) {  
	            if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
	                return "";  
	            }  
	            if(format == null || format.isEmpty()){
	                format = "yyyy-MM-dd HH:mm:ss";
	            }   
	            SimpleDateFormat sdf = new SimpleDateFormat(format);  
	            return sdf.format(new Date(Long.valueOf(seconds)));  
	        }
}
