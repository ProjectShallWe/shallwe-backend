package com.project.board.domain.like.web;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
}
