package com.project.board.domain.like.dto;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.user.web.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeCommentRequestDto {

    public LikeComment toEntity(User user, Comment comment) {
        return LikeComment.builder()
                .user(user)
                .comment(comment)
                .build();
    }
}
