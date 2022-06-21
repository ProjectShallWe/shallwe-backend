package com.project.board.domain.board.web;

import com.project.board.domain.board.dto.BoardCategoryRequestDto;
import com.project.board.domain.board.dto.BoardCategoryResponseDto;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.board.global.util.UserRoleChecker.isAdmin;

@Service
@RequiredArgsConstructor
public class BoardCategoryService {

    private final UserReader userReader;
    private final BoardCategoryReader boardCategoryReader;
    private final BoardCategoryStore boardCategoryStore;

    @Transactional
    public Long create(String email, BoardCategoryRequestDto boardCategoryRequestDto) {
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
            return boardCategory.getId();
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long id) {
        User user = userReader.getUserBy(email);
        BoardCategory boardCategory = boardCategoryReader.getBoardCategoryBy(id);
        if (isAdmin(user)) {
            boardCategoryStore.delete(boardCategory);
            return boardCategory.getId();
        }
        return -1L;
    }

    @Transactional(readOnly = true)
    public List<BoardCategoryResponseDto> getBoardCategoriesWithBoards() {
        List<BoardCategory> boardCategories = boardCategoryReader.getBoardCategoriesWithBoards();
        List<BoardCategoryResponseDto> boardCategoryResponseDtos = boardCategories.stream()
                .map(bc -> new BoardCategoryResponseDto(bc))
                .collect(Collectors.toList());
        return boardCategoryResponseDtos;
    }

}
