package com.project.board.domain.service;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.comment.web.CommentRepository;
import com.project.board.domain.like.dto.LikeCommentRequestDto;
import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.like.web.LikeCommentRepository;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class LikeCommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeCommentRepository likeCommentRepository;

    @Transactional
    public Long like(String email, Long commentId, LikeCommentRequestDto likeCommentRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다. comment_id : " + commentId));
        return likeCommentRepository.save(likeCommentRequestDto.toEntity(user, comment)).getId();
    }

    @Transactional
    public Long cancel(String email, Long likeCommentId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        LikeComment likeComment  = likeCommentRepository.findById(likeCommentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 좋아요를 찾을 수 없습니다. like_comment_id: " + likeCommentId));
        if(user.getEmail().equals(likeComment.getUser().getEmail())) {
            likeCommentRepository.delete(likeComment);
            return likeCommentId;
        }
        return -1L;

    }
}
