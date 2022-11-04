package com.project.board.domain.comment.web;

import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.global.audit.BaseEntity;
import com.project.board.global.exception.CannotDeleteCommentException;
import com.project.board.global.exception.CannotUpdateCommentException;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    // 댓글의 좋아요 수 저장
    @NotNull
    private Long likeCount;

    // 댓글 활성화 여부
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    // 자식댓글일 경우 부모댓글의 comment_id값을 저장한다. 없다면 null
    private Long parentCommentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment")
    private List<LikeComment> likeComments = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ENABLE("활성화"),
        DISABLE("비활성화");

        private final String description;
    }

    @Builder
    public Comment(Long id, String content,
                   Long parentCommentId, User user, Post post,
                   List<LikeComment> likeComments) {
        this.id = id;
        this.content = content;
        this.likeCount = 0L;
        this.status = Status.ENABLE;
        this.parentCommentId = parentCommentId;
        this.user = user;
        this.post = post;
        this.likeComments = likeComments;
    }

    public void update(String content, User user) {
        if (!this.user.equals(user) && !user.getRole().equals(User.Role.ADMIN)) {
            throw new CannotUpdateCommentException();
        }
        this.content = content;
    }

    public void updateStatusToDisable(User user) {
        if (!this.user.equals(user) && !user.getRole().equals(User.Role.ADMIN)) {
            throw new CannotDeleteCommentException();
        }
        this.status = Status.DISABLE;
    }

    public void addLikeCount() {
        likeCount += 1;
    }
    public void minusLikeCount() {
        likeCount -= 1;
    }
}
