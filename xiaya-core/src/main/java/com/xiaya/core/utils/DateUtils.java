/**
 * B5mDateUtils.java
 *
 * 功  能：时间处理工具类
 * 类  名：B5mDateUtils
 *
 *   ver     变更日       公司      作者     变更内容
 * ────────────────────────────────────
 *   V1.00  '12-05-24  iZENEsoft    wiley.wang       初版
 *
 * Copyright (c) 2009 iZENEsoft Business Software corporation All Rights Reserved.
 * LICENSE INFORMATION
 */
package com.xiaya.core.utils;


import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间处理
 * 
 * @author Wiley
 * @version v1.0
 */
public class DateUtils implements Serializable {

	private static final long serialVersionUID = 6622278926579307357L;

	private static ThreadLocal<SimpleDateFormat> THREAD_SAFE_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>(){
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	public static String getToday() {
		// SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");//ComConstants.SDF_YYYYMMDDHHMMSS;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");// ComConstants.SDF_YYYYMMDDHHMMSS;
		return sdf.format(new Date());
	}

	public static String getIndexTime() {
		// SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");//ComConstants.SDF_YYYYMMDDHHMMSS;
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy/MM/dd");//ComConstants.SDF_YYYYMMDDHHMMSS;
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return sdf.format(cal.getTime());
	}

	public static String getDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// ComConstants.SDF_YYYYMMDDHHMMSS;
		return sdf.format(new Date());
	}

	/**
	 * 获取离截止时间的剩余时间
	 * 
	 * @param endDate
	 *            截止时间,格式yyyyMMddHHmmss
	 * @return
	 */
	public static String getRemainingTime(String endDate) {
		String rtn = "";
		if (StringUtils.isBlank(endDate))
			return rtn;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");// ComConstants.SDF_YYYYMMDDHHMMSS;//存在线程安全问题，故不能使用静态
		try {
			Date deadline = sdf.parse(endDate);
			long remaining = deadline.getTime() - System.currentTimeMillis();
			// long remaining=lngDeadline-System.currentTimeMillis();
			if (remaining > 0) {
//				int ms = (int) (remaining % 1000);
				remaining /= 1000;
//				int sc = (int) (remaining % 60);
				remaining /= 60;
				int mn = (int) (remaining % 60);
				remaining /= 60;
				int hr = (int) (remaining % 24);
				long dy = (int) remaining / 24;
				rtn = dy + "天" + hr + "小时" + mn + "分";// + sc + "秒";
			} else {
				rtn = "过期";
			}
		} catch (ParseException e) {

		}
		return rtn;
	}

	public static SimpleDateFormat getThreadSafeFormat() {
		return THREAD_SAFE_DATE_FORMAT.get();
	}
	
	public static Date parse(String dateStr) throws ParseException{
	    if(dateStr == null || StringUtils.isEmpty(dateStr)){
	       return null; 
	    }
	    DateFormat df = getThreadSafeFormat();
	    return df.parse(dateStr);
	}
	
	public static String format(Date date){
	    if(date == null){
	        return null;
	    }
	    DateFormat df = getThreadSafeFormat();
	    return df.format(date);
	}

    /**
     * 如果时间为空，获取当前系统时间那天的开始时间
     * @param time 解析的时间
     * @return 时间对象,如果参数为空，返还系统当天的开始时间。
     * @throws ParseException
     */
    public static Date parseIfBlank(String time) throws ParseException {
        if (StringUtils.isBlank(time)) {
            return getDayBeginTime();
        } else {
            return parse(time);
        }
    }

    public static Date getDayBeginTime(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date parseIfEmpty(String time) throws ParseException {
        if(StringUtils.isBlank(time)){
            return null;
        }
        return parse(time);
    }

    /**
     * 不抛异常，日期格式错误返回null的转换
     * @param time 时间.
     * @return date日期
     */
    public static Date parseQuietly(String time){
        if(StringUtils.isBlank(time)){
            return null;
        }
        try {
           return parse(time);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date addDate(Date now, int days) {
        Calendar calender = Calendar.getInstance();
        calender.setTime(now);
        calender.add(Calendar.DAY_OF_MONTH, days);
        return calender.getTime();
    }
    
    /**
     * 当前时间前几天
     * @param days 天数
     * @return
     */
    public static Date nowBefore(int days){
        Calendar calender = Calendar.getInstance();
        calender.add(Calendar.DAY_OF_MONTH, -days);
        return calender.getTime();
    }

    public static void main(String[] args) throws ParseException, DateParseException {

        String lastUpdateTime = DateUtils.format(addDate(new Date(), -30));
        System.err.println(lastUpdateTime);
        int duration = 15;
        String compareTime = DateUtils.format(addDate(new Date(), -duration));
        System.err.println(compareTime);
        System.err.println(DateUtils.parseIfBlank(compareTime).after(DateUtils.parseIfBlank(lastUpdateTime)));

        Date start = new Date();
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException ignored) {
        }
        Date end  = new Date();
        System.out.println(betweenSeconds(start, end));

        final Date startTime = parseIfBlank("2015-08-05 00:00:00");
        final Date endTime = parseIfBlank("2015-08-07 23:59:59");
        System.out.println(betweenNow(startTime, endTime));
    }

    public static Long betweenSeconds(Date startTime, Date endTime) {
        if (null == startTime || null == endTime) {
            return 0L;
        }
        long start = startTime.getTime();
        long end = endTime.getTime();
        return ((end - start) / 1000);
    }

    public static boolean betweenNow(Date startTime, Date endTime) {
        return between(new Date(System.currentTimeMillis()), startTime, endTime);
    }

    public static boolean between(Date date, Date startTime, Date endTime) {
        long time;
        if (null == date) {
            time = System.currentTimeMillis();
        } else {
            time = date.getTime();
        }
        return null != startTime && null != endTime && time >= startTime.getTime() && time <= endTime.getTime();
    }

}
