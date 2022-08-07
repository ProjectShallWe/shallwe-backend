package com.project.board.global.security;

import com.project.board.domain.user.web.User;
import com.project.board.infrastructure.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("이메일을 찾을 수 없습니다."));
        UserDetails userDetails = new UserDetailsImpl(user, getUserDetails(user));
        return userDetails;
    }

    public static Collection<SimpleGrantedAuthority> getUserDetails(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if(user.getRole().equals(User.Role.USER)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        } else if(user.getRole().equals(User.Role.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return authorities;
    }
}
