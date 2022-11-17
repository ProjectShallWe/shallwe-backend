package com.project.board.infrastructure.file;

import com.project.board.domain.file.web.PostFile;
import com.project.board.domain.user.web.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostFileRepository extends JpaRepository<PostFile, Long>, PostFileRepositoryCustom {

    Optional<PostFile> findByFileUrl(String fileUrl);
}
