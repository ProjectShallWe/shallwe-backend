package com.project.board.domain.post.web;

import com.project.board.domain.post.dto.RecommendPostsInBoardQueryDto;
import com.project.board.domain.post.dto.RecommendPostsInBoardResponseDto;
import com.project.board.global.redis.CacheKey;
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

    private final PostReader postReader;

    @Cacheable(value = CacheKey.RECOMMEND_POST, key = "#boardId", unless = "#result == null")
    @Transactional(readOnly = true)
    public List<RecommendPostsInBoardResponseDto> getRecommendPostsInBoardFromRedis(Long boardId) {
        Page<RecommendPostsInBoardQueryDto> queryDtos
                = postReader.getRecommendPostsInBoard(boardId,
                LocalDateTime.now(), LocalDateTime.now().minusHours(12),
                PageRequest.of(0, 5));
        List<RecommendPostsInBoardResponseDto> resDtos = queryDtos.stream()
                .map(RecommendPostsInBoardResponseDto::new)
                .collect(Collectors.toList());
        return resDtos;
    }
}
