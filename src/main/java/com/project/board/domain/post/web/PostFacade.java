package com.project.board.domain.post.web;

import com.project.board.domain.file.web.FileService;
import com.project.board.domain.post.dto.PostUpdateRequestDto;
import com.project.board.domain.post.dto.PostWriteRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostFacade {

    private final PostService postService;
    private final FileService fileService;

    public Long createPostWithImages(String email, Long postCategoryId, PostWriteRequestDto postWriteRequestDto) {
        Long savedPostId = postService.write(email, postCategoryId, postWriteRequestDto);
        fileService.createPostImage(savedPostId, postWriteRequestDto.getImages());
        return savedPostId;
    }

    public Long updatePostWithImages(String email, Long postId, Long postCategoryId, PostUpdateRequestDto postUpdateRequestDto) {
        Long savedPostId = postService.update(email, postId, postCategoryId, postUpdateRequestDto);
        fileService.updatePostImage(postId, postUpdateRequestDto.getImages());
        return savedPostId;
    }
}
