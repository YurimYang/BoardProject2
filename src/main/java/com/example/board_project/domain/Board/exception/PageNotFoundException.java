package com.example.board_project.domain.Board.exception;

import com.example.board_project.global.exception.ApplicationException;
import com.example.board_project.global.exception.ErrorCode;

public class PageNotFoundException extends ApplicationException {
    public PageNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}