package com.project.board.controller;

import com.project.board.domain.like.dto.LikeCommentDeleteRequestDto;
import com.project.board.domain.like.dto.LikeCommentRequestDto;
import com.project.board.domain.like.web.LikeCommentService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like-comment")
public class LikeCommentController {

    private final LikeCommentService likeCommentService;

    @PostMapping
    public Long open(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @RequestParam("comment") Long commentId,
                     @RequestBody LikeCommentRequestDto likeCommentRequestDto) {
        return likeCommentService.like(userDetails.getUsername(), commentId, likeCommentRequestDto);
    }

    @DeleteMapping
    @PreAuthorize("isAuthenticated() " +
            "and ((#LCDReqDto.writer == principal.username) " +
            "or hasRole('ROLE_ADMIN'))")
    public Long cancel(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @RequestParam("comment") Long commentId,
                       @RequestBody LikeCommentDeleteRequestDto LCDReqDto) {
        return likeCommentService.cancel(userDetails.getUsername(), commentId);
    }
}
