package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.dto.CommentQueryDto;
import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.user.web.User;
import com.project.board.infrastructure.post.PostRepository;
import com.project.board.infrastructure.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.project.board.infrastructure.fixture.CommentFixture.*;
import static com.project.board.infrastructure.fixture.PostFixture.createPost1;
import static com.project.board.infrastructure.fixture.UserFixture.createUser1;
import static com.project.board.infrastructure.fixture.UserFixture.createUser2;
import static org.assertj.core.api.Assertions.assertThat;

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
        User user1 = createUser1();
        User user2 = createUser2();
        Post post = createPost1(user1);
        Comment comment1 = createComment1(user2, post);
        Comment comment2 = createComment2(user1, post);

        userRepository.save(user1);
        userRepository.save(user2);
        Post savedPost = postRepository.save(post);
        Comment savedComment1 = commentRepository.save(comment1);
        commentRepository.save(comment2);

        // childComment는 ParentComment 하위에 오기 때문에 무조건 후생성
        Comment comment3 = createComment3(user1, post, comment1);
        commentRepository.save(comment3);

        //when
        List<CommentQueryDto> queryDtos = commentRepository.getCommentsInPostByPostId(savedPost.getId());

        //then
        assertThat(queryDtos.get(0).getContent()).isEqualTo("농구공을 어떻게 잘 던지는데요?");
        assertThat(queryDtos.get(0).getParentId()).isNull();
        assertThat(queryDtos.get(1).getContent()).isEqualTo("팔꿈치가 수직이어야 멀리 던질 수 있어요!");
        assertThat(queryDtos.get(1).getParentId()).isNull();
        assertThat(queryDtos.get(2).getContent()).isEqualTo("스테판 커리 영상을 참고해보세요!");
        assertThat(queryDtos.get(2).getParentId()).isEqualTo(savedComment1.getId());
    }
}