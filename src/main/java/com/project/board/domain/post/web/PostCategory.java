package com.project.board.domain.post.web;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.board.web.Board;
import com.project.board.global.audit.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post_category_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PostCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_category_id")
    private Long id;

    // 글 카테고리 제목
    @Column(name = "post_category_topic")
    private String topic;

    @ManyToOne
    @JsonBackReference("board-postcategory")
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "postCategory", fetch = FetchType.LAZY)
    @JsonManagedReference("postcategory-post")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public PostCategory(Long id, String topic, Board board, List<Post> posts) {
        this.id = id;
        this.topic = topic;
        this.board = board;
        this.posts = posts;
    }

    public void update(String topic) {
        this.topic = topic;
    }
}
