package com.project.board.domain.category.dto;

import com.project.board.domain.category.web.Category;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CategoryRequestDto {

    String topic;

    public Category toEntity() {
        return Category.builder()
                .topic(topic)
                .build();
    }
}
