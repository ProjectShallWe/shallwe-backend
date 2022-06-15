package com.project.board.domain.board.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardCategoryRepository extends JpaRepository<BoardCategory, Long> {

    @Query("select distinct bc " +
            "from BoardCategory bc " +
            "join fetch bc.boards b " +
            "order by bc.id asc, b.id asc")
    List<BoardCategory> findAllWithBoard();
}
