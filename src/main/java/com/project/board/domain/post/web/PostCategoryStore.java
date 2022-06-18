package com.project.board.domain.post.web;

import com.project.board.domain.post.web.PostCategory;

public interface PostCategoryStore {

    PostCategory store(PostCategory postCategory);
    void delete(PostCategory postCategory);
}
