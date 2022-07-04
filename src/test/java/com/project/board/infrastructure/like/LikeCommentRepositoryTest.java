package com.project.board.infrastructure.like;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.infrastructure.comment.CommentRepository;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.infrastructure.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.project.board.infrastructure.fixture.CommentFixture.createComment1;
import static com.project.board.infrastructure.fixture.LikeCommentFixture.createLikeComment;
import static com.project.board.infrastructure.fixture.PostFixture.createPost1;
import static com.project.board.infrastructure.fixture.UserFixture.createUser1;
import static com.project.board.infrastructure.fixture.UserFixture.createUser2;

@DataJpaTest
class LikeCommentRepositoryTest {

    @Autowired
    private LikeCommentRepository likeCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void findByUserIdAndPostId() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Post post = createPost1(user1);

        User savedUser1 = userRepository.save(user1);
        userRepository.save(user2);
        postRepository.save(post);

        Comment comment = createComment1(user2, post);
        Comment savedComment = commentRepository.save(comment);

        LikeComment likeComment = createLikeComment(user1, comment);
        LikeComment savedLikeComment = likeCommentRepository.save(likeComment);

        //when
        Optional<LikeComment> findLikeComment = likeCommentRepository.findByUserIdAndCommentId(savedUser1.getId(), savedComment.getId());

        //then
        Assertions.assertThat(findLikeComment.get().getUser())
                .isEqualTo(savedLikeComment.getUser());
        Assertions.assertThat(findLikeComment.get().getComment())
                .isEqualTo(savedLikeComment.getComment());
    }
}