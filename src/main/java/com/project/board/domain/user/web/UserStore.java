package com.project.board.domain.user.web;

import com.project.board.global.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserStore {

    private final UserRepository userRepository;

    public User store (User user) {
        validCheck(user);
        return userRepository.save(user);
    }

    private void validCheck(User user) {
        if (StringUtils.isEmpty(user.getEmail())) throw new InvalidParamException("User.email");
        if (StringUtils.isEmpty(user.getPassword())) throw new InvalidParamException("User.password");
        if (StringUtils.isEmpty(user.getNickname())) throw new InvalidParamException("User.nickname");
        if (user.getRole() == null) throw new InvalidParamException("User.role");
        if (user.getStatus() == null) throw new InvalidParamException("User.status");
    }
}
