package util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;


public class DateUtils {
	// 默认日期格式
	public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd";
	public static final String DATE_MINUTES_FORMAT = "yyyy-MM-dd HH:mm";
	// 默认时间格式
	public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_DEFAULT_FORMAT2  = "yyyy-MM-dd HH:mm:ss SSS";
	
	public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";
	public static final String YEAR_MONTH_DEFAULT_FORMAT = "yyyy-MM";
	// 日期格式化
	private static DateFormat dateFormat = null;
	// 时间格式化
	private static DateFormat dateTimeFormat = null;
	private static DateFormat dateTimeFormat2 = null;
	private static DateFormat yearMonthFormat = null;
	private static DateFormat dateMinutesFormat = null;
	private static Calendar calendar=null;
    static{
    	calendar=Calendar.getInstance();
    	dateFormat = new SimpleDateFormat(DATE_DEFAULT_FORMAT);
    	dateMinutesFormat = new SimpleDateFormat(DATE_MINUTES_FORMAT);
    	dateTimeFormat = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
    	yearMonthFormat = new SimpleDateFormat(YEAR_MONTH_DEFAULT_FORMAT);
    	dateTimeFormat2=new SimpleDateFormat(DATETIME_DEFAULT_FORMAT2);
    }
	/**
	 * 获取当前时间 ,格式:2016-10-28 09:36:36
	 * @author:zhouyc
	 * @date:2016年10月28日
	 * @param 
	 * @return String  格式：2016-10-28 09:36:36
	 */
	public static String getCurrentTime(){
		return dateTimeFormat.format(new Date());
	}
	/**
	 * 计算日期的天数
	 * @author:zhouyc
	 * @date:2016年10月27日
	 * @param curDate 要计算的日期  days 加减的天数 可为正负
	 * @return String  某月中的日期  格式 ：2016-10-28
	 */
	public static String calculateDays(Date curDate,int days){
		if(curDate==null){
			return null;
		}
		Date resultdate=null;
		try {
			calendar.setTime(curDate); 
			calendar.add(Calendar.DAY_OF_MONTH, days); 
			resultdate= calendar.getTime(); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return dateFormat.format(resultdate);
	}
	/**
	 * 计算小时数
	 * @author:zhouyc
	 * @date:2016年10月27日
	 * @param curDate 要计算的日期  hours 加减的小时 可为正负
	 * @return String  某月中的日期  格式 ：2016-10-28
	 */
	public static String calculateHours(Date curDate,int hours){
		if(curDate==null){
			return null;
		}
		Date resultdate=null;
		try {
			calendar.setTime(curDate); 
			calendar.add(Calendar.HOUR_OF_DAY, hours); 
			resultdate= calendar.getTime(); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return dateTimeFormat.format(resultdate);
	}
	/**
	 * 计算日期的天数
	 * @author:zhouyc
	 * @date:2017年07月2日
	 * @param date1 起始日期  date2 终止日期
	 * @return int 
	 */
	public static int compareToDate(String date1,String date2){
		int flag=0;
		if(StringUtils.isEmpty(date1)||StringUtils.isEmpty(date2)){
			flag=2;
			return flag;
		}
		try {
			Date startDate = dateTimeFormat.parse(date1); 
			Date endDate = dateTimeFormat.parse(date2); 
			Calendar start = Calendar.getInstance(); 
			Calendar end = Calendar.getInstance(); 
			start.setTime(startDate); 
			end.setTime(endDate); 
			if(start.before(end)){ 
				flag=-1;
			}else if(start.after(end)){ 
				flag=1;
			}else if(start.equals(end)){ 
				flag=0;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			flag=2;
			e.printStackTrace();
			
		}
		return flag;
	}
	
	/**
	 * 计算月份
	 * @author:zhouyc
	 * @date:2016年10月28日
	 * @param date 要计算的日期,months 加减的月份数，可为正负
	 * @return String 格式 2016-10
	 */
	public static String calculateMonth(Date date,int months){
		if(date==null){
			return null;
		}
		Date resultdDate=null;
		try {
			calendar.setTime(date); 
			calendar.add(Calendar.MONTH, months); 
			resultdDate = calendar.getTime(); 
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return yearMonthFormat.format(resultdDate);
	}
	/**
	 * 递增计算分钟
	 * @author:zhouyc
	 * @date:2016年10月28日
	 * @param startDate 要计算的日期,length 循环次数
	 * @return startDate 格式 2016-10-03 12:12
	 */
	public static List<String> calculateMinutes(String startDate,int length){
		if(StringUtils.isEmpty(startDate)||0>=length){
			System.out.println("************日期或长度length格式不正确******************");
			return null;
		}
		List<String> dateList=new ArrayList<String>();
		try {
			Date returnDate = dateMinutesFormat.parse(startDate);
			calendar.setTime(returnDate); 
			for(int i=1;i<=length;i++){
				dateList.add(dateMinutesFormat.format(calendar.getTime()));
				calendar.add(Calendar.MINUTE, 1); 
			}
			System.out.println(dateList.toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateList;
	}
	/**
	 * 日期格式化：获取指定日期格式的日期
	 * @author:zhouyc
	 * @date:2016年10月28日
	 * @param date 要格式化的日期   formatStr 指定日期格式
	 * @return String
	 */
	public static String getDateFormat(Date date,String formatStr){
		if(date==null ||formatStr==null){
			return null;
		}
		return new SimpleDateFormat(formatStr).format(date);
	}
	
	/**
	 * 日期格式化：获取指定日期格式的日期
	 * @author:zhouyc
	 * @date:2016年10月28日
	 * @param dateStr 要格式化的日期:2016-10-28 11:17:17   formatStr 指定日期格式:2016-10-28 11:17:17或20161028111717
	 * @return String
	 */
	public static String  parseDateFormat(String dateStr,String formatStr){
		if(dateStr==null ||formatStr==null||dateStr==""){
			return null;
		}
		Date parseDate=null;
		try {
			parseDate = new SimpleDateFormat(formatStr).parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return new SimpleDateFormat(formatStr).format(parseDate);
	}
	/**
	 * 日期格式化：根据传入的日期月份中的第一个    2016-10-10
	 * @author:zhouyc
	 * @date:2016年11月04日
	 * @param dateStr 要格式化的日期:2016-10-28 11:17:17   formatStr 指定日期格式:2016-10-28 11:17:17或20161028111717
	 * @return String
	 */
	public static String getFirstDayOfMonth(String dateStr){
		String dateStr2="";
		try {
			if(dateStr==""||dateStr==null){
				return null;
			}
			if(dateStr.length()<7){
				return "日期长度至少为7！";
			}
			dateStr2 = dateStr.substring(0, 7);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateStr2+"-01";
	}
	
	/**根据开始时间和结束时间返回时间段内的时间集合
	 * @author:wyx
	 * @date:2017年4月29日
	 * @param beginDate,endDate
	 * @return List<Date>
	 */
	public static List<String> getDatesBetweenTwoDate(String beginTime, String endTime) {  
		List<String> dateList=new ArrayList<String>();
		try {
			 Date returnDate = dateMinutesFormat.parse(beginTime);
				calendar.setTime(returnDate); 
				long length=(dateMinutesFormat.parse(endTime).getTime()-dateMinutesFormat.parse(beginTime).getTime())/60000;
				for(int i=1;i<=length+1;i++){
					dateList.add(dateMinutesFormat.format(calendar.getTime()));
					calendar.add(Calendar.MINUTE, 1); 
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateList;  
    }  
	public static long getMs(String date1,String date2){
		long ms=0L;
		try {
			calendar.setTime(dateTimeFormat.parse(date1));
			long date1Millis = calendar.getTimeInMillis();
			calendar.setTime(dateTimeFormat.parse(date2));
			long date2Millis = calendar.getTimeInMillis();
			ms=date1Millis-date2Millis;
			ms= Math.abs(ms);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ms;
	}
	public static String formatTime(String date1,String date2){
		String onlineTime="";
		
		try {
			long ms=getMs(date1,date2);
			int ss = 1000;  
            int mi = ss * 60;  
	        int hh = mi * 60;  
	        int dd = hh * 24;  
			long day = ms / dd;  
	        long hour = (ms - day * dd) / hh;  
	        long minute = (ms - day * dd - hour * hh) / mi;  
	        long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
	        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  

	        String strDay = day < 10 ? "0" + day : "" + day; //天  
	        String strHour = hour < 10 ? "0" + hour : "" + hour;//小时  
	        String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟  
	        String strSecond = second < 10 ? "0" + second : "" + second;//秒  
	        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒  
	        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
	        onlineTime=strHour+":"+strMinute+":"+strSecond;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return onlineTime;
	}
	
	public static String formatTime2(long ms){
		String onlineTime="";
		try {
			int ss = 1000;  
			int mi = ss * 60;  
			int hh = mi * 60;  
			int dd = hh * 24;  
			long day = ms / dd;  
			long hour = (ms - day * dd) / hh;  
			long minute = (ms - day * dd - hour * hh) / mi;  
			long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
			long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  

			String strDay = day < 10 ? "0" + day : "" + day; //天  
			String strHour = hour < 10 ? "0" + hour : "" + hour;//小时  
			String strMinute = minute < 10 ? "0" + minute : "" + minute;//分钟  
			String strSecond = second < 10 ? "0" + second : "" + second;//秒  
			String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;//毫秒  
			strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
			onlineTime=strHour+":"+strMinute+":"+strSecond;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return onlineTime;
	}
	public static void main(String[] args) {
		System.out.println(DateUtils.formatTime2(82714657L));
		System.out.println("2017-01-12 12:12:12".substring(11, 19));
		System.out.println(DateUtils.calculateDays(new Date(), -1));
//		System.out.println(formatTime("2017-06-01 18:12:33","2017-05-28 12:12:12"));
//		System.out.println(DateUtils.calculateHours(new Date(), -1));
	}
}