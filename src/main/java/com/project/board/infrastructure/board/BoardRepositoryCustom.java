package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepositoryCustom {

    List<Board> findAllWithPostCategories(Long boardId);
    List<Board> findBoardsBySearchWord(String keyword);
    List<Board> findBoardsByRecommendScore(LocalDateTime now, LocalDateTime twelveHoursAgo);
}
