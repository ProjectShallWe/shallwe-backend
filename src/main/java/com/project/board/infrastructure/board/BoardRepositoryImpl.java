package com.project.board.infrastructure.board;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.web.Post;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.project.board.domain.board.web.QBoard.board;
import static com.project.board.domain.post.web.QPost.post;
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

    @Override
    public List<Board> findBoardsByRecommendScore(LocalDateTime now, LocalDateTime twelveHoursAgo) {
        QueryResults<Board> results = queryFactory
                .selectFrom(board)
                .distinct()
                .join(board.postCategories, postCategory)
                .join(postCategory.posts, post)
                .where(post.createdDate.loe(now),
                        post.createdDate.gt(twelveHoursAgo),
                        post.status.eq(Post.Status.ENABLE))
                .orderBy(post.hits.sum()
                        .add(post.commentCount.sum())
                        .add(post.likeCount.sum())
                        .desc())
                .groupBy(board.id)
                .fetchResults();
        return results.getResults();
    }
}
