package com.project.board.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateConverter {

    public static String convertLocalDateTimeToMMdd(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("MM-dd"));
    }

    public static String convertLocalDateTimeFromYearToMinute(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static String convertLocalDateTimeFromYearToSecond(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
