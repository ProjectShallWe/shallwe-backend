package com.project.board.domain.service;

import com.project.board.domain.like.dto.LikePostRequestDto;
import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.like.web.LikePostRepository;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostRepository;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikePostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikePostRepository likePostRepository;

    @Transactional
    public Long like(String email, Long postId, LikePostRequestDto likePostRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다. post_id : " + postId));
        return likePostRepository.save(likePostRequestDto.toEntity(user, post)).getId();
    }

    @Transactional
    public Long cancel(String email, Long likePostId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        LikePost likePost  = likePostRepository.findById(likePostId)
                .orElseThrow(() -> new IllegalArgumentException("해당 좋아요를 찾을 수 없습니다. like_post_id: " + likePostId));
        if(user.getEmail().equals(likePost.getUser().getEmail())) {
            likePostRepository.delete(likePost);
            return likePostId;
        }
        return -1L;

    }
}
