package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardCategory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
class BoardCategoryRepositoryTest {

    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void findAllWithBoards() {
        //given
        BoardCategory boardCategory1 = BoardCategory.builder()
                .id(1L)
                .topic("스포츠 카테고리")
                .boards(new ArrayList<>())
                .build();
        Board board1 = Board.builder()
                .id(1L)
                .title("농구")
                .boardCategory(boardCategory1)
                .build();
        Board board2 = Board.builder()
                .id(2L)
                .title("축구")
                .boardCategory(boardCategory1)
                .build();
        BoardCategory savedBoardCategory1 = boardCategoryRepository.save(boardCategory1);
        Board savedBoard1 = boardRepository.save(board1);
        Board savedBoard2 =boardRepository.save(board2);

        //when
        List<BoardCategory> savedBoardCategories = boardCategoryRepository.findAllWithBoards();

        //then
        Assertions.assertThat(savedBoardCategories.get(0).getId()).isEqualTo(savedBoardCategory1.getId());
        Assertions.assertThat(savedBoardCategories.get(0).getTopic()).isEqualTo(savedBoardCategory1.getTopic());
        Assertions.assertThat(savedBoard1.getBoardCategory().getId()).isEqualTo(savedBoardCategories.get(0).getId());
        Assertions.assertThat(savedBoard1.getBoardCategory().getTopic()).isEqualTo(savedBoardCategories.get(0).getTopic());
        Assertions.assertThat(savedBoard2.getBoardCategory().getId()).isEqualTo(savedBoardCategories.get(0).getId());
        Assertions.assertThat(savedBoard2.getBoardCategory().getTopic()).isEqualTo(savedBoardCategories.get(0).getTopic());
    }
}