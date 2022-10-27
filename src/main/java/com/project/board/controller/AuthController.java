package com.project.board.controller;

import com.project.board.domain.auth.dto.TokenReqDto;
import com.project.board.domain.auth.dto.TokenResDto;
import com.project.board.domain.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public TokenResDto reissue(@RequestBody TokenReqDto tokenReqDto) {
        return authService.reissue(tokenReqDto.getRefreshToken());
    }

    @PostMapping("/logout")
    public String logOut(@RequestBody TokenReqDto tokenReqDto) {
        return authService.logOut(tokenReqDto.getRefreshToken());
    }
}
