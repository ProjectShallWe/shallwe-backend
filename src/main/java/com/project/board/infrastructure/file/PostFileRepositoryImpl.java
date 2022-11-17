package com.project.board.infrastructure.file;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.board.domain.file.web.QPostFile.*;

@RequiredArgsConstructor
public class PostFileRepositoryImpl implements PostFileRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findImageUrlsInPost(Long postId) {
        return jpaQueryFactory
                .select(postFile.fileUrl)
                .from(postFile)
                .where(postFile.post.id.eq(postId))
                .fetchResults()
                .getResults();
    }
}
