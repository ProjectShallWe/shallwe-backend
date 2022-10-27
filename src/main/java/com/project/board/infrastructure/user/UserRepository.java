package com.project.board.infrastructure.user;

import com.project.board.domain.user.web.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByNickname(String nickname);
}
