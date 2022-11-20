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
    private Long postCategoryId;
    private String postCategory;
    private String title;
    private Long commentCount;
    private String nickname;
    private String createdDate;
    private Long hits;
    private Long likeCount;
    private String thumbnailUrl;

    @Builder
    public PostsResponseDto (PostsQueryDto postsQueryDto) {
        this.postId = postsQueryDto.getPostId();
        this.postCategoryId = postsQueryDto.getPostCategoryId();
        this.postCategory = postsQueryDto.getPostCategory();
        this.title = postsQueryDto.getTitle();
        this.commentCount = postsQueryDto.getCommentCount();
        this.nickname = postsQueryDto.getNickname();
        this.createdDate = convertLocalDateTimeToMMdd(postsQueryDto.getCreatedDate());
        this.hits = postsQueryDto.getHits();
        this.likeCount = postsQueryDto.getLikeCount();
        this.thumbnailUrl = postsQueryDto.getThumbnailUrl();
    }
}
