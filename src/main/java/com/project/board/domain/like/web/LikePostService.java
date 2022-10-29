package com.project.board.domain.like.web;

import com.project.board.domain.like.dto.LikePostRequestDto;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.exception.InvalidParamException;
import com.project.board.infrastructure.like.LikePostRepository;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LikePostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;

    @Transactional
    public Long like(String email, Long postId, LikePostRequestDto likePostRequestDto) {
        ;
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        Boolean isClick = likePostRepository.existsByUserIdAndPostId(user.getId(), post.getId());

        if (!isClick) {
            post.addLikeCount();
            LikePost likePost = likePostRequestDto.toEntity(user, post);

            validCheck(likePost);
            return likePostRepository.save(likePost).getId();
        }
        post.minusLikeCount();
        LikePost likePost = likePostRepository.findByUserIdAndPostId(user.getId(), post.getId())
                .orElseThrow(EntityNotFoundException::new);
        likePostRepository.delete(likePost);
        return likePost.getId();

    }

    private void validCheck(LikePost likePost) {
        if (likePost.getUser() == null) throw new InvalidParamException("LikePost.user");
        if (likePost.getPost() == null) throw new InvalidParamException("LikePost.post");
    }
}
