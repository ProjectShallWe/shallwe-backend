package com.project.board.global.util;

import com.project.board.domain.user.web.User;

public class UserRoleChecker {

    public static boolean isAdmin(User user) {
        return user.getRole().equals(User.Role.ADMIN);
    }
}
