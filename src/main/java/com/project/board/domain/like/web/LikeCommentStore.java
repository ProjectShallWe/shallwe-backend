package com.project.board.domain.like.web;

public interface LikeCommentStore {

    LikeComment store(LikeComment likeComment);
    void delete(LikeComment likeComment);
}
