package com.project.board.infrastructure.file;

import java.util.List;

public interface PostFileRepositoryCustom {

    List<String> findImageUrlsInPost(Long postId);
}
