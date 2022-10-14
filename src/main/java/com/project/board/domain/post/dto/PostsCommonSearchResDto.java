package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.board.global.util.DateConverter.convertLocalDateTimeFromYearToMinute;
import static com.project.board.global.util.HtmlTagRemover.removeHtmlTag;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostsCommonSearchResDto {

    private Long postId;
    private Long boardId;
    private String boardTitle;
    private String title;
    private String content;
    private String createdDate;

    @Builder
    public PostsCommonSearchResDto (PostsCommonSearchQueryDto postsCommonSearchQueryDto) {
        this.postId = postsCommonSearchQueryDto.getPostId();
        this.boardId = postsCommonSearchQueryDto.getBoardId();
        this.boardTitle = postsCommonSearchQueryDto.getBoardTitle();
        this.title = postsCommonSearchQueryDto.getTitle();
        this.content = removeHtmlTag(postsCommonSearchQueryDto.getContent());
        this.createdDate = convertLocalDateTimeFromYearToMinute(postsCommonSearchQueryDto.getCreatedDate());
    }
}
