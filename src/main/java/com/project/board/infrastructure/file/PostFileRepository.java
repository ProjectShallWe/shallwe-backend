package com.project.board.infrastructure.file;

import com.project.board.domain.file.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {
}
