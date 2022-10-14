package com.project.board.global.util;

import java.time.LocalDateTime;

public class HtmlTagRemover {
    public static String removeHtmlTag(String text) {
        return text.replaceAll("<(/)?([0-9a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");

    }
}
