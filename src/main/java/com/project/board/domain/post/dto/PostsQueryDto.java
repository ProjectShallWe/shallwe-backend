package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostsQueryDto {

    private Long postId;
    private Long postCategoryId;
    private String postCategory;
    private String title;
    private Long commentCount;
    private String nickname;
    private LocalDateTime createdDate;
    private Long hits;
    private Long likeCount;

    @Builder
    public PostsQueryDto(Long postId,
                         Long postCategoryId,
                         String postCategory,
                         String title,
                         Long commentCount,
                         String nickname,
                         LocalDateTime createdDate,
                         Long hits,
                         Long likeCount) {
        this.postId = postId;
        this.postCategoryId = postCategoryId;
        this.postCategory = postCategory;
        this.title = title;
        this.commentCount = commentCount;
        this.nickname = nickname;
        this.createdDate = createdDate;
        this.hits = hits;
        this.likeCount = likeCount;
    }
}
