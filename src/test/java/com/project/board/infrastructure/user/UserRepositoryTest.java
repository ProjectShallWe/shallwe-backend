package com.project.board.infrastructure.user;

import com.project.board.domain.user.web.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.project.board.infrastructure.repositoryFixture.UserFixture.createUser1;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByEmail() {
        //given
        User user = createUser1();

        User savedUser = userRepository.save(user);

        //when
        Optional <User> findByEmailUser = userRepository.findByEmail(savedUser.getEmail());

        //then
        Assertions.assertThat(findByEmailUser.get().getEmail()).isEqualTo(user.getEmail());
        Assertions.assertThat(findByEmailUser.get().getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(findByEmailUser.get().getNickname()).isEqualTo(user.getNickname());
    }
}