package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.dto.CommentQueryDto;
import com.project.board.domain.comment.dto.CommentsUserQueryDto;
import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.comment.web.CommentReader;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentReaderImpl implements CommentReader {

    private final CommentRepository commentRepository;

    @Override
    public Comment getCommentBy(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<CommentQueryDto> getCommentsInPostByPostId(Long postId) {
        return commentRepository.getCommentsInPostByPostId(postId);
    }
    @Override
    public Page<CommentsUserQueryDto> getCommentsByNickname(String nickname, PageRequest pageRequest) {
        return commentRepository.findCommentsByNickname(nickname, pageRequest);
    }
}
