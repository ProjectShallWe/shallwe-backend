package com.project.board.domain.comment.web;

import com.project.board.domain.comment.dto.*;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostReader;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserReader userReader;
    private final PostReader postReader;
    private final CommentReader commentReader;
    private final CommentStore commentStore;

    @Transactional
    public Long writeParentComment(String email, Long postId, CommentWriteRequestDto commentWriteRequestDto) {
        User user = userReader.getUserBy(email);
        Post post = postReader.getPostBy(postId);
        post.addCommentCount();
        return commentStore.store(commentWriteRequestDto.toEntity(user, post, null)).getId();
    }

    @Transactional
    public Long writeChildComment(String email, Long postId, Long commentId, CommentWriteRequestDto commentWriteRequestDto) {
        User user = userReader.getUserBy(email);
        Post post = postReader.getPostBy(postId);
        Comment comment = commentReader.getCommentBy(commentId);
        post.addCommentCount();
        return commentStore.store(commentWriteRequestDto.toEntity(user, post, comment.getId())).getId();
    }

    @Transactional
    public Long update(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        Comment comment = commentReader.getCommentBy(commentId);
        comment.update(commentUpdateRequestDto.getContent());
        return commentId;
    }

    @Transactional
    public Long delete(Long commentId) {
        Comment comment = commentReader.getCommentBy(commentId);
        comment.updateStatusToDisable();
        return commentId;
    }

    @Transactional(readOnly = true)
    public List<ParentCommentsResponseDto> getCommentsInPost(Long postId) {
        List<CommentQueryDto> cResDtos = commentReader.getCommentsInPostByPostId(postId);
        List<ParentCommentsResponseDto> pcResDtos = new ArrayList<>();
        List<ChildCommentsResponseDto> ccResDtos = new ArrayList<>();

        for (CommentQueryDto cQueryDto : cResDtos) {
            if (isParentComment(cQueryDto)) {
                pcResDtos.add(new ParentCommentsResponseDto(cQueryDto));
            } else {
                ccResDtos.add(new ChildCommentsResponseDto(cQueryDto));
            }
        }

        for (ParentCommentsResponseDto pcResDto : pcResDtos) {
            for (ChildCommentsResponseDto ccResDto : ccResDtos) {
                if (isThisCommentChildren(pcResDto, ccResDto)) {
                      pcResDto.getChildComments().add(ccResDto);
                }
            }
        }

        return pcResDtos;
    }

    private boolean isParentComment(CommentQueryDto cQueryDto) {
        return cQueryDto.getParentId() == null;
    }

    private boolean isThisCommentChildren(ParentCommentsResponseDto pcResDto, ChildCommentsResponseDto ccResDto) {
        return pcResDto.getCommentId().equals(ccResDto.getParentId());
    }
}
