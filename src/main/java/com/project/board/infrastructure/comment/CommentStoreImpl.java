package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.comment.web.CommentStore;
import com.project.board.global.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentStoreImpl implements CommentStore {

    private final CommentRepository commentRepository;

    public Comment store(Comment comment) {
        validCheck(comment);
        return commentRepository.save(comment);
    }

    private void validCheck(Comment comment) {
        if (comment.getUser() == null) throw new InvalidParamException("Comment.user");
        if (comment.getPost() == null) throw new InvalidParamException("Comment.post");
        if (StringUtils.isEmpty(comment.getContent())) throw new InvalidParamException("Comment.content");
        if (comment.getLikeCount() == null) throw new InvalidParamException("Comment.likeCount");
        if (comment.getStatus() == null) throw new InvalidParamException("Comment.status");
    }
}
