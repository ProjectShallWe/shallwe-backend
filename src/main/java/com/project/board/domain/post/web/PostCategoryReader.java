package com.project.board.domain.post.web;

import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCategoryReader {

    private final PostCategoryRepository postCategoryRepository;

    public PostCategory getPostCategoryBy(Long id) {
        return postCategoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
