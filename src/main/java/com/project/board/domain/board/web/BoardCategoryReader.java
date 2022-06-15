package com.project.board.domain.board.web;

import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardCategoryReader {

    private final BoardCategoryRepository boardCategoryRepository;

    public BoardCategory getBoardCategoryBy(Long id) {
        return boardCategoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<BoardCategory> getAllBoardCategoryWithBoard() {
        return boardCategoryRepository.findAllWithBoard();
    }
}
