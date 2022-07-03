package com.project.board.infrastructure.repositoryFixture;

import com.project.board.domain.board.web.BoardCategory;

import java.util.ArrayList;

public class BoardCategoryFixture {
    public static BoardCategory createBoardCategory1() {
        return BoardCategory.builder()
                .topic("스포츠")
                .boards(new ArrayList<>())
                .build();
    }

    public static BoardCategory createBoardCategory2() {
        return BoardCategory.builder()
                .topic("게임")
                .boards(new ArrayList<>())
                .build();
    }
}
