package com.project.board.domain.service;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardRepository;
import com.project.board.domain.post.dto.PostCategoryRequestDto;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.domain.post.web.PostCategoryReader;
import com.project.board.domain.post.web.PostCategoryStore;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostCategoryService {

    private final UserReader userReader;
    private final PostCategoryReader postCategoryReader;
    private final PostCategoryStore postCategoryStore;
    private final BoardRepository boardRepository;


    @Transactional
    public Long open(String email, Long boardId, PostCategoryRequestDto postCategoryDto) {
        User user = userReader.getUserBy(email);
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판을 찾을 수 없습니다. board_id: " + boardId));
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

    private Boolean isAdmin(User user) {
        return user.getRole().equals(User.Role.ADMIN);
    }
}
