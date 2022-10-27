package com.project.board.domain.board.web;

import com.project.board.domain.board.dto.BoardCategoryRequestDto;
import com.project.board.domain.board.dto.BoardCategoryResponseDto;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.exception.InvalidParamException;
import com.project.board.infrastructure.board.BoardCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardCategoryService {

    private final BoardCategoryRepository boardCategoryRepository;

    @Transactional
    public Long create(BoardCategoryRequestDto boardCategoryRequestDto) {
        BoardCategory boardCategory = boardCategoryRequestDto.toEntity();

        validCheck(boardCategory);
        return boardCategoryRepository.save(boardCategory).getId();
    }

    private void validCheck(BoardCategory boardCategory) {
        if (StringUtils.isEmpty(boardCategory.getTopic())) throw new InvalidParamException("BoardCategory.topic");
    }

    @Transactional
    public Long update(Long id, BoardCategoryRequestDto boardCategoryRequestDto) {
        BoardCategory boardCategory =  boardCategoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        boardCategory.update(boardCategoryRequestDto.getTopic());
        return boardCategory.getId();
    }

    @Transactional
    public Long delete(Long id) {
        BoardCategory boardCategory = boardCategoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        boardCategoryRepository.delete(boardCategory);
        return boardCategory.getId();
    }

    @Transactional(readOnly = true)
    public List<BoardCategoryResponseDto> getBoardCategoriesWithBoards() {
        List<BoardCategory> boardCategories = boardCategoryRepository.findAllWithBoards();
        List<BoardCategoryResponseDto> boardCategoryResponseDtos = boardCategories.stream()
                .map(BoardCategoryResponseDto::new)
                .collect(Collectors.toList());
        return boardCategoryResponseDtos;
    }

}
