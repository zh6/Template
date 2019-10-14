package com.jiuzhou.template.utils;
import android.annotation.SuppressLint;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class DateUtils {
    private static SimpleDateFormat simpleDateFormat;

    /**
     * long类型转换为String类型
     *
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    /**
     * date类型转换为String类型
     *
     * @param data
     * @param formatType
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * String转换为Date类型
     *
     * @param strTime
     * @param formatType
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }

    /**
     * date要转换的date类型的时间
     *
     * @param date
     * @return
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }

    /**
     * 获取当前时间
     *
     * @param formatType
     * @return
     */
    public static String currentTime(String formatType) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getNowMDHMSTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "MM-dd HH:mm:ss");
        String date = formatter.format(new Date());
        return date;
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
     * 获取指定日期数组
     *
     * @param distributionToday 是否包含今天
     * @param num               增加天数
     * @return
     */
    public static List getTomorrowList(int distributionToday, int num) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        List list = new ArrayList();
        if (distributionToday == 1) {
            list.add(sf.format(c.getTime()));
        }
        for (int i = 0; i < num; i++) {
            c.add(Calendar.DAY_OF_MONTH, 1);
            list.add(sf.format(c.getTime()));
        }
        return list;
    }

    /**
     * 获取间隔后的时间
     *
     * @param num +往后推几天，-往前推几天
     * @return
     */
    public static String getIntervalDateTime(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, num);//往上推一天  30推三十天  365推一年
        String mBefore = sdf.format(calendar.getTime());
        return mBefore;
    }

    /**
     * 获取间隔后的短时间
     *
     * @param num +往后推几天，-往前推几天
     * @return
     */
    public static String getIntervalDate(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBefore = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, num);//往上推一天  30推三十天  365推一年
        String mBefore = sdf.format(calendar.getTime());
        return mBefore;
    }

    /**
     * 把yyyy-MM-dd HH:mm转成yyyy-MM-dd HH:mm:ss
     *
     * @param dateStr
     * @return
     */
    public static String StringDate(String dateStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(dateStr, pos);
        String date = dateToString(strtodate, "yyyy-MM-dd HH:mm:ss");
        return date;
    }

    /**
     * 获取当月第一天
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String first = simpleDateFormat.format(c.getTime());
        return first;
    }

    /**
     * 获取当月最后一天
     *
     * @return
     */
    public static String getLastDayOfMonth() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = simpleDateFormat.format(ca.getTime());
        return last;
    }

    /**
     * 取得当前日期所在周的第一天
     *
     * @return
     */
    public static String getFirstDayOfWeek() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar ca = Calendar.getInstance();
        // 将每周第一天设为星期一，默认是星期天
        ca.setFirstDayOfWeek(Calendar.MONDAY);
        ca.add(Calendar.DATE, 1);
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        String first = simpleDateFormat.format(ca.getTime());
        return first;
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @return
     */
    public static String getLastDayOfWeek() {
        Calendar ca = Calendar.getInstance();
        //将每周第一天设为星期一，默认是星期天
        ca.setFirstDayOfWeek(Calendar.MONDAY);
        ca.add(Calendar.DATE, 1);
        ca.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        String last = simpleDateFormat.format(ca.getTime());
        return last;
    }

    /**
     * 获取当前时期加星期
     *
     * @return
     */
    public static String StringDateWeek() {
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份
        String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
        String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
        String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if ("1".equals(mWay)) {
            mWay = "天";
        } else if ("2".equals(mWay)) {
            mWay = "一";
        } else if ("3".equals(mWay)) {
            mWay = "二";
        } else if ("4".equals(mWay)) {
            mWay = "三";
        } else if ("5".equals(mWay)) {
            mWay = "四";
        } else if ("6".equals(mWay)) {
            mWay = "五";
        } else if ("7".equals(mWay)) {
            mWay = "六";
        }
        return mYear + "年" + mMonth + "月" + mDay + "日" + "   星期" + mWay;
    }
    /**
     * 转化为XXXX-XX-XX格式
     * @param year
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static String getIntDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return DateFormat.format("yyyy-MM-dd", calendar).toString();
    }
}
