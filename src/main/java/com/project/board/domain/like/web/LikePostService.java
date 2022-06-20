package com.project.board.domain.like.web;

import com.project.board.domain.like.dto.LikePostRequestDto;
import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.like.web.LikePostReader;
import com.project.board.domain.like.web.LikePostStore;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostReader;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikePostService {

    private final UserReader userReader;
    private final PostReader postReader;
    private final LikePostStore likePostStore;
    private final LikePostReader likePostReader;

    @Transactional
    public Long like(String email, Long postId, LikePostRequestDto likePostRequestDto) {;
        User user = userReader.getUserBy(email);
        Post post = postReader.getPostBy(postId);
        try {
            // userId와 postId를 이용해 해당 글에 해당 유저가 좋아요를 눌렀는지 확인한다.
            likePostReader.getLikePostBy(user.getId(), post.getId());
            return -1L;
        } catch (Exception e) {
            // 좋아요를 누르지 않았다면 post에 좋아요 수 1을 더하고, 좋아요 기록을 DB에 추가한다.
            post.addLikeCount();
            return likePostStore.store(likePostRequestDto.toEntity(user, post)).getId();
        }
    }

    @Transactional
    public Long cancel(String email, Long postId, Long likePostId) {
        User user = userReader.getUserBy(email);
        Post post = postReader.getPostBy(postId);
        LikePost likePost = likePostReader.getLikePostBy(likePostId);
        if(isLikePostWriter(user, likePost)) {
            post.minusLikeCount();
            likePostStore.delete(likePost);
            return likePostId;
        }
        return -1L;

    }

    private boolean isLikePostWriter(User user, LikePost likePost) {
        return user.getEmail().equals(likePost.getUser().getEmail());
    }
}
