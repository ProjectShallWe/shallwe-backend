package com.project.board.domain.user.service;

import com.project.board.domain.user.dto.UserRequestDto;
import com.project.board.domain.user.model.Role;
import com.project.board.domain.user.model.User;
import com.project.board.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(UserRequestDto userRequestDto) {
        User user = User.builder()
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .nickname(userRequestDto.getNickname())
                .role(userRequestDto.getRole())
                .build();

        userRepository.save(user);

        return user.getId();
    }
}
