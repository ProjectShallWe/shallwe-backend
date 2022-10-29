package com.project.board.controller;

import com.project.board.domain.like.dto.LikePostDeleteReqestDto;
import com.project.board.domain.like.dto.LikePostRequestDto;
import com.project.board.domain.like.web.LikePostService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like-post")
public class LikePostController {

    private final LikePostService likePostService;

    @PostMapping
    public Long like(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @RequestParam("post") Long postId,
                     @RequestBody LikePostRequestDto likePostRequestDto) {
        return likePostService.like(userDetails.getUsername(), postId, likePostRequestDto);
    }
}
