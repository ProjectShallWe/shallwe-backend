package com.project.board.controller;

import com.project.board.domain.comment.dto.CommentDeleteRequestDto;
import com.project.board.domain.comment.dto.CommentUpdateRequestDto;
import com.project.board.domain.comment.dto.CommentWriteRequestDto;
import com.project.board.domain.comment.dto.ParentCommentsResponseDto;
import com.project.board.domain.comment.web.CommentService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Long writeParentComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                   @RequestParam("post") Long postId,
                                   @RequestBody CommentWriteRequestDto commentWriteRequestDto) {
        return commentService.writeParentComment(userDetails.getUsername(), postId, commentWriteRequestDto);
    }

    @PostMapping("/{commentId}")
    public Long writeChildComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                  @RequestParam("post") Long postId,
                                  @PathVariable Long commentId,
                                  @RequestBody CommentWriteRequestDto commentWriteRequestDto) {
        return commentService.writeChildComment(userDetails.getUsername(), postId, commentId, commentWriteRequestDto);
    }

    @PutMapping("/{commentId}")
    @PreAuthorize("isAuthenticated() " +
            "and ((#CUReqDto.writer == principal.username) " +
            "or hasRole('ROLE_ADMIN'))")
    public Long update(@PathVariable Long commentId,
                       @RequestBody CommentUpdateRequestDto CUReqDto) {
        return commentService.update(commentId, CUReqDto);
    }

    @DeleteMapping("/{commentId}")
    @PreAuthorize("isAuthenticated() " +
            "and ((#CDReqDto.writer == principal.username) " +
            "or hasRole('ROLE_ADMIN'))")
    public Long delete(@PathVariable Long commentId,
                       @RequestBody CommentDeleteRequestDto CDReqDto){
        return commentService.delete(commentId);
    }

    @GetMapping
    public List<ParentCommentsResponseDto> getCommentsInPost(@RequestParam("post") Long postId) {
        return commentService.getCommentsInPost(postId);
    }
}
