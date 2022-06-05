package com.project.board.domain.controller;

import com.project.board.domain.board.dto.BoardRequestDto;
import com.project.board.domain.board.service.BoardService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/board/open")
    public Long open(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.open(userDetails.getUsername(), boardRequestDto);
    }

    @PutMapping("/api/board/{id}/update")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody BoardRequestDto boardRequestDto) {
        return boardService.update(userDetails.getUsername(), id, boardRequestDto);
    }

    @DeleteMapping("/api/board/{id}/delete")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return boardService.delete(userDetails.getUsername(), id);
    }
}
