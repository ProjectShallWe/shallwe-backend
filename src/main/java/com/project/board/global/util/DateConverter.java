package com.project.board.global.util;

import java.time.LocalDateTime;

public class DateConverter {


    public static String getDate(LocalDateTime localDateTime) {
        return checkNumLength(String.valueOf(localDateTime.getMonthValue())) +
               "-" + checkNumLength(String.valueOf(localDateTime.getDayOfMonth()));
    }

    private static String checkNumLength (String num) {
        if (num.length() == 1) {
            return "0" + num;
        }
        return num;
    }
}
