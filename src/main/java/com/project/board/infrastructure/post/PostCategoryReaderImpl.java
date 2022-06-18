package com.project.board.infrastructure.post;

import com.project.board.domain.post.web.PostCategoryReader;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCategoryReaderImpl implements PostCategoryReader {

    private final PostCategoryRepository postCategoryRepository;

    public PostCategory getPostCategoryBy(Long id) {
        return postCategoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
