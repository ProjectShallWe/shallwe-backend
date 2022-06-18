package com.project.board.infrastructure.user;

import com.project.board.domain.user.web.UserReader;
import com.project.board.domain.user.web.User;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {
    private final UserRepository userRepository;

    @Override
    public User getUserBy(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }
}
