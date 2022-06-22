package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendPostsInBoardQueryDto {

    private Long postId;
    private String postCategory;
    private String title;
    private Long commentCount;

    @Builder
    public RecommendPostsInBoardQueryDto(Long postId,
                                         String postCategory,
                                         String title,
                                         Long commentCount) {
        this.postId = postId;
        this.postCategory = postCategory;
        this.title = title;
        this.commentCount = commentCount;
    }
}
