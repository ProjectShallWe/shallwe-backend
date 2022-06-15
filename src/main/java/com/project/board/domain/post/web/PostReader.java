package com.project.board.domain.post.web;

import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReader {

    private final PostRepository postRepository;

    public Post getPostBy(Long id) {
        return postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<Post> getPostsInPostCategory(Long id, PageRequest pageRequest) {
        return postRepository.findAllInPostCategory(id, pageRequest);
    }

}
