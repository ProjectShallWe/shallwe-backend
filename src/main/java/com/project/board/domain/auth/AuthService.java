package com.project.board.domain.auth;

import com.project.board.domain.auth.dto.TokenResDto;
import com.project.board.domain.user.web.User;
import com.project.board.global.exception.CustomRefreshNotValidException;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.response.ErrorCode;
import com.project.board.global.security.jwt.JwtProperties;
import com.project.board.global.security.jwt.TokenProvider;
import com.project.board.infrastructure.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Transactional
    public TokenResDto reissue(String refreshToken) {
        User user = userRepository.findByRefreshToken(
                refreshToken.replace(JwtProperties.TOKEN_PREFIX, ""))
                .orElseThrow(() -> new CustomRefreshNotValidException(ErrorCode.JWT_REFRESH_NOT_VALID.getErrorMsg()));

        String accessToken = tokenProvider.createAccessToken(user.getEmail(), user.getNickname());
        String resRefreshToken = tokenProvider.createRefreshToken();

        user.setRefreshToken(resRefreshToken);

        return TokenResDto.builder()
                .grantType(JwtProperties.TOKEN_PREFIX)
                .accessToken(accessToken)
                .refreshToken(resRefreshToken)
                .build();
    }

    @Transactional
    public Long logOut(String refreshToken) {
        String tokenValue = refreshToken
                .replace(JwtProperties.TOKEN_PREFIX, "");
        User user = userRepository.findByRefreshToken(tokenValue)
                .orElseThrow(CustomRefreshNotValidException::new);
        user.setRefreshToken(null);

        return user.getId();
    }

    @Transactional
    public void setRefreshToken(String email, String refreshToken) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
        user.setRefreshToken(refreshToken);
    }
}
