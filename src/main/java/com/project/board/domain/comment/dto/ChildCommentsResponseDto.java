package com.project.board.domain.comment.dto;

import com.project.board.domain.comment.web.Comment;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.board.domain.comment.web.Comment.*;
import static com.project.board.global.util.DateConverter.convertLocalDateTimeFromYearToSecond;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChildCommentsResponseDto {

    Long commentId;
    Long parentId;
    String nickname;
    String createdDate;
    Long likeCommentCount;
    String content;
    Status status;
    @Builder
    public ChildCommentsResponseDto(CommentQueryDto ETCResponseDto) {
        this.commentId = ETCResponseDto.getCommentId();
        this.parentId = ETCResponseDto.getParentId();
        this.nickname = ETCResponseDto.getNickname();
        this.createdDate = convertLocalDateTimeFromYearToSecond(ETCResponseDto.getCreatedDate());
        this.likeCommentCount = ETCResponseDto.getLikeCommentCount();
        this.content = ETCResponseDto.getContent();
        this.status = ETCResponseDto.getStatus();
    }
}
