package com.project.board.domain.controller;


import com.project.board.domain.post.dto.PostCategoryRequestDto;
import com.project.board.domain.post.web.PostCategoryService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post-category")
public class PostCategoryController {

    private final PostCategoryService postCategoryService;

    @PostMapping
    public Long create(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @RequestParam("board") Long boardId,
                     @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        return postCategoryService.create(userDetails.getUsername(), boardId, postCategoryRequestDto);
    }

    @PutMapping("/{id}")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        return postCategoryService.update(userDetails.getUsername(), id, postCategoryRequestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return postCategoryService.delete(userDetails.getUsername(), id);
    }
}
