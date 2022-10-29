package com.project.board.infrastructure.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LikePostRepositoryImpl implements LikePostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
}
