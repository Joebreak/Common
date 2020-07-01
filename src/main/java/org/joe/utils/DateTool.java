package org.joe.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {

    public static Date getCurrentDate() {
        return Calendar.getInstance().getTime();
    }
    
    public static String toFormat(String pattern, Date date) {
        if (date == null || pattern == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        try {
            format.applyPattern(pattern);
            return format.format(date);
        } catch (Exception e) {
        }
        return null;
    }
    
    public static Date toFormatDate(String pattern, String input) {
        if (input == null || pattern == null) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat();
        try {
            format.applyPattern(pattern);
            return format.parse(input);
        } catch (Exception e) {
        }
        return null;
    }
}
