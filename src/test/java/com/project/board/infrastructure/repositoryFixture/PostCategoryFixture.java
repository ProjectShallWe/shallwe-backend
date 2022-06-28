package com.project.board.infrastructure.repositoryFixture;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.web.PostCategory;

public class PostCategoryFixture {
    public static PostCategory createPostCategory1(Board board) {
        return PostCategory.builder()
                .topic("국내 농구")
                .board(board)
                .build();
    }

    public static PostCategory createPostCategory1() {
        return PostCategory.builder()
                .topic("국내 농구")
                .build();
    }

    public static PostCategory createPostCategory2(Board board) {
        return PostCategory.builder()
                .topic("해외 농구")
                .board(board)
                .build();
    }

    public static PostCategory createPostCategory2() {
        return PostCategory.builder()
                .topic("해외 농구")
                .build();
    }
}
