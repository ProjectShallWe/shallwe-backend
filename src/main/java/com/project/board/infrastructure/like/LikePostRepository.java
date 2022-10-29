package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long>, LikePostRepositoryCustom {

    Boolean existsByUserIdAndPostId(Long userId, Long postId);
    Optional<LikePost> findByUserIdAndPostId(Long userId, Long postId);
}
