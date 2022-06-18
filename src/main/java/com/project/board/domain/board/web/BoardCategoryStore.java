package com.project.board.domain.board.web;

public interface BoardCategoryStore {

    BoardCategory store(BoardCategory boardCategory);
    void delete(BoardCategory boardCategory);
}
