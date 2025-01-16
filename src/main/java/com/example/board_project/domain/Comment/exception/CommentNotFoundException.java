package com.example.board_project.domain.Comment.exception;

import com.example.board_project.global.exception.ApplicationException;
import com.example.board_project.global.exception.ErrorCode;

public class CommentNotFoundException extends ApplicationException {
    public CommentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}