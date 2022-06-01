package com.project.board.domain.user.service;

import com.project.board.domain.user.dto.UserJoinRequestDto;
import com.project.board.domain.user.dto.UserUpdateNicknameRequestDto;
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
    public Long join(UserJoinRequestDto userJoinRequestDto) {
        return userRepository.save(userJoinRequestDto.toEntity()).getId();
    }

    @Transactional
    public Long updateNickname(Long id,
                               UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디가 없습니다. id =" + id));
        user.updateNickname(userUpdateNicknameRequestDto.getNickname());

        return id;
    }

}
