package com.project.board.domain.controller;

import com.project.board.domain.user.dto.UserJoinRequestDto;
import com.project.board.domain.user.dto.UserUpdateNicknameRequestDto;
import com.project.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/user/join")
    public Long join(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        return userService.join(userJoinRequestDto);
    }

    @PutMapping("/api/user/{id}/nickname")
    public Long updateNickname(@PathVariable Long id,
            @RequestBody UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        return userService.updateNickname(id, userUpdateNicknameRequestDto);
    }
}
