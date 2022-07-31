package com.project.board.controller;

import com.project.board.domain.user.dto.UserJoinRequestDto;
import com.project.board.domain.user.dto.UserUpdateNicknameRequestDto;
import com.project.board.domain.user.dto.UserUpdatePasswordRequestDto;
import com.project.board.domain.user.web.UserService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public Long join(@RequestBody UserJoinRequestDto userJoinRequestDto) {
        return userService.join(userJoinRequestDto);
    }

    @PutMapping("/nickname")
    public String updateNickname(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestBody UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        return userService.updateNickname(userDetails.getUsername(), userUpdateNicknameRequestDto);
    }

    @PutMapping("/password")
    public String updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestBody UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        return userService.updatePassword(userDetails.getUsername(), userUpdatePasswordRequestDto);
    }

    @DeleteMapping
    public String delete(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.delete(userDetails.getUsername());
    }
}
