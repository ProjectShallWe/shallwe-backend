package com.project.board.domain.board.web;

import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardCategoryReader {

    private final BoardCategoryRepository boardCategoryRepository;

    public BoardCategory getUserBy(Long id) {
        return boardCategoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
