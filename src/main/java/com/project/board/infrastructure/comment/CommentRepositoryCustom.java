package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.dto.CommentQueryDto;
import com.project.board.domain.comment.dto.CommentsUserQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentQueryDto> getCommentsInPostByPostId(Long postId);

    Page<CommentsUserQueryDto> findCommentsByNickname(String nickname, Pageable pageable);
}
