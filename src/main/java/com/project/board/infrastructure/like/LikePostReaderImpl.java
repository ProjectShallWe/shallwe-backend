package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.like.web.LikePostReader;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LikePostReaderImpl implements LikePostReader {

    private final LikePostRepository likePostRepository;

    @Override
    public LikePost getLikePostBy(Long id) {
        return likePostRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public LikePost getLikePostBy(Long userId, Long postId) {
        return likePostRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
