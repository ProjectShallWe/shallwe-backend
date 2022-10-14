package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.project.board.global.util.DateConverter.convertLocalDateTimeToMMdd;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostsUserResDto {

    private Long postId;
    private Long boardId;
    private String boardTitle;
    private String title;
    private Long commentCount;
    private String createdDate;
    private Long likeCount;

    @Builder
    public PostsUserResDto (PostsUserQueryDto postsUserQueryDto) {
        this.postId = postsUserQueryDto.getPostId();
        this.boardId = postsUserQueryDto.getBoardId();
        this.boardTitle = postsUserQueryDto.getBoardTitle();
        this.title = postsUserQueryDto.getTitle();
        this.commentCount = postsUserQueryDto.getCommentCount();
        this.createdDate = convertLocalDateTimeToMMdd(postsUserQueryDto.getCreatedDate());
        this.likeCount = postsUserQueryDto.getLikeCount();
    }
}
