package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.like.web.LikeCommentReader;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikeCommentReaderImpl implements LikeCommentReader {

    private final LikeCommentRepository likeCommentRepository;

    @Override
    public LikeComment getLikeCommentBy(Long id) {
        return likeCommentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public LikeComment getLikeCommentBy(Long userId, Long commentId) {
        return likeCommentRepository.findByUserIdAndPostId(userId, commentId)
                .orElseThrow(EntityNotFoundException::new);

    }
}
