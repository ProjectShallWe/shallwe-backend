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
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostRecommendScheduledService {

    private final RedisTemplate<String, List<RecommendPostsInBoardResponseDto>> redisTemplate;
    private final PostReader postReader;
    private final BoardReader boardReader;

    public final static String REDIS_KEY_PREFIX_RECOMMEND_POSTS = "recommendPostList:board#";
    public final static Long LIKE_WEIGHTED_VALUE = 3L;
    public final static Long COMMENT_WEIGHTED_VALUE = 7L;

    @Scheduled(cron = "10 * * * * *")
    @Transactional
    public Long saveRecommendPostsInBoardToRedis() {
        System.out.println("-----Start save recommend posts to redis-----");

        List<Board> boards = boardReader.getAll();

        for (int i = 0; i < boards.size(); i++) {
            Page<RecommendPostsInBoardQueryDto> queryDtos
                    = postReader.getRecommendPostsInBoard(boards.get(i).getId(),
                    LocalDateTime.now(), LocalDateTime.now().minusHours(12),
                    LIKE_WEIGHTED_VALUE, COMMENT_WEIGHTED_VALUE, PageRequest.of(0, 5));

            List<RecommendPostsInBoardResponseDto> responseDtos = queryDtos.stream()
                    .map(RecommendPostsInBoardResponseDto::new)
                    .collect(Collectors.toList());

            // Redis에 List<Dto>형태로 저장
            ValueOperations<String, List<RecommendPostsInBoardResponseDto>> vop = redisTemplate.opsForValue();
            String key = REDIS_KEY_PREFIX_RECOMMEND_POSTS + Long.toString(boards.get(i).getId());
            vop.set(key, responseDtos);
            redisTemplate.expireAt(key, Date.from(ZonedDateTime.now().plusHours(12).toInstant()));
        }

        System.out.println("-----End save recommend posts to redis-----");
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
