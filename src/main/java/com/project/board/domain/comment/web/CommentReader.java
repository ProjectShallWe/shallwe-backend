package com.project.board.domain.comment.web;

import com.project.board.domain.comment.dto.EntityToCommentResponseDto;

import java.util.List;

public interface CommentReader {

    Comment getCommentBy(Long commentId);
    List<EntityToCommentResponseDto> getCommentsInPostByPostId(Long postId);
}
