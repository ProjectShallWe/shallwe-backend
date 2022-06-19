package com.project.board.domain.post.dto;

import com.project.board.domain.post.web.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.board.global.util.DateConverter.convertLocalDateTimeToMMdd;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostsResponseDto {

    private Long postId;
    private String title;
    private String postCategory;
    private String nickname;
    private Long likeCount;
    private String createdDate;

    @Builder
    public PostsResponseDto (Post post) {
        this.postId = post.getId();
        this.postCategory = post.getPostCategory().getTopic();
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.likeCount = post.getLikeCount();
        this.createdDate = convertLocalDateTimeToMMdd(post.getCreatedDate());
    }
}
