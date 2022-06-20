package com.project.board.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EntityToCommentResponseDto {

    Long commentId;
    Long parentId;
    String nickname;
    LocalDateTime createdDate;
    Long likeCommentCount;
    String content;

    @Builder
    public EntityToCommentResponseDto(Long commentId,
                                      Long parentId,
                                      String nickname,
                                      LocalDateTime createdDate,
                                      Long likeCommentCount,
                                      String content) {
        this.commentId = commentId;
        this.parentId = parentId;
        this.nickname = nickname;
        this.createdDate = createdDate;
        this.likeCommentCount = likeCommentCount;
        this.content = content;
    }
}
