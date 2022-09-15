package com.project.board.infrastructure.post;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.dto.PostDetailsQueryDto;
import com.project.board.domain.post.dto.PostsQueryDto;
import com.project.board.domain.post.dto.RecommendPostsQueryDto;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.domain.user.web.User;
import com.project.board.global.audit.JpaAuditConfig;
import com.project.board.global.querydsl.QuerydslConfig;
import com.project.board.infrastructure.board.BoardRepository;
import com.project.board.infrastructure.like.LikePostRepository;
import com.project.board.infrastructure.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.project.board.infrastructure.fixture.BoardFixture.createBoard1;
import static com.project.board.infrastructure.fixture.PostCategoryFixture.createPostCategory1;
import static com.project.board.infrastructure.fixture.PostFixture.*;
import static com.project.board.infrastructure.fixture.UserFixture.createUser1;
import static com.project.board.infrastructure.fixture.UserFixture.createUser2;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JpaAuditConfig.class))
@Import(QuerydslConfig.class)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LikePostRepository likePostRepository;


    private static final Pageable pageable = PageRequest.of(0, 10);

    @Test
    void findAllInBoard() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Board board = createBoard1();
        PostCategory postCategory = createPostCategory1(board);
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);

        userRepository.save(user1);
        userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        postCategoryRepository.save(postCategory);
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findAllInBoard(savedBoard.getId(), pageable);

        //then
        assertThat(queryDtos.getContent().get(0).getTitle())
                .isEqualTo("농구 잘하는 사람");
        assertThat(queryDtos.getContent().get(1).getTitle())
                .isEqualTo("농구 잘하는 법");
    }

    @Test
    void findAllInPostCategory() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Board board = createBoard1();
        PostCategory postCategory = createPostCategory1(board);
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);

        userRepository.save(user1);
        userRepository.save(user2);
        boardRepository.save(board);
        PostCategory savedPostCategory = postCategoryRepository.save(postCategory);
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findAllInPostCategory(savedPostCategory.getId(), pageable);

        //then
        assertThat(queryDtos.getContent().get(0).getTitle())
                .isEqualTo("농구 잘하는 사람");
        assertThat(queryDtos.getContent().get(1).getTitle())
                .isEqualTo("농구 잘하는 법");
    }

    @Test
    void findPostsByPostTitleInBoard() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Board board = createBoard1();
        PostCategory postCategory = createPostCategory1(board);
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);

        userRepository.save(user1);
        userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        postCategoryRepository.save(postCategory);
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findPostsBySearchWordInBoard(savedBoard.getId(), "title", "사람", pageable);

        //then
        assertThat(queryDtos.getContent().get(0).getTitle())
                .isEqualTo("농구 잘하는 사람");
    }

    @Test
    void findPostsByPostContentInBoard() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Board board = createBoard1();
        PostCategory postCategory = createPostCategory1(board);
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);

        userRepository.save(user1);
        userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        postCategoryRepository.save(postCategory);
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findPostsBySearchWordInBoard(savedBoard.getId(), "content", "드리블", pageable);

        //then
        assertThat(queryDtos.getContent().get(0).getTitle())
                .isEqualTo("농구 잘하는 사람");
    }

    @Test
    void findPostsByPostTitleOrPostContentInBoard() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Board board = createBoard1();
        PostCategory postCategory = createPostCategory1(board);
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);

        userRepository.save(user1);
        userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        postCategoryRepository.save(postCategory);
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findPostsBySearchWordInBoard(savedBoard.getId(), "ticon", "농구", pageable);

        //then
        assertThat(queryDtos.getContent().get(0).getTitle())
                .isEqualTo("농구 잘하는 사람");
        assertThat(queryDtos.getContent().get(1).getTitle())
                .isEqualTo("농구 잘하는 법");
    }

    @Test
    void findPostsByUserNicknameInBoard() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Board board = createBoard1();
        PostCategory postCategory = createPostCategory1(board);
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);

        userRepository.save(user1);
        userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        postCategoryRepository.save(postCategory);
        postRepository.save(post1);
        postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findPostsBySearchWordInBoard(savedBoard.getId(), "nickname", "0001", pageable);

        //then
        assertThat(queryDtos.getContent().get(0).getTitle())
                .isEqualTo("농구 잘하는 법");
    }

    @Test
    void findPostDetailsBy() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        PostCategory postCategory = createPostCategory1();
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);

        userRepository.save(user1);
        userRepository.save(user2);
        postCategoryRepository.save(postCategory);
        postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);

        //when
        Optional<PostDetailsQueryDto> queryDtos
                = postRepository.findPostDetailsBy(savedPost2.getId());
        //then
        assertThat(queryDtos.get().getTitle()).isEqualTo("농구 잘하는 사람");
        assertThat(queryDtos.get().getNickname()).isEqualTo("구글0002");
        assertThat(queryDtos.get().getPostCategory()).isEqualTo("국내 농구");
    }

    @Test
    void findRecommendPostsInBoard() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Board board = createBoard1();
        PostCategory postCategory = createPostCategory1(board);
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);
        Post post3 = createPost3(user1, postCategory);

        userRepository.save(user1);
        userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        postCategoryRepository.save(postCategory);
        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        post2.addLikeCount();
        post2.addLikeCount();
        post1.addLikeCount();
        post3.addCommentCount();
        post3.addCommentCount();
        post3.addCommentCount();

        //when
        Page<RecommendPostsQueryDto> queryDtos
                = postRepository.findRecommendPostsInBoard(savedBoard.getId(), LocalDateTime.now(),
                                                           LocalDateTime.now().minusHours(12), pageable);

        //then
        assertThat(queryDtos.getContent().get(0).getTitle()).isEqualTo("농구의 신은 누구일까?");
        assertThat(queryDtos.getContent().get(1).getTitle()).isEqualTo("농구 잘하는 사람");
        assertThat(queryDtos.getContent().get(2).getTitle()).isEqualTo("농구 잘하는 법");

    }
}