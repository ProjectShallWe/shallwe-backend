package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikePost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.project.board.domain.like.web.QLikePost.likePost;
import static com.project.board.domain.post.web.QPost.post;
import static com.project.board.domain.user.web.QUser.user;

@RequiredArgsConstructor
public class LikePostRepositoryImpl implements LikePostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<LikePost> findByUserIdAndPostId(Long userId, Long postId) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(likePost)
                        .join(likePost.user, user)
                        .join(likePost.post, post)
                        .where(likePost.user.id.eq(userId),
                                likePost.post.id.eq(postId)
                        )
                        .fetchOne()
        );
    }
}
