package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendPostsResponseDto implements Serializable {

    private Long postId;
    private Long boardId;
    private String postCategory;
    private String title;
    private Long commentCount;

    @Builder
    public RecommendPostsResponseDto(RecommendPostsQueryDto queryDto) {
        this.postId = queryDto.getPostId();
        this.boardId = queryDto.getBoardId();
        this.postCategory = queryDto.getPostCategory();
        this.title = queryDto.getTitle();
        this.commentCount = queryDto.getCommentCount();
    }
}
