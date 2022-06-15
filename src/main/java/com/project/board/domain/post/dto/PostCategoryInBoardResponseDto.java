package com.project.board.domain.post.dto;

import com.project.board.domain.post.web.PostCategory;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategoryInBoardResponseDto {

    private Long postCategoryId;

    private String topic;

    @Builder
    public PostCategoryInBoardResponseDto(PostCategory postCategory) {
        this.postCategoryId = postCategory.getId();
        this.topic = postCategory.getTopic();
    }
}
