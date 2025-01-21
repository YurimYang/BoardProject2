package com.example.board_project.domain.Comment.repository.impl;

import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.entity.Comment;
import com.example.board_project.domain.Comment.repository.CustomCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomCommentRepositoryImpl implements CustomCommentRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Comment> getCommentsByPostId(String postId) {
        return mongoTemplate.find(new Query(Criteria.where("postId").is(postId)
                .and("deletedAt").is(null)), Comment.class);
    }

    @Override
    public void updateCommentById(Comment comment, CommentPatchRequest commentPatchRequest) {
        Query query = new Query(Criteria.where("postId").is(comment.getPostId())
                .and("id").is(comment.getId())
                .and("deletedAt").is(null));
        Update update = new Update()
                .set("content", commentPatchRequest.content())
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Comment.class);
    }

    @Override
    public void deleteCommentById(Comment comment) {
        Query query = new Query(Criteria.where("postId").is(comment.getPostId())
                .and("id").is(comment.getId())
                .and("deletedAt").is(null));
        Update update = new Update()
                .set("deletedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Comment.class);
    }

    @Override
    public void deleteComments(List<Comment> commentList){
        for(Comment comment : commentList){
            deleteCommentById(comment);
        }
    }
}