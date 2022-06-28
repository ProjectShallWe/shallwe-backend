package com.project.board.infrastructure.repositoryFixture;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardCategory;

public class BoardFixture {
    public static Board createBoard1(BoardCategory boardCategory) {
        return Board.builder()
                .title("농구")
                .boardCategory(boardCategory)
                .build();
    }

    public static Board createBoard1() {
        return Board.builder()
                .title("농구")
                .build();
    }

    public static Board createBoard2(BoardCategory boardCategory) {
        return Board.builder()
                .title("축구")
                .boardCategory(boardCategory)
                .build();
    }

    public static Board createBoard2() {
        return Board.builder()
                .title("축구")
                .build();
    }
}
