package com.project.board.domain.service;

import com.project.board.domain.board.dto.BoardCategoryRequestDto;
import com.project.board.domain.board.web.BoardCategory;
import com.project.board.domain.board.web.BoardCategoryRepository;
import com.project.board.domain.user.web.Role;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class BoardCategoryService {

    private final BoardCategoryRepository boardCategoryRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long open(String email, BoardCategoryRequestDto boardCategoryRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        if (user.getRole().equals(Role.ADMIN)){
            return boardCategoryRepository.save(boardCategoryRequestDto.toEntity()).getId();
        }
        return -1L;
    }

    @Transactional
    public Long update(String email, Long id, BoardCategoryRequestDto boardCategoryRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        BoardCategory boardCategory = boardCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판 카테고리를 찾을 수 없습니다. boardCategory_id : " + id));
        if (user.getRole().equals(Role.ADMIN)) {
            boardCategory.update(boardCategoryRequestDto.getTopic());

            return id;
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        BoardCategory boardCategory = boardCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판 카테고리를 찾을 수 없습니다. boardCategory_id : " + id));
        if (user.getRole().equals(Role.ADMIN)) {
            boardCategoryRepository.delete(boardCategory);
            return id;
        }

        return -1L;
    }
}
