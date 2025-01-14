package com.example.board_project.domain.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostResponse(
        String id,
        String title,
        String content,
        String writer,
        LocalDateTime createdAt

) {
}