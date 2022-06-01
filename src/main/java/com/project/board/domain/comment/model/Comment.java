package com.project.board.domain.comment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.like.model.LikeComment;
import com.project.board.domain.post.model.Post;
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
    // false가 기본 값이며, 댓글 삭제시 true로 바뀌어 검색되지 않는다.
    @Column(name = "comment_is_deleted")
    private Boolean isDeleted;

    @Column(name = "comment_like_commnet")
    private Long likeComment;

    // 자식댓글일 경우 부모댓글의 comment_id값을 저장한다. 없다면 null
    @Column(name = "comment_parent_comment_id")
    private Long parent_comment_id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<LikeComment> likeComments = new ArrayList<>();
}
