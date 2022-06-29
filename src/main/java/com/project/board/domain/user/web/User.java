package com.project.board.domain.user.web;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.post.web.Post;
import com.project.board.global.audit.BaseEntity;
import com.project.board.global.exception.InvalidParamException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // 사용자 아이디
    @Email
    @Column(name = "user_email", unique = true)
    private String email;

    // 사용자 비밀번호
    @Column(name = "user_password")
    private String password;

    // 사용자 닉네임
    @Column(name = "user_nickname")
    private String nickname;

    // 사용자 역할 (관리자, 사용자)
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private Status status;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference("user-post")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference("user-likepost")
    private List<LikePost> likePosts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference("user-comment")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonManagedReference("user-likecomment")
    private List<LikeComment> likeComments = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum Role {
        ADMIN("관리자"),
        USER("사용자");

        private final String description;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ACTIVE("계정 활성화"),
        SUSPENDED("계정 정지"),
        WITHDRAWAL("계정 탈퇴");

        private final String description;
    }

    @Builder
    public User(Long id, String email, String password,
                String nickname, List<Post> posts, List<LikePost> likePosts,
                List<Comment> comments, List<LikeComment> likeComments) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = Role.USER;
        this.status = Status.ACTIVE;
        this.posts = posts;
        this.likePosts = likePosts;
        this.comments = comments;
        this.likeComments = likeComments;
    }



    public void updateNickname(String nickname) {
        if (StringUtils.isEmpty(nickname)) throw new InvalidParamException("User.nickname");
        this.nickname = nickname;
    }

    public void updatePassword(String password) {
        if (StringUtils.isEmpty(password)) throw new InvalidParamException("User.password");
        this.password = password;
    }

    public void updateStatusToActive() {
        this.status = Status.ACTIVE;
    }

    public void updateStatusToSuspended() {
        this.status = Status.SUSPENDED;
    }

    public void updateStatusToWithdrawal() {
        this.status = Status.WITHDRAWAL;
    }
}
