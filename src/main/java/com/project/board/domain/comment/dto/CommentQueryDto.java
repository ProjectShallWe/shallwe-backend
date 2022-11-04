package com.project.board.domain.comment.dto;

import com.project.board.domain.comment.web.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.project.board.domain.comment.web.Comment.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentQueryDto {

    Long commentId;
    Long parentId;
    String nickname;
    LocalDateTime createdDate;
    Long likeCommentCount;
    String content;
    Status status;

    @Builder
    public CommentQueryDto(Long commentId,
                           Long parentId,
                           String nickname,
                           LocalDateTime createdDate,
                           Long likeCommentCount,
                           String content,
                           Status status) {
        this.commentId = commentId;
        this.parentId = parentId;
        this.nickname = nickname;
        this.createdDate = createdDate;
        this.likeCommentCount = likeCommentCount;
        this.content = content;
        this.status = status;
    }
}
