package com.project.board.domain.like.web;

public interface LikePostReader {

    LikePost getLikePostBy(Long id);

    LikePost getLikePostBy(Long userId, Long postId);
}
