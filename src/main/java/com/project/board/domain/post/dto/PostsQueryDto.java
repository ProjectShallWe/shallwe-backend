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
    private String postCategory;
    private String title;
    private String nickname;
    private LocalDateTime createdDate;
    private Long likeCount;

    @Builder
    public PostsQueryDto(Long postId,
                         String postCategory,
                         String title,
                         String nickname,
                         LocalDateTime createdDate,
                         Long likeCount) {
        this.postId = postId;
        this.postCategory = postCategory;
        this.title = title;
        this.nickname = nickname;
        this.createdDate = createdDate;
        this.likeCount = likeCount;
    }
}
