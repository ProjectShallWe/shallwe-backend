package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.BoardCategory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.board.domain.board.web.QBoard.board;
import static com.project.board.domain.board.web.QBoardCategory.boardCategory;

@RequiredArgsConstructor
public class BoardCategoryRepositoryImpl implements BoardCategoryRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BoardCategory> findAllWithBoards() {
        return queryFactory
                .selectFrom(boardCategory)
                .distinct()
                .join(boardCategory.boards, board)
                .fetchJoin()
                .orderBy(boardCategory.id.asc(), board.id.asc())
                .fetch();
    }
}
