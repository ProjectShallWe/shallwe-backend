package com.project.board.domain.post.web;

import com.project.board.domain.board.web.Board;
import com.project.board.domain.board.web.BoardReader;
import com.project.board.domain.post.dto.RecommendPostsInBoardQueryDto;
import com.project.board.domain.post.dto.RecommendPostsInBoardResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostRecommendScheduledService {

    private final RedisTemplate<String, List<RecommendPostsInBoardResponseDto>> redisTemplate;
    private final PostReader postReader;
    private final BoardReader boardReader;

    public final static String REDIS_KEY_PREFIX_RECOMMEND_POSTS = "recommendPostList:board#";

    // 매일 8시, 20시에 실행
    @Scheduled(cron = "* * 8,20 * * *")
    @Transactional
    public Long saveRecommendPostsInBoardToRedis() {
        List<Board> boards = boardReader.getAll();

        for (Board board : boards) {
            Page<RecommendPostsInBoardQueryDto> queryDtos
                    = postReader.getRecommendPostsInBoard(board.getId(),
                    LocalDateTime.now(), LocalDateTime.now().minusHours(12),
                    PageRequest.of(0, 5));

            List<RecommendPostsInBoardResponseDto> responseDtos = queryDtos.stream()
                    .map(RecommendPostsInBoardResponseDto::new)
                    .collect(Collectors.toList());

            // Redis에 List<Dto>형태로 저장
            ValueOperations<String, List<RecommendPostsInBoardResponseDto>> vop = redisTemplate.opsForValue();
            String key = REDIS_KEY_PREFIX_RECOMMEND_POSTS + Long.toString(board.getId());
            vop.set(key, responseDtos);
        }

        return 1L;
    }

    @Transactional(readOnly = true)
    public List<RecommendPostsInBoardResponseDto> getRecommendPostsInBoardFromRedis(Long boardId) {
        ValueOperations<String, List<RecommendPostsInBoardResponseDto>> vop = redisTemplate.opsForValue();
        String key = REDIS_KEY_PREFIX_RECOMMEND_POSTS + Long.toString(boardId);
        List<RecommendPostsInBoardResponseDto> value = vop.get(key);
        return value;
    }
}
