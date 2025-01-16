package com.example.board_project.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다."),
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "페이지가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String simpleMessage;

    ErrorCode(HttpStatus httpStatus, String simpleMessage) {
        this.httpStatus = httpStatus;
        this.simpleMessage = simpleMessage;
    }
}
