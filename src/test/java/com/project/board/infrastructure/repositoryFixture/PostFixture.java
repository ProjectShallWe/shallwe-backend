package com.project.board.infrastructure.repositoryFixture;

import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.domain.user.web.User;

public class PostFixture {
    public static Post createPost1(User user, PostCategory postCategory) {
        return Post.builder()
                .title("농구 잘하는 법")
                .content("1. 농구공을 잘 던진다.")
                .user(user)
                .postCategory(postCategory)
                .build();
    }

    public static Post createPost1(User user) {
        return Post.builder()
                .title("농구 잘하는 법")
                .content("1. 농구공을 잘 던진다.")
                .user(user)
                .build();
    }
}
