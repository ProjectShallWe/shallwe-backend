package com.project.board.infrastructure.fixture;

import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;

public class LikePostFixture {
    public static LikePost createLikePost(User user, Post post) {
        return LikePost.builder()
                .user(user)
                .post(post)
                .build();
    }
}
