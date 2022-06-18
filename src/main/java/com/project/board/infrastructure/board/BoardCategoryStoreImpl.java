package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.BoardCategory;
import com.project.board.domain.board.web.BoardCategoryStore;
import com.project.board.global.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoardCategoryStoreImpl implements BoardCategoryStore {

    private final BoardCategoryRepository boardCategoryRepository;

    public BoardCategory store(BoardCategory boardCategory) {
        validCheck(boardCategory);
        return boardCategoryRepository.save(boardCategory);
    }

    public void delete(BoardCategory boardCategory) {
        boardCategoryRepository.delete(boardCategory);
    }

    private void validCheck(BoardCategory boardCategory) {
        if (StringUtils.isEmpty(boardCategory.getTopic())) throw new InvalidParamException("BoardCategory.topic");
    }
}
