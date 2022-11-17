package com.project.board.domain.post.dto;

import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.domain.user.web.User;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostWriteRequestDto {

    private String title;
    private String content;
    private List<String> images;

    @Builder
    public Post toEntity(User user, PostCategory postCategory) {
        return Post.builder()
                .user(user)
                .postCategory(postCategory)
                .title(title)
                .content(content)
                .build();
    }
}
