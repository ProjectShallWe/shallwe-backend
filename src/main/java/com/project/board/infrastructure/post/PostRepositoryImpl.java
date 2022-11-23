package com.project.board.infrastructure.post;

import com.project.board.controller.PostSearchType;
import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.dto.*;
import com.project.board.domain.post.web.Post;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.project.board.domain.board.web.QBoard.board;
import static com.project.board.domain.post.web.QPost.post;
import static com.project.board.domain.post.web.QPostCategory.postCategory;
import static com.project.board.domain.user.web.QUser.user;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<PostsQueryDto> findAllInBoard(Long boardId, Pageable pageable) {
        QueryResults<PostsQueryDto> results = queryFactory
                .select(Projections.constructor(PostsQueryDto.class,
                        post.id,
                        postCategory.id,
                        postCategory.topic,
                        post.title,
                        post.commentCount,
                        user.nickname,
                        post.createdDate,
                        post.hits,
                        post.likeCount,
                        post.thumbnailUrl
                ))
                .distinct()
                .from(post)
                .join(post.user, user)
                .join(post.postCategory, postCategory)
                .join(postCategory.board, board)
                .where(board.id.eq(boardId).and(post.status.eq(Post.Status.ENABLE)))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostsQueryDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<PostsQueryDto> findAllInPostCategory(Long postCategoryId, Pageable pageable) {
        QueryResults<PostsQueryDto> results = queryFactory
                .select(Projections.constructor(PostsQueryDto.class,
                        post.id,
                        postCategory.id,
                        postCategory.topic,
                        post.title,
                        post.commentCount,
                        user.nickname,
                        post.createdDate,
                        post.hits,
                        post.likeCount,
                        post.thumbnailUrl
                ))
                .from(post)
                .join(post.user, user)
                .join(post.postCategory, postCategory)
                .where(postCategory.id.eq(postCategoryId),
                       post.status.eq(Post.Status.ENABLE))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostsQueryDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Optional<PostDetailsQueryDto> findPostDetailsBy(Long postId) {
        return Optional.ofNullable(queryFactory
                .select(Projections.constructor(PostDetailsQueryDto.class,
                        post.id,
                        user.nickname,
                        post.createdDate,
                        postCategory.id,
                        postCategory.topic,
                        post.likeCount,
                        post.commentCount,
                        post.hits,
                        post.title,
                        post.content))
                .from(post)
                .join(post.postCategory, postCategory)
                .join(post.user, user)
                .where(post.id.eq(postId),
                       post.status.eq(Post.Status.ENABLE))
                .fetchOne()
        );
    }

    @Override
    public Page<RecommendPostsQueryDto> findRecommendPostsInBoard(Long boardId, LocalDateTime now, LocalDateTime twelveHoursAgo, Pageable pageable) {

        QueryResults<RecommendPostsQueryDto> results = queryFactory
                .select(Projections.constructor(RecommendPostsQueryDto.class,
                        post.id,
                        board.id,
                        postCategory.topic,
                        post.title,
                        post.commentCount
                        ))
                .from(post)
                .join(post.postCategory, postCategory)
                .join(postCategory.board, board)
                .where(board.id.eq(boardId),
                        post.createdDate.loe(now),
                        post.createdDate.gt(twelveHoursAgo),
                        post.status.eq(Post.Status.ENABLE))
                .orderBy(post.likeCount.add(post.commentCount).desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<RecommendPostsQueryDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<RecommendPostsQueryDto> findRecommendPosts(LocalDateTime now, LocalDateTime twelveHoursAgo, Pageable pageable) {
        QueryResults<RecommendPostsQueryDto> results = queryFactory
                .select(Projections.constructor(RecommendPostsQueryDto.class,
                        post.id,
                        board.id,
                        postCategory.topic,
                        post.title,
                        post.commentCount
                ))
                .from(post)
                .join(post.postCategory, postCategory)
                .join(postCategory.board, board)
                .where(post.createdDate.loe(now),
                       post.createdDate.gt(twelveHoursAgo),
                       post.status.eq(Post.Status.ENABLE))
                .orderBy(post.likeCount.add(post.commentCount).desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<RecommendPostsQueryDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<PostsQueryDto> findPostsBySearchWordInBoard(Long boardId, Long postCategoryId, String postSearchType, String searchWord, Pageable pageable) {
        QueryResults<PostsQueryDto> results = queryFactory
                .select(Projections.constructor(PostsQueryDto.class,
                        post.id,
                        postCategory.id,
                        postCategory.topic,
                        post.title,
                        post.commentCount,
                        user.nickname,
                        post.createdDate,
                        post.hits,
                        post.likeCount,
                        post.thumbnailUrl
                ))
                .from(post)
                .join(post.user, user)
                .join(post.postCategory, postCategory)
                .join(postCategory.board, board)
                .where(board.id.eq(boardId),
                        isPostCategoryExist(postCategoryId),
                        postSearchTypeEq(postSearchType, searchWord),
                        post.status.eq(Post.Status.ENABLE))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostsQueryDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression isPostCategoryExist(Long postCategoryId) {
        return postCategoryId != null ? postCategory.id.eq(postCategoryId) : null;
    }

    private BooleanExpression postSearchTypeEq(String postSearchType, String searchWord) {
        if (postSearchType.equals(PostSearchType.TICON.stringValue)) {
            return ticonEq(searchWord);
        }

        if (postSearchType.equals(PostSearchType.TITLE.stringValue)) {
            return titleEq(searchWord);
        }

        if (postSearchType.equals(PostSearchType.CONTENT.stringValue)) {
            return contentEq(searchWord);
        }

        if (postSearchType.equals(PostSearchType.NICKNAME.stringValue)) {
            return nicknameEq(searchWord);
        }

        return null;
    }

    private BooleanExpression ticonEq(String searchWord) {
        return searchWord != null ? post.title.contains(searchWord).or(post.content.contains(searchWord)) : null;
    }

    private BooleanExpression titleEq(String searchWord) {
        return searchWord != null ? post.title.contains(searchWord) : null;
    }

    private BooleanExpression contentEq(String searchWord) {
        return searchWord != null ? post.content.contains(searchWord) : null;
    }

    private BooleanExpression nicknameEq(String searchWord) {
        return searchWord != null ? user.nickname.contains(searchWord) : null;
    }

    @Override
    public Page<PostsCommonSearchQueryDto> findPostsByKeyword(String keyword, Pageable pageable) {
        QueryResults<PostsCommonSearchQueryDto> results = queryFactory
                .select(Projections.constructor(PostsCommonSearchQueryDto.class,
                        post.id,
                        board.id,
                        board.title,
                        post.title,
                        post.content,
                        post.createdDate
                ))
                .from(post)
                .join(post.user, user)
                .join(post.postCategory, postCategory)
                .join(postCategory.board, board)
                .where(ticonEq(keyword),
                       post.status.eq(Post.Status.ENABLE))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostsCommonSearchQueryDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<PostsUserQueryDto> findPostsByNickname(String nickname, Pageable pageable) {
        QueryResults<PostsUserQueryDto> results = queryFactory
                .select(Projections.constructor(PostsUserQueryDto.class,
                        post.id,
                        board.id,
                        board.title,
                        post.title,
                        post.commentCount,
                        post.createdDate,
                        post.hits,
                        post.likeCount
                ))
                .from(post)
                .join(post.user, user)
                .join(post.postCategory, postCategory)
                .join(postCategory.board, board)
                .where(user.nickname.eq(nickname),
                       post.status.eq(Post.Status.ENABLE))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostsUserQueryDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<RealTimeBestPostQueryDto> findRealTimeBestPosts(Pageable pageable) {
        QueryResults<RealTimeBestPostQueryDto> results = queryFactory
                .select(Projections.constructor(RealTimeBestPostQueryDto.class,
                        board.id,
                        post.id,
                        board.title,
                        post.title,
                        post.thumbnailUrl,
                        post.commentCount,
                        post.createdDate
                ))
                .from(post)
                .join(post.postCategory, postCategory)
                .join(postCategory.board, board)
                .where(post.status.eq(Post.Status.ENABLE)
                        .and(post.likeCount
                                .add(post.commentCount)
                                .add(post.hits)
                                .goe(100L)
                        )
                )
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<RealTimeBestPostQueryDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
