package com.project.board.domain.post.web;

import com.project.board.domain.post.dto.PostDetailsQueryDto;
import com.project.board.domain.post.dto.PostsQueryDto;
import com.project.board.domain.post.dto.RecommendPostsQueryDto;
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
    Page<PostsQueryDto> getPostsBySearchWordInBoard(Long boardId, String type, String keyword, PageRequest pageRequest);

}
