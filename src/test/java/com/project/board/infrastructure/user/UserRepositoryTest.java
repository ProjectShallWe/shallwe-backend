package com.project.board.infrastructure.user;

import com.project.board.domain.user.web.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.project.board.infrastructure.fixture.UserFixture.createUser1;
import static org.assertj.core.api.Assertions.assertThat;

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
        Optional<User> findUserByEmail = userRepository.findByEmail(savedUser.getEmail());

        //then
        assertThat(findUserByEmail.get().getEmail()).isEqualTo("google0001@gmail.com");
        assertThat(findUserByEmail.get().getPassword()).isEqualTo("0001");
        assertThat(findUserByEmail.get().getNickname()).isEqualTo("구글0001");
        assertThat(findUserByEmail.get().getRole()).isEqualTo(User.Role.USER);
    }
}