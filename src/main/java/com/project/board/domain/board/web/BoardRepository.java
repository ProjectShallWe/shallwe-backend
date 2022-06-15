package com.project.board.domain.board.web;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("select distinct b " +
            "from Board b " +
            "join fetch b.postCategories pc " +
            "where b.id =:id " +
            "order by b.id asc, pc.id asc")
    List<Board> findAllWithPostCategory(@Param("id") Long id);
}
