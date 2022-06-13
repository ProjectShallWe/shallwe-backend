package com.project.board.domain.post.dto;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.web.PostCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostCategoryRequestDto {

    private String topic;

    public PostCategory toEntity(Board board) {
        return PostCategory.builder()
                .board(board)
                .topic(topic)
                .build();
    }
}
