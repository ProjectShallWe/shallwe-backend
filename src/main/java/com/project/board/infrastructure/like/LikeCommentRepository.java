package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long>, LikeCommentRepositoryCustom {

    Boolean existsByUserIdAndCommentId(Long userId, Long commentId);
    Optional<LikeComment> findByUserIdAndCommentId(Long userId, Long commentId);
}
