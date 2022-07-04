package com.project.board.infrastructure.fixture;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.user.web.User;

public class LikeCommentFixture {
    public static LikeComment createLikeComment(User user, Comment comment) {
        return LikeComment.builder()
                .user(user)
                .comment(comment)
                .build();
    }
}
