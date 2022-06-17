package com.project.board.domain.post.web;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findById(Long id);

    @Query("select distinct p " +
            "from Post p " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "order by p.id desc")
    Page<Post> findAllInBoard(@Param("id") Long id,
                                     Pageable pageable);

    @Query("select distinct p " +
            "from Post p " +
            "join p.postCategory pc " +
            "where pc.id =:id " +
            "order by p.id desc")
    Page<Post> findAllInPostCategory(@Param("id") Long id,
                                     Pageable pageable);

    @Query("select distinct p from Post p " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "and p.title like concat('%',:title,'%') " +
            "order by p.id desc")
    Page<Post> findPostsByPostTitleInBoard(@Param("id") Long id,
                                           @Param("title") String title,
                                           Pageable pageable);

    @Query("select distinct p from Post p " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "and p.content like concat('%',:content,'%') " +
            "order by p.id desc")
    Page<Post> findPostsByPostContentInBoard(@Param("id") Long id,
                                           @Param("content") String content,
                                           Pageable pageable);

    @Query("select distinct p from Post p " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "and p.title like concat('%',:keyword,'%')" +
            "or p.content like concat('%',:keyword,'%') " +
            "order by p.id desc")
    Page<Post> findPostsByPostTitleOrPostContentInBoard(@Param("id") Long id,
                                             @Param("keyword") String keyword,
                                             Pageable pageable);

    @Query("select distinct p from Post p " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "and p.user.nickname like concat('%',:nickname,'%') " +
            "order by p.id desc")
    Page<Post> findPostsByUserNicknameInBoard(@Param("id") Long id,
                                             @Param("nickname") String nickname,
                                             Pageable pageable);
}
