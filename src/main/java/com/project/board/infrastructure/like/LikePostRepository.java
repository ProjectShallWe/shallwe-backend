package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
}
