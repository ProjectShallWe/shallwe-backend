package com.project.board.domain.controller;

import com.project.board.domain.like.dto.LikeCommentRequestDto;
import com.project.board.domain.like.web.LikeCommentService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeCommentController {

    private final LikeCommentService likeCommentService;

    @PostMapping("/api/comment/{commentId}/like-comment")
    public Long open(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @PathVariable Long commentId,
                     @RequestBody LikeCommentRequestDto likeCommentRequestDto) {
        return likeCommentService.like(userDetails.getUsername(), commentId, likeCommentRequestDto);
    }

    @DeleteMapping("/api/like-comment/{likeCommentId}")
    public Long cancel(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long likeCommentId) {
        return likeCommentService.cancel(userDetails.getUsername(), likeCommentId);
    }
}
