package com.project.board.domain.board.web;

import java.util.List;

public interface BoardReader {

    Board getBoardBy(Long id);
    List<Board> getBoardWithPostCategories(Long id);
}
