package com.project.board.domain.user.web;

public interface UserReader {
    User getUserBy(String email);

    Boolean existsByNickname(String nickname);
}
