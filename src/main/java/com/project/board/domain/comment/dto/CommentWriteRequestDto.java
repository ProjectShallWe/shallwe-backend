package com.project.board.domain.comment.dto;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommentWriteRequestDto {

    private String content;

    public Comment toEntity(User user, Post post, Long commentId) {
        return Comment.builder()
                .user(user)
                .post(post)
                .parentCommentId(commentId)
                .content(content)
                .build();
    }
}
