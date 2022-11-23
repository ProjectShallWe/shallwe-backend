package com.project.board.controller;

import com.project.board.domain.post.dto.RealTimeBestPostResDto;
import com.project.board.domain.post.dto.RecommendPostsWithBoardResDto;
import com.project.board.domain.post.web.PostRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/recommend")
public class PostRecommendScheduledController {

    private final PostRecommendService postRecommendService;

    @GetMapping("/all")
    public RecommendPostsWithBoardResDto getRecommendPostsFromRedis() {
        return postRecommendService.getRecommendPostsFromRedis();
    }

    @GetMapping
    public RecommendPostsWithBoardResDto getRecommendPostsInBoardFromRedis(@RequestParam("board") Long boardId) {
        return postRecommendService.getRecommendPostsInBoardFromRedis(boardId);
    }

    @GetMapping("/main")
    public Page<RealTimeBestPostResDto> getRealTimeBastPosts (@RequestParam("page") Integer page) {
        return postRecommendService.getRealTimeBestPosts(page);
    }
}
