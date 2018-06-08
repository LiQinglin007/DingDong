package com.li.xiaomi.xiaomilibrary.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jax on 16/1/25.
 */
public class DateUtils {
    public static Date date = null;
    public static DateFormat dateFormat = null;
    public static Calendar calendar = null;

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            dateFormat = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]",
                        "0");
            }
            date = (Date) dateFormat.parse(dt);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 时间戳转换成时间
     *
     * @param timeStamp
     * @return
     */
    public static Date LogToDate(long timeStamp) {
        return new Date(Long.parseLong(String.valueOf(timeStamp)));
    }

    /**
     * 拿到指定的天
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static Date getDate(int year, int month, int day) {
        Date endDate = new Date();
        if (year < 1900) {
            year = 1900;
            month = 1;
            day = 1;
        }
        if (month > 12) {
            month = 12;
            day = 1;
        }
        // 添加大小月月份并将其转换为list,方便之后的判断
        String[] months_big = {"1", "3", "5", "7", "8", "10", "12"};
        String[] months_little = {"4", "6", "9", "11"};

        List list_big = Arrays.asList(months_big);
        List list_little = Arrays.asList(months_little);
        if (list_big.contains(month + "")) {
            day = day > 31 ? 31 : day;
        } else if (list_little.contains(month + "")) {
            day = day > 30 ? 30 : day;
        } else {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {//闰年
                day = day > 29 ? 29 : day;
            } else {//平年
                day = day > 28 ? 28 : day;
            }
        }
        endDate.setYear(year - 1900);
        endDate.setMonth(month - 1);
        endDate.setDate(day);
        return endDate;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy/MM/dd");
    }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String formatYM(Date date) {
        return format(date, "yyyy-MM");
    }


    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy/MM/dd 格式
     */
    public static String getDate(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：取得指定月份的第一天
     *
     * @param strdate String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static String getMonthBegin(String strdate) {
        date = parseDate(strdate);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 功能描述：取得指定月份的最后一天
     *
     * @param strdate String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String getMonthEnd(String strdate) {
        date = parseDate(getMonthBegin(strdate));
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取某天是星期几
     *
     * @param date
     * @return
     */
    public static String getMonthDayWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);    //获取年
        int month = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        int day = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int week = c.get(Calendar.DAY_OF_WEEK);

        String weekStr = null;

        switch (week) {

            case Calendar.SUNDAY:
                weekStr = "周日";
                break;

            case Calendar.MONDAY:
                weekStr = "周一";
                break;

            case Calendar.TUESDAY:
                weekStr = "周二";
                break;

            case Calendar.WEDNESDAY:
                weekStr = "周三";
                break;

            case Calendar.THURSDAY:
                weekStr = "周四";
                break;

            case Calendar.FRIDAY:
                weekStr = "周五";
                break;

            case Calendar.SATURDAY:
                weekStr = "周六";
                break;
        }

        return month + "月" + day + "日" + "(" + weekStr + ")";
    }


    public static int getMonthDayWeekNmuber() {
        Calendar c = Calendar.getInstance();
        int week = c.get(Calendar.DAY_OF_WEEK);
        int weekNumber = 1;
        switch (week) {
            case Calendar.MONDAY:
                weekNumber = 1;
                break;
            case Calendar.TUESDAY:
                weekNumber = 2;
                break;
            case Calendar.WEDNESDAY:
                weekNumber = 3;
                break;
            case Calendar.THURSDAY:
                weekNumber = 4;
                break;
            case Calendar.FRIDAY:
                weekNumber = 5;
                break;
            case Calendar.SATURDAY:
                weekNumber = 6;
                break;
            case Calendar.SUNDAY:
                weekNumber = 7;
                break;
        }
        return weekNumber;
    }

    /**
     * 日期字符串转换为日期
     *
     * @param date    日期字符串
     * @param pattern 格式
     * @return 日期
     */
    public static Date formatStringByFormat(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期字符串转换为日期
     *
     * @param date    日期字符串
     * @param pattern 格式
     * @return 日期
     */
    public static String reformatTime(String date, String pattern) {
        String fmt = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simple = new SimpleDateFormat(pattern);
        Date old = parseToDate(date, fmt);
        return simple.format(old);
    }

    /**
     * 字符串转换成日期.
     *
     * @param dateString 日期字符
     * @param pattern    格式化.
     * @return
     */
    public static Date parseToDate(String dateString, String pattern) {
        if (pattern == null || "".equals(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {

        }
        return new Date();
    }

    public static String getTimeInterval(Date d) {
        String date = format(d, "yyyy-MM-dd HH:mm:ss");
        return getTimeInterval(date);
    }


    /**
     * 获得口头时间字符串，如今天，昨天等
     *
     * @param d 时间格式为yyyy-MM-dd HH:mm:ss
     * @return 口头时间字符串
     */
    public static String getTimeInterval(String d) {
        Date date = formatStringByFormat(d, "yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH);
        int nowWeek = now.get(Calendar.WEEK_OF_MONTH);
        int nowDay = now.get(Calendar.DAY_OF_WEEK);
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int nowMinute = now.get(Calendar.MINUTE);

        Calendar ca = Calendar.getInstance();
        if (date != null)
            ca.setTime(date);
        else
            ca.setTime(new Date());
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH);
        int week = ca.get(Calendar.WEEK_OF_MONTH);
        int day = ca.get(Calendar.DAY_OF_WEEK);
        int hour = ca.get(Calendar.HOUR_OF_DAY);
        int minute = ca.get(Calendar.MINUTE);
        if (year != nowYear) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //不同年份
            return sdf.format(date);
        } else {
            if (month != nowMonth) {
                //不同月份
                SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
                return sdf.format(date);
            } else {
                if (week != nowWeek) {
                    //不同周
                    SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
                    return sdf.format(date);
                } else if (day != nowDay) {
                    if (day + 1 == nowDay) {
                        return "昨天" + formatDateByFormat(date, "HH:mm");
                    }
                    if (day + 2 == nowDay) {
                        return "前天" + formatDateByFormat(date, "HH:mm");
                    }
                    //不同天
                    SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
                    return sdf.format(date);
                } else {
                    //同一天
                    int hourGap = nowHour - hour;
                    if (hourGap == 0)//1小时内
                    {
                        if (nowMinute - minute < 1) {
                            return "刚刚";
                        } else {
                            return (nowMinute - minute) + "分钟前";
                        }
                    } else if (hourGap >= 1 && hourGap <= 12) {
                        return hourGap + "小时前";
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("今天 HH:mm");
                        return sdf.format(date);
                    }
                }
            }
        }
    }

    /**
     * 时间戳转化时间
     *
     * @param time
     * @return
     */
    public static String LongToStr(Long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd ");
        String StrTime = format.format(time);
        return StrTime;
    }

    /**
     * 时间戳转化时间
     *
     * @param time         时间戳
     * @param formatString 转化规则
     * @return
     */
    public static String LongToStr(Long time, String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString);
        String StrTime = format.format(time);
        return StrTime;
    }

    /**
     * 前一天
     *
     * @param date
     * @return
     */
    public static Date getAgoDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 后一天
     *
     * @param date
     * @return
     */
    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 下下天
     *
     * @param date
     * @return
     */
    public static Date getSecondDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        date = calendar.getTime();
        return date;
    }

    /**
     * 下下下天
     *
     * @param date
     * @return
     */
    public static Date getThirdDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        date = calendar.getTime();
        return date;
    }
}
