package com.project.board.controller;

import com.project.board.domain.board.dto.BoardInfoResDto;
import com.project.board.domain.board.dto.BoardRecommendResDto;
import com.project.board.domain.board.dto.BoardRequestDto;
import com.project.board.domain.board.dto.BoardResponseDto;
import com.project.board.domain.board.web.BoardFacade;
import com.project.board.domain.board.web.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardFacade boardFacade;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long createBoardWithBasicPostCategory(@RequestParam("category") Long boardCategoryId,
                       @RequestBody BoardRequestDto boardRequestDto) {
        return boardFacade.createBoardWithBasicPostCategory(boardCategoryId, boardRequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long update(@PathVariable Long id,
                       @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.update(id, boardRequestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long delete(@PathVariable Long id) {
        return boardService.delete(id);
    }

    @GetMapping("/{id}")
    public List<BoardResponseDto> getBoardWithPostCategories (
                       @PathVariable Long id) {
        return boardService.getBoardWithPostCategories(id);
    }

    @GetMapping
    public List<BoardInfoResDto> getBoardsBySearchWord (
            @RequestParam("keyword") String keyword) {
        return boardService.getBoardsBySearchWord(keyword);
    }

    @GetMapping("/recommend")
    public BoardRecommendResDto getBoardsByRecommendScore() {
        return boardService.getBoardsByRecommendScore();
    }
}
