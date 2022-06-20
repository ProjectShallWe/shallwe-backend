package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.dto.EntityToCommentResponseDto;
import com.project.board.domain.comment.web.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query ("select distinct new com.project.board.domain.comment.dto.EntityToCommentResponseDto(" +
            "c.id, c.parentCommentId, u.nickname, c.createdDate, c.likeCount, c.content)" +
            "from Comment c " +
            "join c.user u " +
            "join c.post p " +
            "where p.id =:id")
    List<EntityToCommentResponseDto> getCommentsInPostByPostId(@Param("id") Long id);
}
