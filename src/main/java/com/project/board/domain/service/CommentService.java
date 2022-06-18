package com.project.board.domain.service;

import com.project.board.domain.comment.dto.CommentUpdateRequestDto;
import com.project.board.domain.comment.dto.CommentWriteRequestDto;
import com.project.board.domain.comment.web.Comment;
import com.project.board.infrastructure.comment.CommentRepository;
import com.project.board.domain.post.web.Post;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.domain.user.web.User;
import com.project.board.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long write(String email, Long postId, Long commentId, CommentWriteRequestDto commentWriteRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 글을 찾을 수 없습니다. post_id : " + postId));
        if (!(commentId == null)) {
            Comment comment = commentRepository.findById(commentId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다. comment_id : " + commentId));
            return commentRepository.save(commentWriteRequestDto.toEntity(user, post, comment.getId())).getId();
        } else {
            return commentRepository.save(commentWriteRequestDto.toEntity(user, post, commentId)).getId();
        }

    }

    @Transactional
    public Long update(String email, Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다. comment_id : " + commentId));
        if (user.getEmail().equals(comment.getUser().getEmail())) {
            comment.update(commentUpdateRequestDto.getContent());
            return commentId;
        }
        return -1L;
    }

    @Transactional
    public Long delete(String email, Long commentId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글을 찾을 수 없습니다. comment_id : " + commentId));
        if (user.getEmail().equals(comment.getUser().getEmail())) {
            comment.updateStatusToDisable();
            return commentId;
        }
        return -1L;
    }
}
