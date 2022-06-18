package com.project.board.infrastructure.post;

import com.project.board.domain.post.web.PostStore;
import com.project.board.domain.post.web.Post;
import com.project.board.global.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostStoreImpl implements PostStore {

    private final PostRepository postRepository;

    public Post store (Post post) {
        validCheck(post);
        return postRepository.save(post);
    }

    private void validCheck(Post post) {
        if (post.getUser() == null) throw new InvalidParamException("Post.user");
        if (post.getPostCategory() == null) throw new InvalidParamException("Post.postCategory");
        if (StringUtils.isEmpty(post.getTitle())) throw new InvalidParamException("Post.title");
        if (StringUtils.isEmpty(post.getContent())) throw new InvalidParamException("Post.content");
        if (post.getLikeCount() == null) throw new InvalidParamException("Post.likeCount");
    }
}
