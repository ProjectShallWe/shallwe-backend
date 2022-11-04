package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailsQueryDto {

    private Long postId;
    private String nickname;
    private LocalDateTime createdDate;
    private Long postCategoryId;
    private String postCategory;
    private Long likeCount;
    private Long commentCount;
    private Long hits;
    private String title;
    private String content;

    @Builder
    public PostDetailsQueryDto(Long postId, String nickname,
                               LocalDateTime createdDate,
                               Long postCategoryId,
                               String postCategory,
                               Long likeCount, Long commentCount, Long hits,
                               String title, String content) {
        this.postId = postId;
        this.nickname = nickname;
        this.createdDate = createdDate;
        this.postCategoryId = postCategoryId;
        this.postCategory = postCategory;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.hits = hits;
        this.title = title;
        this.content = content;
    }
}
