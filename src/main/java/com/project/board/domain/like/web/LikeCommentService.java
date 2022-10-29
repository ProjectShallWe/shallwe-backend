package com.project.board.domain.like.web;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.like.dto.LikeCommentRequestDto;
import com.project.board.domain.user.web.User;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.exception.InvalidParamException;
import com.project.board.infrastructure.comment.CommentRepository;
import com.project.board.infrastructure.like.LikeCommentRepository;
import com.project.board.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LikeCommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Transactional
    public Long like(String email, Long commentId, LikeCommentRequestDto likeCommentRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(EntityNotFoundException::new);
        Boolean isClicked = likeCommentRepository.existsByUserIdAndCommentId(user.getId(), comment.getId());

        if (!isClicked) {
            comment.addLikeCount();

            LikeComment likeComment = likeCommentRequestDto.toEntity(user, comment);

            validCheck(likeComment);
            return likeCommentRepository.save(likeComment).getId();
        }
        comment.minusLikeCount();
        LikeComment likeComment = likeCommentRepository.findByUserIdAndCommentId(user.getId(), comment.getId())
                .orElseThrow(EntityNotFoundException::new);

        likeCommentRepository.delete(likeComment);
        return likeComment.getId();


    }


    private void validCheck(LikeComment likeComment) {
        if (likeComment.getUser() == null) throw new InvalidParamException("LikeComment.user");
        if (likeComment.getComment() == null) throw new InvalidParamException("LikeComment.comment");
    }
}
