package com.project.board.infrastructure.post;

import com.project.board.domain.post.web.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
