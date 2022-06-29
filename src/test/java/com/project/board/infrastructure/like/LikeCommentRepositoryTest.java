package com.project.board.infrastructure.like;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.like.web.LikeComment;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.infrastructure.comment.CommentRepository;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.infrastructure.repositoryFixture.CommentFixture;
import com.project.board.infrastructure.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static com.project.board.infrastructure.repositoryFixture.LikeCommentFixture.createLikeComment;
import static com.project.board.infrastructure.repositoryFixture.PostFixture.createPost1;
import static com.project.board.infrastructure.repositoryFixture.UserFixture.createUser1;
import static com.project.board.infrastructure.repositoryFixture.UserFixture.createUser2;

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
        Comment comment = CommentFixture.createComment1(user2, post);
        LikeComment likeComment = createLikeComment(user2, comment);

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Post savedPost = postRepository.save(post);
        Comment savedComment = commentRepository.save(comment);
        LikeComment savedLikeComment = likeCommentRepository.save(likeComment);

        //when
        Optional<LikeComment> findLikeComment = likeCommentRepository.findByUserIdAndCommentId(savedUser2.getId(), savedComment.getId());

        //then
        Assertions.assertThat(findLikeComment.get().getId()).isEqualTo(savedLikeComment.getId());
        Assertions.assertThat(findLikeComment.get().getUser()).isEqualTo(savedLikeComment.getUser());
        Assertions.assertThat(findLikeComment.get().getComment()).isEqualTo(savedLikeComment.getComment());
    }
}