package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.dto.CommentQueryDto;
import com.project.board.domain.comment.dto.CommentsUserQueryDto;
import com.project.board.domain.post.dto.PostsUserQueryDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.project.board.domain.board.web.QBoard.board;
import static com.project.board.domain.comment.web.QComment.comment;
import static com.project.board.domain.post.web.QPost.post;
import static com.project.board.domain.post.web.QPostCategory.postCategory;
import static com.project.board.domain.user.web.QUser.user;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public List<CommentQueryDto> getCommentsInPostByPostId(Long postId) {
        return queryFactory
                .select(Projections.constructor(CommentQueryDto.class,
                        comment.id,
                        comment.parentCommentId,
                        user.nickname,
                        comment.createdDate,
                        comment.likeCount,
                        comment.content))
                .from(comment)
                .join(comment.user, user)
                .join(comment.post, post)
                .where(post.id.eq(postId))
                .fetch();
    }

    @Override
    public Page<CommentsUserQueryDto> findCommentsByNickname(String nickname, Pageable pageable) {
        QueryResults<CommentsUserQueryDto> results = queryFactory
                .select(Projections.constructor(CommentsUserQueryDto.class,
                        comment.id,
                        comment.content,
                        comment.createdDate,
                        comment.likeCount,
                        post.id,
                        board.id,
                        board.title
                ))
                .from(comment)
                .join(comment.post, post)
                .join(comment.user, user)
                .join(post.postCategory, postCategory)
                .join(postCategory.board, board)
                .where(user.nickname.eq(nickname))
                .orderBy(comment.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CommentsUserQueryDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
}
