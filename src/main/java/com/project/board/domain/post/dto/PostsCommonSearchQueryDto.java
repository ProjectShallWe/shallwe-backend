package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostsCommonSearchQueryDto {

    private Long postId;
    private Long boardId;
    private String boardTitle;
    private String title;
    private String content;
    private LocalDateTime createdDate;

    @Builder
    public PostsCommonSearchQueryDto(Long postId,
                         Long boardId,
                         String boardTitle,
                         String title,
                         String content,
                         LocalDateTime createdDate) {
        this.postId = postId;
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
    }
}
