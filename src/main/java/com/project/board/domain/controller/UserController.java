package com.project.board.domain.controller;

import com.project.board.domain.user.dto.UserRequestDto;
import com.project.board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/join")
    public void join(@RequestBody UserRequestDto userRequestDto) {
        userService.join(userRequestDto);
    }
}
