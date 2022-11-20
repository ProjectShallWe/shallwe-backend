package com.project.board.domain.post.web;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.file.web.PostFile;
import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.user.web.User;
import com.project.board.global.audit.BaseEntity;
import com.project.board.global.exception.CannotDeletePostException;
import com.project.board.global.exception.CannotUpdateCommentException;
import com.project.board.global.exception.CannotUpdatePostException;
import com.project.board.global.exception.InvalidParamException;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Column(length = 3000)
    private String content;

    // 글 활성화 여부
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    // 글의 좋아요 수 저장
    @NotNull
    private Long likeCount;

    // 댓글 갯수 저장
    @NotNull
    private Long commentCount;

    @NotNull
    private Long hits;

    @NotNull
    private String thumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<LikePost> likePosts = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostFile> postFiles = new ArrayList<>();

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        ENABLE("활성화"),
        DISABLE("비활성화");

        private final String description;
    }

    @Builder
    public Post(Long id, String title, String content,
                User user, PostCategory postCategory,
                List<PostFile> postFiles, List<Comment> comments, List<LikePost> likePosts) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = Status.ENABLE;
        this.likeCount = 0L;
        this.commentCount = 0L;
        this.hits = 0L;
        this.thumbnailUrl = "";
        this.user = user;
        this.postCategory = postCategory;
        this.comments = comments;
        this.likePosts = likePosts;
        this.postFiles = postFiles;
    }

    public void update(String title, String content, User user, PostCategory postCategory) {
        if (StringUtils.isEmpty(title)) throw new InvalidParamException("Post.title");
        if (StringUtils.isEmpty(content)) throw new InvalidParamException("Post.content");

        if (!this.user.equals(user) && !user.getRole().equals(User.Role.ADMIN)) {
            throw new CannotUpdatePostException();
        }

        this.title = title;
        this.content = content;
        this.postCategory = postCategory;
    }

    public void updateStatusToDisable(User user) {
        if (!this.user.equals(user) && !user.getRole().equals(User.Role.ADMIN)) {
            throw new CannotDeletePostException();
        }

        this.status = Status.DISABLE;
    }

    public void addLikeCount() {
        likeCount += 1;
    }

    public void minusLikeCount() {
        likeCount -= 1;
    }

    public void addCommentCount() {
        commentCount += 1;
    }

    public void addHits() {
        hits += 1;
    }

    public void updateThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
