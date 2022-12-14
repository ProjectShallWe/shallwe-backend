package com.project.board.domain.post.web;

import com.project.board.domain.board.web.Board;
import com.project.board.global.audit.BaseEntity;
import com.project.board.global.exception.InvalidParamException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post_category_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 글 카테고리 제목
    @NotNull
    private String topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "postCategory")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public PostCategory(Long id, String topic, Board board, List<Post> posts) {
        this.id = id;
        this.topic = topic;
        this.board = board;
        this.posts = posts;
    }

    public void update(String topic) {
        if (StringUtils.isEmpty(topic)) throw new InvalidParamException("PostCategory.topic");

        this.topic = topic;
    }
}
