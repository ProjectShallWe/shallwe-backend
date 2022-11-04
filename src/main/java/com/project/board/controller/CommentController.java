package com.project.board.controller;

import com.project.board.domain.comment.dto.*;
import com.project.board.domain.comment.web.CommentService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @PutMapping("/{id}")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody CommentUpdateRequestDto CUReqDto) {
        return commentService.update(userDetails.getUsername(), id, CUReqDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return commentService.delete(userDetails.getUsername(), id);
    }

    @GetMapping
    public List<ParentCommentsResponseDto> getCommentsInPost(@RequestParam("post") Long postId) {
        return commentService.getCommentsInPost(postId);
    }

    @GetMapping("/nickname")
    public Page<CommentsUserResDto> getCommentsByNickname(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Integer page) {
        return commentService.getCommentsByNickname(userDetails.getUsername(), page);
    }
}
