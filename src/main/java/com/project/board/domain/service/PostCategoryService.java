package com.project.board.domain.service;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardRepository;
import com.project.board.domain.post.dto.PostCategoryRequestDto;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.domain.post.web.PostCategoryRepository;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long open(String email, Long boardId, PostCategoryRequestDto postCategoryRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판을 찾을 수 없습니다. board_id: " + boardId));
        if (user.getRole().equals(User.Role.ADMIN)){
            return postCategoryRepository.save(postCategoryRequestDto.toEntity(board)).getId();
        }
        return -1L;
    }

    @Transactional
    public Long update(String email, Long postCategoryId, PostCategoryRequestDto postCategoryRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        PostCategory postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글 카테고리를 찾을 수 없습니다. post_category_id: " + postCategoryId));
        if (user.getRole().equals(User.Role.ADMIN)) {
            postCategory.update(postCategoryRequestDto.getTopic());

            return postCategoryId;
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long postCategoryId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        PostCategory postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글 카테고리를 찾을 수 없습니다. post_category_id: " + postCategoryId));
        if (user.getRole().equals(User.Role.ADMIN)) {
            postCategoryRepository.delete(postCategory);
            return postCategoryId;
        }
        return -1L;
    }
}
