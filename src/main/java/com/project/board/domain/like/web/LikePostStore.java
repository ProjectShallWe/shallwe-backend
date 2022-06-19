package com.project.board.domain.like.web;

public interface LikePostStore {

    LikePost store(LikePost likePost);
    void delete(LikePost likePost);
}
