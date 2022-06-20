package com.project.board.domain.like.web;

public interface LikeCommentReader {

    LikeComment getLikeCommentBy(Long id);
    LikeComment getLikeCommentBy(Long userId, Long commentId);
}
