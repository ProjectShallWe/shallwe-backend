package com.project.board.controller;

import com.project.board.domain.board.dto.BoardCategoryRequestDto;
import com.project.board.domain.board.dto.BoardCategoryResponseDto;
import com.project.board.domain.board.web.BoardCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board-category")
public class BoardCategoryController {

    private final BoardCategoryService boardCategoryService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long create(@RequestBody BoardCategoryRequestDto boardCategoryRequestDto) {
        return boardCategoryService.create(boardCategoryRequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long update(@PathVariable Long id,
                       @RequestBody BoardCategoryRequestDto boardCategoryRequestDto) {
        return boardCategoryService.update(id, boardCategoryRequestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long delete(@PathVariable Long id) {
        return boardCategoryService.delete(id);
    }

    @GetMapping("/board")
    public List<BoardCategoryResponseDto> getBoardCategoriesWithBoards() {
        return boardCategoryService.getBoardCategoriesWithBoards();
    }
}
