package com.project.board.infrastructure.file;

import com.project.board.domain.file.CommentFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentFileRepository extends JpaRepository<CommentFile, Long> {
}
