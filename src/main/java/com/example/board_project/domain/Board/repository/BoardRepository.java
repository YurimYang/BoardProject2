package com.example.board_project.domain.Board.repository;

import com.example.board_project.domain.Board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {

    Page<Board> findAll(Pageable pageable);
}
