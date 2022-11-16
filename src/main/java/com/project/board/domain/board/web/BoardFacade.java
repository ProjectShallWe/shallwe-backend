package com.project.board.domain.board.web;

import com.project.board.domain.board.dto.BoardRequestDto;
import com.project.board.domain.post.dto.PostCategoryRequestDto;
import com.project.board.domain.post.web.PostCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardFacade {
    private final BoardService boardService;
    private final PostCategoryService postCategoryService;
    private final static String common = "일반";

    public Long createBoardWithBasicPostCategory(Long boardCategoryId, BoardRequestDto boardRequestDto) {
        Long savedBoardId = boardService.create(boardCategoryId, boardRequestDto);
        postCategoryService.create(savedBoardId, new PostCategoryRequestDto(common));
        return savedBoardId;
    }
}
