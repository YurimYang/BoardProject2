package com.example.board_project.domain.Comment.dao.impl;

import com.example.board_project.domain.Comment.dao.CommentDAO;
import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentDAOImpl implements CommentDAO {

    private final MongoTemplate mongoTemplate;

    @Override
    public Comment insertComment(Comment comment) {
        return mongoTemplate.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(String commentId) {
        Comment comment = mongoTemplate.findById(commentId, Comment.class);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> getCommentsByPostId(String postId) {
        return mongoTemplate.find(new Query(Criteria.where("postId").is(postId)
                .and("deletedAt").is(null)), Comment.class);
    }

    @Override
    public void updateComment(Comment comment, CommentPatchRequest commentPatchRequest) {
        Query query = new Query(Criteria.where("postId").is(comment.getPostId())
                .and("id").is(comment.getId())
                .and("deletedAt").is(null));
        Update update = new Update()
                .set("content", commentPatchRequest.content())
                .set("updatedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Comment.class);
    }

    @Override
    public void deleteComment(Comment comment) {
        Query query = new Query(Criteria.where("postId").is(comment.getPostId())
                .and("id").is(comment.getId())
                .and("deletedAt").is(null));
        Update update = new Update()
                .set("deletedAt", LocalDateTime.now());
        mongoTemplate.updateFirst(query, update, Comment.class);
    }
}
