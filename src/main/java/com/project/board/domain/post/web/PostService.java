package com.project.board.domain.post.web;

import com.project.board.domain.post.dto.*;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.project.board.global.util.UserRoleChecker.isAdmin;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserReader userReader;
    private final PostCategoryReader postCategoryReader;
    private final PostReader postReader;
    private final PostStore postStore;

    public final static Long LIKE_WEIGHTED_VALUE = 3L;
    public final static Long COMMENT_WEIGHTED_VALUE = 7L;

    @Transactional
    public Long write(String email, Long postCategoryId, PostWriteRequestDto writeDto) {
        User user = userReader.getUserBy(email);
        PostCategory postCategory = postCategoryReader.getPostCategoryBy(postCategoryId);
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
    public Page<PostsResponseDto> getPostsByPostTitleInBoard(Long id, String title, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postReader.getPostsByPostTitleInBoard(id, title, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsByPostContentInBoard(Long id, String content, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postReader.getPostsByPostContentInBoard(id, content, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsByPostTitleOrPostContentInBoard(Long id, String keyword, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postReader.getPostsByPostTitleOrPostContentInBoard(id, keyword, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsByUserNicknameInBoard(Long id, String keyword, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postReader.getPostsByUserNicknameInBoard(id, keyword, pageRequest);
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

    @Transactional(readOnly = true)
    public List<RecommendPostsInBoardResponseDto> getRecommendPostsInBoard(Long id) {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<RecommendPostsInBoardQueryDto> queryDtos
                = postReader.getRecommendPostsInBoard(id,
                LocalDateTime.now(), LocalDateTime.now().minusHours(12),
                LIKE_WEIGHTED_VALUE, COMMENT_WEIGHTED_VALUE, pageRequest);
        List<RecommendPostsInBoardResponseDto> responseDtos = queryDtos.stream()
                .map(RecommendPostsInBoardResponseDto::new)
                .collect(Collectors.toList());
        return responseDtos;
    }

    private Boolean isPostWriter(User user, Post post) {
        return user.getEmail().equals(post.getUser().getEmail());
    }
}
