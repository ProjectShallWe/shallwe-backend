package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.infrastructure.post.PostCategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.project.board.infrastructure.repositoryFixture.BoardFixture.createBoard1;
import static com.project.board.infrastructure.repositoryFixture.PostCategoryFixture.createPostCategory1;
import static com.project.board.infrastructure.repositoryFixture.PostCategoryFixture.createPostCategory2;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Test
    void findAllWithPostCategories() {
        //given
        Board board = createBoard1();
        PostCategory postCategory1 = createPostCategory1(board);
        PostCategory postCategory2 = createPostCategory2(board);

        Board savedBoard = boardRepository.save(board);
        PostCategory savedPostCategory1 = postCategoryRepository.save(postCategory1);
        PostCategory savedPostCategory2 = postCategoryRepository.save(postCategory2);

        //when
        List<Board> savedBoards = boardRepository.findAllWithPostCategories(1L);

        //then
        Assertions.assertThat(savedBoards.get(0).getId()).isEqualTo(savedBoard.getId());
        Assertions.assertThat(savedBoards.get(0).getTitle()).isEqualTo(savedBoard.getTitle());
        Assertions.assertThat(savedPostCategory1.getBoard().getId()).isEqualTo(savedBoards.get(0).getId());
        Assertions.assertThat(savedPostCategory1.getBoard().getTitle()).isEqualTo(savedBoards.get(0).getTitle());
        Assertions.assertThat(savedPostCategory2.getBoard().getId()).isEqualTo(savedBoards.get(0).getId());
        Assertions.assertThat(savedPostCategory2.getBoard().getTitle()).isEqualTo(savedBoards.get(0).getTitle());
    }
}