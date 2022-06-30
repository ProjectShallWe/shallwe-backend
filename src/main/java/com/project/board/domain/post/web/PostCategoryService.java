package com.project.board.domain.post.web;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardReader;
import com.project.board.domain.post.dto.PostCategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostCategoryService {

    private final BoardReader boardReader;
    private final PostCategoryReader postCategoryReader;
    private final PostCategoryStore postCategoryStore;


    @Transactional
    public Long create(Long boardId, PostCategoryRequestDto postCategoryDto) {
        Board board = boardReader.getBoardBy(boardId);
        return postCategoryStore.store(postCategoryDto.toEntity(board)).getId();
    }

    @Transactional
    public Long update(Long postCategoryId, PostCategoryRequestDto postCategoryDto) {
        PostCategory postCategory = postCategoryReader.getPostCategoryBy(postCategoryId);
        postCategory.update(postCategoryDto.getTopic());
        return postCategoryId;
    }

    @Transactional
    public Long delete(Long postCategoryId) {
        PostCategory postCategory = postCategoryReader.getPostCategoryBy(postCategoryId);
        postCategoryStore.delete(postCategory);
        return postCategoryId;
    }
}
