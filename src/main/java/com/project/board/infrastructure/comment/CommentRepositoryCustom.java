package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.dto.CommentQueryDto;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentQueryDto> getCommentsInPostByPostId(Long postId);
}
