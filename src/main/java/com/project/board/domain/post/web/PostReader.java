package com.project.board.domain.post.web;

import com.project.board.domain.post.dto.PostDetailResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PostReader {

    Post getPostBy(Long id);
    Page<Post> getPostsInBoard(Long id, PageRequest pageRequest);
    Page<Post> getPostsInPostCategory(Long id, PageRequest pageRequest);
    Page<Post> getPostsByPostTitleInBoard(Long id, String title, PageRequest pageRequest);
    Page<Post> getPostsByPostContentInBoard(Long id, String content, PageRequest pageRequest);
    Page<Post> getPostsByPostTitleOrPostContentInBoard(Long id, String keyword, PageRequest pageRequest);
    Page<Post> getPostsByUserNicknameInBoard(Long id, String keyword, PageRequest pageRequest);
    PostDetailResponseDto getPostDetail(Long id);
}
