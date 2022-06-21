package com.project.board.domain.controller;

import com.project.board.domain.board.dto.BoardCategoryRequestDto;
import com.project.board.domain.board.dto.BoardCategoryResponseDto;
import com.project.board.domain.board.web.BoardCategoryService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board-category")
public class BoardCategoryController {

    private final BoardCategoryService boardCategoryService;

    @PostMapping
    public Long create(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @RequestBody BoardCategoryRequestDto boardCategoryRequestDto) {
        return boardCategoryService.create(userDetails.getUsername(), boardCategoryRequestDto);
    }

    @PutMapping("/{id}")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody BoardCategoryRequestDto boardCategoryRequestDto) {
        return boardCategoryService.update(userDetails.getUsername(), id, boardCategoryRequestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return boardCategoryService.delete(userDetails.getUsername(), id);
    }

    @GetMapping("/board")
    public List<BoardCategoryResponseDto> getBoardCategoriesWithBoards() {
        return boardCategoryService.getBoardCategoriesWithBoards();
    }
}
