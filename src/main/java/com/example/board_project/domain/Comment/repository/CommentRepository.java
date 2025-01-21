package com.example.board_project.domain.Comment.repository;

import com.example.board_project.domain.Comment.entity.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends MongoRepository<Comment, String>, CustomCommentRepository {

}
