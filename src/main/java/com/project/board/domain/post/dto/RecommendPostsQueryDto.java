package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendPostsQueryDto {

    private Long postId;
    private Long boardId;
    private String postCategory;
    private String title;
    private Long commentCount;

    @Builder
    public RecommendPostsQueryDto(Long postId,
                                  Long boardId,
                                  String postCategory,
                                  String title,
                                  Long commentCount) {
        this.postId = postId;
        this.boardId = boardId;
        this.postCategory = postCategory;
        this.title = title;
        this.commentCount = commentCount;
    }
}
