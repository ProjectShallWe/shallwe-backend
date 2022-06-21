package com.project.board.domain.board.dto;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.dto.PostCategoryInBoardResponseDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDto {

    private Long boardId;
    private String title;
    private List<PostCategoryInBoardResponseDto> postCategories = new ArrayList<>();

    @Builder
    public BoardResponseDto(Board board) {
        this.boardId = board.getId();
        this.title = board.getTitle();
        this.postCategories = board.getPostCategories().stream()
                .map(PostCategoryInBoardResponseDto::new)
                .collect(Collectors.toList());
    }
}
