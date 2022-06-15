package com.project.board.domain.controller;

import com.project.board.domain.board.dto.BoardCategoryRequestDto;
import com.project.board.domain.board.dto.BoardCategoryResponseDto;
import com.project.board.domain.service.BoardCategoryService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardCategoryController {

    private final BoardCategoryService boardCategoryService;

    @PostMapping("/api/board-category")
    public Long open(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @RequestBody BoardCategoryRequestDto boardCategoryRequestDto) {
        return boardCategoryService.open(userDetails.getUsername(), boardCategoryRequestDto);
    }

    @PutMapping("/api/board-category/{id}")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody BoardCategoryRequestDto boardCategoryRequestDto) {
        return boardCategoryService.update(userDetails.getUsername(), id, boardCategoryRequestDto);
    }

    @DeleteMapping("/api/board-category/{id}")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return boardCategoryService.delete(userDetails.getUsername(), id);
    }

    @GetMapping("/api/board-category/board")
    public List<BoardCategoryResponseDto> getBoardCategoryWithBoard() {
        return boardCategoryService.getAllBoardCategoryWithBoard();
    }
}
