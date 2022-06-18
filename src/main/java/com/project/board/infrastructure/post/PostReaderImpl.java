package com.project.board.infrastructure.post;

import com.project.board.domain.post.web.PostReader;
import com.project.board.domain.post.web.Post;
import com.project.board.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReaderImpl implements PostReader {

    private final PostRepository postRepository;

    public Post getPostBy(Long id) {
        return postRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Page<Post> getPostsInBoard(Long id, PageRequest pageRequest) {
        return postRepository.findAllInBoard(id, pageRequest);
    }

    public Page<Post> getPostsInPostCategory(Long id, PageRequest pageRequest) {
        return postRepository.findAllInPostCategory(id, pageRequest);
    }

    public Page<Post> getPostsByPostTitleInBoard(Long id, String title, PageRequest pageRequest) {
        return postRepository.findPostsByPostTitleInBoard(id, title, pageRequest);
    }

    public Page<Post> getPostsByPostContentInBoard(Long id, String content, PageRequest pageRequest) {
        return postRepository.findPostsByPostContentInBoard(id, content, pageRequest);
    }

    public Page<Post> getPostsByPostTitleOrPostContentInBoard(Long id, String keyword, PageRequest pageRequest) {
        return postRepository.findPostsByPostTitleOrPostContentInBoard(id, keyword, pageRequest);
    }

    public Page<Post> getPostsByUserNicknameInBoard(Long id, String keyword, PageRequest pageRequest) {
        return postRepository.findPostsByUserNicknameInBoard(id, keyword, pageRequest);
    }


}
