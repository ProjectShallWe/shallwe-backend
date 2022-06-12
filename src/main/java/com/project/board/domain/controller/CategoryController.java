package com.project.board.domain.controller;

import com.project.board.domain.board.dto.BoardCategoryRequestDto;
import com.project.board.domain.service.CategoryService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/api/category/open")
    public Long open(@AuthenticationPrincipal UserDetailsImpl userDetails,
                     @RequestBody BoardCategoryRequestDto boardCategoryRequestDto) {
        return categoryService.open(userDetails.getUsername(), boardCategoryRequestDto);
    }

    @PutMapping("/api/category/{id}/update")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody BoardCategoryRequestDto boardCategoryRequestDto) {
        return categoryService.update(userDetails.getUsername(), id, boardCategoryRequestDto);
    }

    @DeleteMapping("/api/category/{id}/delete")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return categoryService.delete(userDetails.getUsername(), id);
    }
}
