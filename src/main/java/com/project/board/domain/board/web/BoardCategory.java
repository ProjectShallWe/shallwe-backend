package com.project.board.domain.board.web;

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
@Table(name = "board_category_table")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시판 카테고리 제목
    @NotNull
    private String topic;

    @OneToMany(mappedBy = "boardCategory")
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
