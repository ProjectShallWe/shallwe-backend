package com.project.board.domain.category.web;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.domain.board.web.Board;
import com.project.board.global.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "category_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    // 카테고리 대분류
    @Column(name = "category_topic")
    private String topic;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonManagedReference("category-board")
    private List<Board> boards = new ArrayList<>();

    @Builder
    public Category(Long id, String topic, List<Board> boards) {
        this.id = id;
        this.topic = topic;
        this.boards = boards;
    }

    public void update(String topic) {
        this.topic = topic;
    }
}
