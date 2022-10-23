package com.project.board.global.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.board.domain.user.dto.UserLoginRequestDto;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {
            /*
                request에서 username과 password를 파싱해서 Object로 받는다.
            */
            UserLoginRequestDto userLoginRequestDto = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginRequestDto.class);
            /*
                AuthenticationManager는 사용자 아이디 / 비밀번호가 유효한 인증인지 확인한다.
                .authenticate()메서드 내의 Authentication이 유효한지 확인하고, Authentication 객체를 리턴한다.
            */
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDto.getEmail(),
                            userLoginRequestDto.getPassword()
                    ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        // 토큰 생성
        String jwtToken = JWT.create()
                .withIssuer("ShallWe") // 발행자
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME)) // 토큰 유효기간
                .withClaim("email", userDetails.getUser().getEmail())
                .withClaim("nickname", userDetails.getUser().getNickname()) // 토큰에 담은 정보(번호)
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX + jwtToken);

    }

}