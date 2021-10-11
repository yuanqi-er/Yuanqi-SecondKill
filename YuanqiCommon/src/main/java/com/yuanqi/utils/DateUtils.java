package com.yuanqi.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    //把 格式化的时间字符串，转换为 Date 类型的时间
    public static Date transformTimeBy(String time, String formatString) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(formatString);
        Date date = dateFormat.parse(time);
        return date;
    }
}
