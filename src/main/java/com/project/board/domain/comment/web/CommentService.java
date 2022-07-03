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
        return commentStore.store(commentWriteRequestDto.toEntity(user, post, null)).getId();
    }

    @Transactional
    public Long writeChildComment(String email, Long postId, Long commentId, CommentWriteRequestDto commentWriteRequestDto) {
        User user = userReader.getUserBy(email);
        Post post = postReader.getPostBy(postId);
        Comment comment = commentReader.getCommentBy(commentId);
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
        List<CommentQueryDto> ETCResponseDtos = commentReader.getCommentsInPostByPostId(postId);
        List<ParentCommentsResponseDto> PCResponseDtos = new ArrayList<>();
        List<ChildCommentsResponseDto> CCResponseDtos = new ArrayList<>();

        for (int i = 0; i < ETCResponseDtos.size(); i++) {
            if (isParentComment(ETCResponseDtos, i)) {
                PCResponseDtos.add(
                        new ParentCommentsResponseDto(ETCResponseDtos.get(i)));
            } else {
                CCResponseDtos.add(
                        new ChildCommentsResponseDto(ETCResponseDtos.get(i)));
            }
        }

        for (int i = 0; i < PCResponseDtos.size(); i++) {
            for (int j = 0; j < CCResponseDtos.size(); j++) {
                if (isThisCommentChildren(PCResponseDtos, CCResponseDtos, i, j)) {
                    PCResponseDtos.get(i).getChildComments().add(CCResponseDtos.get(j));
                }
            }
        }

        return PCResponseDtos;
    }

    private boolean isParentComment(List<CommentQueryDto> ETCResponseDtos, int i) {
        return ETCResponseDtos.get(i).getParentId() == null;
    }

    private boolean isThisCommentChildren(List<ParentCommentsResponseDto> PCResponseDtos,
                                    List<ChildCommentsResponseDto> CCResponseDtos,
                                    int i, int j) {
        return PCResponseDtos.get(i).getCommentId().equals(CCResponseDtos.get(j).getParentId());
    }
}
