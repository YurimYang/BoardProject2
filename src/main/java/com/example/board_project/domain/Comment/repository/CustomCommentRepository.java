package com.example.board_project.domain.Comment.repository;

import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.entity.Comment;

import java.util.List;

public interface CustomCommentRepository {

    List<Comment> getCommentsByPostId(String postId);
    void updateCommentById(Comment comment, CommentPatchRequest commentPatchRequest);
    void deleteCommentById(Comment comment);
    void deleteComments(List<Comment> commentList);
}