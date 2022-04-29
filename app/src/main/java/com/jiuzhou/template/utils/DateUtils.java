package com.jiuzhou.template.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
     * String 转时间戳
     */
    public static long StringToTimeMillisLong(String dateString) {
        Date date = null;
        try {
            date = datetimeFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String currentDateTime() {
        return datetimeFormat.format(new Date());
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String currentDate() {
        return dateFormat.format(new Date());
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
     * String时间格式转日期格式yyyy-MM-dd
     */
    public static String formatStrDate(String dateString) {
        return dateFormat.format(StringDate(dateString));
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

    public static String turnDayHourMinuteString(int minute) {
        //如果传入的分钟是0，默认24小时
        if (0 == minute) {
            return 24 + "小时";
        }
        //如果分钟小于60，默认返回分钟
        if (0 < minute && minute < 60) {
            return minute + "分钟";
        }
        //如果分钟小于24小时（1440分钟），返回小时和分钟
        if (60 <= minute && minute < 1440) {

            if (minute % 60 == 0) {
                int h = minute / 60;
                return h + "小时";
            } else {
                int h = minute / 60;
                int m = minute % 60;
                return h + "小时" + m + "分钟";
            }

        }
        //如果分钟大于1天
        if (minute >= 1440) {

            int d = minute / 60 / 24;
            int h = minute / 60 % 24;
            int m = minute % 60;
            String s1 = null;
            if (d > 0) {
                s1 = d + "天";
            }
            //h如果计算大于等于1再展示，否则只展示天和分钟
            if (h >= 1) {
                s1 += h + "小时";
            }
            if (m > 0) {
                s1 += m + "分钟";
            }

            return s1;
        }
        return null;
    }
}
