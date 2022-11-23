package com.project.board.domain.post.dto;

import com.project.board.global.util.DateConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RealTimeBestPostResDto implements Serializable {

    private Long boardId;
    private Long postId;
    private String boardTitle;
    private String postTitle;
    private String thumbnailUrl;
    private Long commentCount;
    private String createdDate;

    @Builder
    public RealTimeBestPostResDto(RealTimeBestPostQueryDto dto) {
        this.boardId = dto.getBoardId();
        this.postId = dto.getPostId();
        this.boardTitle = dto.getBoardTitle();
        this.postTitle = dto.getPostTitle();
        this.thumbnailUrl = dto.getThumbnailUrl();
        this.commentCount = dto.getCommentCount();
        this.createdDate = DateConverter.convertLocalDateTimeToMMdd(dto.getCreatedDate());
    }
}
