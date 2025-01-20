package com.example.board_project.domain.Comment.dao;

import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {
    Comment insertComment(Comment comment);
    Optional<Comment> getCommentById(String commentId);
    List<Comment> getCommentsByPostId(String postId);
    void updateComment(Comment comment, CommentPatchRequest commentPatchRequest);
    void deleteComment(Comment comment);
}