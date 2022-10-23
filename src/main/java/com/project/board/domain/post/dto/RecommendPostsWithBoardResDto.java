package com.project.board.domain.post.dto;

import com.project.board.domain.board.web.Board;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendPostsWithBoardResDto implements Serializable {

    public static final String todayHitPosts = "오늘의 인기글";
    public static final String boardHitPosts = " 게시판 인기글";

    private Long boardId;
    private String boardTitle;
    private List<RecommendPostsResponseDto> recommendPosts = new ArrayList<>();

    @Builder
    public RecommendPostsWithBoardResDto(List<RecommendPostsResponseDto> recommendPosts) {
        this.boardId = 0L;
        this.boardTitle = todayHitPosts;
        this.recommendPosts = recommendPosts;
    }
    @Builder
    public RecommendPostsWithBoardResDto(Board board, List<RecommendPostsResponseDto> recommendPosts) {
        this.boardId = board.getId();
        this.boardTitle = board.getTitle() + boardHitPosts;
        this.recommendPosts = recommendPosts;
    }
}
