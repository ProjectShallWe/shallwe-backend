package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.dto.CommentQueryDto;
import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.infrastructure.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.project.board.infrastructure.repositoryFixture.CommentFixture.createComment1;
import static com.project.board.infrastructure.repositoryFixture.CommentFixture.createComment2;
import static com.project.board.infrastructure.repositoryFixture.PostFixture.createPost1;
import static com.project.board.infrastructure.repositoryFixture.UserFixture.createUser1;

@DataJpaTest
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @Test
    void getCommentsInPostByPostId() {
        //given
        User user = createUser1();
        Post post = createPost1(user);
        Comment comment1 = createComment1(user, post);
        Comment comment2 = createComment2(user, post);

        User savedUser = userRepository.save(user);
        Post savedPost = postRepository.save(post);
        Comment savedComment1 = commentRepository.save(comment1);
        Comment savedComment2 = commentRepository.save(comment2);
        //when
        List<CommentQueryDto> queryDtos = commentRepository.getCommentsInPostByPostId(post.getId());

        //then
        Assertions.assertThat(queryDtos.get(0).getCommentId()).isEqualTo(savedComment1.getId());
        Assertions.assertThat(queryDtos.get(0).getNickname()).isEqualTo(savedUser.getNickname());
        Assertions.assertThat(queryDtos.get(0).getContent()).isEqualTo(savedComment1.getContent());
        Assertions.assertThat(queryDtos.get(1).getCommentId()).isEqualTo(savedComment2.getId());
        Assertions.assertThat(queryDtos.get(1).getNickname()).isEqualTo(savedUser.getNickname());
        Assertions.assertThat(queryDtos.get(1).getContent()).isEqualTo(savedComment2.getContent());
    }
}