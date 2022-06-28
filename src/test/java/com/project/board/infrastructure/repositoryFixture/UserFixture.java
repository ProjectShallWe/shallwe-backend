package com.project.board.infrastructure.repositoryFixture;

import com.project.board.domain.user.web.User;

public class UserFixture {
    public static User createUser1() {
        return User.builder()
                .email("google1234@gmail.com")
                .password("1234")
                .nickname("구글1234")
                .build();
    }
    public static User createUser2() {
        return User.builder()
                .email("google2345@gmail.com")
                .password("2345")
                .nickname("구글2345")
                .build();
    }
}
