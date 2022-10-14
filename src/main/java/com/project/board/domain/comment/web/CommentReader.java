package com.project.board.domain.comment.web;

import com.project.board.domain.comment.dto.CommentQueryDto;
import com.project.board.domain.comment.dto.CommentsUserQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CommentReader {

    Comment getCommentBy(Long commentId);
    List<CommentQueryDto> getCommentsInPostByPostId(Long postId);
    Page<CommentsUserQueryDto> getCommentsByNickname(String nickname, PageRequest pageRequest);
}
