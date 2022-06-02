package com.project.board.domain.post.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.board.model.Board;
import com.project.board.domain.comment.model.Comment;
import com.project.board.domain.like.model.LikePost;
import com.project.board.domain.user.model.User;
import com.project.board.global.model.BaseEntity;
import lombok.AccessLevel;
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
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<LikePost> likePosts = new ArrayList<>();

}
