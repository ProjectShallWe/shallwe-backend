package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardReader;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardReaderImpl implements BoardReader {

    private final BoardRepository boardRepository;

    public Board getBoardBy(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<Board> getBoardWithPostCategories(Long id) {
        return boardRepository.findAllWithPostCategories(id);
    }

    public List<Board> getBoardsBySearchWord(String keyword) {
        return boardRepository.findBoardsBySearchWord(keyword);
    }
}
