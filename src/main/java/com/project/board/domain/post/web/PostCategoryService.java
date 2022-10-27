package com.project.board.domain.post.web;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.dto.PostCategoryRequestDto;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.exception.InvalidParamException;
import com.project.board.infrastructure.board.BoardRepository;
import com.project.board.infrastructure.post.PostCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PostCategoryService {

    private final BoardRepository boardRepository;
    private final PostCategoryRepository postCategoryRepository;


    @Transactional
    public Long create(Long boardId, PostCategoryRequestDto postCategoryDto) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);
        PostCategory postCategory = postCategoryDto.toEntity(board);

        validCheck(postCategory);
        return postCategoryRepository.save(postCategory).getId();
    }

    private void validCheck(PostCategory postCategory) {
        if (postCategory.getBoard() == null) throw new InvalidParamException("PostCategory.board");
        if (StringUtils.isEmpty(postCategory.getTopic())) throw new InvalidParamException("PostCategory.topic");
    }

    @Transactional
    public Long update(Long postCategoryId, PostCategoryRequestDto postCategoryDto) {
        PostCategory postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(EntityNotFoundException::new);
        postCategory.update(postCategoryDto.getTopic());
        return postCategoryId;
    }

    @Transactional
    public Long delete(Long postCategoryId) {
        PostCategory postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(EntityNotFoundException::new);
        postCategoryRepository.delete(postCategory);
        return postCategoryId;
    }
}
