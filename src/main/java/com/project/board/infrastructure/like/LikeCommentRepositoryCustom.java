package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikeComment;

import java.util.Optional;

public interface LikeCommentRepositoryCustom {

    Optional<LikeComment> findByUserIdAndCommentId(Long userId, Long commentId);
}
