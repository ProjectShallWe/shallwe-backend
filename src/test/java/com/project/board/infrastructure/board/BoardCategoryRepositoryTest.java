package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static com.project.board.infrastructure.repositoryFixture.BoardCategoryFixture.createBoardCategory;
import static com.project.board.infrastructure.repositoryFixture.BoardFixture.createBoard1;
import static com.project.board.infrastructure.repositoryFixture.BoardFixture.createBoard2;

@DataJpaTest
class BoardCategoryRepositoryTest {

    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void findAllWithBoards() {
        //given
        BoardCategory boardCategory = createBoardCategory();
        Board board1 = createBoard1(boardCategory);
        Board board2 = createBoard2(boardCategory);

        BoardCategory savedBoardCategory = boardCategoryRepository.save(boardCategory);
        Board savedBoard1 = boardRepository.save(board1);
        Board savedBoard2 =boardRepository.save(board2);

        //when
        List<BoardCategory> savedBoardCategories = boardCategoryRepository.findAllWithBoards();

        //then
        Assertions.assertThat(savedBoardCategories.get(0).getId()).isEqualTo(savedBoardCategory.getId());
        Assertions.assertThat(savedBoardCategories.get(0).getTopic()).isEqualTo(savedBoardCategory.getTopic());
        Assertions.assertThat(savedBoard1.getBoardCategory().getId()).isEqualTo(savedBoardCategories.get(0).getId());
        Assertions.assertThat(savedBoard1.getBoardCategory().getTopic()).isEqualTo(savedBoardCategories.get(0).getTopic());
        Assertions.assertThat(savedBoard2.getBoardCategory().getId()).isEqualTo(savedBoardCategories.get(0).getId());
        Assertions.assertThat(savedBoard2.getBoardCategory().getTopic()).isEqualTo(savedBoardCategories.get(0).getTopic());
    }
}