package com.project.board.domain.comment.web;

import com.project.board.domain.comment.dto.*;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.exception.InvalidParamException;
import com.project.board.infrastructure.comment.CommentRepository;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long writeParentComment(String email, Long postId, CommentWriteRequestDto commentWriteRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        post.addCommentCount();
        Comment comment = commentWriteRequestDto.toEntity(user, post, null);

        validCheck(comment);
        return commentRepository.save(comment).getId();
    }

    private void validCheck(Comment comment) {
        if (comment.getUser() == null) throw new InvalidParamException("Comment.user");
        if (comment.getPost() == null) throw new InvalidParamException("Comment.post");
        if (StringUtils.isEmpty(comment.getContent())) throw new InvalidParamException("Comment.content");
        if (comment.getLikeCount() == null) throw new InvalidParamException("Comment.likeCount");
        if (comment.getStatus() == null) throw new InvalidParamException("Comment.status");
    }

    @Transactional
    public Long writeChildComment(String email, Long postId, Long commentId, CommentWriteRequestDto commentWriteRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Post post = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(EntityNotFoundException::new);
        post.addCommentCount();
        Comment childComment = commentWriteRequestDto.toEntity(user, post, parentComment.getId());

        validCheck(childComment);
        return commentRepository.save(childComment).getId();
    }

    @Transactional
    public Long update(String email, Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(EntityNotFoundException::new);
        comment.update(commentUpdateRequestDto.getContent(), user);
        return commentId;
    }

    @Transactional
    public Long delete(String email, Long commentId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(EntityNotFoundException::new);
        comment.updateStatusToDisable(user);
        return commentId;
    }

    @Transactional(readOnly = true)
    public List<ParentCommentsResponseDto> getCommentsInPost(Long postId) {
        List<CommentQueryDto> cResDtos = commentRepository.getCommentsInPostByPostId(postId);
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

    @Transactional(readOnly = true)
    public Page<CommentsUserResDto> getCommentsByNickname(String email, Integer page) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);

        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<CommentsUserQueryDto> commentsUserQueryDtos = commentRepository.findCommentsByNickname(user.getNickname(), pageRequest);
        Page<CommentsUserResDto> commentsUserResDtos = commentsUserQueryDtos.map(
                CommentsUserResDto::new);
        return commentsUserResDtos;
    }
}
