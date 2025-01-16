package com.example.board_project.domain.Board.enums;

public enum BoardSearchEnum {

    TITLE("00"),
    CONTENT("01"),
    WRITER("02");

    String code;
    BoardSearchEnum(String code) {
        this.code = code;
    }
}