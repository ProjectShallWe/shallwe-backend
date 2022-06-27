package com.project.board.domain.board.web;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.board.global.audit.BaseEntity;
import com.project.board.global.exception.InvalidParamException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_category_table",
       indexes = @Index(name = "bc_topic",
                        columnList = "board_category_topic"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class BoardCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_category_id")
    private Long id;

    // 게시판 카테고리 제목
    @Column(name = "board_category_topic")
    private String topic;

    @OneToMany(mappedBy = "boardCategory", fetch = FetchType.LAZY)
    @JsonManagedReference("boardcategory-board")
    private List<Board> boards = new ArrayList<>();

    @Builder
    public BoardCategory(Long id, String topic, List<Board> boards) {
        this.id = id;
        this.topic = topic;
        this.boards = boards;
    }

    public void update(String topic) {
        if (StringUtils.isEmpty(topic)) throw new InvalidParamException("BoardCategory.topic");

        this.topic = topic;
    }
}
