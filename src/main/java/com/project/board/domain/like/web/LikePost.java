package com.project.board.domain.like.web;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.global.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "like_post_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class LikePost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_post_id")
    private Long id;

    @ManyToOne
    @JsonBackReference("user-likepost")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference("post-likepost")
    @JoinColumn(name = "post_id")
    private Post post;
}
