package com.project.board.domain.post.web;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PageSize {
    TEN(10),
    TWENTY(20),
    FIFTY(50);

    public final int value;
}
