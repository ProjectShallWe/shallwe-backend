package com.project.board.domain.controller;

import com.project.board.domain.comment.dto.CommentUpdateRequestDto;
import com.project.board.domain.comment.dto.CommentWriteRequestDto;
import com.project.board.domain.service.CommentService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping(value = {"/api/post/{postId}/comment/{commentId}",
                          "/api/post/{postId}/comment"})
    public Long write(@AuthenticationPrincipal UserDetailsImpl userDetails,
                      @PathVariable Long postId,
                      @PathVariable(required = false) Long commentId,
                      @RequestBody CommentWriteRequestDto commentWriteRequestDto) {
        return commentService.write(userDetails.getUsername(), postId, commentId, commentWriteRequestDto);
    }

    @PutMapping("/api/comment/{commentId}")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long commentId,
                       @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return commentService.update(userDetails.getUsername(), commentId, commentUpdateRequestDto);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long commentId){
        return commentService.delete(userDetails.getUsername(), commentId);
    }
}
