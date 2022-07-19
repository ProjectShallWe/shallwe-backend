package com.project.board.infrastructure.post;

import com.project.board.domain.post.dto.PostDetailsQueryDto;
import com.project.board.domain.post.dto.PostsQueryDto;
import com.project.board.domain.post.dto.RecommendPostsInBoardQueryDto;
import com.project.board.domain.post.web.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select distinct new com.project.board.domain.post.dto.PostsQueryDto(" +
            "p.id, pc.topic, p.title, u.nickname, p.createdDate, p.likeCount) " +
            "from Post p " +
            "join p.user u " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "order by p.id desc")
    Page<PostsQueryDto> findAllInBoard(@Param("id") Long id,
                                       Pageable pageable);

    @Query("select distinct new com.project.board.domain.post.dto.PostsQueryDto(" +
            "p.id, pc.topic, p.title, u.nickname, p.createdDate, p.likeCount) " +
            "from Post p " +
            "join p.user u " +
            "join p.postCategory pc " +
            "where pc.id =:id " +
            "order by p.id desc")
    Page<PostsQueryDto> findAllInPostCategory(@Param("id") Long id,
                                              Pageable pageable);

    @Query("select distinct new com.project.board.domain.post.dto.PostsQueryDto(" +
            "p.id, pc.topic, p.title, u.nickname, p.createdDate, p.likeCount) " +
            "from Post p " +
            "join p.user u " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "and p.title like concat('%',:title,'%') " +
            "order by p.id desc")
    Page<PostsQueryDto> findPostsByPostTitleInBoard(@Param("id") Long id,
                                                    @Param("title") String title,
                                                    Pageable pageable);

    @Query("select distinct new com.project.board.domain.post.dto.PostsQueryDto(" +
            "p.id, pc.topic, p.title, u.nickname, p.createdDate, p.likeCount) " +
            "from Post p " +
            "join p.user u " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "and p.content like concat('%',:content,'%') " +
            "order by p.id desc")
    Page<PostsQueryDto> findPostsByPostContentInBoard(@Param("id") Long id,
                                                      @Param("content") String content,
                                                      Pageable pageable);

    @Query("select distinct new com.project.board.domain.post.dto.PostsQueryDto(" +
            "p.id, pc.topic, p.title, u.nickname, p.createdDate, p.likeCount) " +
            "from Post p " +
            "join p.user u " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "and p.title like concat('%',:keyword,'%')" +
            "or p.content like concat('%',:keyword,'%') " +
            "order by p.id desc")
    Page<PostsQueryDto> findPostsByPostTitleOrPostContentInBoard(@Param("id") Long id,
                                                                 @Param("keyword") String keyword,
                                                                 Pageable pageable);

    @Query("select distinct new com.project.board.domain.post.dto.PostsQueryDto(" +
            "p.id, pc.topic, p.title, u.nickname, p.createdDate, p.likeCount) " +
            "from Post p " +
            "join p.user u " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id =:id " +
            "and p.user.nickname like concat('%',:nickname,'%') " +
            "order by p.id desc")
    Page<PostsQueryDto> findPostsByUserNicknameInBoard(@Param("id") Long id,
                                                       @Param("nickname") String nickname,
                                                       Pageable pageable);

    @Query("select new com.project.board.domain.post.dto.PostDetailsQueryDto(" +
            "p.id, u.nickname, p.createdDate, pc.topic, " +
            "p.likeCount, p.commentCount, " +
            "p.title, p.content) " +
            "from Post p " +
            "join p.postCategory pc " +
            "join p.user u " +
            "where p.id =:id")
    Optional<PostDetailsQueryDto> findPostDetailsBy(@Param("id") Long id);

    @Query("select new com.project.board.domain.post.dto.RecommendPostsInBoardQueryDto(" +
            "p.id, pc.topic, p.title, p.commentCount) " +
            "from Post p " +
            "join p.postCategory pc " +
            "join pc.board b " +
            "where b.id = :id " +
            "and p.createdDate < :now " +
            "and p.createdDate > :twelveHoursAgo " +
            "order by (p.likeCount + p.commentCount) desc ")
    Page<RecommendPostsInBoardQueryDto> findRecommendPostsInBoard(@Param("id") Long id,
                                                                  @Param("now") LocalDateTime now,
                                                                  @Param("twelveHoursAgo") LocalDateTime twelveHoursAgo,
                                                                  Pageable pageable);
}
