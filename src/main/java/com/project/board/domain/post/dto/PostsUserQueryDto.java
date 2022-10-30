package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostsUserQueryDto {

    private Long postId;
    private Long boardId;
    private String boardTitle;
    private String title;
    private Long commentCount;
    private LocalDateTime createdDate;
    private Long hits;
    private Long likeCount;

    @Builder
    public PostsUserQueryDto(Long postId,
                         Long boardId,
                         String boardTitle,
                         String title,
                         Long commentCount,
                         LocalDateTime createdDate,
                         Long hits,
                         Long likeCount) {
        this.postId = postId;
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.title = title;
        this.commentCount = commentCount;
        this.createdDate = createdDate;
        this.hits = hits;
        this.likeCount = likeCount;
    }
}
