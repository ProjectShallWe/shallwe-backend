package com.project.board.domain.post.web;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardReader;
import com.project.board.domain.post.dto.PostCategoryRequestDto;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.board.global.util.UserRoleChecker.isAdmin;


@Service
@RequiredArgsConstructor
public class PostCategoryService {

    private final UserReader userReader;
    private final BoardReader boardReader;
    private final PostCategoryReader postCategoryReader;
    private final PostCategoryStore postCategoryStore;


    @Transactional
    public Long create(String email, Long boardId, PostCategoryRequestDto postCategoryDto) {
        User user = userReader.getUserBy(email);
        Board board = boardReader.getBoardBy(boardId);
        if (isAdmin(user)) {
            return postCategoryStore.store(postCategoryDto.toEntity(board)).getId();
        }
        return -1L;
    }

    @Transactional
    public Long update(String email, Long postCategoryId, PostCategoryRequestDto postCategoryDto) {
        User user = userReader.getUserBy(email);
        PostCategory postCategory = postCategoryReader.getPostCategoryBy(postCategoryId);
        if (isAdmin(user)) {
            postCategory.update(postCategoryDto.getTopic());
            return postCategoryId;
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long postCategoryId) {
        User user = userReader.getUserBy(email);
        PostCategory postCategory = postCategoryReader.getPostCategoryBy(postCategoryId);
        if (isAdmin(user)) {
            postCategoryStore.delete(postCategory);
            return postCategoryId;
        }
        return -1L;
    }
}
