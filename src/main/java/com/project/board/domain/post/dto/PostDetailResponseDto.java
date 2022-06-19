package com.project.board.domain.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.project.board.global.util.DateConverter.convertLocalDateTimeFromYearToMinute;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResponseDto {
    private Long postId;
    private String nickname;
    private String createdDate;
    private String postTopic;
    private Long likeCount;
    private Long commentCount;
    private String title;
    private String content;

    @Builder
    public PostDetailResponseDto(Long postId, String nickname,
                                 LocalDateTime createdDate, String postTopic,
                                 Long likeCount, Long commentCount,
                                 String title, String content) {
        this.postId = postId;
        this.nickname = nickname;
        this.createdDate = convertLocalDateTimeFromYearToMinute(createdDate);
        this.postTopic = postTopic;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.title = title;
        this.content = content;
    }
}
