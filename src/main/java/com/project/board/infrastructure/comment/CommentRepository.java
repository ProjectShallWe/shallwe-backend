package com.project.board.infrastructure.comment;

import com.project.board.domain.comment.web.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
