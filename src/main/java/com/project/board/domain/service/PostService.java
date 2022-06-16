package com.project.board.domain.service;

import com.project.board.domain.post.dto.PostUpdateRequestDto;
import com.project.board.domain.post.dto.PostWriteRequestDto;
import com.project.board.domain.post.dto.PostsResponseDto;
import com.project.board.domain.post.web.*;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserReader userReader;
    private final PostReader postReader;
    private final PostStore postStore;
    private final PostCategoryReader postCategoryReader;

    @Transactional
    public Long write(String email, Long id, PostWriteRequestDto writeDto) {
        User user = userReader.getUserBy(email);
        PostCategory postCategory = postCategoryReader.getPostCategoryBy(id);
        return postStore.store(writeDto.toEntity(user, postCategory)).getId();
    }

    @Transactional
    public Long update(String email, Long id, PostUpdateRequestDto updateDto) {
        User user = userReader.getUserBy(email);
        Post post = postReader.getPostBy(id);
        if (isPostWriter(user, post)) {
           post.update(updateDto.getTitle(), updateDto.getContent());
           return id;
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long id) {
        User user = userReader.getUserBy(email);
        Post post = postReader.getPostBy(id);
        if (isPostWriter(user, post) || isAdmin(user)) {
            post.updateStatusToDisable();
            return id;
        }
        return -1L;
    }

    private Boolean isPostWriter(User user, Post post) {
        return user.getEmail().equals(post.getUser().getEmail());
    }

    private Boolean isAdmin(User user) {
        return user.getRole().equals(User.Role.ADMIN);
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsInPostCategory(Long id, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Post> posts = postReader.getPostsInPostCategory(id, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = posts.map(
                post -> new PostsResponseDto(post));
        return postsResponseDtos;
    }
}
