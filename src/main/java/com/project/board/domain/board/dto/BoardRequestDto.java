package com.project.board.domain.board.dto;

import com.project.board.domain.board.model.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardRequestDto {

    private String title;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .build();

    }
}
