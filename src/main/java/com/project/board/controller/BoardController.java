package com.project.board.controller;

import com.project.board.domain.board.dto.BoardRequestDto;
import com.project.board.domain.board.dto.BoardResponseDto;
import com.project.board.domain.board.web.BoardService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public Long create(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @RequestParam("category") Long boardCategoryId,
                     @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.create(userDetails.getUsername(), boardCategoryId, boardRequestDto);
    }

    @PutMapping("/{id}")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.update(userDetails.getUsername(), id, boardRequestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return boardService.delete(userDetails.getUsername(), id);
    }

    @GetMapping("/{id}")
    public List<BoardResponseDto> getBoardWithPostCategories (
                       @PathVariable Long id) {
        return boardService.getBoardWithPostCategories(id);
    }
}
