package com.project.board.domain.post.dto;

import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostWriteRequestDto {

    private String title;
    private String content;

    @Builder
    public Post toEntity(User user) {
        return Post.builder()
                .user(user)
                .title(title)
                .content(content)
                .build();
    }
}
