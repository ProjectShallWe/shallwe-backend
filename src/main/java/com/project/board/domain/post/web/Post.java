package com.project.board.domain.post.web;

import com.project.board.domain.comment.web.Comment;
import com.project.board.domain.file.web.PostFile;
import com.project.board.domain.like.web.LikePost;
import com.project.board.domain.user.web.User;
import com.project.board.global.audit.BaseEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

    @OneToMany(mappedBy = "post")
    private List<PostFile> postFiles = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<LikePost> likePosts = new ArrayList<>();

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
        this.user = user;
        this.postCategory = postCategory;
        this.postFiles = postFiles;
        this.comments = comments;
        this.likePosts = likePosts;
    }

    public void update(String title, String content) {
        if (StringUtils.isEmpty(title)) throw new InvalidParamException("Post.title");
        if (StringUtils.isEmpty(content)) throw new InvalidParamException("Post.content");

        this.title = title;
        this.content = content;
    }

    public void updateStatusToEnable() {
        this.status = Status.ENABLE;
    }

    public void updateStatusToDisable() {
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
}
