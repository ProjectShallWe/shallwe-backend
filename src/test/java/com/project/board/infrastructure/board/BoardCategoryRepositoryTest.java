package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardCategory;
import com.project.board.global.querydsl.QuerydslConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static com.project.board.infrastructure.fixture.BoardCategoryFixture.createBoardCategory1;
import static com.project.board.infrastructure.fixture.BoardCategoryFixture.createBoardCategory2;
import static com.project.board.infrastructure.fixture.BoardFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QuerydslConfig.class)
class BoardCategoryRepositoryTest {

    @Autowired
    private BoardCategoryRepository boardCategoryRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void findAllWithBoards() {
        //given
        BoardCategory boardCategory1 = createBoardCategory1();
        BoardCategory boardCategory2 = createBoardCategory2();
        Board board1 = createBoard1(boardCategory1);
        Board board2 = createBoard2(boardCategory1);
        Board board3 = createBoard3(boardCategory2);
        Board board4 = createBoard4(boardCategory2);
        boardCategory1.getBoards().add(board1);
        boardCategory1.getBoards().add(board2);
        boardCategory2.getBoards().add(board3);
        boardCategory2.getBoards().add(board4);

        boardCategoryRepository.save(boardCategory1);
        boardCategoryRepository.save(boardCategory2);
        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);

        //when
        List<BoardCategory> savedBoardCategories = boardCategoryRepository.findAllWithBoards();

        //then
        assertThat(savedBoardCategories.get(0).getTopic())
                .isEqualTo("스포츠");
        assertThat(savedBoardCategories.get(1).getTopic())
                .isEqualTo("게임");
        assertThat(savedBoardCategories.get(0).getBoards().get(0).getTitle())
                .isEqualTo("농구");
        assertThat(savedBoardCategories.get(0).getBoards().get(1).getTitle())
                .isEqualTo("축구");
        assertThat(savedBoardCategories.get(1).getBoards().get(0).getTitle())
                .isEqualTo("리그 오브 레전드");
        assertThat(savedBoardCategories.get(1).getBoards().get(1).getTitle())
                .isEqualTo("메이플스토리");
    }
}