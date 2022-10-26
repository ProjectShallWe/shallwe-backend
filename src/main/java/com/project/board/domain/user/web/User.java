package com.project.board.domain.user.web;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.post.web.Post;
import com.project.board.global.audit.BaseEntity;
import com.project.board.global.exception.InvalidParamException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 아이디
    @Column(unique = true)
    @Email
    @NotNull
    private String email;

    // 사용자 비밀번호
    @NotNull
    private String password;

    // 사용자 닉네임
    @Column(unique = true)
    @NotNull
    private String nickname;

    // 사용자 역할 (관리자, 사용자)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    private String refreshToken;

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikePost> likePosts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
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
        WITHDRAWAL("계정 탈퇴");

        private final String description;
    }

    @Builder
    public User(Long id, String email, String password,
                String nickname, Role role, String refreshToken,
                List<Post> posts, List<LikePost> likePosts,
                List<Comment> comments, List<LikeComment> likeComments) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.status = Status.ACTIVE;
        this.refreshToken = refreshToken;
        this.posts = posts;
        this.likePosts = likePosts;
        this.comments = comments;
        this.likeComments = likeComments;
    }

    public void updateNickname(String nickname) {
        if (StringUtils.isEmpty(nickname)) throw new InvalidParamException("User.nickname");
        this.nickname = nickname;
    }

    public boolean checkPassword(String nowPassword, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(nowPassword, password);
    }

    public void updatePassword(String password) {
        if (StringUtils.isEmpty(password)) throw new InvalidParamException("User.password");
        this.password = password;
    }

    public void updateStatusToWithdrawal() {
        this.status = Status.WITHDRAWAL;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
