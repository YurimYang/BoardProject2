package com.example.board_project.domain.Comment.service.impl;


import com.example.board_project.domain.Board.entity.Board;
import com.example.board_project.domain.Board.exception.PostNotFoundException;
import com.example.board_project.domain.Board.repository.BoardRepository;
import com.example.board_project.domain.Comment.dto.request.CommentPatchRequest;
import com.example.board_project.domain.Comment.dto.request.CommentRequest;
import com.example.board_project.domain.Comment.dto.response.CommentResponse;
import com.example.board_project.domain.Comment.entity.Comment;
import com.example.board_project.domain.Comment.exception.CommentNotFoundException;
import com.example.board_project.domain.Comment.repository.CommentRepository;
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

    //private final BoardDAO boardDAO;
    //private final CommentDAO commentDAO;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    @Override
    @Transactional
    public CommentResponse createComment(CommentRequest commentRequest) {
        validatePostExists(commentRequest.postId());
        Comment comment = CommentMapper.toComment(commentRequest);
        //Comment savedComment = commentDAO.insertComment(comment);
        Comment savedComment = commentRepository.save(comment);
        return CommentMapper.toCommentResponse(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponse> getAllComments(String postId) {
        validatePostExists(postId);
        //List<Comment> commentList = commentDAO.getCommentsByPostId(postId);

        List<Comment> commentList = commentRepository.getCommentsByPostId(postId);
        if(commentList.isEmpty()){
            throw new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND);
        }
        return CommentMapper.toCommentListResponse(commentList);
    }

    private void validatePostExists(String postId){
        //boardDAO.getBoardById(postId).orElseThrow(()->
                //new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        Board board = boardRepository.findById(postId).orElseThrow(()->
                new PostNotFoundException(ErrorCode.POST_NOT_FOUND));
        if(board.isDeleted()){
            List<Comment> commentList = commentRepository.getCommentsByPostId(postId);
            commentRepository.deleteComments(commentList);
            throw new PostNotFoundException(ErrorCode.POST_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public CommentResponse updateComment(String commentId, CommentPatchRequest commentPatchRequest) {
        Comment searchedComment = findValidComment(commentId);
        validatePostExists(searchedComment.getPostId());
        //commentDAO.updateComment(searchedComment, commentPatchRequest);
        commentRepository.updateCommentById(searchedComment, commentPatchRequest);
        return CommentMapper.toCommentResponse(findValidComment(commentId));
    }

    @Override
    @Transactional
    public void deleteComment(String commentId) {
        Comment searchedComment = findValidComment(commentId);
        validatePostExists(searchedComment.getPostId());
        //commentDAO.deleteComment(searchedComment);
        commentRepository.deleteCommentById(searchedComment);
    }

    private Comment findValidComment(String commentId) {
//        return commentDAO.getCommentById(commentId)
//                .filter(c -> !c.isDeleted())
//                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
        return commentRepository.findById(commentId)
                .filter(c -> !c.isDeleted())
                .orElseThrow(() -> new CommentNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
    }
}