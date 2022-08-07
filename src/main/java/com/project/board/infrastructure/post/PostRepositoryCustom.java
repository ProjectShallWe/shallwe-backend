package com.project.board.infrastructure.post;

import com.project.board.domain.post.dto.PostDetailsQueryDto;
import com.project.board.domain.post.dto.PostsQueryDto;
import com.project.board.domain.post.dto.RecommendPostsInBoardQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostRepositoryCustom {

    Page<PostsQueryDto> findAllInBoard(Long boardId, Pageable pageable);
    Page<PostsQueryDto> findAllInPostCategory(Long postCategoryId, Pageable pageable);
    Optional<PostDetailsQueryDto> findPostDetailsBy(Long postId);
    Page<RecommendPostsInBoardQueryDto> findRecommendPostsInBoard(Long boardId, LocalDateTime now, LocalDateTime twelveHoursAgo, Pageable pageable);
    Page<PostsQueryDto> findPostsBySearchWordInBoard(Long boardId, String postSearchType, String searchWord, Pageable pageable);
}
