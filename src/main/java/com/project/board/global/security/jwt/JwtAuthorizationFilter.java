package com.project.board.global.security.jwt;


import com.project.board.domain.user.web.User;
import com.project.board.global.exception.*;
import com.project.board.global.security.UserDetailsImpl;
import com.project.board.infrastructure.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,
                                  TokenProvider tokenProvider,
                                  UserRepository userRepository) {
        super(authenticationManager);
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        /*
        * 1. header에 'Authorization'이 있는지,
        * 2. value가 'TOKEN_PREFIX'로 시작하는지 검증한다.
        *
        * 없으면 다시 필터에 태운다.
        * */

        String header = request.getHeader(JwtProperties.ACCESS_HEADER_PREFIX);
        if (header == null || !header.startsWith(JwtProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        // 'TOKEN_PREFIX'를 제거한다.
        String accessToken = tokenProvider.extractToken(request);

        // 유효한 JWT 토큰인지 확인한다.
        if (tokenProvider.validateAccessToken(accessToken)) {
            try {
                String email = tokenProvider.getEmailFrom(accessToken);
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));

                /*
                * 1. 시큐리티 내 권한 처리를 위해 'UsernamePasswordAuthenticationToken'을 만들고,
                * 2. 이를 통해 Authentication 객체를 만든다.
                * */
                UserDetailsImpl userDetails =
                        new UserDetailsImpl(user);
                Authentication auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Authentication 객체를 시큐리티 세션에 저장한다.
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (UsernameNotFoundException e) {
                throw new CustomAccessNotValidException();
            }
        }

        chain.doFilter(request, response);
    }
}
