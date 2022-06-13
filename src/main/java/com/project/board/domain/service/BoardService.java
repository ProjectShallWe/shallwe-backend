package com.project.board.domain.service;

import com.project.board.domain.board.dto.BoardRequestDto;
import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardCategory;
import com.project.board.domain.board.web.BoardCategoryRepository;
import com.project.board.domain.board.web.BoardRepository;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardCategoryRepository boardCategoryRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long open(String email, Long boardCategoryId, BoardRequestDto boardRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        BoardCategory boardCategory = boardCategoryRepository.findById(boardCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("게시판 카테고리를 찾을 수 없습니다."));
        if (user.getRole().equals(User.Role.ADMIN)){
            return boardRepository.save(boardRequestDto.toEntity(boardCategory)).getId();
        }
        return -1L;
    }

    @Transactional
    public Long update(String email, Long id, BoardRequestDto boardRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판을 찾을 수 없습니다. board_id : " + id));
        if (user.getRole().equals(User.Role.ADMIN)) {
            board.update(boardRequestDto.getTitle());

            return id;
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판을 찾을 수 없습니다. board_id : " + id));
        if (user.getRole().equals(User.Role.ADMIN)) {
            boardRepository.delete(board);
            return id;
        }

        return -1L;
    }
}
