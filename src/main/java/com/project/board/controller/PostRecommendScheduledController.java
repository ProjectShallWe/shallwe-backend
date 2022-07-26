package com.project.board.controller;

import com.project.board.domain.post.dto.RecommendPostsInBoardResponseDto;
import com.project.board.domain.post.web.PostRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostRecommendScheduledController {

    private final PostRecommendService postRecommendService;

    @GetMapping("/recommend")
    public List<RecommendPostsInBoardResponseDto> getRecommendPostsInBoardFromRedis(@RequestParam("board") Long boardId) {
        return postRecommendService.getRecommendPostsInBoardFromRedis(boardId);
    }
}
