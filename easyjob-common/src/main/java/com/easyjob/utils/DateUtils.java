package com.easyjob.utils;

import com.easyjob.enums.DateTimePatternEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.lang.ThreadLocal;

public class DateUtils {

    private static final Object lockObj = new Object(); // 同步锁

    // 为 每种日期格式 维护对应的 ThreadLocal<SimpleDateFormat>
    private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<>();

    private static SimpleDateFormat getSdf(final String pattern) {
        ThreadLocal<SimpleDateFormat> tl = sdfMap.get(pattern);
        if (tl == null) {

            synchronized (lockObj) {

                tl = sdfMap.get(pattern);
                if (tl == null) {
                    tl = ThreadLocal.withInitial(() -> new SimpleDateFormat(pattern));
                    sdfMap.put(pattern, tl);
                }
            }
        }
        return tl.get();
    }

    public static String format(Date date, String pattern) {
        return getSdf(pattern).format(date);
    }

    public static Date parse(String dateStr, String pattern) {
        try {
            return getSdf(pattern).parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 获取当前时间前 N 天
    public static Date getDayAgo(Integer day){

        LocalDateTime localDateTime= LocalDateTime.now().minusDays(day); // 减去指定天数
        ZoneId zone = ZoneId.systemDefault();
        Instant ins = localDateTime.atZone(zone).toInstant();
        return Date.from(ins);
    }

    public static void main(String[] args) {

        Date date = getDayAgo(1);
        System.out.println(format(date, DateTimePatternEnum._YYYY_MM_DD_HH_MM_SS.getPattern()));
        System.out.println(format(date, DateTimePatternEnum._YYYY_MM.getPattern()));
        System.out.println(format(date, DateTimePatternEnum._YYYY_MM_DD.getPattern()));
    }

}
