package com.project.board.domain.post.web;

import com.project.board.domain.post.dto.*;
import com.project.board.domain.user.web.User;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.exception.InvalidParamException;
import com.project.board.infrastructure.post.PostCategoryRepository;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long write(String email, Long postCategoryId, PostWriteRequestDto writeDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        PostCategory postCategory = postCategoryRepository.findById(postCategoryId)
                .orElseThrow(EntityNotFoundException::new);
        Post post = writeDto.toEntity(user, postCategory);

        if (!isImageListEmpty(writeDto.getImages())) {
            post.updateThumbnailUrl(writeDto.getImages().get(0));
        }

        validCheck(post);
        return postRepository.save(post).getId();
    }

    private void validCheck(Post post) {
        if (post.getUser() == null) throw new InvalidParamException("Post.user");
        if (post.getPostCategory() == null) throw new InvalidParamException("Post.postCategory");
        if (StringUtils.isEmpty(post.getTitle())) throw new InvalidParamException("Post.title");
        if (StringUtils.isEmpty(post.getContent())) throw new InvalidParamException("Post.content");
        if (post.getLikeCount() == null) throw new InvalidParamException("Post.likeCount");
    }

    @Transactional
    public Long update(String email, Long postId, Long postCategoryId, PostUpdateRequestDto updateDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        PostCategory postCategory = postCategoryRepository.findById(postCategoryId)
                        .orElseThrow(EntityNotFoundException::new);

        post.update(updateDto.getTitle(), updateDto.getContent(), user, postCategory);

        if (!isImageListEmpty(updateDto.getImages())) {
            post.updateThumbnailUrl(updateDto.getImages().get(0));
        } else {
            post.updateThumbnailUrl("");
        }

        return postId;
    }

    private Boolean isImageListEmpty(List<String> images) {
        return images.isEmpty();
    }

    @Transactional
    public Long delete(String email, Long id) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        post.updateStatusToDisable(user);
        return id;
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsInBoard(Long id, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, PageSize.TWENTY.value);
        Page<PostsQueryDto> postsQueryDtos = postRepository.findAllInBoard(id, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsInPostCategory(Long id, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, PageSize.TWENTY.value);
        Page<PostsQueryDto> postsQueryDtos = postRepository.findAllInPostCategory(id, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    @Transactional
    public PostDetailsResponseDto getPostDetails(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        PostDetailsQueryDto postDetailsQueryDto = postRepository.findPostDetailsBy(postId)
                .orElseThrow(EntityNotFoundException::new);
        PostDetailsResponseDto postDetailsResponseDtos
                = new PostDetailsResponseDto(postDetailsQueryDto);

        post.addHits();

        return postDetailsResponseDtos;
    }

    public Page<PostsResponseDto> getPostsBySearchWordInBoard(Long boardId, Long postCategoryId, String type, String keyword, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, PageSize.TWENTY.value);
        Page<PostsQueryDto> postsQueryDtos = postRepository.findPostsBySearchWordInBoard(boardId, postCategoryId, type, keyword, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    public Page<PostsCommonSearchResDto> getPostsByKeyword(String keyword, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, PageSize.TEN.value);
        Page<PostsCommonSearchQueryDto> postsCommonSearchQueryDtos = postRepository.findPostsByKeyword(keyword, pageRequest);
        Page<PostsCommonSearchResDto> postsCommonSearchResDtos = postsCommonSearchQueryDtos.map(
                PostsCommonSearchResDto::new);
        return postsCommonSearchResDtos;
    }

    public Page<PostsUserResDto> getPostsByNickname(String email, Integer page) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);

        PageRequest pageRequest = PageRequest.of(page, PageSize.TEN.value);
        Page<PostsUserQueryDto> postsUserQueryDtos = postRepository.findPostsByNickname(user.getNickname(), pageRequest);
        Page<PostsUserResDto> postsUserResDtos = postsUserQueryDtos.map(
                PostsUserResDto::new);
        return postsUserResDtos;
    }


}
