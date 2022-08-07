package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.global.querydsl.QuerydslConfig;
import com.project.board.infrastructure.post.PostCategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.project.board.infrastructure.fixture.BoardFixture.createBoard1;
import static com.project.board.infrastructure.fixture.PostCategoryFixture.createPostCategory1;
import static com.project.board.infrastructure.fixture.PostCategoryFixture.createPostCategory2;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Test
    void findAllWithPostCategories() {
        //given
        Board board1 = createBoard1();
        PostCategory postCategory1 = createPostCategory1(board1);
        PostCategory postCategory2 = createPostCategory2(board1);
        board1.getPostCategories().add(postCategory1);
        board1.getPostCategories().add(postCategory2);

        Board savedBoard = boardRepository.save(board1);
        postCategoryRepository.save(postCategory1);
        postCategoryRepository.save(postCategory2);

        //when
        List<Board> savedBoards = boardRepository.findAllWithPostCategories(savedBoard.getId());

        //then
        assertThat(savedBoards.get(0).getTitle())
                .isEqualTo("농구");
        assertThat(savedBoards.get(0).getPostCategories().get(0).getTopic())
                .isEqualTo("국내 농구");
        assertThat(savedBoards.get(0).getPostCategories().get(1).getTopic())
                .isEqualTo("해외 농구");
    }
}