package com.project.board.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.board.domain.auth.AuthService;
import com.project.board.domain.auth.dto.TokenResDto;
import com.project.board.domain.user.dto.UserLoginRequestDto;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
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
    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

        try {

            // request에서 username과 password를 파싱해서 Object로 받는다.
            UserLoginRequestDto reqDto = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginRequestDto.class);
            /*
            * AuthenticationManager는 사용자 아이디 / 비밀번호가 유효한 인증인지 확인한다.
            * .authenticate()메서드 내의 Authentication이 유효한지 확인하고, Authentication 객체를 리턴한다.
            * */
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    reqDto.getEmail(),
                    reqDto.getPassword()
            );

            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authResult.getPrincipal();

        Date accessTokenExpiresIn = new Date(System.currentTimeMillis() + JwtProperties.ACCESS_EXPIRATION_TIME);
        Date refreshTokenExpiresIn = new Date(System.currentTimeMillis() + JwtProperties.REFRESH_EXPIRATION_TIME);
        String accessToken = tokenProvider.createAccessToken(
                userDetails.getUser().getEmail(),
                userDetails.getUser().getNickname(),
                accessTokenExpiresIn);
        String refreshToken = tokenProvider.createRefreshToken(
                userDetails.getUser().getEmail(),
                refreshTokenExpiresIn);

        authService.setRefreshTokenToRedis(userDetails.getUser().getEmail(), refreshToken);

        setResponse(response, accessToken, refreshToken, accessTokenExpiresIn, refreshTokenExpiresIn);

    }

    private void setResponse(HttpServletResponse res,
                             String accessToken,
                             String refreshToken,
                             Date accessTokenExpiresIn,
                             Date refreshTokenExpiresIn
    ) throws IOException {
        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType("application/json;charset=UTF-8");

        TokenResDto resDto = TokenResDto.builder()
                .grantType(JwtProperties.TOKEN_PREFIX)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshTokenExpiresIn(refreshTokenExpiresIn.getTime())
                .build();

        JSONObject jo = new JSONObject(resDto);

        res.getWriter().print(jo);
    }
}