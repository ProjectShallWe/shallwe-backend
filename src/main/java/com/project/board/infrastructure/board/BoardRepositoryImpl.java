package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.board.domain.board.web.QBoard.board;
import static com.project.board.domain.post.web.QPostCategory.postCategory;

@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> findAllWithPostCategories(Long boardId) {
        return queryFactory
                .selectFrom(board)
                .distinct()
                .join(board.postCategories, postCategory).fetchJoin()
                .where(board.id.eq(boardId))
                .orderBy(board.id.asc(), postCategory.id.asc())
                .fetch();
    }

    @Override
    public List<Board> findBoardsBySearchWord(String keyword) {
        return queryFactory
                .selectFrom(board)
                .distinct()
                .where(board.title.contains(keyword))
                .orderBy(board.id.asc())
                .fetch();
    }
}
