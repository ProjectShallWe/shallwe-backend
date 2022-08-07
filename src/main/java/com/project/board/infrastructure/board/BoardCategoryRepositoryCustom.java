package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.BoardCategory;

import java.util.List;

public interface BoardCategoryRepositoryCustom {

    List<BoardCategory> findAllWithBoards();
}
