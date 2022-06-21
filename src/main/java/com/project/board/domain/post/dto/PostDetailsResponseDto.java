package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.board.global.util.DateConverter.convertLocalDateTimeFromYearToMinute;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailsResponseDto {
    private Long postId;
    private String nickname;
    private String createdDate;
    private String postCategory;
    private Long likeCount;
    private Long commentCount;
    private String title;
    private String content;

    @Builder
    public PostDetailsResponseDto(PostDetailsQueryDto postDetailsQueryDto) {
        this.postId = postDetailsQueryDto.getPostId();
        this.nickname = postDetailsQueryDto.getNickname();
        this.createdDate = convertLocalDateTimeFromYearToMinute(postDetailsQueryDto.getCreatedDate());
        this.postCategory = postDetailsQueryDto.getPostCategory();
        this.likeCount = postDetailsQueryDto.getLikeCount();
        this.commentCount = postDetailsQueryDto.getCommentCount();
        this.title = postDetailsQueryDto.getTitle();
        this.content = postDetailsQueryDto.getContent();
    }
}
