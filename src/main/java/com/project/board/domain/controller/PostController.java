package com.project.board.domain.controller;

import com.project.board.domain.post.dto.PostUpdateRequestDto;
import com.project.board.domain.post.dto.PostWriteRequestDto;
import com.project.board.domain.service.PostService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post/write")
    public Long write(@AuthenticationPrincipal UserDetailsImpl userDetails,
                      @RequestBody PostWriteRequestDto postWriteRequestDto) {
        return postService.write(userDetails.getUsername(), postWriteRequestDto);
    }

    @PutMapping("/api/post/{id}/update")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return postService.update(userDetails.getUsername(), id, postUpdateRequestDto);
    }

    @DeleteMapping("/api/post/{id}/delete")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return postService.delete(userDetails.getUsername(), id);
    }
}
