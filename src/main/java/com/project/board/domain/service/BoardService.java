package com.project.board.domain.service;

import com.project.board.domain.board.dto.BoardRequestDto;
import com.project.board.domain.board.dto.BoardResponseDto;
import com.project.board.domain.board.web.*;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardReader boardReader;
    private final BoardStore boardStore;
    private final UserReader userReader;
    private final BoardCategoryRepository boardCategoryRepository;

    @Transactional
    public Long open(String email, Long boardCategoryId, BoardRequestDto boardRequestDto) {
        User user = userReader.getUserBy(email);
        BoardCategory boardCategory = boardCategoryRepository.findById(boardCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("게시판 카테고리를 찾을 수 없습니다."));
        if (isAdmin(user)){
            return boardStore.store(boardRequestDto.toEntity(boardCategory)).getId();
        }
        return -1L;
    }

    @Transactional
    public Long update(String email, Long boardId, BoardRequestDto boardRequestDto) {
        User user = userReader.getUserBy(email);
        Board board = boardReader.getBoardBy(boardId);
        if (isAdmin(user)) {
            board.update(boardRequestDto.getTitle());

            return boardId;
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long boardId) {
        User user = userReader.getUserBy(email);
        Board board = boardReader.getBoardBy(boardId);
        if (isAdmin(user)) {
            boardStore.delete(board);
            return boardId;
        }
        return -1L;
    }

    private Boolean isAdmin (User user) {
        return user.getRole().equals(User.Role.ADMIN);
    }

    public List<BoardResponseDto> getBoardWithPostCategory(Long id) {
        List<Board> boards = boardReader.getAllBoardWithPostCategory(id);
        List<BoardResponseDto> boardResponseDtos = boards.stream()
                .map(b -> new BoardResponseDto(b))
                .collect(Collectors.toList());
        return boardResponseDtos;
    }
}
