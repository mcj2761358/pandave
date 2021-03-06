package com.minutch.fox.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Minutch on 15/6/13.
 */
public class DateUtils {
    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String Y_M_D_HM = "yyyy-MM-dd HH:mm";
    public static final String Y_M_D_HMS = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD = "yyyyMMdd";
    public static final String YM = "yyyyMM";
    public static final String D = "dd";
    public static final String YMDHM = "yyyyMMddHHmm";
    public static final String YMDHMS = "yyyyMMddHHmmss";
    public static final String ymd = "yyyy/MM/dd";
    public static final String ymd_HM = "yyyy/MM/dd HH:mm";
    public static final String ymd_HMS = "yyyy/MM/dd HH:mm:ss";
    public static final String YYMMDD = "yyMMdd";
    
    public final static Long MONTH_MILLIS = 30*24*3600*1000L;
    
    public final static Long DAY_MILLIS = 24*3600*1000L;
    
    public final static String DAY_FIRST_TIME = " 00:00:00";
    
    public final static String DAY_LAST_TIME = " 23:59:59";

    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    
    /**
     * 获取当月第一天
     * @return
     */
    public static Date getCurMonthFirstDay(){
        Calendar cal=Calendar.getInstance();//获取当前日期 
        cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        return cal.getTime();
       
       
    }
    
    /**
     * 获取当月最后一天
     * @return
     */
    public static Date getCurMonthLastDay(){
        Calendar cal = Calendar.getInstance();   
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));  
        return cal.getTime();
    }
    
    /**
     * 
     * @param d1
     * @param d2
     * @return 1 d1比d2多1天
     *         0 d1与d2同天
     *         －1 d1比d2少1天
     */
    public static Long compareDays(Date d1,Date d2){
        Long days1 = d1.getTime()/DAY_MILLIS;
        Long days2 = d2.getTime()/DAY_MILLIS;
        return days1 - days2;
    }

    public  static String dateFormat(Date date,String format) {
        if (date == null || format == null) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }
    
    public  static Date formatToData(String str,String format) {
        if (StringUtils.isBlank(str) || StringUtils.isBlank(format)) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
           return null;
        }
    }

    /**
     *
     *
     * @param date
     * @return
     */
    public static String smartFormat(Date date) {
        String dateStr = null;
        if (date == null) {
            dateStr = "";
        } else {
            try {
                dateStr = formatDate(date, Y_M_D_HMS);
                //时分秒
                if (dateStr.endsWith(" 00:00:00")) {
                    dateStr = dateStr.substring(0, 10);
                }
                //时分
                else if (dateStr.endsWith("00:00")) {
                    dateStr = dateStr.substring(0, 16);
                }
                //秒
                else if (dateStr.endsWith(":00")) {
                    dateStr = dateStr.substring(0, 16);
                }
            } catch (Exception ex) {
                throw new IllegalArgumentException("转换日期失败: " + ex.getMessage(), ex);
            }
        }
        return dateStr;
    }

    /**
     *
     *
     * @param text
     * @return
     */
    public static Date smartFormat(String text) {
        Date date = null;
        try {
            if (text == null || text.length() == 0) {
                date = null;
            } else if (text.length() == 10) {
                date = formatStringToDate(text, Y_M_D);
            } else if (text.length() == 13) {
                date = new Date(Long.parseLong(text));
            } else if (text.length() == 16) {
                date = formatStringToDate(text, Y_M_D_HM);
            } else if (text.length() == 19) {
                date = formatStringToDate(text, Y_M_D_HMS);
            } else {
                throw new IllegalArgumentException("日期长度不符合要求!");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("日期转换失败!");
        }
        return date;
    }

    /**
     * 获取当前日期
     * @param format
     * @return
     * @throws Exception
     */
    public static String getNow(String format) throws Exception{
        return formatDate(new Date(), format);
    }

    /**
     * 格式化日期格式
     *
     * @param argDate
     * @param argFormat
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date argDate, String argFormat) {
        if (argDate == null) {
            throw new RuntimeException("参数[日期]不能为空!");
        }
        if (StringUtils.isEmpty(argFormat)) {
            argFormat = Y_M_D;
        }
        SimpleDateFormat sdfFrom = new SimpleDateFormat(argFormat);
        return sdfFrom.format(argDate).toString();
    }

    /**
     * 把字符串格式化成日期
     *
     * @param argDateStr
     * @param argFormat
     * @return
     */
    public static Date formatStringToDate(String argDateStr, String argFormat) throws Exception {
        if (argDateStr == null || argDateStr.trim().length() < 1) {
            throw new Exception("参数[日期]不能为空!");
        }
        String strFormat = argFormat;
        if (StringUtils.isEmpty(strFormat)) {
            strFormat = Y_M_D;
            if (argDateStr.length() > 16) {
                strFormat = Y_M_D_HMS;
            } else if (argDateStr.length() > 10) {
                strFormat = Y_M_D_HM;
            }
        }
        SimpleDateFormat sdfFormat = new SimpleDateFormat(strFormat);
        //严格模式
        sdfFormat.setLenient(false);
        try {
            return sdfFormat.parse(argDateStr);
        } catch (ParseException e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取当日最晚时间的时间戳
     * @return
     */
    public static Long getTodayLastTimestamp(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = new Date(calendar.getTimeInMillis());

        try {
            String tomorrowStr = formatDate(tomorrow, Y_M_D);
            tomorrowStr += " 00:00:00";
            Date resultDate = formatStringToDate(tomorrowStr,Y_M_D_HMS);
            return resultDate.getTime()/1000;
        } catch (Exception e) {
            logger.error("getTodayLastTimestamp["+tomorrow+"]",e);
        }


        return calendar.getTimeInMillis();
    }



    public static Date convertStringToDate(String dateStr) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return f.parse(dateStr);
        } catch (Exception e) {
            logger.error("转化为时间失败",e);
        }
        return null;
    }
    /**
     * {date}日期的{days}天后
     * @param date
     * @param days
     * @return
     */
    public static Date afterNDays(Date date,int days) {

        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE,days);
        Date resultDate = c.getTime();
        return resultDate;
    }
    /**
     * {date}日期的{days}天后
     * @param dateStr 格式(yyyy-MM-DD HH:mm:ss)
     * @param days
     * @return
     */
    public static Date afterNDays(String dateStr,int days) {

        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        Date date = convertStringToDate(dateStr);
        Date resultDate = afterNDays(date,days);
        return resultDate;
    }

    /**
     * {date}日期的{days}天前
     * @param date
     * @param days
     * @return
     */
    public static Date lastNDays(Date date,int days) {
        return afterNDays(date,-days);
    }

    /**
     * {date}日期的{days}天前
     * @param dateStr
     * @param days
     * @return
     */
    public static Date lastNDays(String dateStr,int days) {
        return afterNDays(dateStr,-days);
    }

    private static Date getMonthEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (-index));
        return calendar.getTime();
    }


    public static void main(String[] args) {
       System.out.println(getMonthEnd(new Date()));
    }
}
