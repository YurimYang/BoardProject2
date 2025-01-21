package com.example.board_project.domain.Board.repository;

import com.example.board_project.domain.Board.dto.request.PostPatchRequest;
import com.example.board_project.domain.Board.entity.Board;
import com.example.board_project.domain.Board.enums.BoardSearchEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomBoardRepository {

    void updateBoardById(Board board, PostPatchRequest postPatchRequest);
    void deleteBoardById(Board board);
    List<Board> findBoardByTypeAndKeyword(BoardSearchEnum type, String keyword);
    Page<Board> findPagedBoardByKeyword(BoardSearchEnum type, String keyword, Pageable pageable);
}