package com.project.board.domain.controller;

import com.project.board.domain.post.dto.PostWriteRequestDto;
import com.project.board.domain.post.service.PostService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post/write")
    public Long write(@AuthenticationPrincipal UserDetailsImpl userDetails,
                      @RequestBody PostWriteRequestDto postWriteRequestDto) {
        return postService.write(userDetails.getUsername(), postWriteRequestDto);
    }
}
