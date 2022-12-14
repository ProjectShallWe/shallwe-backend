package com.project.board.domain.board.dto;

import com.project.board.domain.board.web.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardInfoResDto {

    private Long boardId;
    private String title;

    @Builder
    public BoardInfoResDto(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
    }
}
