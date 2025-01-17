package com.example.board_project.domain.Comment.service;

import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.dto.request.CommentRequest;
import com.example.board_project.domain.Comment.dto.response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(CommentRequest commentRequest);
    List<CommentResponse> getAllComments(String postId);
    CommentResponse updateComment(String commentId, CommentPatchRequest commentPatchRequest);
    void deleteComment(String commentId);
}