package com.project.board.domain.post.web;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.post.dto.RecommendPostsQueryDto;
import com.project.board.domain.post.dto.RecommendPostsResponseDto;
import com.project.board.domain.post.dto.RecommendPostsWithBoardResDto;
import com.project.board.global.exception.EntityNotFoundException;
import com.project.board.global.redis.CacheKey;
import com.project.board.infrastructure.board.BoardRepository;
import com.project.board.infrastructure.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostRecommendService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @Cacheable(value = CacheKey.RECOMMEND_POST, unless = "#result == null")
    @Transactional(readOnly = true)
    public RecommendPostsWithBoardResDto getRecommendPostsFromRedis() {
        Page<RecommendPostsQueryDto> queryDtos
                = postRepository.findRecommendPosts(
                LocalDateTime.now(), LocalDateTime.now().minusHours(12),
                PageRequest.of(0, 10));
        List<RecommendPostsResponseDto> resDtos = queryDtos.stream()
                .map(RecommendPostsResponseDto::new)
                .collect(Collectors.toList());
        return new RecommendPostsWithBoardResDto(resDtos);
    }

    @Cacheable(value = CacheKey.RECOMMEND_POST, key = "#boardId", unless = "#result == null")
    @Transactional(readOnly = true)
    public RecommendPostsWithBoardResDto getRecommendPostsInBoardFromRedis(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);
        Page<RecommendPostsQueryDto> queryDtos
                = postRepository.findRecommendPostsInBoard(boardId,
                LocalDateTime.now(), LocalDateTime.now().minusHours(12),
                PageRequest.of(0, 10));
        List<RecommendPostsResponseDto> resDtos = queryDtos.stream()
                .map(RecommendPostsResponseDto::new)
                .collect(Collectors.toList());
        return new RecommendPostsWithBoardResDto(board,resDtos);
    }
}
