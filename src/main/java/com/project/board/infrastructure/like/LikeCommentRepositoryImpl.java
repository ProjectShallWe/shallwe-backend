package com.project.board.infrastructure.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikeCommentRepositoryImpl implements LikeCommentRepositoryCustom{

    private final JPAQueryFactory queryFactory;
}
