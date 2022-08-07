package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.dto.CommentQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.board.domain.comment.web.QComment.comment;
import static com.project.board.domain.post.web.QPost.post;
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
}
