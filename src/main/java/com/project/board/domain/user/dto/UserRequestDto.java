package com.project.board.domain.user.dto;

import com.project.board.domain.user.model.Role;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserRequestDto {

    private String email;

    private String password;

    private String nickname;

    private Role role;
}
