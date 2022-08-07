package com.project.board.infrastructure.post;

import com.project.board.domain.post.web.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {
}
