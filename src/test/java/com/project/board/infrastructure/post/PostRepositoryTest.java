package com.project.board.infrastructure.post;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.post.dto.PostDetailsQueryDto;
import com.project.board.domain.post.dto.PostsQueryDto;
import com.project.board.domain.post.dto.RecommendPostsInBoardQueryDto;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.domain.user.web.User;
import com.project.board.global.audit.JpaAuditConfig;
import com.project.board.infrastructure.board.BoardRepository;
import com.project.board.infrastructure.like.LikePostRepository;
import com.project.board.infrastructure.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.project.board.infrastructure.repositoryFixture.BoardFixture.createBoard1;
import static com.project.board.infrastructure.repositoryFixture.LikePostFixture.createLikePost;
import static com.project.board.infrastructure.repositoryFixture.PostCategoryFixture.createPostCategory1;
import static com.project.board.infrastructure.repositoryFixture.PostFixture.createPost1;
import static com.project.board.infrastructure.repositoryFixture.PostFixture.createPost2;
import static com.project.board.infrastructure.repositoryFixture.UserFixture.createUser1;
import static com.project.board.infrastructure.repositoryFixture.UserFixture.createUser2;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JpaAuditConfig.class
))
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

    @Test
    void findAllInBoard() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        Board board = createBoard1();
        PostCategory postCategory = createPostCategory1(board);
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        PostCategory savedPostCategory = postCategoryRepository.save(postCategory);
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findAllInBoard(savedBoard.getId(), PageRequest.of(0, 10));

        //then
        Assertions.assertThat(queryDtos.getContent().get(0).getPostId()).isEqualTo(savedPost2.getId());
        Assertions.assertThat(queryDtos.getContent().get(1).getPostId()).isEqualTo(savedPost1.getId());
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

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        PostCategory savedPostCategory = postCategoryRepository.save(postCategory);
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findAllInPostCategory(savedPostCategory.getId(), PageRequest.of(0, 10));

        //then
        Assertions.assertThat(queryDtos.getContent().get(0).getPostId()).isEqualTo(savedPost2.getId());
        Assertions.assertThat(queryDtos.getContent().get(1).getPostId()).isEqualTo(savedPost1.getId());
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

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        PostCategory savedPostCategory = postCategoryRepository.save(postCategory);
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findPostsByPostTitleInBoard(savedBoard.getId(), "사람", PageRequest.of(0, 10));

        //then
        Assertions.assertThat(queryDtos.getContent().get(0).getPostId()).isEqualTo(savedPost2.getId());
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

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        PostCategory savedPostCategory = postCategoryRepository.save(postCategory);
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findPostsByPostContentInBoard(savedBoard.getId(), "드리블", PageRequest.of(0, 10));

        //then
        Assertions.assertThat(queryDtos.getContent().get(0).getPostId()).isEqualTo(savedPost2.getId());
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

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        PostCategory savedPostCategory = postCategoryRepository.save(postCategory);
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findPostsByPostTitleOrPostContentInBoard(savedBoard.getId(), "농구", PageRequest.of(0, 10));

        //then
        Assertions.assertThat(queryDtos.getContent().get(0).getPostId()).isEqualTo(savedPost2.getId());
        Assertions.assertThat(queryDtos.getContent().get(1).getPostId()).isEqualTo(savedPost1.getId());
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

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        PostCategory savedPostCategory = postCategoryRepository.save(postCategory);
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);

        //when
        Page<PostsQueryDto> queryDtos
                = postRepository.findPostsByUserNicknameInBoard(savedBoard.getId(), "1234", PageRequest.of(0, 10));

        //then
        Assertions.assertThat(queryDtos.getContent().get(0).getPostId()).isEqualTo(savedPost1.getId());
    }

    @Test
    void findPostDetailsBy() {
        //given
        User user1 = createUser1();
        User user2 = createUser2();
        PostCategory postCategory = createPostCategory1();
        Post post1 = createPost1(user1, postCategory);
        Post post2 = createPost2(user2, postCategory);

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        PostCategory savedPostCategory = postCategoryRepository.save(postCategory);
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);

        //when
        Optional<PostDetailsQueryDto> queryDtos
                = postRepository.findPostDetailsBy(savedPost2.getId());
        //then
        Assertions.assertThat(queryDtos.get().getPostId()).isEqualTo(savedPost2.getId());
        Assertions.assertThat(queryDtos.get().getNickname()).isEqualTo(savedUser2.getNickname());
        Assertions.assertThat(queryDtos.get().getPostCategory()).isEqualTo(savedPostCategory.getTopic());
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
        LikePost likePost = createLikePost(user1, post2);

        User savedUser1 = userRepository.save(user1);
        User savedUser2 = userRepository.save(user2);
        Board savedBoard = boardRepository.save(board);
        PostCategory savedPostCategory = postCategoryRepository.save(postCategory);
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);
        LikePost savedLikePost = likePostRepository.save(likePost);

        //when
        Page<RecommendPostsInBoardQueryDto> queryDtos
                = postRepository.findRecommendPostsInBoard(savedBoard.getId(), LocalDateTime.now(), LocalDateTime.now().minusHours(12),
                                                    3L, 7L, PageRequest.of(0, 10));

        //then
        Assertions.assertThat(queryDtos.getContent().get(0).getPostId()).isEqualTo(savedPost2.getId());
        Assertions.assertThat(queryDtos.getContent().get(0).getPostCategory()).isEqualTo(savedPostCategory.getTopic());
        Assertions.assertThat(queryDtos.getContent().get(1).getPostId()).isEqualTo(savedPost1.getId());
        Assertions.assertThat(queryDtos.getContent().get(1).getPostCategory()).isEqualTo(savedPostCategory.getTopic());
    }
}