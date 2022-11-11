package com.project.board.domain.user.web;

import com.project.board.domain.user.dto.*;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.exception.InvalidParamException;
import com.project.board.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(UserJoinRequestDto joinDto) {
        User user = joinDto
                .toEntity(passwordEncoder.encode(joinDto.getPassword()));

        validCheck(user);
        return userRepository.save(user).getId();
    }

    private void validCheck(User user) {
        if (StringUtils.isEmpty(user.getEmail())) throw new InvalidParamException("User.email");
        if (StringUtils.isEmpty(user.getPassword())) throw new InvalidParamException("User.password");
        if (StringUtils.isEmpty(user.getNickname())) throw new InvalidParamException("User.nickname");
        if (user.getRole() == null) throw new InvalidParamException("User.role");
        if (user.getStatus() == null) throw new InvalidParamException("User.status");
    }

    @Transactional(readOnly = true)
    public Boolean checkNicknameDuplication(UserUpdateNicknameRequestDto nicknameUpdateDto) {
        return userRepository.existsByNickname(nicknameUpdateDto.getNickname());
    }

    @Transactional(readOnly = true)
    public Boolean checkEmailDuplication(UserEmailRequestDto userEmailRequestDto) {
        return userRepository.existsByEmail(userEmailRequestDto.getEmail());
    }

    @Transactional(readOnly = true)
    public Boolean checkNowPassword(String email,
                                 UserNowPasswordRequestDto userNowPasswordRequestDto){
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        return user.checkPassword(userNowPasswordRequestDto.getNowPassword(), user.getPassword());
    }

    @Transactional
    public String updateNickname(String email,
                               UserUpdateNicknameRequestDto nicknameUpdateDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        user.updateNickname(nicknameUpdateDto.getNickname());

        return email;
    }

    @Transactional
    public String updatePassword(String email,
                               UserUpdatePasswordRequestDto passwordUpdateDto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        if (user.checkPassword(passwordUpdateDto.getNowPassword(), user.getPassword())){
            user.updatePassword(passwordEncoder.encode(passwordUpdateDto.getNewPassword()));
            return email;
        } else {
            return "-1";
        }
    }

    @Transactional
    public String delete(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        user.updateStatusToWithdrawal();

        return email;
    }
}
