package com.project.board.infrastructure.fixture;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.web.PostCategory;

import java.util.ArrayList;

public class PostCategoryFixture {
    public static PostCategory createPostCategory1(Board board) {
        return PostCategory.builder()
                .topic("국내 농구")
                .posts(new ArrayList<>())
                .board(board)
                .build();
    }

    public static PostCategory createPostCategory1() {
        return PostCategory.builder()
                .topic("국내 농구")
                .posts(new ArrayList<>())
                .build();
    }

    public static PostCategory createPostCategory2(Board board) {
        return PostCategory.builder()
                .topic("해외 농구")
                .posts(new ArrayList<>())
                .board(board)
                .build();
    }

    public static PostCategory createPostCategory2() {
        return PostCategory.builder()
                .topic("해외 농구")
                .posts(new ArrayList<>())
                .build();
    }

    public static PostCategory createPostCategory3(Board board) {
        return PostCategory.builder()
                .topic("국내 축구")
                .posts(new ArrayList<>())
                .board(board)
                .build();
    }

    public static PostCategory createPostCategory3() {
        return PostCategory.builder()
                .topic("국내 축구")
                .posts(new ArrayList<>())
                .build();
    }

    public static PostCategory createPostCategory4(Board board) {
        return PostCategory.builder()
                .topic("해외 축구")
                .posts(new ArrayList<>())
                .board(board)
                .build();
    }

    public static PostCategory createPostCategory4() {
        return PostCategory.builder()
                .topic("해외 축구")
                .posts(new ArrayList<>())
                .build();
    }
}
