package com.project.board.domain.controller;


import com.project.board.domain.post.dto.PostCategoryRequestDto;
import com.project.board.domain.post.web.PostCategoryService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostCategoryController {

    private final PostCategoryService postCategoryService;

    @PostMapping("/api/board/{boardId}/post-category")
    public Long open(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @PathVariable Long boardId,
                     @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        return postCategoryService.open(userDetails.getUsername(), boardId, postCategoryRequestDto);
    }

    @PutMapping("/api/post-category/{postCategoryId}")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long postCategoryId,
                       @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        return postCategoryService.update(userDetails.getUsername(), postCategoryId, postCategoryRequestDto);
    }

    @DeleteMapping("/api/post-category/{postCategoryId}")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long postCategoryId) {
        return postCategoryService.delete(userDetails.getUsername(), postCategoryId);
    }
}
