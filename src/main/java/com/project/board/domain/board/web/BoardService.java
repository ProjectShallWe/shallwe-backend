package com.project.board.domain.board.web;

import com.project.board.domain.board.dto.BoardInfoResDto;
import com.project.board.domain.board.dto.BoardRequestDto;
import com.project.board.domain.board.dto.BoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardCategoryReader boardCategoryReader;
    private final BoardReader boardReader;
    private final BoardStore boardStore;

    @Transactional
    public Long create(Long boardCategoryId, BoardRequestDto boardRequestDto) {
        BoardCategory boardCategory = boardCategoryReader.getBoardCategoryBy(boardCategoryId);
        return boardStore.store(boardRequestDto.toEntity(boardCategory)).getId();
    }

    @Transactional
    public Long update(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardReader.getBoardBy(boardId);
        board.update(boardRequestDto.getTitle());
        return boardId;

    }

    @Transactional
    public Long delete(Long boardId) {
        Board board = boardReader.getBoardBy(boardId);
        boardStore.delete(board);
        return boardId;
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoardWithPostCategories(Long boardId) {
        List<Board> boards = boardReader.getBoardWithPostCategories(boardId);
        List<BoardResponseDto> boardResponseDtos = boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        return boardResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<BoardInfoResDto> getBoardsBySearchWord(String keyword) {
        List<Board> boards = boardReader.getBoardsBySearchWord(keyword);
        List<BoardInfoResDto> boardInfoResDtos = boards.stream()
                .map(BoardInfoResDto::new)
                .collect(Collectors.toList());
        return boardInfoResDtos;
    }
}
