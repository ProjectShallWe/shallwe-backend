package com.project.board.domain.like.web;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.comment.web.CommentReader;
import com.project.board.domain.like.dto.LikeCommentRequestDto;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LikeCommentService {

    private final UserReader userReader;
    private final CommentReader commentReader;
    private final LikeCommentReader likeCommentReader;
    private final LikeCommentStore likeCommentStore;

    @Transactional
    public Long like(String email, Long commentId, LikeCommentRequestDto likeCommentRequestDto) {
        User user = userReader.getUserBy(email);
        Comment comment = commentReader.getCommentBy(commentId);
        try {
            likeCommentReader.getLikeCommentBy(user.getId(), comment.getId());
            return -1L;
        } catch (Exception e) {
            comment.addLikeCount();
            return likeCommentStore.store(likeCommentRequestDto.toEntity(user, comment)).getId();
        }
    }

    @Transactional
    public Long cancel(String email, Long commentId, Long likeCommentId) {
        User user = userReader.getUserBy(email);
        Comment comment = commentReader.getCommentBy(commentId);
        LikeComment likeComment = likeCommentReader.getLikeCommentBy(likeCommentId);
        if(isLikeCommentWriter(user, likeComment)) {
            comment.minusLikeCount();
            likeCommentStore.delete(likeComment);
            return likeCommentId;
        }
        return -1L;

    }

    private boolean isLikeCommentWriter(User user, LikeComment likeComment) {
        return user.getEmail().equals(likeComment.getUser().getEmail());
    }
}
