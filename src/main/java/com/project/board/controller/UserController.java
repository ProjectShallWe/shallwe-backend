package com.project.board.controller;

import com.project.board.domain.user.dto.*;
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

    @PostMapping("/check/nickname")
    public Boolean checkNicknameDuplication(@RequestBody UserUpdateNicknameRequestDto userUpdateNicknameRequestDto) {
        return userService.checkNicknameDuplication(userUpdateNicknameRequestDto);
    }

    @PostMapping("/check/email")
    public Boolean checkEmailDuplication(@RequestBody UserEmailRequestDto userEmailRequestDto) {
        return userService.checkEmailDuplication(userEmailRequestDto);
    }

    @PostMapping("/check/password")
    public Boolean checkNowPassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                               @RequestBody UserNowPasswordRequestDto userNowPasswordRequestDto) {
        return userService.checkNowPassword(userDetails.getUsername(), userNowPasswordRequestDto);
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
