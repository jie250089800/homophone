package com.yehuijie.homophone.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yehuijie.homophone.constants.TimeFormat;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.time.Year.isLeap;
import static okhttp3.internal.http.HttpDate.parse;

/**
 * 时间工具类
 * Created by cent on 2018/3/7.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TimeUtil {


    HOUR(0, "hour"), DAY(1, "day"),MONTH(2, "month");
    private int value;
    private String description;

    TimeUtil(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    public static boolean valid(int value) {
        return Arrays.stream(values()).anyMatch(item -> item.value == value);
    }

    public static void validOrElseThrow(int value, RuntimeException e) {
        if (!valid(value)) {
            throw e;
        }
    }

    public static TimeUtil getByCode(Integer value) {
        if (BlankUtil.isEmpty(value)) {
            return null;
        }
        Optional<TimeUtil> optional = Arrays.asList(TimeUtil.values())
                .stream()
                .filter(type -> type.getValue() == value)
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    public static TimeUtil getByName(String value) {
        if (BlankUtil.isEmpty(value)) {
            return null;
        }
        Optional<TimeUtil> optional = Arrays.asList(TimeUtil.values())
                .stream()
                .filter(type -> type.getDescription() == value)
                .findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }



    /**
     * 获取当前时间的时间戳
     */
    public static long getTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取某种时间格式的时间
     */
    public static String getCurrentDateTime(String format) {
        Date date = new Date();
        SimpleDateFormat fm = new SimpleDateFormat(format);
        String dm = fm.format(date);
        return dm;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     *
     * @param strDate
     * @return
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        return strtodate;
    }

    /**
     * 格式化日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate(Date date, String format) {
        if (BlankUtil.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat dtf = new SimpleDateFormat(format);
        return dtf.format(date);
    }

    /**
     * 日期字符串转化为日期类型
     *
     * @param date
     * @param format
     * @return
     */
    public static Date parseDate(String date, String format) throws ParseException {
        if (BlankUtil.isEmpty(date) || BlankUtil.isEmpty(format)) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.parse(date);
    }

    /**
     * 字符串转成时间戳
     */
    public static int strToTimestamp(String datetime) {
        Date dt = null;
        int time = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(TimeFormat.YYYYMMDDHHMMSS);
            dt = sdf.parse(datetime);
            long times = dt.getTime();
            time = (int) (times / 1000);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 加时间
     */
    public static String addTime(String datetime, int interval) {
        SimpleDateFormat sdf = new SimpleDateFormat(TimeFormat.YYYYMMDDHHMMSS);
        try {
            Date now = sdf.parse(datetime);
            long time = interval * 1000;//30分钟
            Date afterDate = new Date(now.getTime() + time);//30分钟后的时间
            return sdf.format(afterDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取与当前时间相隔days的日期
     *
     * @param days
     * @return
     */
    public static Date getCurrentDateAfterDay(int days) {
        Calendar calendar = new Calendar.Builder().setInstant(System.currentTimeMillis()).build();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * 加天数
     *
     * @param days
     * @return
     */
    public static Calendar addDay(Calendar date, int days) {
        Calendar newDate = (Calendar) date.clone();
        newDate.add(Calendar.DAY_OF_MONTH, days);
        return newDate;
    }

    /**
     * 加秒
     *
     * @param seconds
     * @return
     */
    public static Calendar addSecond(Calendar date, int seconds) {
        Calendar newDate = (Calendar) date.clone();
        newDate.add(Calendar.SECOND, seconds);
        return newDate;
    }

    /**
     * 加分钟
     *
     * @param mins
     * @return
     */
    public static Calendar addMin(Calendar date, int mins) {
        Calendar newDate = (Calendar) date.clone();
        newDate.add(Calendar.MINUTE, mins);
        return newDate;
    }

    /**
     * 加小时
     *
     * @param hours
     * @return
     */
    public static Calendar addHour(Calendar date, int hours) {
        Calendar newDate = (Calendar) date.clone();
        newDate.add(Calendar.HOUR_OF_DAY, hours);
        return newDate;
    }

    /**
     * 加月份
     *
     * @param months
     * @return
     */
    public static Calendar addMon(Calendar date, int months) {
        Calendar newDate = (Calendar) date.clone();
        newDate.add(Calendar.MONTH, months);
        return newDate;
    }

    /**
     * 日期加减
     *
     * @param date
     * @param field  需加减的日期属性：对应Calendar的日期属性值
     * @param amount
     * @return
     */
    public static Date add(Date date, int field, int amount) {
        Calendar calendar = new Calendar.Builder().setInstant(date).build();
        calendar.add(field, amount);
        return calendar.getTime();
    }

    /**
     * 获取与当前时间
     *
     * @return
     */
    public static Date getCurrentDate() {
        Calendar calendar = new Calendar.Builder().setInstant(System.currentTimeMillis()).build();
        return calendar.getTime();
    }

    /**
     * 获取与当前时间
     *
     * @return
     */
    public static Calendar getCurrentCalendar() {
        Calendar calendar = new Calendar.Builder().setInstant(System.currentTimeMillis()).build();
        return calendar;
    }

    /**
     * 获取与当前时间相隔days的日期
     *
     * @param minutes
     * @return
     */
    public static Date getCurrentDateAfterMin(int minutes) {
        Calendar calendar = new Calendar.Builder().setInstant(System.currentTimeMillis()).build();
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 字符串转日期类型
     *
     * @param datestr
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date str2Date(String datestr, String pattern) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(datestr);
    }

    /**
     * 日期字符串转化为日历对象
     *
     * @param dateString
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Calendar str2Calendar(String dateString, String pattern) throws ParseException {
        Date date = TimeUtil.str2Date(dateString, pattern);
        Calendar calendar = new Calendar.Builder().setInstant(date.getTime()).build();
        return calendar;
    }

    /**
     * 获取间隔的月数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getRangeMonth(Calendar beginDate, Calendar endDate) {
        int beginYear = beginDate.get(Calendar.YEAR);
        int endYear = endDate.get(Calendar.YEAR);
        int beginMonth = beginDate.get(Calendar.MONTH);
        int endMonth = endDate.get(Calendar.MONTH);
        long months = 0l;
        if (beginYear < endYear) {
            months = 12 * (endYear - beginYear - 1);
            months = months + (12 - beginMonth + 1) + endMonth;
        } else if (beginYear == endYear) {
            months = endMonth - beginMonth + 1;
        } else {
            months = -1;
        }
        return months;
    }

    /**
     * 获取时间间隔天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getRangeDays(Calendar beginDate, Calendar endDate) {
        if (BlankUtil.isEmpty(beginDate)
                || BlankUtil.isEmpty(endDate)) {
            return 0;
        }
        return (endDate.getTimeInMillis() - beginDate.getTimeInMillis()) / 1000 / 60 / 60 / 24;
    }

    /**
     * 获取时间间隔天数(不足一天计一天)
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Integer getRangeDaysCeil(Calendar beginDate, Calendar endDate) {
        if (BlankUtil.isEmpty(beginDate)
                || BlankUtil.isEmpty(endDate)) {
            return 0;
        }
        Double days = (endDate.getTimeInMillis() - beginDate.getTimeInMillis()) / 1000d / 60 / 60d / 24d;
        return ((Double) Math.ceil(days)).intValue();
    }

    /**
     * 获取时间间隔天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Integer getRangeYears(Calendar beginDate, Calendar endDate) {
        if (BlankUtil.isEmpty(beginDate)
                || BlankUtil.isEmpty(endDate)) {
            return 0;
        }

        return (beginDate.get(Calendar.YEAR) - beginDate.get(Calendar.YEAR)) + 1;
    }

    /**
     * 获取时间间隔天数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Integer getRangeMonths(Calendar beginDate, Calendar endDate) {
        if (BlankUtil.isEmpty(beginDate)
                || BlankUtil.isEmpty(endDate)) {
            return 0;
        }

        int beginMon = beginDate.get(Calendar.YEAR) * 100 + beginDate.get(Calendar.MONTH);
        int endMon = endDate.get(Calendar.YEAR) * 100 + endDate.get(Calendar.MONTH);

        return (endMon - beginMon) + 1;
    }

    /**
     * 获取时间间隔小时数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static Long getRangeHours(Calendar beginDate, Calendar endDate) {
        if (BlankUtil.isEmpty(beginDate)
                || BlankUtil.isEmpty(endDate)) {
            return 0l;
        }
        return (endDate.getTimeInMillis() - beginDate.getTimeInMillis()) / 1000 / 60 / 60;
    }

    /**
     * 获取时间间隔小时数
     *
     * @param beginTime
     * @param endTime
     * @return
     */
    public static Long getRangeHours(long beginTime, long endTime) {
        return (endTime - beginTime) / 1000 / 60 / 60;
    }

    /**
     * 获取时间间隔分钟数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getRangeMins(Calendar beginDate, Calendar endDate) {
        if (BlankUtil.isEmpty(beginDate)
                || BlankUtil.isEmpty(endDate)) {
            return 0;
        }
        return (endDate.getTimeInMillis() - beginDate.getTimeInMillis()) / 1000 / 60;
    }

    /**
     * 获取时间间隔秒数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getRangeSeconds(Calendar beginDate, Calendar endDate) {
        if (BlankUtil.isEmpty(beginDate)
                || BlankUtil.isEmpty(endDate)) {
            return 0;
        }
        return (endDate.getTimeInMillis() - beginDate.getTimeInMillis()) / 1000;
    }

    /**
     * 获取时间间隔秒数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getRangeSeconds(Date beginDate, Date endDate) {
        if (BlankUtil.isEmpty(beginDate)
                || BlankUtil.isEmpty(endDate)) {
            return 0;
        }
        return (endDate.getTime() - beginDate.getTime()) / 1000;
    }

    /**
     * 获取相隔毫秒数
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static long getRangeMillis(Calendar beginDate, Calendar endDate) {
        if (BlankUtil.isEmpty(beginDate)
                || BlankUtil.isEmpty(endDate)) {
            return 0;
        }
        return (endDate.getTimeInMillis() - beginDate.getTimeInMillis());
    }


    /**
     * 秒转化为时分秒标准格式，如：10h50'33"
     *
     * @param time
     * @return
     */
    public static String secToTime(Long time) {
        if (BlankUtil.isEmpty(time)) {
            return "--";
        }

        String timeStr = null;
        long hour = 0;
        long minute = 0;
        long second = 0;
        long day = 0;
        if (time <= 0)
            return "0分钟";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                if (second > 30) {
                    minute++;
                }
                timeStr = (minute + 1) + "分钟";
            } else {
                hour = time / 3600;
                if (hour < 24) {
                    minute = (time - hour * 3600) / 60;
                    timeStr = hour + "小时" + minute + "分钟";
                } else {
                    day = time / 3600 / 24;
                    if (day > 30) {
                        return "30天+";
                    }
                    hour = (time - day * 24 * 3600) / 3600;
                    minute = (time - day * 24 * 3600 - hour * 3600) / 60;
                    timeStr = day + "天" + hour + "小时" + minute + "分钟";
                }
            }
        }
        return timeStr;
    }

    /**
     * 获取当前UTC时间
     *
     * @return
     */
    public static Calendar getUTCTime() {
        Calendar now = Calendar.getInstance();
        int zoneOffset = now.get(Calendar.ZONE_OFFSET);
        int dstOffset = now.get(Calendar.DST_OFFSET);
        now.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return now;
    }

    public static String str2FullFormat(String pattern, String date) throws Exception {
        String format = "";
        switch (pattern) {
            case "second":
                format = date;
                break;
            case "minute":
                format = date + ":00";
                break;
            case "hour":
                format = date + ":00:00";
                break;
            case "day":
                format = date + " 00:00:00";
                break;
            case "month":
                format = date + "-00 00:00:00";
                break;
            case "year":
                format = date + "-00-00 00:00:00";
                break;
        }

        return format;
    }

    public static Calendar getCalendar(String dateStr) {
        Calendar calendar = null;
        // 声明一个Date类型的对象
        Date date = null;
        // 声明格式化日期的对象
        SimpleDateFormat format = null;
        if (dateStr != null) {
            // 创建日期的格式化类型
            format = new SimpleDateFormat("yyyy-MM-dd");
            // 创建一个Calendar类型的对象
            calendar = Calendar.getInstance();
            // forma.parse()方法会抛出异常
            try {
                //解析日期字符串，生成Date对象
                date = format.parse(dateStr);
                // 使用Date对象设置此Calendar对象的时间
                calendar.setTime(date);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return calendar;
    }


    public static int getDays(int year, int month) {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

    public static Calendar getDateTransCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Integer getDateYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    /*public static Integer getStringYear(String date){
        Date dt=parseDate(date,"yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();
        cal.setTime(dt);
        return cal.get(Calendar.YEAR);
    }*/
    public static Integer getDateMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static Integer getDateDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static Integer getDateHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }



    public static String formatChineseDate(Calendar calendar) {
        String chineseDate;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        chineseDate = year + "年" + month + "月" + day + "日";
        return chineseDate;
    }

    //JAVA获取某段时间内的所有日期
    public static List<Date> findDates(Date dStart, Date dEnd) throws ParseException {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(TimeUtil.parseDate(formatDate(cStart.getTime(), "yyyy-MM-dd"), "yyyy-MM-dd"));
        }
        return dateList;
    }

    //JAVA获取某段时间内的所有日期
    public static List<String> strDateList(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(formatDate(cStart.getTime(), "yyyy-MM-dd"));
        }
        return dateList;
    }
    public static Date parseOrThrow(String time, RuntimeException e) {
        try {
            return parse(time);
        } catch (Exception e1) {
            throw e;
        }
    }

}


