package com.project.board.domain.user.service;

import com.project.board.domain.user.dto.UserRequestDto;
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
        return userRepository.save(userRequestDto.toEntity()).getId();
    }
}
