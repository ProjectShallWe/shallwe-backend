package com.project.board.domain.board.dto;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardRequestDto {

    private String title;

    public Board toEntity(BoardCategory boardCategory) {
        return Board.builder()
                .boardCategory(boardCategory)
                .title(title)
                .build();

    }
}
