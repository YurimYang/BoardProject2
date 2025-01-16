package com.example.board_project.domain.Comment.service;

import com.example.board_project.domain.Board.dao.BoardDAO;
import com.example.board_project.domain.Board.exception.PostNotFoundException;
import com.example.board_project.domain.Board.service.impl.BoardServiceImpl;
import com.example.board_project.domain.Comment.dao.CommentDAO;
import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.dto.request.CommentRequest;
import com.example.board_project.domain.Comment.dto.response.CommentResponse;
import com.example.board_project.domain.Comment.entity.Comment;
import com.example.board_project.domain.Comment.exception.CommentNotFoundException;
import com.example.board_project.domain.Comment.util.CommentMapper;
import com.example.board_project.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDAO commentDAO;
    private final BoardDAO boardDAO;
    private final BoardServiceImpl boardService;

    @Transactional
    public CommentResponse createComment(CommentRequest commentRequest) {
        boardDAO.selectBoardById(commentRequest.postId()).orElseThrow(
                () -> new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        return CommentMapper.toCommentResponse(commentDAO.insertComment(CommentMapper.toComment(commentRequest)));
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComments(String postId) {
        List<Comment> commentList = commentDAO.selectCommentsByPostId(postId);
        if(commentList.isEmpty()){
            throw new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
        }
        return CommentMapper.toCommentListResponse(commentList);
    }

    @Transactional
    public void updateComment(String commentId, CommentPatchRequest commentPatchRequest) {
        Comment searchedComment = getCommentById(commentId);
        commentDAO.updateComment(searchedComment, commentPatchRequest);
    }

    @Transactional
    public void deleteComment(String commentId) {
        Comment searchedComment = getCommentById(commentId);
        commentDAO.deleteComment(searchedComment);
    }

    private Comment getCommentById(String commentId) {
        return commentDAO.selectCommentById(commentId)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }
}