package com.zh.template.utils;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    private static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat(
            "HH:mm:ss");

    /**
     * 日期字符串转date
     *
     * @param dateString
     * @return
     */
    public static Date StringDate(String dateString) {
        Date date = null;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 日期时间字符串转date
     *
     * @param dateString
     * @return
     */
    public static Date StringDateTime(String dateString) {
        Date date = null;
        try {
            date = datetimeFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获得当前时间戳
     */
    public static long currentTimeMillisLong() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前日期
     * @return
     */
    public static String currentDateTime() {
        return  datetimeFormat.format(new Date());
    }
    /**
     * 获得当前时间戳
     */
    public static String currentTimeMillisString() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 时间戳转date
     */
    public static Date timeMillisToDate(long timeMillis) {
        return new Date(timeMillis);
    }

    /**
     * 时间戳转date
     */
    public static Date timeMillisToDate(String timeMillis) {
        return new Date(Long.parseLong(timeMillis));
    }

    /**
     * 时间戳转对应格式的日期
     */
    public static String formatDate(long timeMillisLong, SimpleDateFormat sdf) {
        return sdf.format(new Date(timeMillisLong));
    }

    /**
     * 时间戳转对应格式的日期
     */
    public static String formatDate(String timeMillisLong, SimpleDateFormat sdf) {
        return sdf.format(new Date(Long.parseLong(timeMillisLong)));
    }

    /**
     * 日期格式yyyy-MM-dd HH:mm:ss
     */
    public static String formatDatetime(String timeMillisLong) {
        return datetimeFormat.format(new Date(Long.parseLong(timeMillisLong)));
    }

    /**
     * 日期格式yyyy-MM-dd HH:mm:ss
     */
    public static String formatDatetime(Long timeMillisLong) {
        return datetimeFormat.format(new Date(timeMillisLong));
    }

    /**
     * 日期格式yyyy-MM-dd
     */
    public static String formatDate(String timeMillisLong) {
        return dateFormat.format(new Date(Long.parseLong(timeMillisLong)));
    }

    /**
     * 日期格式yyyy-MM-dd
     */
    public static String formatDate(Long timeMillisLong) {
        return dateFormat.format(new Date(timeMillisLong));
    }

    /**
     * 日期格式HH:mm:ss
     */
    public static String formatTime(String timeMillisLong) {
        return timeFormat.format(new Date(Long.parseLong(timeMillisLong)));
    }

    /**
     * 日期格式HH:mm:ss
     */
    public static String formatTime(Long timeMillisLong) {
        return timeFormat.format(new Date(timeMillisLong));
    }

    /**
     * 判断原日期是否在目标日期之前
     *
     * @param src 原日期
     * @param dst 目标日期
     */
    public static boolean isBefore(Date src, Date dst) {
        return src.before(dst);
    }

    /**
     * 判断原日期是否在目标日期之后
     *
     * @param src 原日期
     * @param dst 目标日期
     */
    public static boolean isAfter(Date src, Date dst) {
        return src.after(dst);
    }

    /**
     * 判断两日期是否相同
     */
    public static boolean isEqual(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    /**
     * 判断某个日期是否在某个日期范围
     *
     * @param beginDate 日期范围开始
     * @param endDate   日期范围结束
     * @param src       需要判断的日期
     */
    public static boolean between(Date beginDate, Date endDate, Date src) {
        return beginDate.before(src) && endDate.after(src);
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long between_days = 0;
        try {
            cal.setTime(sdf.parse(smdate));
            long time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            long time2 = cal.getTimeInMillis();
            between_days = (time2 - time1) / (1000 * 3600 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        int day = -1;
        if (smdate != null && bdate != null) {

            Calendar aCalendar = Calendar.getInstance();

            aCalendar.setTime(smdate);

            int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);

            aCalendar.setTime(bdate);

            int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);

            day = day2 - day1;

        }
        return day;
    }
    /**
     * 为日期增加毫秒
     */
    public static Date DateAdd(Date date, long times) {
        Date newDate = null;
        if (date != null) {
            long dateOfTimes = date.getTime();
            long calculateTimes = dateOfTimes + times;
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(calculateTimes);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 为日期添加年(year传负数则为减去年)
     */
    public static Date DateAddYear(Date date, int year) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.YEAR, year);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 为日期添加月(month传负数则为减去月)
     */
    public static Date DateAddMonth(Date date, int month) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MONTH, month);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 为日期添加日(day传负数则为减去日)
     */
    public static Date DateAddDay(Date date, int day) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, day);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 为日期添加小时(day传负数则为减去小时)
     */
    public static Date DateAddHour(Date date, int hour) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, hour);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 为日期添加分钟(day传负数则为减去分钟)
     */
    public static Date DateAddMinute(Date date, int minute) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, minute);
            newDate = calendar.getTime();
        }
        return newDate;
    }

    /**
     * 为日期添加分钟(day传负数则为减去分钟)
     */
    public static Date DateAddSecond(Date date, int second) {
        Date newDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.SECOND, second);
            newDate = calendar.getTime();
        }
        return newDate;
    }
}
