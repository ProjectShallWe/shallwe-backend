package com.project.board.infrastructure.like;

import com.project.board.domain.like.web.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {
    @Query("select lc from LikeComment lc " +
            "where lc.user.id =:userId " +
            "and lc.comment.id =:commentId")
    Optional<LikeComment> findByUserIdAndCommentId(@Param("userId") Long userId,
                                           @Param("commentId") Long commentId);
}
