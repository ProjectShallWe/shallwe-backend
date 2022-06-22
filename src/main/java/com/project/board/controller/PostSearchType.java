package com.project.board.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostSearchType {
    TICON("ticon","제목 + 내용"),
    TITLE("title","제목"),
    CONTENT("content","내용"),
    NICKNAME("nickname", "닉네임");

    public final String stringValue;
    public final String description;
}
