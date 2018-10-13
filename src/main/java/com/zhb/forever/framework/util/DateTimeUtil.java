package com.zhb.forever.framework.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang3.time.DateUtils;

public class DateTimeUtil {

    public static final String TODAY_FORMAT = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
    
    public static float subtract(Date start, Date end){
        long l = end.getTime()-start.getTime();
        double time =  (double)l / (60*60*1000);
        //设置位数  
        int   scale  =   2;
        //表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
        int   roundingMode  =  4;
        BigDecimal   bd  =   new  BigDecimal(time);  
        bd   =  bd.setScale(scale,roundingMode);  
        return bd.floatValue();
    }
    
    /**
     * *  获取日期时间字符串,例如： yyyy-MM-dd HH:mm:ss
     * @param calendar
     * @param format
     * @return
     */
    public static String getDateTime(Calendar calendar,String format){
        if (null == calendar ) {
            return null;
        }
        if (StringUtil.isBlank(format)) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        String dateTime = df.format(calendar.getTime());
        return dateTime;
    }
    
    /**
     ** 获取日期时间字符串,例如： yyyy-MM-dd HH:mm:ss
     * @param date  format
     * @return
     */
    public static String getDateTime(Date date,String format){
        if (null == date ) {
            return null;
        }
        if (StringUtil.isBlank(format)) {
            format = " yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        String dateTime = df.format(date);
        return dateTime;
    }
    
    /**
     ** 字符串转date,例如： yyyy-MM-dd HH:mm:ss
     * @param date  format
     * @return
     */
    public static Date parseDate(String date,String format) throws ParseException{
        if (StringUtil.isBlank(date) ) {
            return null;
        }
        if (StringUtil.isBlank(format)) {
            format = " yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date now = df.parse(date);
        return now;
    }
    
    /**
     ** 字符串转Calendar,例如： yyyy-MM-dd HH:mm:ss
     * @param date  format
     * @return
     */
    public static Calendar parseCalendar(String date,String format) throws ParseException{
        if (StringUtil.isBlank(date) ) {
            return null;
        }
        if (StringUtil.isBlank(format)) {
            format = " yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date now = df.parse(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        return calendar;
    }
    

    /**
     * *判断两个日期是否是同一天（不比较时分秒）
     * @param d1  d2
     * @return
     */
    public static boolean sameDate(Date d1, Date d2){       
        return DateUtils.isSameDay(d1, d2);
    }
    
    /**
     * *获取星期几
     * @param date
     * @return
     * @throws ParseException 
     */
    public static String getDayOfWeek(String date) throws ParseException{
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        Date day = parseDate(date, "yyyy-MM-dd");
        calendar.setTime(day);
        return weekDays[calendar.get(Calendar.DAY_OF_WEEK)-1];
    }
    
    /**
     ** 转换 Wed Aug 01 2018 00:00:00 GMT+0800 (中国标准时间) 为指定的日期时间格式
     * @param gmtTime  format
     * @return
     * @throws ParseException 
     */
    public static Calendar formatGMT(String gmtTime,String format) throws ParseException{
        if (StringUtil.isBlank(gmtTime)) {
            return null;
        }
        gmtTime = gmtTime.replace("GMT", "").replaceAll("\\(.*\\)", "");
        SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z",Locale.ENGLISH);
        Date dateTrans = simpleDateFormat.parse(gmtTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateTrans);
        return calendar;
    }
    
    /**
     * *日期增加一天,不改变原来的date
     * @param date
     * @return
     */
    public static Calendar addDateOneDay(Calendar calendar) {  
        if (null == calendar) {  
            return calendar;  
        }  
        calendar.add(Calendar.DATE, 1); //日期加1天  
        return calendar;  
    } 
    
    /**
     * *获取年份
     * @param date
     * @return
     */
    public static String getYear(Calendar calendar){
        if (null == calendar) {
            return null;
        }
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

}
