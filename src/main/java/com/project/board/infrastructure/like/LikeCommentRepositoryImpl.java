package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikeComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.project.board.domain.comment.web.QComment.comment;
import static com.project.board.domain.like.web.QLikeComment.likeComment;
import static com.project.board.domain.user.web.QUser.user;

@RequiredArgsConstructor
public class LikeCommentRepositoryImpl implements LikeCommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public Optional<LikeComment> findByUserIdAndCommentId(Long userId, Long commentId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(likeComment)
                        .join(likeComment.user, user)
                        .join(likeComment.comment, comment)
                        .where(likeComment.user.id.eq(userId),
                                likeComment.comment.id.eq(commentId)
                        )
                        .fetchOne()
        );
    }
}
