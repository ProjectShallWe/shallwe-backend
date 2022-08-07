package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.global.querydsl.QuerydslConfig;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.infrastructure.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static com.project.board.infrastructure.fixture.LikePostFixture.createLikePost;
import static com.project.board.infrastructure.fixture.PostFixture.createPost1;
import static com.project.board.infrastructure.fixture.UserFixture.createUser1;
import static com.project.board.infrastructure.fixture.UserFixture.createUser2;

@DataJpaTest
@Import(QuerydslConfig.class)
class LikePostRepositoryTest {

    @Autowired
    private LikePostRepository likePostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    void findByUserIdAndPostId() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Post post = createPost1(user1);

        userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Post savedPost = postRepository.save(post);

        LikePost likePost = createLikePost(user2, post);
        LikePost savedLikePost = likePostRepository.save(likePost);

        //when
        Optional<LikePost> findLikePost = likePostRepository.findByUserIdAndPostId(savedUser2.getId(), savedPost.getId());

        //then
        Assertions.assertThat(findLikePost.get().getUser())
                .isEqualTo(savedLikePost.getUser());
        Assertions.assertThat(findLikePost.get().getPost())
                .isEqualTo(savedLikePost.getPost());
    }
}