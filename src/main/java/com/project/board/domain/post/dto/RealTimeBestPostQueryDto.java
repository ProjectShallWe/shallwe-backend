package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RealTimeBestPostQueryDto {

    private Long boardId;
    private Long postId;
    private String boardTitle;
    private String postTitle;
    private String thumbnailUrl;
    private Long commentCount;
    private LocalDateTime createdDate;

    @Builder
    public RealTimeBestPostQueryDto(Long boardId,
                                    Long postId,
                                    String boardTitle,
                                    String postTitle,
                                    String thumbnailUrl,
                                    Long commentCount,
                                    LocalDateTime createdDate) {
        this.boardId = boardId;
        this.postId = postId;
        this.boardTitle = boardTitle;
        this.postTitle = postTitle;
        this.thumbnailUrl = thumbnailUrl;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
    }
}
