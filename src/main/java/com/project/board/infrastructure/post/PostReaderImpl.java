package com.project.board.infrastructure.post;

import com.project.board.domain.post.dto.PostDetailsQueryDto;
import com.project.board.domain.post.dto.PostsQueryDto;
import com.project.board.domain.post.dto.RecommendPostsInBoardQueryDto;
import com.project.board.domain.post.web.Post;
import com.project.board.domain.post.web.PostReader;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {

    private final PostRepository postRepository;

    public Post getPostBy(Long id) {
        return postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<PostsQueryDto> getPostsInBoard(Long id, PageRequest pageRequest) {
        return postRepository.findAllInBoard(id, pageRequest);
    }

    public Page<PostsQueryDto> getPostsInPostCategory(Long id, PageRequest pageRequest) {
        return postRepository.findAllInPostCategory(id, pageRequest);
    }

    @Override
    public PostDetailsQueryDto getPostDetails(Long id) {
        return postRepository.findPostDetailsBy(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<RecommendPostsInBoardQueryDto> getRecommendPostsInBoard(Long id, LocalDateTime now, LocalDateTime twelveHoursAgo, PageRequest pageRequest) {
        return postRepository.findRecommendPostsInBoard(id, now, twelveHoursAgo, pageRequest);
    }

    @Override
    public Page<PostsQueryDto> getPostsBySearchWordInBoard(Long boardId, String type, String keyword, PageRequest pageRequest) {
        return postRepository.findPostsBySearchWordInBoard(boardId, type, keyword, pageRequest);
    }


}
