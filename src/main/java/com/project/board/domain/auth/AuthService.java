package com.project.board.domain.auth;

import com.project.board.domain.auth.dto.TokenResDto;
import com.project.board.domain.user.web.User;
import com.project.board.global.exception.CustomRefreshNotValidException;
import com.project.board.global.redis.CacheKey;
import com.project.board.global.security.jwt.JwtProperties;
import com.project.board.global.security.jwt.TokenProvider;
import com.project.board.infrastructure.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public TokenResDto reissue(String refreshToken) {
        String tokenValue = refreshToken
                .replace(JwtProperties.TOKEN_PREFIX, "");

        if (!tokenProvider.validateRefreshToken(tokenValue)) {
            throw new RuntimeException();
        }

        String email = tokenProvider.getEmailFrom(tokenValue);

        if (!getRefreshTokenByEmail(email).equals(tokenValue)) {
            throw new CustomRefreshNotValidException();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(CustomRefreshNotValidException::new);

        Date accessTokenExpiresIn = new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME);
        Date refreshTokenExpiresIn = new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME);
        String accessToken = tokenProvider.createAccessToken(user.getEmail(), user.getNickname(), accessTokenExpiresIn);
        String resRefreshToken = tokenProvider.createRefreshToken(user.getEmail(), refreshTokenExpiresIn);

        setRefreshTokenToRedis(user.getEmail(), resRefreshToken);

        return TokenResDto.builder()
                .grantType(JwtProperties.TOKEN_PREFIX)
                .accessToken(accessToken)
                .refreshToken(resRefreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();
    }

    @Transactional
    public void setRefreshTokenToRedis(String email, String refreshToken) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String key = CacheKey.REFRESH_TOKEN + email;
        vop.set(key, refreshToken);
        redisTemplate.expireAt(key, Date.from(ZonedDateTime.now()
                .plusHours(CacheKey.REFRESH_TOKEN_EXPIRE_SEC).toInstant()));
    }

    @Transactional
    public String logOut(String refreshToken) {
        String tokenValue = refreshToken
                .replace(JwtProperties.TOKEN_PREFIX, "");

        if (!tokenProvider.validateRefreshToken(tokenValue)) {
            throw new RuntimeException();
        }

        String email = tokenProvider.getEmailFrom(tokenValue);

        if (!getRefreshTokenByEmail(email).equals(tokenValue)) {
            throw new CustomRefreshNotValidException();
        }

        deleteRefreshTokenToRedis(email);

        return email;
    }

    @Transactional
    public void deleteRefreshTokenToRedis(String email) {
        redisTemplate.delete(CacheKey.REFRESH_TOKEN + email);
    }

    @Transactional
    public String getRefreshTokenByEmail(String email) {
        return redisTemplate.opsForValue()
                .get(CacheKey.REFRESH_TOKEN + email);
    }
}
