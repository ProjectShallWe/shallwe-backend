package com.project.board.infrastructure.repositoryFixture;

import com.project.board.domain.board.web.BoardCategory;

public class BoardCategoryFixture {
    public static BoardCategory createBoardCategory() {
        return BoardCategory.builder()
                .topic("스포츠 카테고리")
                .build();
    }
}
