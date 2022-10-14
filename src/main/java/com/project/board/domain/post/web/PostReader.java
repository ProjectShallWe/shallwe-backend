package com.project.board.domain.post.web;

import com.project.board.domain.post.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;

public interface PostReader {

    Post getPostBy(Long id);
    Page<PostsQueryDto> getPostsInBoard(Long id, PageRequest pageRequest);
    Page<PostsQueryDto> getPostsInPostCategory(Long id, PageRequest pageRequest);
    PostDetailsQueryDto getPostDetails(Long id);
    Page<RecommendPostsQueryDto> getRecommendPostsInBoard(Long id, LocalDateTime now, LocalDateTime twelveHoursAgo, PageRequest pageRequest);
    Page<RecommendPostsQueryDto> getRecommendPosts(LocalDateTime now, LocalDateTime twelveHoursAgo, PageRequest pageRequest);
    Page<PostsQueryDto> getPostsBySearchWordInBoard(Long boardId, Long postCategoryId, String type, String keyword, PageRequest pageRequest);
    Page<PostsCommonSearchQueryDto> getPostsByKeyword(String keyword, PageRequest pageRequest);
    Page<PostsUserQueryDto> getPostsByNickname(String nickname, PageRequest pageRequest);
}
