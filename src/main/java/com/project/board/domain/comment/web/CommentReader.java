package com.project.board.domain.comment.web;

import com.project.board.domain.comment.dto.CommentQueryDto;

import java.util.List;

public interface CommentReader {

    Comment getCommentBy(Long commentId);
    List<CommentQueryDto> getCommentsInPostByPostId(Long postId);
}
