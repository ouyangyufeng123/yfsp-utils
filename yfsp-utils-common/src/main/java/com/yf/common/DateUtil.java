package com.yf.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ouyangyufeng
 */
public class DateUtil {

    private static final String CUR_TIME_FORMAT = "YYYYMMddhhmmssSSS";

    private static final String CUR_TIME_FORMAT2 = "yyyy-MM-dd HH:mm:ss";

    private static final String CUR_TIME_FORMAT3 = "yyyy-MM-dd";


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {
        return getStampToDate(System.currentTimeMillis());
    }

    /**
     * 时间戳转换日期格式(time stamp)
     *
     * @param time：时间戳
     * @return
     */
    public static String getStampToDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(CUR_TIME_FORMAT2);
        return dateFormat.format(date);
    }

    /**
     * 毫秒值转换日期格式
     *
     * @param seconds：毫秒值
     * @param format：日期格式
     * @return
     */
    public static Date getSecondToDate(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return new Date();
        }
        if (format == null || format.isEmpty()) {
            format = CUR_TIME_FORMAT2;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return new Date(Long.valueOf(seconds + "000"));
    }

    /**
     * 格林威治时间转换日期格式
     *
     * @param date：时间
     * @return
     * @throws Exception
     */
    public static Date getDate(Date date) throws Exception {
        Date time = new Date(date.toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat(CUR_TIME_FORMAT3);
        return dateFormat.parse(dateFormat.format(time));
    }

    /**
     * 字符串转换日期格式
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static Date getParseStringToDate(String date) throws Exception {
        //转换提日期输出格式
        SimpleDateFormat dateFormat = null;
        if (date.length() <= 10) {
            dateFormat = new SimpleDateFormat(CUR_TIME_FORMAT3);
        } else {
            dateFormat = new SimpleDateFormat(CUR_TIME_FORMAT2);
        }
        return dateFormat.parse(date);
    }

    public static void main(String[] args) {
        try {
            System.out.println(getParseStringToDate("2018-10-10"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
