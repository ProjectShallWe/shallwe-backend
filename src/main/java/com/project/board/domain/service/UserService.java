package com.project.board.domain.service;

import com.project.board.domain.user.dto.UserJoinRequestDto;
import com.project.board.domain.user.dto.UserUpdateNicknameRequestDto;
import com.project.board.domain.user.dto.UserUpdatePasswordRequestDto;
import com.project.board.domain.user.web.User;
import com.project.board.domain.user.web.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(UserJoinRequestDto userJoinRequestDto) {
        String encodedPassword = passwordEncoder.encode(userJoinRequestDto.getPassword());
        return userRepository.save(userJoinRequestDto.toEntity(encodedPassword)).getId();
    }

    @Transactional
    public String updateNickname(String email,
                               UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다. email =" + email));
        user.updateNickname(userUpdateNicknameRequestDto.getNickname());

        return email;
    }

    @Transactional
    public String updatePassword(String email,
                               UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다. email =" + email));
        String encodedPassword = passwordEncoder.encode(userUpdatePasswordRequestDto.getPassword());
        user.updatePassword(encodedPassword);

        return email;
    }

    @Transactional
    public String delete(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다. email =" + email));
        user.updateStatusToWithdrawal();

        return email;
    }
}
