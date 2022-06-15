package com.project.board.domain.board.dto;

import com.project.board.domain.board.web.BoardCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardCategoryResponseDto {

    private Long postCategoryId;
    private String topic;
    private List<BoardInBoardCategoryResponseDto> boards = new ArrayList<>();

    @Builder
    public BoardCategoryResponseDto(BoardCategory boardCategory) {
        this.postCategoryId = boardCategory.getId();
        this.topic = boardCategory.getTopic();
        this.boards = boardCategory.getBoards().stream()
                .map(b -> (new BoardInBoardCategoryResponseDto(b)))
                .collect(Collectors.toList());
    }
}
