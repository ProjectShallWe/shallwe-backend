package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.like.web.LikePostStore;
import com.project.board.global.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikePostStoreImpl implements LikePostStore {

    private final LikePostRepository likePostRepository;

    @Override
    public LikePost store(LikePost likePost) {
        validCheck(likePost);
        return likePostRepository.save(likePost);
    }

    @Override
    public void delete(LikePost likePost) {
        likePostRepository.delete(likePost);
    }

    private void validCheck(LikePost likePost) {
        if (likePost.getUser() == null) throw new InvalidParamException("LikePost.user");
        if (likePost.getPost() == null) throw new InvalidParamException("LikePost.post");
    }
}
