package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;

import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> findAllWithPostCategories(Long boardId);
}
