package com.project.board.controller;

import com.project.board.domain.post.dto.*;
import com.project.board.domain.post.web.PostService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Long write(@AuthenticationPrincipal UserDetailsImpl userDetails,
                      @RequestParam("category") Long postCategoryId,
                      @RequestBody PostWriteRequestDto postWriteRequestDto) {
        return postService.write(userDetails.getUsername(), postCategoryId, postWriteRequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated() " +
            "and ((#PUReqDto.writer == principal.username) " +
            "or hasRole('ROLE_ADMIN'))")
    public Long update(@PathVariable Long id,
                       @RequestBody PostUpdateRequestDto PUReqDto) {
        return postService.update(id, PUReqDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated() " +
            "and ((#PDReqDto.writer == principal.username) " +
            "or hasRole('ROLE_ADMIN'))")
    public Long delete(@PathVariable Long id,
                       @RequestBody PostDeleteRequestDto PDReqDto) {
        return postService.delete(id);
    }

    @GetMapping("/all")
    public Page<PostsResponseDto> getPostsInBoard(
            @RequestParam("board") Long boardId,
            @RequestParam Integer page) {
        return postService.getPostsInBoard(boardId, page);
    }

    @GetMapping
    public Page<PostsResponseDto> getPostsInPostCategory(
            @RequestParam("category") Long postCategoryId,
            @RequestParam Integer page) {
        return postService.getPostsInPostCategory(postCategoryId, page);
    }

    @GetMapping("/search")
    public Page<PostsResponseDto> getPostsBySearchKeywordInBoard(
            @RequestParam("board") Long boardId,
            @RequestParam Integer page,
            @RequestParam(name = "category", required = false) Long postCategoryId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword) {
        return postService.getPostsBySearchWordInBoard(boardId, postCategoryId, type, keyword, page);
    }

    @GetMapping("/search/common")
    public Page<PostsCommonSearchResDto> getPostsBySearchKeyword(
            @RequestParam("keyword") String keyword,
            @RequestParam Integer page) {
        return postService.getPostsByKeyword(keyword, page);

    }

    @GetMapping("/{id}")
    public PostDetailsResponseDto getPostDetails(@PathVariable Long id,
                                                 HttpServletRequest req,
                                                 HttpServletResponse res) {
        return postService.getPostDetails(id, req, res);
    }

    @GetMapping("/nickname")
    public Page<PostsUserResDto> getPostsByNickname(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Integer page) {
        return postService.getPostsByNickname(userDetails.getUsername(), page);
    }
}
