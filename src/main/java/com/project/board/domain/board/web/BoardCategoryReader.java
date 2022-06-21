package com.project.board.domain.board.web;

import java.util.List;

public interface BoardCategoryReader {

    BoardCategory getBoardCategoryBy(Long id);
    List<BoardCategory> getBoardCategoriesWithBoards();
}
