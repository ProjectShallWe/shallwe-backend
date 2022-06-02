package com.project.board.domain.like.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.board.domain.comment.model.Comment;
import com.project.board.domain.user.model.User;
import com.project.board.global.model.BaseEntity;
import lombok.AccessLevel;
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

    @ManyToOne
    @JsonBackReference("user-likecomment")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference("comment-likecomment")
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
