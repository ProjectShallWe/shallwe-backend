package com.project.board.domain.like.dto;

import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikePostRequestDto {

    public LikePost toEntity(User user, Post post) {
        return LikePost.builder()
                .user(user)
                .post(post)
                .build();
    }
}
