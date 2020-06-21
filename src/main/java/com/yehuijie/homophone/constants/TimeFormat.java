package com.yehuijie.homophone.constants;

/**
 * 时间格式字符串常量类
 * Created by cent on 2018/3/7.
 */
public enum TimeFormat {
    ;

    public static final String YYYY = "yyyy";
    public static final String YYYYMM = "yyyy-MM";
    public static final String YYYYMMDD = "yyyy-MM-dd";
    public static final String YYYYMMDDHH = "yyyy-MM-dd HH";
    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String HHMM = "HH:mm";

    // 定义数据库表中年月日的标签名称
    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";

    /**
     * 请求时间戳格式
     */
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * 请求时间戳允许偏差范围（ms）
     */
    public static final long TIMESTAMP_RANGE = 5 * 60 * 1000;

    /**
     * 日期类型：用于接口参数定义（按月、按天）
     */
    public enum DateTypeMD {
        month, day
    }

    /**
     * 日期类型：用于接口参数定义（按月、按天、按小时）
     */
    public enum DateTypeMDH {
        month, day, hour
    }

    /**
     * 日期类型：用于接口参数定义（按月、按周、按小时）
     */
    public enum MonitorDateTypeMWD {
//        "0","1","2"
    }
}
