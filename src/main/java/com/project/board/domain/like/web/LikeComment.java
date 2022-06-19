package com.project.board.domain.like.web;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.user.web.User;
import com.project.board.global.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "like_comment_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class LikeComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("user-likecomment")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("comment-likecomment")
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public LikeComment(Long id, User user, Comment comment) {
        this.id = id;
        this.user = user;
        this.comment = comment;
    }
}
