package com.project.board.domain.post.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.board.model.Board;
import com.project.board.domain.comment.model.Comment;
import com.project.board.domain.like.model.LikePost;
import com.project.board.domain.user.model.User;
import com.project.board.global.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    // false가 기본 값이며, 글 삭제시 true로 바뀌어 검색되지 않는다.
    @Column(name = "post_is_deleted")
    private Boolean isDeleted;

    // 글의 좋아요 수 저장
    @Column(name = "post_like_post")
    private Long likePost;

    @ManyToOne
    @JsonBackReference("user-post")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference("board-post")
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference("post-comment")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference("post-likepost")
    private List<LikePost> likePosts = new ArrayList<>();

    @Builder
    public Post(Long id, String title, String content,
                Boolean isDeleted, Long likePost,
                User user, Board board,
                List<Comment> comments, List<LikePost> likePosts) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isDeleted = isDeleted;
        this.likePost = likePost;
        this.user = user;
        this.board = board;
        this.comments = comments;
        this.likePosts = likePosts;
    }
}
