package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.dto.EntityToCommentResponseDto;
import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.comment.web.CommentReader;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {

    private final CommentRepository commentRepository;

    public Comment getCommentBy(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<EntityToCommentResponseDto> getCommentsInPostByPostId(Long postId) {
      return commentRepository.getCommentsInPostByPostId(postId);
    }
}
