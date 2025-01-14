package com.example.board_project.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    private final HttpStatus httpStatus;
    private final String simpleMessage;

    ErrorCode(HttpStatus httpStatus, String simpleMessage) {
        this.httpStatus = httpStatus;
        this.simpleMessage = simpleMessage;
    }
}
