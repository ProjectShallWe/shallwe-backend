package com.project.board.domain.post.web;

import com.project.board.domain.post.dto.*;
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
    private final PostCategoryReader postCategoryReader;
    private final PostReader postReader;
    private final PostStore postStore;

    @Transactional
    public Long write(String email, Long postCategoryId, PostWriteRequestDto writeDto) {
        User user = userReader.getUserBy(email);
        PostCategory postCategory = postCategoryReader.getPostCategoryBy(postCategoryId);
        return postStore.store(writeDto.toEntity(user, postCategory)).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto updateDto) {
        Post post = postReader.getPostBy(id);
        post.update(updateDto.getTitle(), updateDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        Post post = postReader.getPostBy(id);
        post.updateStatusToDisable();
        return id;
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsInBoard(Long id, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postReader.getPostsInBoard(id, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsInPostCategory(Long id, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postReader.getPostsInPostCategory(id, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    @Transactional(readOnly = true)
    public PostDetailsResponseDto getPostDetails(Long id) {
        PostDetailsQueryDto postDetailsQueryDto = postReader.getPostDetails(id);
        PostDetailsResponseDto postDetailsResponseDtos
                = new PostDetailsResponseDto(postDetailsQueryDto);
        return postDetailsResponseDtos;
    }

    public Page<PostsResponseDto> getPostsBySearchWordInBoard(Long boardId, String type, String keyword, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postReader.getPostsBySearchWordInBoard(boardId, type, keyword, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }
}
