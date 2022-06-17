package com.project.board.domain.post.dto;

import com.project.board.domain.post.web.Post;
import com.project.board.global.util.DateConverter;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostsResponseDto {

    private Long postId;
    private String title;
    private String postCategory;
    private String nickname;
    private Long likeCount;
    private String createdAt;

    @Builder
    public PostsResponseDto (Post post) {
        this.postId = post.getId();
        this.postCategory = post.getPostCategory().getTopic();
        this.title = post.getTitle();
        this.nickname = post.getUser().getNickname();
        this.likeCount = post.getLikeCount();
        this.createdAt = DateConverter.getDate(post.getCreatedDate());
    }
}
