package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
}
