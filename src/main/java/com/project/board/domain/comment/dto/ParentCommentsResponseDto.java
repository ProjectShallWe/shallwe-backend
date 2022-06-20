package com.project.board.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.project.board.global.util.DateConverter.convertLocalDateTimeFromYearToSecond;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParentCommentsResponseDto {

    Long commentId;
    String nickname;
    String createdDate;
    Long likeCommentCount;
    String content;
    List<ChildCommentsResponseDto> childComments;

    @Builder
    public ParentCommentsResponseDto(EntityToCommentResponseDto ETCResponseDto) {
        this.commentId = ETCResponseDto.getCommentId();
        this.nickname = ETCResponseDto.getNickname();
        this.createdDate = convertLocalDateTimeFromYearToSecond(ETCResponseDto.getCreatedDate());
        this.likeCommentCount = ETCResponseDto.getLikeCommentCount();
        this.content = ETCResponseDto.getContent();
        this.childComments = new ArrayList<>();
    }
}
