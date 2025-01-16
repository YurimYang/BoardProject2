package com.example.board_project.domain.Comment.dao;

import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentDAO {
    Comment insertComment(Comment comment);
    Optional<Comment> selectCommentById(String commentId);
    List<Comment> selectCommentsByPostId(String postId);
    void updateComment(Comment comment, CommentPatchRequest commentPatchRequest);
    void deleteComment(Comment comment);
}