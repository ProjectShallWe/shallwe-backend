package com.project.board.domain.user.model;

import com.project.board.global.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;

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
    private String Email;

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

    @Builder
    public User(Long id, String email, String password,
                String nickname, Role role, Status status) {
        this.id = id;
        this.Email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.status = status;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
}
