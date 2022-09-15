package com.project.board.controller;

import com.project.board.domain.post.dto.RecommendPostsResponseDto;
import com.project.board.domain.post.dto.RecommendPostsWithBoardResDto;
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

    @GetMapping("/recommend/all")
    public RecommendPostsWithBoardResDto getRecommendPostsFromRedis() {
        return postRecommendService.getRecommendPostsFromRedis();
    }
    @GetMapping("/recommend")
    public RecommendPostsWithBoardResDto getRecommendPostsInBoardFromRedis(@RequestParam("board") Long boardId) {
        return postRecommendService.getRecommendPostsInBoardFromRedis(boardId);
    }
}
