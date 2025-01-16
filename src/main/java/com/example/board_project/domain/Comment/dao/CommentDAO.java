package com.example.board_project.domain.Comment.dao;

import com.example.board_project.domain.Comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentDAO {

    private final MongoTemplate mongoTemplate;

    public Comment insertComment(Comment comment) {
        return mongoTemplate.save(comment);
    }

    public List<Comment> selectCommentsByPostId(String postId) {
        return mongoTemplate.find(new Query(Criteria.where("postId").is(postId)), Comment.class);
    }
}