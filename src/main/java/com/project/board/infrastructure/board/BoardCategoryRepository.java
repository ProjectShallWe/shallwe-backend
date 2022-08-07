package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.BoardCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long>, BoardCategoryRepositoryCustom {
}
