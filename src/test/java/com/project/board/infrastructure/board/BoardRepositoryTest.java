package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.web.PostCategory;
import com.project.board.infrastructure.post.PostCategoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Test
    void findAllWithPostCategories() {
        //given
        Board board1 = Board.builder()
                .id(1L)
                .title("농구")
                .build();
        PostCategory postCategory1 = PostCategory.builder()
                .id(1L)
                .topic("국내 농구")
                .board(board1)
                .build();
        PostCategory postCategory2 = PostCategory.builder()
                .id(2L)
                .topic("해외 농구")
                .board(board1)
                .build();
        Board savedBoard1 = boardRepository.save(board1);
        PostCategory savedPostCategory1 = postCategoryRepository.save(postCategory1);
        PostCategory savedPostCategory2 = postCategoryRepository.save(postCategory2);

        //when
        List<Board> savedBoards = boardRepository.findAllWithPostCategories(1L);

        //then
        Assertions.assertThat(savedBoards.get(0).getId()).isEqualTo(savedBoard1.getId());
        Assertions.assertThat(savedBoards.get(0).getTitle()).isEqualTo(savedBoard1.getTitle());
        Assertions.assertThat(savedPostCategory1.getBoard().getId()).isEqualTo(savedBoards.get(0).getId());
        Assertions.assertThat(savedPostCategory1.getBoard().getTitle()).isEqualTo(savedBoards.get(0).getTitle());
        Assertions.assertThat(savedPostCategory2.getBoard().getId()).isEqualTo(savedBoards.get(0).getId());
        Assertions.assertThat(savedPostCategory2.getBoard().getTitle()).isEqualTo(savedBoards.get(0).getTitle());
    }
}