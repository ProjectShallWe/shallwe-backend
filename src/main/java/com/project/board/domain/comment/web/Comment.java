package com.project.board.domain.comment.web;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.global.model.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_content")
    private String content;

    // 댓글의 좋아요 수 저장
    @Column(name = "comment_like_count")
    private Long likeCount;

    // 댓글 활성화 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "comment_status")
    private Status status;

    // 자식댓글일 경우 부모댓글의 comment_id값을 저장한다. 없다면 null
    @Column(name = "comment_parent_comment_id")
    private Long parentCommentId;

    @ManyToOne
    @JsonBackReference("user-comment")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference("post-comment")
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    @JsonManagedReference("comment-likecomment")
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

    public void update(String content) {
        this.content = content;
    }

    public void updateStatusToEnable() {
        this.status = Status.ENABLE;
    }

    public void updateStatusToDisable() {
        this.status = Status.ENABLE;
    }
}
