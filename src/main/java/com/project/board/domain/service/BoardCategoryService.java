package com.project.board.domain.service;

import com.project.board.domain.board.dto.BoardCategoryRequestDto;
import com.project.board.domain.board.dto.BoardCategoryResponseDto;
import com.project.board.domain.board.web.BoardCategory;
import com.project.board.domain.board.web.BoardCategoryReader;
import com.project.board.domain.board.web.BoardCategoryStore;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardCategoryService {

    private final UserReader userReader;
    private final BoardCategoryReader boardCategoryReader;
    private final BoardCategoryStore boardCategoryStore;

    @Transactional
    public Long open(String email, BoardCategoryRequestDto boardCategoryRequestDto) {
        User user = userReader.getUserBy(email);
        if (isAdmin(user)){
            return boardCategoryStore.store(boardCategoryRequestDto.toEntity()).getId();
        }
        return -1L;
    }

    @Transactional
    public Long update(String email, Long id, BoardCategoryRequestDto boardCategoryRequestDto) {
        User user = userReader.getUserBy(email);
        BoardCategory boardCategory =  boardCategoryReader.getBoardCategoryBy(id);
        if (isAdmin(user)) {
            boardCategory.update(boardCategoryRequestDto.getTopic());

            return id;
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long id) {
        User user = userReader.getUserBy(email);
        BoardCategory boardCategory = boardCategoryReader.getBoardCategoryBy(id);
        if (isAdmin(user)) {
            boardCategoryStore.delete(boardCategory);
            return id;
        }
        return -1L;
    }

    private Boolean isAdmin(User user) {
        return user.getRole().equals(User.Role.ADMIN);
    }

    @Transactional(readOnly = true)
    public List<BoardCategoryResponseDto> getAllBoardCategoryWithBoard() {
        List<BoardCategory> boardCategories = boardCategoryReader.getAllBoardCategoryWithBoard();
        List<BoardCategoryResponseDto> boardCategoryResponseDtos = boardCategories.stream()
                .map(bc -> new BoardCategoryResponseDto(bc))
                .collect(Collectors.toList());
        return boardCategoryResponseDtos;
    }
}
