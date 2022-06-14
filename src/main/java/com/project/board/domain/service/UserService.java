package com.project.board.domain.service;

import com.project.board.domain.user.dto.UserJoinRequestDto;
import com.project.board.domain.user.dto.UserUpdateNicknameRequestDto;
import com.project.board.domain.user.dto.UserUpdatePasswordRequestDto;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserReader;
import com.project.board.domain.user.web.UserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserReader userReader;
    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(UserJoinRequestDto joinDto) {
        String encodedPassword = passwordEncoder.encode(joinDto.getPassword());
        return userStore.store(joinDto.toEntity(encodedPassword)).getId();
    }

    @Transactional
    public String updateNickname(String email,
                               UserUpdateNicknameRequestDto nicknameUpdateDto) {
        User user = userReader.getUserBy(email);
        user.updateNickname(nicknameUpdateDto.getNickname());

        return email;
    }

    @Transactional
    public String updatePassword(String email,
                               UserUpdatePasswordRequestDto passwordUpdateDto) {
        User user = userReader.getUserBy(email);
        String encodedPassword = passwordEncoder.encode(passwordUpdateDto.getPassword());
        user.updatePassword(encodedPassword);

        return email;
    }

    @Transactional
    public String delete(String email) {
        User user = userReader.getUserBy(email);
        user.updateStatusToWithdrawal();

        return email;
    }

}
