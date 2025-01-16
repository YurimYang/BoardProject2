package com.example.board_project.domain.Comment.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentResponse(
        String id,
        String postId,
        String content,
        String writer,
        LocalDateTime createdAt
) {
}