package com.project.board.domain.board.web;

public interface BoardStore {

    Board store(Board board);
    void delete(Board board);
}
