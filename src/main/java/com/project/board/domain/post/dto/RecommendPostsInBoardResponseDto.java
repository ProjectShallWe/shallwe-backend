package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendPostsInBoardResponseDto implements Serializable {

    private Long postId;
    private String postCategory;
    private String title;
    private Long commentCount;

    @Builder
    public RecommendPostsInBoardResponseDto(RecommendPostsInBoardQueryDto queryDto) {
        this.postId = queryDto.getPostId();
        this.postCategory = queryDto.getPostCategory();
        this.title = queryDto.getTitle();
        this.commentCount = queryDto.getCommentCount();
    }
}
