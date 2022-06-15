package com.project.board.domain.post.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

    @Query("select distinct p " +
            "from Post p " +
            "join p.postCategory pc " +
            "where pc.id =:id")
    Page<Post> findAllInPostCategory(@Param("id") Long id, Pageable pageable);
}
