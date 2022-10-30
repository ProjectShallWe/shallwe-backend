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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public Long update(Long id, PostUpdateRequestDto updateDto) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        post.update(updateDto.getTitle(), updateDto.getContent());
        return id;
    }

    @Transactional
    public Long delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        post.updateStatusToDisable();
        return id;
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsInBoard(Long id, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postRepository.findAllInBoard(id, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    @Transactional(readOnly = true)
    public Page<PostsResponseDto> getPostsInPostCategory(Long id, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postRepository.findAllInPostCategory(id, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    @Transactional
    public PostDetailsResponseDto getPostDetails(Long postId, HttpServletRequest req, HttpServletResponse res) {
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);

        Cookie oldCookie = null;
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            // 쿠키가 있다면 기존의 쿠키에 [postId]를 추가한다.
            if (!oldCookie.getValue().contains("["+ postId +"]")) {
                post.addHits();
                oldCookie.setValue(oldCookie.getValue() + "_[" + postId + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                res.addCookie(oldCookie);
            }
        } else {
            // 쿠키가 없다면 새로 만들어 [postId]를 추가한다.
            post.addHits();
            Cookie newCookie = new Cookie("postView", "[" + postId + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            res.addCookie(newCookie);
        }

        PostDetailsQueryDto postDetailsQueryDto = postRepository.findPostDetailsBy(postId)
                .orElseThrow(EntityNotFoundException::new);
        PostDetailsResponseDto postDetailsResponseDtos
                = new PostDetailsResponseDto(postDetailsQueryDto);

        return postDetailsResponseDtos;
    }

    public Page<PostsResponseDto> getPostsBySearchWordInBoard(Long boardId, Long postCategoryId, String type, String keyword, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsQueryDto> postsQueryDtos = postRepository.findPostsBySearchWordInBoard(boardId, postCategoryId, type, keyword, pageRequest);
        Page<PostsResponseDto> postsResponseDtos = postsQueryDtos.map(
                PostsResponseDto::new);
        return postsResponseDtos;
    }

    public Page<PostsCommonSearchResDto> getPostsByKeyword(String keyword, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsCommonSearchQueryDto> postsCommonSearchQueryDtos = postRepository.findPostsByKeyword(keyword, pageRequest);
        Page<PostsCommonSearchResDto> postsCommonSearchResDtos = postsCommonSearchQueryDtos.map(
                PostsCommonSearchResDto::new);
        return postsCommonSearchResDtos;
    }

    public Page<PostsUserResDto> getPostsByNickname(String email, Integer page) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostsUserQueryDto> postsUserQueryDtos = postRepository.findPostsByNickname(user.getNickname(), pageRequest);
        Page<PostsUserResDto> postsUserResDtos = postsUserQueryDtos.map(
                PostsUserResDto::new);
        return postsUserResDtos;
    }


}
