package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikePost;

import java.util.Optional;

public interface LikePostRepositoryCustom {

    Optional<LikePost> findByUserIdAndPostId(Long userId, Long postId);
}
