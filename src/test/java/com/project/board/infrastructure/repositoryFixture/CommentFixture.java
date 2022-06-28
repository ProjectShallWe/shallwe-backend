package com.project.board.infrastructure.repositoryFixture;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;

public class CommentFixture {
    public static Comment createComment1(User user, Post post) {
        return Comment.builder()
                .user(user)
                .post(post)
                .parentCommentId(null)
                .content("농구공을 어떻게 잘 던지는데요?")
                .build();
    }

    public static Comment createComment2(User user, Post post) {
        return Comment.builder()
                .user(user)
                .post(post)
                .parentCommentId(null)
                .content("팔꿈치가 수직이어야 멀리 던질 수 있어요!")
                .build();
    }
}
