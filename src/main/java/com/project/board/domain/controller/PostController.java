package com.project.board.domain.controller;

import com.project.board.domain.post.dto.PostDetailsResponseDto;
import com.project.board.domain.post.dto.PostUpdateRequestDto;
import com.project.board.domain.post.dto.PostWriteRequestDto;
import com.project.board.domain.post.dto.PostsResponseDto;
import com.project.board.domain.post.web.PostService;
import com.project.board.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.project.board.domain.controller.PostSearchType.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public Long write(@AuthenticationPrincipal UserDetailsImpl userDetails,
                      @RequestParam("category") Long postCategoryId,
                      @RequestBody PostWriteRequestDto postWriteRequestDto) {
        return postService.write(userDetails.getUsername(), postCategoryId, postWriteRequestDto);
    }

    @PutMapping("/{id}")
    public Long update(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id,
                       @RequestBody PostUpdateRequestDto postUpdateRequestDto) {
        return postService.update(userDetails.getUsername(), id, postUpdateRequestDto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@AuthenticationPrincipal UserDetailsImpl userDetails,
                       @PathVariable Long id) {
        return postService.delete(userDetails.getUsername(), id);
    }

    @GetMapping
    public Page<PostsResponseDto> getPostsInPostCategory(
            @RequestParam("category") Long postCategoryId,
            @RequestParam Integer page) {
        return postService.getPostsInPostCategory(postCategoryId, page);
    }

    @GetMapping("/search")
    public Page<PostsResponseDto> getPostsBySearchKeywordInBoard(
            @RequestParam("board") Long boardId,
            @RequestParam Integer page,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String keyword) {
        // type이 null로 들어올 경우 빈 값으로 변환
        if (StringUtils.isEmpty(type)) {
            type = "";
        }

        if (type.equals(TICON.stringValue)) {
            return postService.getPostsByPostTitleOrPostContentInBoard(boardId, keyword, page);
        }
        if (type.equals(TITLE.stringValue)) {
            return postService.getPostsByPostTitleInBoard(boardId, keyword, page);
        }
        if (type.equals(CONTENT.stringValue)) {
            return postService.getPostsByPostContentInBoard(boardId, keyword, page);
        }
        if (type.equals(NICKNAME.stringValue)) {
            return postService.getPostsByUserNicknameInBoard(boardId, keyword, page);
        }

        return postService.getPostsInBoard(boardId, page);
    }

    @GetMapping("/{id}")
    public PostDetailsResponseDto getPostDetails(@PathVariable Long id) {
        return postService.getPostDetails(id);
    }
}
