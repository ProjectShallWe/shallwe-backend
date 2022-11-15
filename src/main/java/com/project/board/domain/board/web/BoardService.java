package com.project.board.domain.board.web;

import com.project.board.domain.board.dto.BoardInfoResDto;
import com.project.board.domain.board.dto.BoardRecommendResDto;
import com.project.board.domain.board.dto.BoardRequestDto;
import com.project.board.domain.board.dto.BoardResponseDto;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.exception.InvalidParamException;
import com.project.board.global.redis.CacheKey;
import com.project.board.infrastructure.board.BoardCategoryRepository;
import com.project.board.infrastructure.board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardCategoryRepository boardCategoryRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Long create(Long boardCategoryId, BoardRequestDto boardRequestDto) {
        BoardCategory boardCategory = boardCategoryRepository.findById(boardCategoryId)
                .orElseThrow(EntityNotFoundException::new);
        Board board = boardRequestDto.toEntity(boardCategory);

        validCheck(board);
        return boardRepository.save(board).getId();
    }

    private void validCheck(Board board) {
        if (board.getBoardCategory() == null) throw new InvalidParamException("Board.boardCategory");
        if (StringUtils.isEmpty(board.getTitle())) throw new InvalidParamException("Board.title");
    }

    @Transactional
    public Long update(Long boardId, BoardRequestDto boardRequestDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);
        board.update(boardRequestDto.getTitle());
        return boardId;

    }

    @Transactional
    public Long delete(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);
        boardRepository.delete(board);
        return boardId;
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getBoardWithPostCategories(Long boardId) {
        List<Board> boards = boardRepository.findAllWithPostCategories(boardId);
        List<BoardResponseDto> boardResponseDtos = boards.stream()
                .map(BoardResponseDto::new)
                .collect(Collectors.toList());
        return boardResponseDtos;
    }

    @Transactional(readOnly = true)
    public List<BoardInfoResDto> getBoardsBySearchWord(String keyword) {
        List<Board> boards = boardRepository.findBoardsBySearchWord(keyword);
        List<BoardInfoResDto> boardInfoResDtos = boards.stream()
                .map(BoardInfoResDto::new)
                .collect(Collectors.toList());
        return boardInfoResDtos;
    }

    @Cacheable(value = CacheKey.RECOMMEND_BOARDS, unless = "#result == null")
    @Transactional(readOnly = true)
    public BoardRecommendResDto getBoardsByRecommendScore() {
        List<Board> boards = boardRepository.findBoardsByRecommendScore(LocalDateTime.now(), LocalDateTime.now().minusHours(12));
        return new BoardRecommendResDto(boards.stream()
                .map(Board::getId)
                .collect(Collectors.toList()));
    }
}
