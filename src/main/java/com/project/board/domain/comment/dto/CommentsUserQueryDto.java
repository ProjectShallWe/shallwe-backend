package com.project.board.domain.comment.dto;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsUserQueryDto {

    Long commentId;
    String content;
    LocalDateTime createdDate;
    Long likeCommentCount;
    Long postId;
    Long boardId;
    String boardTitle;

    @Builder
    public CommentsUserQueryDto(Long commentId,
                                String content,
                                LocalDateTime createdDate,
                                Long likeCommentCount,
                                Long postId,
                                Long boardId,
                                String boardTitle) {
        this.commentId = commentId;
        this.content = content;
        this.createdDate = createdDate;
        this.likeCommentCount = likeCommentCount;
        this.postId = postId;
        this.boardId = boardId;
        this.boardTitle = boardTitle;
    }
}
