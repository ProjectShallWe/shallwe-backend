package com.project.board.domain.post.repository;

import com.project.board.domain.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
