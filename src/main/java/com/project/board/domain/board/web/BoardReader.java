package com.project.board.domain.board.web;

import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardReader {

    private final BoardRepository boardRepository;

    public Board getBoardBy(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Board> getAllBoardWithPostCategory(Long id) {
        return boardRepository.findAllWithPostCategory(id);
    }
}
