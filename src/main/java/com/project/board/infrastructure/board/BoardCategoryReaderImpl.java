package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.BoardCategory;
import com.project.board.domain.board.web.BoardCategoryReader;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardCategoryReaderImpl implements BoardCategoryReader {

    private final BoardCategoryRepository boardCategoryRepository;

    public BoardCategory getBoardCategoryBy(Long id) {
        return boardCategoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<BoardCategory> getBoardCategoriesWithBoards() {
        return boardCategoryRepository.findAllWithBoards();
    }
}
