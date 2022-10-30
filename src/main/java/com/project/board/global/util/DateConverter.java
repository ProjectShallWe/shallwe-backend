package com.project.board.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static String convertLocalDateTimeToMMdd(LocalDateTime localDateTime) {
        if (isToday(localDateTime)) {
            return localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            return localDateTime.format(DateTimeFormatter.ofPattern("MM-dd"));
        }
    }
    private static Boolean isToday(LocalDateTime localDateTime) {
        String now = convertLocalDateTimeFromYearToDate(LocalDateTime.now());
        String date = convertLocalDateTimeFromYearToDate(localDateTime);
        return now.equals(date);
    }

    private static String convertLocalDateTimeFromYearToDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String convertLocalDateTimeFromYearToMinute(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static String convertLocalDateTimeFromYearToSecond(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
