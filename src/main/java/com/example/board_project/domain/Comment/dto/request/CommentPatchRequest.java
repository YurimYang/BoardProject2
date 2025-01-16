package com.example.board_project.domain.Comment.dto.request;

import jakarta.validation.constraints.Size;

public record CommentPatchRequest(
        @Size(min = 1, message = "댓글은 1자 이상 입력해주세요.")
        String content
) {
}
