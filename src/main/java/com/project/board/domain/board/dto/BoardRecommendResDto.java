package com.project.board.domain.board.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class  BoardRecommendResDto implements Serializable {
    private List<Long> boardRecommendIdList;

    @Builder

    public BoardRecommendResDto(List<Long> boardRecommendIdList) {
        this.boardRecommendIdList = boardRecommendIdList;
    }
}
