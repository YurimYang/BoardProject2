package com.example.board_project.domain.dao;

import com.example.board_project.domain.dto.request.PostPatchRequest;
import com.example.board_project.domain.entity.Board;
import com.example.board_project.domain.enums.BoardSearchEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface  BoardDAO {
    Board insertBoard(Board board);
    List<Board> selectAllBoard();
    Optional<Board> selectBoardById(String id);
    Page<Board> selectAllBoardPage(Pageable pageable);
    void updateBoard(String postId, PostPatchRequest postPatchRequest);
    void deleteBoardById(String postId, Board board);
    List<Board> findByKeyword(BoardSearchEnum type, String keyword);
    Page<Board> findByKeywordWithPage(BoardSearchEnum type, String keyword, Pageable pageable);
}