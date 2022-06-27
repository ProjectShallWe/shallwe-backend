package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.domain.user.web.User;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.infrastructure.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
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
        User user1 = User.builder()
                .email("google1234@gmail.com")
                .password("1234")
                .nickname("구글1234")
                .build();
        User user2 = User.builder()
                .email("google2345@gmail.com")
                .password("2345")
                .nickname("구글2345")
                .build();
        PostCategory postCategory = PostCategory.builder()
                .topic("국내 농구")
                .build();
        Post post = Post.builder()
                .title("농구 잘하는 법")
                .content("1. 농구공을 잘 던진다.")
                .user(user1)
                .postCategory(postCategory)
                .build();
        LikePost likePost = LikePost.builder()
                .user(user2)
                .post(post)
                .build();
        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Post savedPost = postRepository.save(post);
        LikePost savedLikePost = likePostRepository.save(likePost);

        //when
        Optional<LikePost> findLikePost = likePostRepository.findByUserIdAndPostId(savedUser2.getId(), savedPost.getId());

        //then
        Assertions.assertThat(findLikePost.get().getId()).isEqualTo(savedLikePost.getId());
        Assertions.assertThat(findLikePost.get().getUser()).isEqualTo(savedLikePost.getUser());
        Assertions.assertThat(findLikePost.get().getPost()).isEqualTo(savedLikePost.getPost());
    }
}