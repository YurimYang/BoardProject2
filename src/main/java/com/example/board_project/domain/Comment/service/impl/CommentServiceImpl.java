package com.example.board_project.domain.Comment.service.impl;

import com.example.board_project.domain.Board.dao.BoardDAO;
import com.example.board_project.domain.Board.exception.PostNotFoundException;
import com.example.board_project.domain.Comment.dao.CommentDAO;
import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.dto.request.CommentRequest;
import com.example.board_project.domain.Comment.dto.response.CommentResponse;
import com.example.board_project.domain.Comment.entity.Comment;
import com.example.board_project.domain.Comment.exception.CommentNotFoundException;
import com.example.board_project.domain.Comment.service.CommentService;
import com.example.board_project.domain.Comment.util.CommentMapper;
import com.example.board_project.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentDAO commentDAO;
    private final BoardDAO boardDAO;

    @Override
    @Transactional
    public CommentResponse createComment(CommentRequest commentRequest) {
        validatePostExists(commentRequest.postId());
        Comment comment = CommentMapper.toComment(commentRequest);
        Comment savedComment = commentDAO.insertComment(comment);
        return CommentMapper.toCommentResponse(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComments(String postId) {
        validatePostExists(postId);
        List<Comment> commentList = commentDAO.selectCommentsByPostId(postId);
        if(commentList.isEmpty()){
            throw new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
        }
        return CommentMapper.toCommentListResponse(commentList);
    }

    private void validatePostExists(String postId){
        boardDAO.getBoardById(postId).orElseThrow(()->
                new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
    }

    @Override
    @Transactional
    public CommentResponse updateComment(String commentId, CommentPatchRequest commentPatchRequest) {
        Comment searchedComment = findValidComment(commentId);
        commentDAO.updateComment(searchedComment, commentPatchRequest);
        return CommentMapper.toCommentResponse(findValidComment(commentId));
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        Comment searchedComment = findValidComment(commentId);
        commentDAO.deleteComment(searchedComment);
    }

    private Comment findValidComment(String commentId) {
        return commentDAO.selectCommentById(commentId)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }
}