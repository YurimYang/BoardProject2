package com.example.board_project.domain.Board.repository;

import com.example.board_project.domain.Board.entity.Board;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends MongoRepository<Board, String>, CustomBoardRepository {

}