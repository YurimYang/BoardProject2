package com.example.board_project.domain.repository;

import com.example.board_project.domain.entity.Board;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BoardRepository extends MongoRepository<Board, String> {
}
