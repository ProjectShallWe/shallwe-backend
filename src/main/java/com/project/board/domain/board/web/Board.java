package com.project.board.domain.board.web;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.category.model.Category;
import com.project.board.domain.post.web.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    // 카테고리 소분류 제목 (= 게시판 제목)
    @Column(name = "board_title")
    private String title;

    @ManyToOne
    @JsonBackReference("category-board")
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    @JsonManagedReference("board-post")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Board(Long id, String title, Category category, List<Post> posts) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.posts = posts;
    }

    public void update(String title) {
        this.title = title;
    }
}
