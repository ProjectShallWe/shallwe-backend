package com.project.board.domain.post.web;

import com.project.board.global.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCategoryStore {

    private final PostCategoryRepository postCategoryRepository;

    public PostCategory store(PostCategory postCategory) {
        validCheck(postCategory);
        return postCategoryRepository.save(postCategory);
    }

    public void delete(PostCategory postCategory) {
        postCategoryRepository.delete(postCategory);
    }

    private void validCheck(PostCategory postCategory) {
        if (postCategory.getBoard() == null) throw new InvalidParamException("PostCategory.board");
        if (StringUtils.isEmpty(postCategory.getTopic())) throw new InvalidParamException("PostCategory.topic");
    }
}
