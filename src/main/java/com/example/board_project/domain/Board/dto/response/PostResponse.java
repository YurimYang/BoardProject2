package com.example.board_project.domain.Board.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        String id,
        Long seq,
        String title,
        String content,
        String writer,
        LocalDateTime createdAt

) {
}