package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {
}
