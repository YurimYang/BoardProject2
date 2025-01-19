package com.example.board_project.domain.Board.dao;

import com.example.board_project.domain.Board.dto.request.PostPatchRequest;
import com.example.board_project.domain.Board.entity.Board;
import com.example.board_project.domain.Board.enums.BoardSearchEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface  BoardDAO {
    Board insertBoard(Board board);
    List<Board> getAllBoard();
    Page<Board> getAllPagedBoard(Pageable pageable);
    Optional<Board> getBoardById(String id);
    void updateBoard(Board board, PostPatchRequest postPatchRequest);
    void deleteBoardById(Board board);
    List<Board> findBoardByKeyword(BoardSearchEnum type, String keyword);
    Page<Board> findPagedBoardByKeyword(BoardSearchEnum type, String keyword, Pageable pageable);
}