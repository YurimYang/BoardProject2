package com.example.board_project.domain.Comment.service;

import com.example.board_project.domain.Comment.dao.CommentDAO;
import com.example.board_project.domain.Comment.dto.request.CommentRequest;
import com.example.board_project.domain.Comment.dto.response.CommentResponse;
import com.example.board_project.domain.Comment.util.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDAO commentDAO;

    @Transactional
    public CommentResponse createComment(CommentRequest commentRequest) {
        return CommentMapper.toCommentResponse(commentDAO.insertComment(CommentMapper.toComment(commentRequest)));
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComments(String commentId) {
        return CommentMapper.toCommentListResponse(commentDAO.selectCommentsByPostId(commentId));
    }
}