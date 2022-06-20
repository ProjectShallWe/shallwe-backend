package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.like.web.LikeCommentStore;
import com.project.board.global.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeCommentStoreImpl implements LikeCommentStore {

    private final LikeCommentRepository likeCommentRepository;

    @Override
    public LikeComment store(LikeComment likeComment) {
        validCheck(likeComment);
        return likeCommentRepository.save(likeComment);
    }

    @Override
    public void delete(LikeComment likeComment) {
        likeCommentRepository.delete(likeComment);
    }

    private void validCheck(LikeComment likeComment) {
        if (likeComment.getUser() == null) throw new InvalidParamException("LikeComment.user");
        if (likeComment.getComment() == null) throw new InvalidParamException("LikeComment.comment");
    }
}
