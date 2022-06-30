package com.project.board.domain.board.web;

import com.project.board.domain.board.dto.BoardCategoryRequestDto;
import com.project.board.domain.board.dto.BoardCategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardCategoryService {

    private final BoardCategoryReader boardCategoryReader;
    private final BoardCategoryStore boardCategoryStore;

    @Transactional
    public Long create(BoardCategoryRequestDto boardCategoryRequestDto) {
        return boardCategoryStore.store(boardCategoryRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, BoardCategoryRequestDto boardCategoryRequestDto) {
        BoardCategory boardCategory =  boardCategoryReader.getBoardCategoryBy(id);
        boardCategory.update(boardCategoryRequestDto.getTopic());
        return boardCategory.getId();
    }

    @Transactional
    public Long delete(Long id) {
        BoardCategory boardCategory = boardCategoryReader.getBoardCategoryBy(id);
        boardCategoryStore.delete(boardCategory);
        return boardCategory.getId();
    }

    @Transactional(readOnly = true)
    public List<BoardCategoryResponseDto> getBoardCategoriesWithBoards() {
        List<BoardCategory> boardCategories = boardCategoryReader.getBoardCategoriesWithBoards();
        List<BoardCategoryResponseDto> boardCategoryResponseDtos = boardCategories.stream()
                .map(BoardCategoryResponseDto::new)
                .collect(Collectors.toList());
        return boardCategoryResponseDtos;
    }

}
