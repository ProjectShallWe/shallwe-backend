package com.project.board.domain.user.dto;

import com.project.board.domain.user.model.Role;
import com.project.board.domain.user.model.Status;
import com.project.board.domain.user.model.User;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserJoinRequestDto {

    private String email;

    private String password;

    private String nickname;

    public User toEntity() {
        return User.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .role(Role.USER)
                .status(Status.ACTIVE)
                .build();
    }
}
