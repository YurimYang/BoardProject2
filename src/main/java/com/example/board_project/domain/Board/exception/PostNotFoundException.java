package com.example.board_project.domain.Board.exception;

import com.example.board_project.global.exception.ApplicationException;
import com.example.board_project.global.exception.ErrorCode;

public class PostNotFoundException extends ApplicationException {
    public PostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}