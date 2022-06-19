package com.project.board.domain.controller;

import com.project.board.domain.post.dto.PostDetailResponseDto;
import com.project.board.domain.post.dto.PostUpdateRequestDto;
import com.project.board.domain.post.dto.PostWriteRequestDto;
import com.project.board.domain.post.dto.PostsResponseDto;
import com.project.board.domain.service.PostService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.project.board.domain.controller.PostSearchType.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/api/post-category/{id}/post")
    public Long write(@AuthenticationPrincipal UserDetailsImpl userDetails,
                      @PathVariable Long id,
                      @RequestBody PostWriteRequestDto postWriteRequestDto) {
        return postService.write(userDetails.getUsername(), id, postWriteRequestDto);
    }

    @PutMapping("/api/post/{id}")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return postService.update(userDetails.getUsername(), id, postUpdateRequestDto);
    }

    @DeleteMapping("/api/post/{id}")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return postService.delete(userDetails.getUsername(), id);
    }

    @GetMapping("/api/post-category/{id}/post")
    public Page<PostsResponseDto> getPostsInPostCategory(
            @PathVariable Long id,
            @RequestParam Integer page) {
        return postService.getPostsInPostCategory(id, page);
    }

    @GetMapping("/api/board/{id}/post")
    public Page<PostsResponseDto> getPostsBySearchKeywordInBoard(
            @PathVariable Long id,
            @RequestParam Integer page,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword) {
        // type이 null로 들어올 경우 빈 값으로 변환
        if (StringUtils.isEmpty(type)) {
            type = "";
        }

        if (type.equals(TICON.stringValue)) {
            return postService.getPostsByPostTitleOrPostContentInBoard(id, keyword, page);
        }
        if (type.equals(TITLE.stringValue)) {
            return postService.getPostsByPostTitleInBoard(id, keyword, page);
        }
        if (type.equals(CONTENT.stringValue)) {
            return postService.getPostsByPostContentInBoard(id, keyword, page);
        }
        if (type.equals(NICKNAME.stringValue)) {
            return postService.getPostsByUserNicknameInBoard(id, keyword, page);
        }

        return postService.getPostsInBoard(id, page);
    }

    @GetMapping("/api/post/{id}")
    public PostDetailResponseDto getPostDetails(@PathVariable Long id) {
        return postService.getPostsDetail(id);
    }
}
