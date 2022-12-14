package com.project.board.domain.like.web;

import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.global.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "like_post_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikePost extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public LikePost(Long id, User user, Post post) {
        this.id = id;
        this.user = user;
        this.post = post;
    }
}
