package com.project.board.domain.post.web;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.user.web.User;
import com.project.board.global.model.BaseEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_title")
    private String title;

    @Column(name = "post_content")
    private String content;

    // 글 활성화 여부
    @Enumerated(EnumType.STRING)
    @Column(name = "post_status")
    private Status status;

    // 글의 좋아요 수 저장
    @Column(name = "post_like_count")
    private Long likeCount;

    @ManyToOne
    @JsonBackReference("user-post")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference("postcategory-post")
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference("post-comment")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference("post-likepost")
    private List<LikePost> likePosts = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ENABLE("활성화"),
        DISABLE("비활성화");

        private final String description;
    }

    @Builder
    public Post(Long id, String title, String content,
                User user, PostCategory postCategory,
                List<Comment> comments, List<LikePost> likePosts) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = Status.ENABLE;
        this.likeCount = 0L;
        this.user = user;
        this.postCategory = postCategory;
        this.comments = comments;
        this.likePosts = likePosts;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateStatusToEnable() {
        this.status = Status.ENABLE;
    }

    public void updateStatusToDisable() {
        this.status = Status.ENABLE;
    }
}
