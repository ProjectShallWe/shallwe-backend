package com.project.board.domain.board.dto;

import com.project.board.domain.board.web.BoardCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardCategoryRequestDto {

    String topic;

    public BoardCategory toEntity() {
        return BoardCategory.builder()
                .topic(topic)
                .build();
    }
}
