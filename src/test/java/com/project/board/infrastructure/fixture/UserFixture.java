package com.project.board.infrastructure.fixture;

import com.project.board.domain.user.web.User;

public class UserFixture {
    public static User createUser1() {
        return User.builder()
                .email("google0001@gmail.com")
                .password("0001")
                .nickname("구글0001")
                .role(User.Role.USER)
                .build();
    }
    public static User createUser2() {
        return User.builder()
                .email("google0002@gmail.com")
                .password("0002")
                .nickname("구글0002")
                .role(User.Role.USER)
                .build();
    }
}
