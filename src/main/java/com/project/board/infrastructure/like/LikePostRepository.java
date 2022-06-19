package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikePostRepository extends JpaRepository<LikePost, Long> {
    @Query("select lp from LikePost lp " +
            "where lp.user.id =:userId " +
            "and lp.post.id =:postId")
    Optional<LikePost> findByUserIdAndPostId(@Param("userId") Long userId,
                                            @Param("postId") Long postId);
}
