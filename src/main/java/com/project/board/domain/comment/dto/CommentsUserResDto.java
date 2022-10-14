package com.project.board.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.project.board.global.util.DateConverter.convertLocalDateTimeToMMdd;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsUserResDto {

    Long commentId;
    String content;
    String createdDate;
    Long likeCommentCount;
    Long postId;
    Long boardId;
    String boardTitle;

    @Builder
    public CommentsUserResDto(CommentsUserQueryDto commentsUserQueryDto) {
        this.commentId = commentsUserQueryDto.commentId;
        this.content = commentsUserQueryDto.content;
        this.createdDate = convertLocalDateTimeToMMdd(commentsUserQueryDto.createdDate);
        this.likeCommentCount = commentsUserQueryDto.likeCommentCount;
        this.postId = commentsUserQueryDto.postId;
        this.boardId = commentsUserQueryDto.boardId;
        this.boardTitle = commentsUserQueryDto.boardTitle;
    }
}
