package com.project.board.controller;


import com.project.board.domain.post.dto.PostCategoryRequestDto;
import com.project.board.domain.post.web.PostCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post-category")
public class PostCategoryController {

    private final PostCategoryService postCategoryService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long create(@RequestParam("board") Long boardId,
                       @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        return postCategoryService.create(boardId, postCategoryRequestDto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long update(@PathVariable Long id,
                       @RequestBody PostCategoryRequestDto postCategoryRequestDto) {
        return postCategoryService.update(id, postCategoryRequestDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Long delete(@PathVariable Long id) {
        return postCategoryService.delete(id);
    }
}
