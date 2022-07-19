package com.project.board.domain.user.web;

import com.project.board.domain.user.dto.UserJoinRequestDto;
import com.project.board.domain.user.dto.UserUpdateNicknameRequestDto;
import com.project.board.domain.user.dto.UserUpdatePasswordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserReader userReader;
    private final UserStore userStore;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(UserJoinRequestDto joinDto) {
        return userStore.store(joinDto.toEntity(passwordEncoder.encode(joinDto.getPassword()))).getId();
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
