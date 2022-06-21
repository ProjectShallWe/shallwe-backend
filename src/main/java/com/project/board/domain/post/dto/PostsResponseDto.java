package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.board.global.util.DateConverter.convertLocalDateTimeToMMdd;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostsResponseDto {

    private Long postId;
    private String postCategory;
    private String title;
    private String nickname;
    private String createdDate;
    private Long likeCount;

    @Builder
    public PostsResponseDto (PostsQueryDto postsQueryDto) {
        this.postId = postsQueryDto.getPostId();
        this.postCategory = postsQueryDto.getPostCategory();
        this.title = postsQueryDto.getTitle();
        this.nickname = postsQueryDto.getNickname();
        this.createdDate = convertLocalDateTimeToMMdd(postsQueryDto.getCreatedDate());
        this.likeCount = postsQueryDto.getLikeCount();
    }
}
